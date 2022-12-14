<%--@elvariable id="ticket" type="fr.usmb.tp.negro.salihi.ticket.jpa.Ticket"--%>
<%@ page contentType="text/html; charset=utf-16"
		 pageEncoding="utf-16"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>JUSTIFICATIF TICKET</title>
	</head>
	<body>
	<h2>JUSTIFICATIF TICKET</h2>
	<p>
		numero du ticket : ${ ticket.id } <br>
		date d'entrée : <fmt:formatDate value="${ ticket.dateEntree }" pattern="dd/MM/yyyy HH:mm:ss" /> <br>
		date du dernier paiement :
		<c:if test="${ticket.lastPaiement() != null}">
			<fmt:formatDate value="${ ticket.lastPaiement().datePaiement }" pattern="dd/MM/yyyy HH:mm:ss" />
			<br>
			moyen utilisé :
			<c:set value="${ticket.lastPaiement().moyenDePaiement.toString()}" var="moyen" scope="session"/>
			<c:choose>
				<c:when test="${moyen == 'CB'}">
					Carte Bancaire
				</c:when>
				<c:when test="${moyen == 'especes'}">
					Espèces
				</c:when>
				<c:when test="${moyen == 'crypto'}">
					Cryptomonnaie
				</c:when>
				<c:when test="${moyen == 'nature'}">
					Nature
				</c:when>
				<c:otherwise>
					Chèque
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${ticket.lastPaiement() == null}">
			JAMAIS
		</c:if>
		<br>
		montant total payé par le client : <fmt:formatNumber pattern="#0.00" value="${ ticket.sommePaiement() }" /> €

	</p>
	  <form method="get" action="AllerBorneSortie">
		  <input type="hidden" name="ticket" value="${ ticket.id }">
		  <input type="submit" name="create"  value="Aller à la sortie">
	  </form>
	</body>
	<form method="get" action="AllerPayer">
		<input type="hidden" name="ticket" value="${ ticket.id }">
		<input type="submit" name="create"  value="Revenir à la borne de paiement">
	</form>
	<br><br>
	<form method="get" action="VoirAdmin">
		<input type="submit" name="adminView"  value="Admin View">
	</form>
</html>