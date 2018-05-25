<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bloom Apartments Console</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
	<h2>Bloom Rental BAM Console</h2>
	<hr />
	<form action="showlog" method="POST">
		<table>
			<tr>
				<td>Process Name</td>
				<td><input type="text" name="processName"
					, value="com.bb.cust.bloomcustrentalapp" /></td>
			</tr>

			<tr>
				<td>Start Date</td>
				<td><input type="datetime-local" name="startDate" /></td>
			</tr>
			<tr>
				<td>End Date</td>
				<td><input type="datetime-local" name="endDate" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="SUBMIT" value="Show Log" /></td>
			</tr>
		</table>
	</form>
	<hr />
	<c:if test="${not empty processAuditList}">
		<c:forEach items="${processAuditList}" var="item">
			<table>
				<tr>
					<td>Process Name:<span /> <b>${item.processName}</b></td>
					<td>Process Instance Id:<span /> <b>${item.processInstanceId}</b></td>
				</tr>
				<tr>
					<td>Start Date:<span /> <b>${item.startDate}</b></td>
					<td>End Date:<span /> <b>${item.endDate}</b></td>
				</tr>
			</table>
			<hr class="hr50" />
			<table>
				<tr>
					<td><b>Node Name</b></td>
					<td><b>Start Date</b></td>
					<td><b>End Date</b></td>
				</tr>
				<c:forEach items="${item.nodeAuditDetail}" var="nodeItem">
					<tr>
						<td>${nodeItem.nodeName}</td>
						<td>${nodeItem.startDate}</td>
						<td>${nodeItem.endDate}</td>
					</tr>
				</c:forEach>
			</table>
		</c:forEach>
	</c:if>
</body>
</html>
