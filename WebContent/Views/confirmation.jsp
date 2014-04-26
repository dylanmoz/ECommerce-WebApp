<%@page import="java.util.Locale"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.math.MathContext"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.sql.SQLException"%>
<%@page import="dylanandapril.database.DBConnection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="dylanandapril.database.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="url">${pageContext.request.requestURL}</c:set>
<base
	href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
<link rel="stylesheet" href="scripts/ThirdParty/bootstrap.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order Confirmation</title>
</head>
<body style="padding-top: 80px; padding-bottom:100px">

	<jsp:include page="/Views/Partials/navbar.jsp"></jsp:include>

	<%
	System.out.println("Confimation Page entered");
	// TODO : redirect if user is null.
	
		if(request.getParameter("error") != null) {
	%>
			<div class="container">
				<div class="page-header">
				  <h1>Order Confirmation Error</h1>
				</div>
				<div class="row">
					<h3>An error occurred while purchasing the products.</h3>
					<h4>Your credit card was not charged. Please go back to the shopping cart and try again.</h4>
				</div>
			</div>
			<jsp:include page="/Views/Partials/footer.html"></jsp:include>
			<jsp:include page="/Views/Partials/javascript.jsp"></jsp:include>
		  </body>
		</html>
	<%
		} else {
	
			User u = (User)request.getSession(true).getAttribute("user");
			long userId = u.getId();
			
			String ccStr = request.getParameter("cc");
			System.out.println("cc : " + ccStr);
			
			boolean dbError = false;
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
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
		%>
		<div class="container">
			<div class="page-header">
			  <h1>Order Confirmation</h1>
			</div>
			<div class="col-md-8 col-md-offset-2">
				<table class="table table-hover table-bordered">
					<thead>
						<tr>
							<th>Product</th>
							<th>Price</th>
							<th>Quantity</th>
							<th>Subtotal</th>
						</tr>
					</thead>
					<tbody>
						<% 
						BigDecimal total = new BigDecimal(0);
						NumberFormat usdFormat = NumberFormat.getCurrencyInstance(Locale.US);
						usdFormat.setMinimumFractionDigits(2);
						usdFormat.setMaximumFractionDigits(2);
						while(rs.next()) { 
							double price = rs.getDouble("productPrice");
							int quantity = rs.getInt("quantity");
							BigDecimal subtotal = (new BigDecimal(price)).multiply((new BigDecimal(quantity)), MathContext.DECIMAL32);
							total = total.add(subtotal);
						%>
						<tr>
							<td><%= rs.getString("productName") %></td>
							<td><%= usdFormat.format(price) %></td>
							<td><%= quantity %></td>
							<td><%= usdFormat.format( subtotal.setScale(2, RoundingMode.HALF_EVEN).doubleValue() )%></td>
						</tr>
						<% } %>
						<tr class=active>
							<td colspan="3" style="border-top-width:2px; border-top-color: darkgray">
								<strong>Purchase Total</strong>
							</td>
							<td style="border-top-width:2px; border-top-color: darkgray">
								<%= usdFormat.format(total.setScale(2, RoundingMode.HALF_EVEN).doubleValue() )%>
							</td>
						</tr>
					</tbody>
				</table>
				<div style="text-align: center">
					<button class="btn btn-primary btn-lg">Continue Shopping</button>
				</div>
			</div>
		</div>
		<%
		
				conn.setAutoCommit(false);
			
			    // Create the prepared statement and use it to
			    // DELETE students FROM the Students table.
			    pstmt2 = conn
			        .prepareStatement("DELETE FROM shoppingcart WHERE account = ?");
			
			    pstmt.setLong(1, userId);
			    int rowCount = pstmt.executeUpdate();
			    if(rowCount < 1) {
			    	dbError = true;
			    }
			
			    // Commit transaction
			    conn.commit();
			    conn.setAutoCommit(true);
			    
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
	            if (pstmt2 != null) {
	                try {
	                    pstmt2.close();
	                } catch (SQLException e) { } // Ignore
	                pstmt2 = null;
	            }
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) { } // Ignore
	                conn = null;
	            }
	        }
	        
	        if(dbError) {
	        	response.sendRedirect(request.getContextPath()+"/confirmation?error=true");
				return;
	        }
		
	%>

	<jsp:include page="/Views/Partials/footer.html"></jsp:include>
	<jsp:include page="/Views/Partials/javascript.jsp"></jsp:include>
	<!-- <script type="text/javascript" src="scripts/EXTRA_JS"></script> -->
</body>
</html>
<% } %>