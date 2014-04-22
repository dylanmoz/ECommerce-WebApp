<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/Views/Partials/header.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Products</title>
</head>
<body style="padding-top: 80px; padding-bottom:100px">

	<jsp:include page="/Views/Partials/navbar.jsp"></jsp:include>

	<div class="container" ng-app="products" ng-controller="CustomerCtrl">
		<div class="page-header">
		  <h1>Products</h1>
		</div>
		<div class="row">
			<div class="col-md-6"></div>
			<div class="col-md-3"></div>
			<div class="col-md-3">
				<div class="input-group input-group-md">
				  <!-- <span class="input-group-addon">@</span> -->
				  <input style="border-radius: 4px" type="search" class="form-control" placeholder="Search">
				</div>
			</div>
		</div>
		<div class="row" style="padding-left:15px;padding-right:15px">
			<div class="col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active">
						<a href="#">Test</a>
					</li>
					<li>
						<a href="#">Product</a>
					</li>
					<li>
						<a href="#">Sidebar</a>
					</li>
					<li>
						<a href="#">blah</a>
					</li>
					<li>
						<a href="#">hue</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<jsp:include page="/Views/Partials/footer.html"></jsp:include>
	<jsp:include page="/Views/Partials/javascript.jsp"></jsp:include>
	<script type="text/javascript" src="scripts/products.js"></script>
</body>
</html>