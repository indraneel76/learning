<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bloom Apartments-Admin</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
	<h2>Bloom Rental Application - Admin</h2>
	<hr />
	<form action="submitadmin" method="POST">
		<table>
			<tr>
				<td>Select your role:</td>
				<td>&nbsp;</td>
				<td><select name="role">
						<option>Reviewer</option>
						<option>Super</option>
				</select></td>
			</tr>
			<tr>
				<td colspan="2"><input type="SUBMIT" value="Submit" /></td>
			</tr>
		</table>
	</form>
	<hr />
</body>
</html>
