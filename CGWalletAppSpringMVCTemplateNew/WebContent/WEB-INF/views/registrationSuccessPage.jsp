<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Welcome</title>
</head>
<body>
	<div align="center">
		<h2>Welcome ${customer.name}</h2>
		<br>
		<h2>Your Account number is:-${customer.mobileNo}</h2>
		<a href="/mypaymentapp">HomePage</a>
	</div>
</body>
</html>