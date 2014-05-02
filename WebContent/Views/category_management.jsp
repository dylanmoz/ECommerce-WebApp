<%@page import="dylanandapril.database.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/Views/Partials/header.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Category Management</title>
</head>
<body style="padding-top: 80px; padding-bottom:100px">

<%
User u = (User) request.getSession(true).getAttribute("user"); 
if(u == null || !u.getRole().equals("owner")) {
	response.sendRedirect(request.getContextPath()+"/login");
	return;
}
%>

	<jsp:include page="/Views/Partials/navbar.jsp"></jsp:include>

	<div class="container" ng-app="categories" ng-controller="CategoryMgmtCtrl">
		<div class="page-header">
		  <h1>Category Management</h1>
		</div>
		<div class="row">
		  	<div class="alert alert-danger alert-dismissable" ng-repeat="alert in alerts">
		  		<button type="button" ng-click="removeAlert($index)"
		  			class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		  		<strong>Warning!</strong> {{alert}}
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<button type="button" class="btn btn-lg btn-primary" 
					data-template="Views/Partials/add_category.html" bs-modal="modal">Add Category</button>
			</div>
			<div class="col-md-3"></div>
			<div class="col-md-3">
				<div class="input-group input-group-lg">
				  <!-- <span class="input-group-addon">@</span> -->
				  <input style="border-radius: 4px" type="search" ng-model="search" class="form-control" placeholder="Search Categories">
				</div>
			</div>
		</div>
		<div class="row" style="padding-left:15px;padding-right:15px; margin-top: 20px">
			<div class="col-md-12 well" style="margin-top: 20px; min-height: 250px">
				<div class="col-md-4" ng-repeat="category in categories | filter:search" style="margin-bottom: 60px">
					<div class="panel panel-primary">
						<div class="panel-heading">
						    <h3 class="panel-title">{{category.name}}</h3>
						</div>
					  	<div class="panel-body">
					    	<p ng-show="!category.showUpdateForm">{{category.description ? category.description : 'No description.'}}</p>
					    	<div ng-show="category.showUpdateForm">
					    		<form>
					    			<div class="form-group">
										<label for="name" >Name</label> 
										<input ng-model="category.tempName" class="form-control" placeholder="Enter name">
									</div>
									<div class="form-group">
										<label>Description</label>
										<textarea ng-model="category.tempDescription" class="form-control" rows="3"></textarea>
									</div>
									<button class="btn btn-sm btn-success" ng-click="updateCategory(category)">Save</button>
									<button class="btn btn-sm btn-default" ng-click="category.showUpdateForm = false">Cancel</button>
					    		</form>
					    	</div>
					  	</div>
					  	<div class="panel-footer">
					  		<button class="btn btn-primary" style="left:5px;" ng-click="showUpdateForm(category)">Update</button>
					  		<button class="btn btn-danger" style="position:absolute;right:30px" 
					  			ng-if="!category.hasProducts" ng-click="deleteCategory(category)">Delete Category</button>
					  	</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/Views/Partials/footer.html"></jsp:include>
	<jsp:include page="/Views/Partials/javascript.jsp"></jsp:include>
	<script type="text/javascript" src="scripts/categories.js"></script>
</body>
</html>