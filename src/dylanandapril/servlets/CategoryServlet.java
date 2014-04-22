package dylanandapril.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import dylanandapril.database.Category;
import dylanandapril.database.DBConnection;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Returns JSON response with all categories, and for each category states
	 * whether there are products attached to it.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> categoryList = new LinkedList<Category>();
		Set<Long> categoriesWithProducts = new HashSet<Long>();
		
		JsonFactory jf = new JsonFactory();
        JsonGenerator jg = jf.createGenerator(response.getOutputStream());
		
		Connection conn = null;
		Statement statement = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        
        try {
            conn = DBConnection.getConnection();

            // Create the prepared statement and use it to
            // INSERT student values INTO the students table.
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM category");
            
            while(rs.next()) {
            	categoryList.add(new Category(rs.getLong("id"), rs.getString("name"), rs.getString("description")));
            }
            
            rs2 = statement.executeQuery("SELECT category FROM product");
            while(rs2.next()) {
            	categoriesWithProducts.add(rs2.getLong("category"));
            }
            
            
            jg.writeStartObject();
	        	jg.writeArrayFieldStart("categories");
	        	for(Category c : categoryList) {
	        		jg.writeStartObject();
	        		jg.writeNumberField("id", c.getId());
	        		jg.writeStringField("name", c.getName());
	        		jg.writeStringField("description", c.getDescription());
	        		jg.writeBooleanField("hasProducts", categoriesWithProducts.contains(c.getId()));
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
            if(rs2 != null) {
            	try {
            		rs2.close();
            	} catch (SQLException e) {
            		
            	}
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
	 * Updates, Deletes, or Inserts categories depending on the 'action' parameter.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String idStr = request.getParameter("id");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		long id = -1;
		try {
			if(idStr != null) id = Long.parseLong(idStr);
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
	                .prepareStatement("UPDATE category SET name = ?, description = ? WHERE id = ?");
	
	            pstmt.setString(1, name);
	            pstmt.setString(2, description);
	            pstmt.setLong(3, id);
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
                    .prepareStatement("DELETE FROM category WHERE id = ?");

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
                .prepareStatement("INSERT INTO category (name, description) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, name);
                pstmt.setString(2, description);
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
