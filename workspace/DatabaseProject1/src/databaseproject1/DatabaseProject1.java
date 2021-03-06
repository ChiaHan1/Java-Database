package databaseproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProject1 {
	
	Connection con;
	
	public static void main(String[] args) {
		DatabaseProject1 pro = new DatabaseProject1();
		pro.createConnection();
		pro.createTable();
	}
	
	void createTable () {
		
		try {
			String q = "CREATE TABLE DB1 (" 
					+ "name varchar(100), " 
					+ "age int, " 
					+ "salary float" 
					+ ");";
			Statement stmt = con.createStatement();
			stmt.execute (q);
			System.out.println ("Sucessfully Created");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	/******************************************************************************************
	 * load the SQL driver to the program.
	 ******************************************************************************************/
	void createConnection () {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "982026962");
			
			//get data from the database
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS2");
			
			while (rs.next()) {
				String 	name 	= rs.getString("name");
				int 	age 	= rs.getInt("age");
				System.out.println (name + " age = " + age);
				
			}
			
			System.out.println ("Database Connection Success");
			
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);
			
		} catch (SQLException ex) {
			Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);			
			
		}
	}

}
