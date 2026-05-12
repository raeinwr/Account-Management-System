package wong_gonzales;

import java.io.*;
import javax.swing.*;

public class Login extends JFrame{
	
	Login(){
		
	setTitle("Login");
	setSize(400, 300);
	setLayout(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	
	
	JLabel lblUser = new JLabel("Username");
	lblUser.setBounds(50,50,100,30);
	add(lblUser);
	
	JTextField txtUser = new JTextField();
	txtUser.setBounds(150,50,180,30);
	add(txtUser);
	
	JLabel lblPass = new JLabel("Password");
	lblPass.setBounds(50,100,100,30);
	add(lblPass);
	
	JPasswordField txtPass = new JPasswordField();
	txtPass.setBounds(150, 100, 180,30);
	add(txtPass);
	
	JButton btnLog = new JButton("Login");
	btnLog.setBounds(40, 160, 90, 30);
	add(btnLog);
	
	JButton btnReg= new JButton("Register");
	btnReg.setBounds(145, 160, 90, 30);
	add(btnReg);
	
	JButton btnExit= new JButton("Exit");
	btnExit.setBounds(250, 160, 90, 30);
	add(btnExit);
	
	
	btnLog.addActionListener(e ->{
		
		String user = txtUser.getText();
		String pass = String.valueOf(txtPass.getPassword());
				
		boolean found = false;
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("users.txt"));
			
			String line;
			while((line = br.readLine()) != null) {
				
				String [] data = line.split(",");
				if(data[0].equals(user) && 
					data[1].equals(pass)) {
					
					found = true;
			JOptionPane.showMessageDialog(null, "Login Successful!");
				
				
			dispose();
				}
			}
			
			br.close();
			if(found == false) {
				JOptionPane.showMessageDialog(null,
						"<html>Invalid Username or Password.<br>"
						+ "Don't have an account yet? Click "
						+ "<font color='blue'>Register</font>"
						+ " to create.</html>");
			}
			
			} catch (IOException x) {
				JOptionPane.showMessageDialog(null, "ERROR");
			}
		});
		
	btnReg.addActionListener(e -> {
		new Register();
		dispose();
	});
	
	btnExit.addActionListener(e ->{
		System.exit(0);
	});

	
	
	setVisible(true);
	}

	public static void main(String[]args) {
		new Login();
		
	}

}
