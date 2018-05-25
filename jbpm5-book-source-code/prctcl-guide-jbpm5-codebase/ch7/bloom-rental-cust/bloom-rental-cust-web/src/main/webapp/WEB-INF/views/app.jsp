<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
<title>Bloom Apartments</title>
</head>
<body>
	<h2>Rental Application - Hello World</h2>
	<form action="submitapp" method="POST">
	<table border="none">
	<tr>
	<td>First Name</td>
	<td><input type="text" name="firstName" /></td>
	</tr>
	<tr>
	<td>Last Name</td>
	<td><input type="text" name="lastName" /></td>
	</tr>
	<tr>
	<td>SSN</td>
	<td><input type="text" name="ssn" /></td>
	</tr>
	<tr>
	<td colspan="2"><input type="SUBMIT" value="Submit App" /></td>
	</tr>
	</table>
	</form>
</body>
</html>
