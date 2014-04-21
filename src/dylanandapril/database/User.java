package dylanandapril.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class User {
	
	private int id = -1;
	private String username;
	private String role;
	private int age;
	private String state;

    /**
     * Constructor that does not require id 
     */
    public User(String username, String role, int age, String state) {
    	this.username = username;
    	this.role = role;
    	this.age = age;
    	this.state = state;
    }
    
    public User(int id, String username, String role, int age, String state) {
    	this(username, role, age, state);
    	this.id = id;
    }
	
    public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

	public int getAge() {
		return age;
	}

	public String getState() {
		return state;
	}

	/**
     * @param username
     * @return Returns User attached to the parameter username, or null if it does not exist.
     */
    public static User findUserByName(String username) {
    	User u = null;
    	
    	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();

            // Create the prepared statement and use it to
            // INSERT student values INTO the students table.
            pstmt = conn.prepareStatement("SELECT * FROM account WHERE username = ?");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            
            if(!rs.next()) u = null;
            else u = new User(rs.getInt(1), rs.getString("username"), rs.getString("role"), rs.getInt("age"), rs.getString("state"));

            // Close the Connection
            conn.close();
        } catch (SQLException e) {

            // Wrap the SQL exception in a runtime exception to propagate
            // it upwards
            throw new RuntimeException(e);
        }
        finally {
            // Release resources in a finally block in reverse-order of
            // their creation

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { } // Ignore
                rs = null;
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) { } // Ignore
                pstmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { } // Ignore
                conn = null;
            }
        }
        
    	return u;
    }
    
	/**
	 * Attempts to insert user into the database.
	 * Returns the primary key id of the inserted row. 
	 * If there is an error, -1 is returned.
	 */
	public int insertIntoDatabase() {
		int id = -1;
		
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();

            // Begin transaction
            conn.setAutoCommit(false);

            // Create the prepared statement and use it to
            // INSERT student values INTO the students table.
            pstmt = conn
            .prepareStatement("INSERT INTO account (username, role, age, state) VALUES (?, ?, ?, ?)", 
            		Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, this.username);
            pstmt.setString(2, this.role);
            pstmt.setInt(3, this.age);
            pstmt.setString(4, this.state);
            pstmt.executeUpdate();
            
            rs = pstmt.getGeneratedKeys();
            if(!rs.next()) return -1;
            id = rs.getInt(1);

            // Commit transaction
            conn.commit();
            conn.setAutoCommit(true);

            // Close the Connection
            conn.close();
        } catch (SQLException e) { 
        	e.printStackTrace();
        }
        finally {
            // Release resources in a finally block in reverse-order of
            // their creation

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { } // Ignore
                rs = null;
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) { } // Ignore
                pstmt = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { } // Ignore
                conn = null;
            }
        }
		
		return id;
	}
	
	public boolean updateUser(int id, User user) {
		
		return true;
	}

}
