<jsp:useBean id="ticket" scope="application" class="fr.usmb.tp.negro.sahili.ticket.jpa.Ticket"/>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Affichage Ticket</title>
	</head>
	<body>
	  <p>
		id : ${ ticket.id } <br>
		Arrivé à : <fmt:formatDate value="${ ticket.dateEntree }" pattern="dd/MM/yyyy HH:mm:ss" /> <br>
		Payé : ${ ticket.lastPaiement() ? ticket.lastPaiement() : "NON" } <br>
	  </p>
	  <form method="get" action="AllerPayer">
		  <input type="submit" name="goPay"  value="Aller Payer">
	  </form>
	</body>
</html>