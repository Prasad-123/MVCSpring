<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Transactions</title>
<style>
table, th, td {
    border: 1px solid black;
}
</style>
</head>
<body>
	<h1>List of Transactions</h1>
	<div align="center">
		<table>
			<tr>
				<th>Transaction Type</th>
				<th>MobileNo</th>
				<th>Transaction Date</th>
				<th>Balance</th>
			</tr>
			<c:forEach items="${transactions}" var="transaction1">
				<tr>
					<td>${transaction1.transactionType}</td>
					<td>${transaction1.mobileNo}</td>
					<td>${transaction1.transactionDate}</td>
					<td>${transaction1.balance}</td>
				</tr>
			</c:forEach>
		</table>


		<a href="Operations">BACK</a>
	</div>
	
	<div>
		<font color='red'> <c:if test="${not empty errorMessage}">
			${errorMessage}
			</c:if>
			</font>
	</div>
</body>
</html>