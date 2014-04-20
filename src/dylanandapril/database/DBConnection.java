package dylanandapril.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnection {
	
	/**
	 * Connects to cse135 database.
	 * @return Returns a Connection to the database.
	 */
	public static Connection getConnection() {
		try{
			// Registering Postgresql JDBC driver with the DriverManager
			Class.forName("org.postgresql.Driver");
			
			// Open a connection to the database using DriverManager, and return the connection
			return DriverManager.getConnection(
	                "jdbc:postgresql://localhost/cse135?" +
	                "user=postgres&password=postgres");
		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			
		}
		
	}
	
}