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
<title>Homepage</title>
</head>
<body style="padding-top: 80px; padding-bottom:100px">

	<jsp:include page="/Views/Partials/navbar.jsp"></jsp:include>

	<% 
		User u = (User) request.getSession(false).getAttribute("user"); 
	%>
	<div class="container">
		<div class="page-header">
		  <h1>Home</h1>
		</div>
		<div class="jumbotron"
			style="text-align:center; padding-top: 150px; padding-bottom: 150px">
			<h1>Hello, <%= u.getUsername() %>!</h1>
			<p class="lead">Please choose where you would like to go:</p>
			<br />
			<div class="row">
				<% if(u.getRole().equals("customer")) { %>
				<div class="col-md-6">
					<a href="customer/products" class="btn btn-primary btn-lg">Products</a>
				</div>
				<div class="col-md-6">
					<a href="customer/shopping_cart" class="btn btn-primary btn-lg">Shopping Cart</a>
				</div>
				<% } else if(u.getRole().equals("owner")) {%>
				<div class="col-md-6">
					<a href="owner/category_management" class="btn btn-primary btn-lg">Category Management</a>
				</div>
				<div class="col-md-6">
					<a href="owner/product_management" class="btn btn-primary btn-lg">Product Management</a>
				</div>

				<% } %>
			</div>
		</div>
	</div>
	<jsp:include page="/Views/Partials/footer.html"></jsp:include>
	<jsp:include page="/Views/Partials/javascript.jsp"></jsp:include>
	<!-- <script type="text/javascript" src="scripts/login.js"></script> -->
</body>
</html>