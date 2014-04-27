package dylanandapril.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import dylanandapril.database.DBConnection;
import dylanandapril.database.Product;
import dylanandapril.database.User;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
    }

	/**
	 * Returns all products in the database as JSON
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User u = (User) request.getSession(true).getAttribute("user"); 
		if(u == null) {
			// Don't redirect, this is an AJAX servlet. Just don't send JSON data.
			return;
		}
		
		JsonFactory jf = new JsonFactory();
        JsonGenerator jg = jf.createGenerator(response.getOutputStream());
		
		Connection conn = null;
		Statement statement = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();

            // Create the prepared statement and use it to
            // INSERT student values INTO the students table.
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT p.*, c.name AS categoryname FROM product AS p JOIN category AS c ON (p.category = c.id)");
            
            jg.writeStartObject();
	        	jg.writeArrayFieldStart("products");
	        	while(rs.next()) {
	        		jg.writeStartObject();
		        		jg.writeNumberField("id", rs.getLong("id"));
		        		jg.writeStringField("name", rs.getString("name"));
		        		jg.writeStringField("category", rs.getString("categoryname"));
		        		jg.writeStringField("sku", rs.getString("sku"));
		        		jg.writeNumberField("price", rs.getDouble("price"));
	        		jg.writeEndObject();
	            }
	        	jg.writeEndArray();
	        jg.writeEndObject();

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
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) { } // Ignore
                statement = null;
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { } // Ignore
                conn = null;
            }
        }
        
        jg.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User u = (User) request.getSession(true).getAttribute("user"); 
		if(u == null) {
			// Don't redirect, this is an AJAX servlet. Just don't send JSON data.
			return;
		}
		
		String action = request.getParameter("action");
		String idStr = request.getParameter("id");
		String name = request.getParameter("name");
		String categoryIdStr = request.getParameter("categoryId");
		String sku = request.getParameter("sku");
		String priceStr = request.getParameter("price");
		
		long id = -1;
		long categoryId = -1;
		double price = -1;
		try {
			if(idStr != null) id = Long.parseLong(idStr);
			if(categoryIdStr != null) categoryId = Long.parseLong(categoryIdStr);
			if(priceStr != null) price = Double.parseDouble(priceStr);
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		JsonFactory jf = new JsonFactory();
        JsonGenerator jg = jf.createGenerator(response.getOutputStream());
		
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        	conn = DBConnection.getConnection();
    		
		
			if(action == null) {
				
			} else if(action.equals("UPDATE")) {
				// Begin transaction
	            conn.setAutoCommit(false);
	
	            // Create the prepared statement and use it to
	            // UPDATE student values in the Category table.
	            pstmt = conn
	                .prepareStatement("UPDATE product SET name = ?, category = ?, sku = ?, price = ? WHERE id = ?");
	
	            pstmt.setString(1, name);
	            pstmt.setLong(2, categoryId);
	            pstmt.setString(3, sku);
	            pstmt.setDouble(4, price);
	            pstmt.setLong(5, id);
	            int rowCount = pstmt.executeUpdate();
	
	            // Commit transaction
	            conn.commit();
	            conn.setAutoCommit(true);
			} else if(action.equals("DELETE")) {
				// Begin transaction
                conn.setAutoCommit(false);

                // Create the prepared statement and use it to
                // DELETE students FROM the Students table.
                pstmt = conn
                    .prepareStatement("DELETE FROM product WHERE id = ?");

                pstmt.setLong(1, id);
                int rowCount = pstmt.executeUpdate();

                // Commit transaction
                conn.commit();
                conn.setAutoCommit(true);
			} else if(action.equals("INSERT")) {
				// Begin transaction
                conn.setAutoCommit(false);
                
                // Create the prepared statement and use it to
                // INSERT category values INTO the category table.
                pstmt = conn
                .prepareStatement("INSERT INTO product (name, category, sku, price) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, name);
                pstmt.setLong(2, categoryId);
                pstmt.setString(3, sku);
                pstmt.setDouble(4, price);
                pstmt.executeUpdate();
                
                rs = pstmt.getGeneratedKeys();
                if(!rs.next()) id = -1;
                else id = rs.getLong(1);

                // Commit transaction
                conn.commit();
                conn.setAutoCommit(true);
			}
        } catch (SQLException e) {
        	
        } finally {
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
        jg.writeStartObject();
    	jg.writeNumberField("id", id);
    	jg.writeEndObject();
    	jg.close();
	}

}
