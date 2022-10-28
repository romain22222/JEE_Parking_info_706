<%@ page contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>ADMIN : Liste Tickets</title>
	</head>
	<body>
	<table>
		<tr><th>Id</th><th>Date Entrée</th><th>Date Sortie</th><th>Payé</th><th>Somme payée</th></tr>
		<%--@elvariable id="tickets" type="java.util.List"--%>
		<c:forEach items="${tickets}" var="t" >
			<tr>
				<td>${ t.id }</td>
				<td><fmt:formatDate value="${ t.dateEntree }" pattern="dd/MM/yyyy HH:mm:ss" /></td>
				<td><fmt:formatDate value="${ t.dateSortie }" pattern="dd/MM/yyyy HH:mm:ss" /></td>
				<td>${ t.lastPaiement() == null ? "NON" : "OUI" }</td>
				<td><fmt:formatNumber pattern="#0.00" value="${ t.sommePaiement() }" /></td>
			</tr>
		</c:forEach>
	</table>
	</body>
</html>