<%@page import="dylanandapril.database.User"%>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="home">CSE 135 E-Commerce Website</a>
    </div>
    <% User u = null; %>
    <% if(request.getSession(false) != null && (u = (User) request.getSession(false).getAttribute("user")) != null) { %>
    <div>
    	<% if(u.getRole().equals("owner")) { %>
        <ul class="nav navbar-nav">
            <li class="active"><a href="home">Home</a></li>
            <li><a href="owner/category_management">Category Management</a></li>
            <li><a href="owner/product_management">Product Management</a></li>
        </ul>
        <% } else if(u.getRole().equals("customer")) { %>
        <ul class="nav navbar-nav">
            <li class="active"><a href="home">Home</a></li>
            <li><a href="customer/products">Products</a></li>
            <li><a href="customer/shopping_cart">Shopping Cart</a></li>
        </ul>
        <% } %>
        <ul style="margin-right:20px;" class="nav navbar-nav navbar-right">
          <li>
          	<p class="navbar-text">
          		Welcome, <%=u.getUsername()%>
          	</p>
          	<li><a href="account?logout=true">Logout</a>
          </li>
        </ul>
    </div>
    <% } %>
  </div>
</nav>