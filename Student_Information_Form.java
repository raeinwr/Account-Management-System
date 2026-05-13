package wong;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Student_Information_Form extends JFrame {

	private DefaultTableModel model;

	public Student_Information_Form() {

		setTitle("Student Information Form");
		setSize(445, 405);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(20, 20, 100, 30);
		add(lblName);

		JTextField txtName = new JTextField();
		txtName.setBounds(20, 50, 120, 20);
		add(txtName);

		JLabel lblCourse = new JLabel("Course");
		lblCourse.setBounds(150, 20, 100, 30);
		add(lblCourse);

		JTextField txtCourse = new JTextField();
		txtCourse.setBounds(150, 50, 120, 20);
		add(txtCourse);

		JLabel lblSection = new JLabel("Section");
		lblSection.setBounds(280, 20, 100, 30);
		add(lblSection);

		JTextField txtSection = new JTextField();
		txtSection.setBounds(280, 50, 120, 20);
		add(txtSection);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(30, 80, 80, 25);
		add(btnAdd);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(120, 80, 80, 25);
		add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(210, 80, 80, 25);
		add(btnDelete);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(300, 80, 80, 25);
		add(btnClear);

		String[] columns = { "Name", "Course", "Section" };

		model = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable table = new JTable(model);

		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(10, 120, 410, 265);
		add(pane);

		btnAdd.addActionListener(e -> {
			String name = txtName.getText();
			String course = txtCourse.getText();
			String section = txtSection.getText();

			if (name.isEmpty() || course.isEmpty() || section.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please fill in all fields.");
				return;
			}

			for (int i = 0; i < model.getRowCount(); i++) {
				String existingName = model.getValueAt(i, 0).toString();
				String existingCourse = model.getValueAt(i, 1).toString();
				String existingSection = model.getValueAt(i, 2).toString();

				if (existingName.equalsIgnoreCase(name) && existingCourse.equalsIgnoreCase(course)
						&& existingSection.equalsIgnoreCase(section)) {

					JOptionPane.showMessageDialog(null, "This record already exists.", "Warning",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
			}

			model.addRow(new Object[] { name, course, section });
			saveAllToFile();

			txtName.setText("");
			txtCourse.setText("");
			txtSection.setText("");
		});

		btnUpdate.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "Please select a row to update.");
				return;
			}

			String name = txtName.getText();
			String course = txtCourse.getText();
			String section = txtSection.getText();

			if (name.isEmpty() || course.isEmpty() || section.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please fill in all fields.");
				return;
			}

			for (int i = 0; i < model.getRowCount(); i++) {
				if (i != row) {
					String existingName = model.getValueAt(i, 0).toString();
					String existingCourse = model.getValueAt(i, 1).toString();
					String existingSection = model.getValueAt(i, 2).toString();

					if (existingName.equalsIgnoreCase(name) && existingCourse.equalsIgnoreCase(course)
							&& existingSection.equalsIgnoreCase(section)) {

						JOptionPane.showMessageDialog(null, "This record already exists.", "Warning",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			}

			model.setValueAt(name, row, 0);
			model.setValueAt(course, row, 1);
			model.setValueAt(section, row, 2);

			saveAllToFile();
			JOptionPane.showMessageDialog(null, "Record updated successfully.");

			txtName.setText("");
			txtCourse.setText("");
			txtSection.setText("");
		});

		btnDelete.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "Please select a row to delete.");
				return;
			}

			String name = model.getValueAt(row, 0).toString();
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete \"" + name + "\"?",
					"Confirm Delete", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				model.removeRow(row);
				saveAllToFile();
				JOptionPane.showMessageDialog(null, "Record deleted successfully.");
			}
		});

		btnClear.addActionListener(e -> {
			txtName.setText("");
			txtCourse.setText("");
			txtSection.setText("");
		});

		try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 3) {
					model.addRow(new Object[] { data[0], data[1], data[2] });
				}
			}
		} catch (IOException ex) {
			System.out.println("No existing records found or error reading file.");
		}

		setVisible(true);
	}

	private void saveAllToFile() {
		try (FileWriter fw = new FileWriter("students.txt")) {
			for (int i = 0; i < model.getRowCount(); i++) {
				fw.write(model.getValueAt(i, 0) + "," + model.getValueAt(i, 1) + "," + model.getValueAt(i, 2) + "\n");
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
		}
	}

	public static void main(String[] args) {
		new Student_Information_Form();
	}
}