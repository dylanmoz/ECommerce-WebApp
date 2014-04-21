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
<title>Login</title>
</head>
<body style="padding-top: 80px; padding-bottom:100px">

	<%
		boolean signUpAttempt = false;
		boolean signUpSuccess = false;
		String accountCreation = (String) request.getParameter("accountCreation");
		System.out.println("/login, accountCreation = " + accountCreation);
		if (accountCreation != null) {
			signUpAttempt = true;
			if (accountCreation.equals("success")) {
				signUpSuccess = true;
			}
		}
		
		String loginError = (String) request.getParameter("error");
	%>

	<jsp:include page="/Views/Partials/navbar.jsp"></jsp:include>

	<div class="container" ng-app="login" ng-controller="LoginCtrl">
		<div>
			<div class="row">
				<%
					if (signUpAttempt && signUpSuccess) {
				%>
				<div class="alert alert-success">Account Creation Successful! Please log in.</div>
				<%
					} else if (signUpAttempt && !signUpSuccess) {
				%>
				<div class="alert alert-danger">Username is already taken. Please try again.</div>
				<%
					} else if(loginError != null) {
				%>
				<div class="alert alert-danger">Login unsuccessful. Please make sure you entered your username correctly.</div>
				<% } %>
				<div class="col-lg-2 col-md-2"></div>
				<div class="col-lg-3 col-md-3">
					<div class="page-header">
						<h1>Sign In</h1>
					</div>
					<form role="form" action="account" method="GET" novalidate>
						<input type="hidden" name="form" value="login">
						<div class="form-group">
							<label for="username">Username</label> 
							<input name="username" type="text" ng-model="loginUsername"
								class="form-control" id="username" placeholder="Enter username">
						</div>
						<button type="submit" class="btn btn-default" ng-disabled="!loginUsername">Sign In</button>
					</form>
				</div>
				<div class="col-lg-2 col-md-2"></div>
				<div class="col-lg-3 col-md-3">
					<div class="page-header">
						<h1>Sign Up</h1>
					</div>
					<form role="form" name="signup" action="account" method="POST" novalidate>
						<input type="hidden" name="form" value="signup">
						<div class="form-group" ng-class="{'has-error': signup.username.$invalid && !signup.username.$pristine}">
							<label for="username">Username</label> 
							<input name="username" ng-model="username" type="text" class="form-control" id="username" placeholder="Enter username" required>
							<p ng-show="signup.username.$invalid && !signup.username.$pristine" class="help-block">Username is required.</p>
						</div>
						<div class="form-group">
							<label for="role">Role</label> 
							<select name="role" class="form-control">
								<option value="owner">Owner</option>
								<option value="customer">Customer</option>
							</select>
						</div>
						<div class="form-group" ng-class="{'has-error': signup.age.$invalid && !signup.age.$pristine}">
							<label for="age">Age</label> 
							<input name="age" ng-model="age" type="number" class="form-control" id="age" placeholder="Enter your age" required>
							<p ng-show="signup.age.$invalid && !signup.age.$pristine" class="help-block">Your age is required.</p>
						</div>
						<div class="form-group">
							<label for="state">State</label> 
							<select name="state" class="form-control">
								<option value="AL">Alabama</option>
								<option value="AK">Alaska</option>
								<option value="AZ">Arizona</option>
								<option value="AR">Arkansas</option>
								<option value="CA">California</option>
								<option value="CO">Colorado</option>
								<option value="CT">Connecticut</option>
								<option value="DE">Delaware</option>
								<option value="DC">District Of Columbia</option>
								<option value="FL">Florida</option>
								<option value="GA">Georgia</option>
								<option value="HI">Hawaii</option>
								<option value="ID">Idaho</option>
								<option value="IL">Illinois</option>
								<option value="IN">Indiana</option>
								<option value="IA">Iowa</option>
								<option value="KS">Kansas</option>
								<option value="KY">Kentucky</option>
								<option value="LA">Louisiana</option>
								<option value="ME">Maine</option>
								<option value="MD">Maryland</option>
								<option value="MA">Massachusetts</option>
								<option value="MI">Michigan</option>
								<option value="MN">Minnesota</option>
								<option value="MS">Mississippi</option>
								<option value="MO">Missouri</option>
								<option value="MT">Montana</option>
								<option value="NE">Nebraska</option>
								<option value="NV">Nevada</option>
								<option value="NH">New Hampshire</option>
								<option value="NJ">New Jersey</option>
								<option value="NM">New Mexico</option>
								<option value="NY">New York</option>
								<option value="NC">North Carolina</option>
								<option value="ND">North Dakota</option>
								<option value="OH">Ohio</option>
								<option value="OK">Oklahoma</option>
								<option value="OR">Oregon</option>
								<option value="PA">Pennsylvania</option>
								<option value="RI">Rhode Island</option>
								<option value="SC">South Carolina</option>
								<option value="SD">South Dakota</option>
								<option value="TN">Tennessee</option>
								<option value="TX">Texas</option>
								<option value="UT">Utah</option>
								<option value="VT">Vermont</option>
								<option value="VA">Virginia</option>
								<option value="WA">Washington</option>
								<option value="WV">West Virginia</option>
								<option value="WI">Wisconsin</option>
								<option value="WY">Wyoming</option>
							</select>
						</div>
						<button type="submit" class="btn btn-default" ng-disabled="signup.$invalid">Submit</button>
					</form>
				</div>
				<div class="col-lg-2 col-md-2"></div>
			</div>
		</div>
	</div>
	<jsp:include page="/Views/Partials/footer.html"></jsp:include>
	<jsp:include page="/Views/Partials/javascript.jsp"></jsp:include>
	<script type="text/javascript" src="scripts/login.js"></script>
</body>
</html>