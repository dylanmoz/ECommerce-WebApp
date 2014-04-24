package dylanandapril.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import dylanandapril.database.DBConnection;
import dylanandapril.database.User;

/**
 * Servlet implementation class ShoppingServlet
 */
@WebServlet("/ShoppingServlet")
public class ShoppingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Retrieves all items in a user's shopping cart.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO : redirect if user is null.
		
		User u = (User)request.getSession(true).getAttribute("user");
		long userId = u.getId();
		
		JsonFactory jf = new JsonFactory();
        JsonGenerator jg = jf.createGenerator(response.getOutputStream());
		
		Connection conn = null;
		PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();

            // Create the prepared statement and use it to
            // INSERT student values INTO the students table.
            pstmt = conn.prepareStatement("SELECT s.id AS cartItemId, s.quantity AS quantity, p.id AS productId, "
            		+ "p.name AS productName, p.price AS productPrice FROM shoppingcart AS s "
            		+ "JOIN product AS p ON s.product = p.id "
            		+ "WHERE s.account = ?");
            pstmt.setLong(1, userId);
            
            rs = pstmt.executeQuery();
            
            jg.writeStartObject();
	        	jg.writeArrayFieldStart("items");
	        	while(rs.next()) {
	        		jg.writeStartObject();
	        			jg.writeNumberField("id", rs.getLong("cartItemId"));
	        			jg.writeNumberField("quantity", rs.getInt("quantity"));
	        			jg.writeObjectFieldStart("product");
			        		jg.writeNumberField("id", rs.getLong("productId"));
			        		jg.writeStringField("name", rs.getString("productName"));
			        		jg.writeNumberField("price", rs.getDouble("productPrice"));
		        		jg.writeEndObject();
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
        
        jg.close();
	}

	/**
	 * Either saves a shopping cart, adds new products (with default quantity of 1 unit), or deletes the shopping cart (for purchasing)
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ShoppingServlet doPost");
		
		String action = request.getParameter("action");
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		String productIdStr = request.getParameter("productId");
		
		User u = (User)request.getSession(true).getAttribute("user");
		long userId = u.getId();
		
		long id = -1;
		int quantity = -1;
		long productId = -1;
		try {
			if(idStr != null) id = Long.parseLong(idStr);
			if(quantityStr != null) quantity = Integer.parseInt(quantityStr);
			if(productIdStr != null) productId = Long.parseLong(productIdStr);
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
	                .prepareStatement("UPDATE shoppingcart SET quantity = ? WHERE id = ?");
	
	            pstmt.setInt(1, quantity);
	            pstmt.setLong(2, id);
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
                    .prepareStatement("DELETE FROM shoppingcart WHERE id = ?");

                pstmt.setLong(1, id);
                int rowCount = pstmt.executeUpdate();

                // Commit transaction
                conn.commit();
                conn.setAutoCommit(true);
			} else if(action.equals("INSERT")) {
				System.out.println("Insert new shopping cart item");
				
				// Begin transaction
                conn.setAutoCommit(false);
                
                // Create the prepared statement and use it to
                // INSERT category values INTO the category table.
                pstmt = conn
                .prepareStatement("INSERT INTO shoppingcart (account, product, quantity) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                pstmt.setLong(1, userId);
                pstmt.setLong(2, productId);
                pstmt.setInt(3, 1);
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
