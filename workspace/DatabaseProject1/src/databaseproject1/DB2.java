package databaseproject1;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class DB2 extends JFrame {

	private JPanel 		contentPane;
	private JTextField 	input;
	private JTextField 	ageInput;
	private JButton 	btnInsert;
	private JTable 		table;
	private JScrollPane scrollPane;
	private JButton 	btnRefresh;
	Connection con;
	
	/******************************************************************************************
	 * launch the application.
	 ******************************************************************************************/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new DB2().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/******************************************************************************************
	 * Create the frame.
	 ******************************************************************************************/
	public DB2() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 createConnection ();
		
		input = new JTextField();
		input.setBounds(30, 44, 150, 42);
		contentPane.add(input);
		input.setColumns(10);
		
		ageInput = new JTextField();
		ageInput.setBounds(192, 44, 130, 42);
		contentPane.add(ageInput);
		ageInput.setColumns(10);
		
		btnInsert = new JButton("Insert");
		btnInsert.setBounds(334, 52, 86, 29);
		contentPane.add(btnInsert);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 164, 390, 254);
		contentPane.add(scrollPane);
		
		String[] header = {"Name", "Age"};
		DefaultTableModel model = new DefaultTableModel (header,0);
		table = new JTable (model);
		scrollPane.setViewportView(table);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(174, 98, 86, 29);
		contentPane.add(btnRefresh);
		
		
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String 	name 	= input.getText();
					int 	age 	= Integer.parseInt(ageInput.getText());
					
					PreparedStatement stmt = con.prepareStatement ("INSERT INTO USERS2 VALUES (?,?)");
					stmt.setString(1, name);
					stmt.setInt(2, age);
					
					stmt.execute();
					System.out.println ("Insertion Completed");
					stmt.close();
					
				} catch (SQLException ex) {
					Logger.getLogger(DB2.class.getName()).log(Level.SEVERE, null, ex);
					
				}
			
			}
		});
		
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				
				try {
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM USERS2");
					
					while (rs.next()) {
						String 	name 	= rs.getString("name");
						int 	age 	= rs.getInt("age");
						tableModel.addRow(new Object[] {name, age});
						
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		
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
			Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);
			
		} catch (SQLException ex) {
			Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);			
			
		}
	}
	
}
