<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Login</title>
<style>
error {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<div align="Center">
		<h2>Enroll Customer Details here</h2>
		<table>
			<form:form action="registerCustomer" method="post"
				modelAttribute="customer">

				<tr>
					<td>Name:</td>
					<td><form:input path="name" size="30" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>


				<tr>
					<td>Mobileno:</td>
					<td><form:input path="mobileNo" size="30" /></td>
					<td><form:errors path="mobileNo" cssClass="error" /></td>
				</tr>


				<tr>
					<td>Balance:</td>
					<td><form:input path="wallet.balance" size="30" /></td>
					<td><form:errors path="wallet.balance" cssClass="error" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="submit"/></td>
				</tr>
				
			</form:form>
		</table>
	</div>
	<div>
		<font color='red'> <c:if test="${not empty errorMessage}">
			${errorMessage}
			</c:if>
			</font>
	</div>
	

</body>
</html>