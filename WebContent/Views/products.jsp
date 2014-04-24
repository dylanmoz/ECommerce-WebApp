<%@page import="dylanandapril.database.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/Views/Partials/header.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Products</title>
<% String role =  ((User) request.getSession().getAttribute("user")).getRole(); %>
<script>
	window.role = '<%= role %>';
</script>
</head>
<body style="padding-top: 80px; padding-bottom:100px">

	<jsp:include page="/Views/Partials/navbar.jsp"></jsp:include>

	<div class="container" ng-app="products" ng-controller="ProductCtrl">
		<div class="page-header">
		  <% if(role.equals("owner")) { %>
		  <h1>Product Management</h1>
		  <% } else if(role.equals("customer")){ %>
		  <h1>Browse Products</h1>
		  <% } %>
		</div>
		<div class="row">
			<div class="col-md-6">
				<button ng-if="role === 'owner'" type="button" class="btn btn-lg btn-primary" 
					data-template="Views/Partials/add_product.html" bs-modal="modal">Add Product</button>
			</div>
			<div class="col-md-3"></div>
			<div class="col-md-3">
				<div class="input-group input-group-lg">
				  <!-- <span class="input-group-addon">@</span> -->
				  <input style="border-radius: 4px" type="search" ng-model="search" class="form-control" placeholder="Search Products">
				</div>
			</div>
		</div>
		<div class="row" style="padding-left:15px;padding-right:15px; margin-top: 20px">
			<div class="col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li><label class="h4" style="padding-left:10px">Filter by Category:</label></li>
					<li ng-class="{active: filterCategory === null}">
						<a href="javascript:void(0)" ng-click="filterCategory = null">All Categories</a>
					</li>
					<li ng-repeat="category in categories" ng-class="{active: isCategoryFilter(category)}">
						<a href="javascript:void(0)" ng-click="filterByCategory(category)">{{category.name}}</a>
					</li>
				</ul>
			</div>
			<div class="col-md-10 well" style="min-height:250px">
				<div class="col-md-4" ng-repeat="product in products | categoryFilter:filterCategory | filter:search" style="margin-bottom: 60px">
					<div class="panel panel-primary">
						<div class="panel-heading">
						    <h3 class="panel-title">{{product.name}}</h3>
						</div>
					  	<div class="panel-body">
					    	<div ng-show="!product.showUpdateForm">
					    		<form class="form-horizontal product-display">
					    			<!-- <div class="form-group">
										<label for="name" >Name</label> 
										<span>{{product.name}}</span>
									</div> -->
									<div class="form-group">
										<label>Category</label>
										<span>{{product.category}}</span>
									</div>
									<div class="form-group">
										<label>SKU</label>
										<span>{{product.sku}}</span>
									</div>
									<div class="form-group">
										<label>Price</label>
										<span>{{product.price | currency}}</span>
									</div>
					    		</form>
					    	</div>
					    	<div ng-show="product.showUpdateForm && role === 'owner'">
					    		<form role="form" novalidate>
									<div class="form-group" ng-class="{'has-error': tempnameError}">
										<label>Name</label> 
										<input ng-model="product.tempName" class="form-control" placeholder="Enter name">
										<p ng-show="tempnameError" class="help-block">Name is required</p>
									</div>
									<div class="form-group" ng-class="{'has-error': tempcategoryError}">
										<label>Category</label> 
										<select ng-model="product.tempCategory" ng-options="c.name for c in categories" class="form-control"></select>
										<p ng-show="tempcategoryError" class="help-block">Product must have a category</p>
									</div>
									<div class="form-group" ng-class="{'has-error': tempskuError}">
										<label>SKU</label> 
										<input ng-model="product.tempSKU" class="form-control" placeholder="Enter SKU">
										<p ng-show="tempskuError" class="help-block">SKU is required</p>
									</div>
									<div class="form-group" ng-class="{'has-error': temppriceError}">
										<label>Name</label> 
										<div class="input-group">
											<span class="input-group-addon">$</span>
											<input ng-model="product.tempPrice" class="form-control" placeholder="Enter price">
										</div>
										<p ng-show="temppriceError" class="help-block">Price is required and must be a number.</p>
									</div>
									<button class="btn btn-sm btn-success" ng-click="updateProduct(product)">Save</button>
									<button class="btn btn-sm btn-default" ng-click="product.showUpdateForm = false">Cancel</button>
								</form>
					    	</div>
					  	</div>
					  	<div class="panel-footer" ng-class="{centertext: role==='customer'}">
					  		<% if(role.equals("owner")) { %>
					  		<button class="btn btn-primary" style="left:5px;" ng-click="showUpdateForm(product)">Update</button>
					  		<button class="btn btn-danger" style="position:absolute;right:30px" ng-click="deleteProduct(product)">
					  			Delete Product
					  		</button>
					  		<% } else if(role.equals("customer")) { %>
					  		<button class="btn btn-success" ng-click="addToShoppingCart(product)">Add to Shopping Cart</button>
					  		<% } %>
					  	</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/Views/Partials/footer.html"></jsp:include>
	<jsp:include page="/Views/Partials/javascript.jsp"></jsp:include>
	<script type="text/javascript" src="scripts/shopping_cart.js"></script>
	<script type="text/javascript" src="scripts/products.js"></script>
</body>
</html>