<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bloom Apartments-Review Task</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
	<h2>Bloom Rental Application-Review Task</h2>
	<hr class="hr50" />
	<c:forEach items="${rentalTaskDataList}" var="item" varStatus="counter">
	<form action="completetask" method="POST">	
	<table>
	<tr><td>Task Id:<span /> <b><input class="readonlyinput"
	name="taskId" type="text" value="${item.taskId}" readonly /></b></td>
	<td>Task Name:<span /> <b>${item.taskName}</b></td>
	<td>Approved By Rules:<span /> <b>${item.app.approvedByRules}</b></td></tr>
	<tr><td colspan="3"><h4>Applicant Details:</h4></td></tr>
	<tr><td>First Name:<span /><b>${item.app.firstName}</b></td>
	<td>Task Name:<span /> <b>${item.app.lastName}</b></td>
	<td>SSN:<span /> <b>${item.app.ssn}</b></td></tr>
	<tr><td colspan="3">Credit Score:<span /> <b>${item.app.creditScore}</b></td></tr>
	<c:if test="${role eq 'Super'}">
	<tr><td colspan="3" valign="middle">Reviewer Comments:<span />
	<textarea rows="4" cols="50" name="revComments" disabled> ${item.admin.reviewerComments}</textarea></td></tr>
	<tr><td colspan="3" valign="middle">Super user Comments:<span />
	<textarea rows="4" cols="50" name="superComments">${item.admin.superComments}</textarea></td></tr>
	</c:if>
	<c:if test="${role ne 'Super'}">
	<tr><td colspan="3" valign="middle">Reviewer Comments:<span />
	<textarea rows="4" cols="50" name="revComments">${item.admin.reviewerComments}</textarea></td></tr>
	</c:if>
	<tr>	<td><input type="SUBMIT" name="approve" value="Approve" /></td>
	<td><input type="SUBMIT" name="reject" value="Reject" /></td>
	<td>&nbsp;</td></tr>
	<tr>	<td colspan="3"><hr /></td></tr>
	<input name="role" type="hidden" value="${role}" />
	</table>
	 </form>
</c:forEach>
 <c:if test="${empty rentalTaskDataList}">
 <h3>No Tasks Available to work on. Have Fun...</h3>
 </c:if>
 <hr class="hr50" />
</body>
</html>
