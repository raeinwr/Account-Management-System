package wong_gonzales;
import javax.swing.*;
import java.io.*;

public class Register extends JFrame{

	Register(){
		
		setTitle("Register");
		setSize(400, 300);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JLabel lblName = new JLabel("Full Name");
		lblName.setBounds(50, 50, 100, 30);
		add(lblName);
		
		JTextField txtName = new JTextField();
		txtName.setBounds(150, 50, 180, 30);
		add(txtName);
		
		JLabel lblUser = new JLabel("Username");
		lblUser.setBounds(50, 90, 100, 30);
		add(lblUser);
		
		JTextField txtUser = new JTextField();
		txtUser.setBounds(150, 90, 180, 30);
		add(txtUser);
		
		JLabel lblPass = new JLabel("Password");
		lblPass.setBounds(50, 130, 100, 30);
		add(lblPass);
		
		JTextField txtPass = new JTextField();
		txtPass.setBounds(150, 130, 180, 30);
		add(txtPass);
		
		JButton btnReg = new JButton("Register");
		btnReg.setBounds(40, 180, 100, 30);
		add(btnReg);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(150, 180, 100, 30);
		add(btnClear);
		
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(260, 180, 100, 30);
		add(btnBack);
		
		btnReg.addActionListener(e ->{
			
			String name = txtName.getText();
			String user = txtUser.getText();
			String pass = txtPass.getText();
			
			if (name.isEmpty() || user.isEmpty() || pass.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please fill in all fields.");
				return;
			}
			
			try {
				FileWriter fw = new FileWriter("users.txt", true);
				
				fw.write(user + "," + pass + "," + name + "\n");
				fw.close();
				
				JOptionPane.showMessageDialog(null, "Registered Successfully!");
				
			} catch( IOException x) {
				JOptionPane.showMessageDialog(null, "ERROR");
			}
			
		});
		
		btnClear.addActionListener(e -> {
			
			txtName.setText("");
			txtUser.setText("");
			txtPass.setText("");
			
		});
		
		btnBack.addActionListener(e -> {
			new Login();
			dispose();
			
		});
		
		
		
		
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Register();
		
	}

}
