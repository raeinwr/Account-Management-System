package wong;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
 

public class accountSystem extends JFrame {

    private JTextField tfUsername, tfPassword, tfFullname, tfSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private final String FILE_NAME = "users.txt";
 
    public accountSystem() {
        setTitle("Account Management System");
        setSize(750, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 248));
 
        add(buildHeader(),  BorderLayout.NORTH);
        add(buildCenter(),  BorderLayout.CENTER);
        add(buildForm(),    BorderLayout.EAST);
 
        loadFromFile();
        setVisible(true);
    }
 private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));
 
        JLabel title = new JLabel("Account Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
 
        tfSearch = new JTextField(16);
        tfSearch.putClientProperty("JTextField.placeholderText", "Search...");
        tfSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { filterTable(); }
        });
 
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(new JLabel("  Search: ") {{ setForeground(Color.WHITE); }});
        right.add(tfSearch);
 
        panel.add(title, BorderLayout.WEST);
        panel.add(right,  BorderLayout.EAST);
        return panel;
    }
 
    private JPanel buildCenter() {
        String[] cols = {"Username", "Password", "Full Name"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(174, 214, 241));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
        table.setFillsViewportHeight(true);
 
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) populateFields();
        });
 
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
 
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 5));
        panel.setOpaque(false);
        panel.add(scroll);
        return panel;
    }
 
    private JPanel buildForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 18, 20, 18)
        ));
        panel.setPreferredSize(new Dimension(220, 0));
 
        JLabel formTitle = new JLabel("User Details");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formTitle.setForeground(new Color(52, 73, 94));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        tfUsername = makeField();
        tfPassword = makeField();
        tfFullname = makeField();
 
        panel.add(formTitle);
        panel.add(Box.createVerticalStrut(16));
        panel.add(makeLabel("Username"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(tfUsername);
        panel.add(Box.createVerticalStrut(10));
        panel.add(makeLabel("Password"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(tfPassword);
        panel.add(Box.createVerticalStrut(10));
        panel.add(makeLabel("Full Name"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(tfFullname);
        panel.add(Box.createVerticalStrut(20));
        panel.add(makeButton("Add",    new Color(39, 174, 96),  e -> addUser()));
        panel.add(Box.createVerticalStrut(6));
        panel.add(makeButton("Load",   new Color(52, 152, 219), e -> loadFromFile()));
        panel.add(Box.createVerticalStrut(6));
        panel.add(makeButton("Update", new Color(243, 156, 18), e -> updateUser()));
        panel.add(Box.createVerticalStrut(6));
        panel.add(makeButton("Delete", new Color(231, 76,  60), e -> deleteUser()));
        panel.add(Box.createVerticalStrut(16));
        panel.add(makeDivider());
        panel.add(Box.createVerticalStrut(16));
        panel.add(makeButton("Logout", new Color(127, 140, 141), e -> logout()));
 
        return panel;
    }
 
    private JTextField makeField() {
        JTextField f = new JTextField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        return f;
    }
 
    private JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(new Color(100, 100, 100));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }
 
    private JButton makeButton(String text, Color bg, ActionListener al) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("WG UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(al);
        return btn;
    }
 
    private JSeparator makeDivider() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(220, 220, 220));
        return sep;
    }
 
    private void addUser() {
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();
        String fullname = tfFullname.getText().trim();
 
        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
            warn("Please fill in all fields."); return;
        }
 
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).toString().equalsIgnoreCase(username)) {
                warn("Username already exists."); return;
            }
        }
 
        tableModel.addRow(new Object[]{username, password, fullname});
        saveToFile();
        clearFields();
        info("User added successfully.");
    }
 
    private void updateUser() {
        int row = table.getSelectedRow();
        if (row < 0) { warn("Please select a row to update."); return; }
 
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();
        String fullname = tfFullname.getText().trim();
 
        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
            warn("Please fill in all fields."); return;
        }
 
        tableModel.setValueAt(username, row, 0);
        tableModel.setValueAt(password, row, 1);
        tableModel.setValueAt(fullname, row, 2);
        saveToFile();
        clearFields();
        info("User updated successfully.");
    }
 
    private void deleteUser() {
        int row = table.getSelectedRow();
        if (row < 0) { warn("Please select a row to delete."); return; }
 
        String name = tableModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete user \"" + name + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
 
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
            saveToFile();
            clearFields();
            info("User deleted.");
        }
    }
 
    private void loadFromFile() {
        tableModel.setRowCount(0);
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
 
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    tableModel.addRow(new Object[]{parts[0].trim(), parts[1].trim(), parts[2].trim()});
                }
            }
        } catch (IOException ex) {
            warn("Error loading file.");
        }
    }
 
    private void saveToFile() {
        try (FileWriter fw = new FileWriter(FILE_NAME, false)) {
            fw.write("username,password,fullname\n");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                fw.write(tableModel.getValueAt(i, 0) + ","
                       + tableModel.getValueAt(i, 1) + ","
                       + tableModel.getValueAt(i, 2) + "\n");
            }
        } catch (IOException ex) {
            warn("Error saving file.");
        }
    }
 
    private void populateFields() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            tfUsername.setText(tableModel.getValueAt(row, 0).toString());
            tfPassword.setText(tableModel.getValueAt(row, 1).toString());
            tfFullname.setText(tableModel.getValueAt(row, 2).toString());
        }
    }
 
    private void filterTable() {
        String query = tfSearch.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
 
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
 
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", 3);
                if (parts.length == 3 && (
                    parts[0].toLowerCase().contains(query) ||
                    parts[2].toLowerCase().contains(query))) {
                    tableModel.addRow(new Object[]{parts[0].trim(), parts[1].trim(), parts[2].trim()});
                }
            }
        } catch (IOException ex) {
            warn("Error reading file.");
        }
    }
 
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) System.exit(0);
    }
 
    private void clearFields() {
        tfUsername.setText("");
        tfPassword.setText("");
        tfFullname.setText("");
        table.clearSelection();
    }
 
    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }
 
    private void info(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(accountSystem::new);
    }
}