package test;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Register extends JFrame {
	
	public static final long serialVersionUID = 2L;
	
	private JPanel contentPane;
	Connection con;
	private JTextField usernameInput;
	private JTextField emailInput;
	private JPasswordField passwordInput;
	private JPasswordField confirmPasswordInput;
	private JButton btnRegister;
	
	/******************************************************************************************
	 * Launch the application.
	 ******************************************************************************************/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
					frame.setTitle("Register");
					
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}
		});
		
	}
	
	/******************************************************************************************
	 * Create the frame.
	 ******************************************************************************************/
	public Register() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		createConnection();
		
		JLabel userNameLabel = new JLabel("Username");
		userNameLabel.setBounds(30, 40, 120, 40);
		contentPane.add(userNameLabel);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(30, 120, 120, 40);
		contentPane.add(emailLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(30, 200, 120, 40);
		contentPane.add(passwordLabel);
		
		JLabel confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setBounds(30, 280, 120, 40);
		contentPane.add(confirmPasswordLabel);
		
		usernameInput = new JTextField();
		usernameInput.setBounds(170, 40, 290, 40);
		contentPane.add(usernameInput);
		usernameInput.setColumns(10);
		
		emailInput = new JTextField();
		emailInput.setBounds(170, 120, 290, 40);
		contentPane.add(emailInput);
		emailInput.setColumns(10);
		
		passwordInput = new JPasswordField();
		passwordInput.setBounds(170, 200, 290, 40);
		contentPane.add(passwordInput);
		passwordInput.setColumns(10);
		
		confirmPasswordInput = new JPasswordField();
		confirmPasswordInput.setBounds(170, 280, 290, 40);
		contentPane.add(confirmPasswordInput);
		confirmPasswordInput.setColumns(10);
		
		JLabel errorMessage = new JLabel("");
		errorMessage.setBounds(170, 320, 200, 40);
		contentPane.add(errorMessage);
		
		btnRegister = new JButton("Register");
		btnRegister.setBounds(200, 375, 120, 30);
		contentPane.add(btnRegister);
		btnRegister.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String username 		= usernameInput.getText();
					String email 			= emailInput.getText();
					String password 		= new String (passwordInput.getPassword());
					String confirmPassword 	= new String (confirmPasswordInput.getPassword());
					
					if (checkPassword (password, confirmPassword)) {
						
						PreparedStatement stmt = con.prepareStatement ("INSERT INTO AUTHENTICATION VALUES (?, ?, ?)");
						stmt.setString(1, username);
						stmt.setString(2, email);
						stmt.setString(3, password);
						
						stmt.execute();
						System.out.println ("Insertion Completed");
						stmt.close();
						
					} else {
						
						errorMessage.setText("Passwords don't match");
						
					}
					
				} catch (SQLException ex) {
					Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
					
				}
				
			}
		});
		
	}
	
	private boolean checkPassword (String p1, String p2) {
		if (p1.equals(p2)) {
			return true;
			
		} else {
			return false;
			
		}
		
	}
	
	/******************************************************************************************
	 * load the SQL driver to the program.
	 ******************************************************************************************/
	void createConnection () {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "982026962");
			System.out.println ("Database Connection Success");
			
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
			
		} catch (SQLException ex) {
			Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);			
			
		}
	}
}
