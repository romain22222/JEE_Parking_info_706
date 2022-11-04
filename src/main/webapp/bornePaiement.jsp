<%--@elvariable id="ticket" type="fr.usmb.tp.negro.salihi.ticket.jpa.Ticket"--%>
<%@ page contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Borne Paiement</title>
</head>
<body>
<p>
  Vous voulez payer le ticket suivant :<br><br>
     id : ${ ticket.id } <br>
     Arrivé à : <fmt:formatDate value="${ ticket.dateEntree }" pattern="dd/MM/yyyy HH:mm:ss" /> <br>
     Payé : ${ ticket.lastPaiement() != null ? ticket.lastPaiement() : "NON" } <br><br>

<%--@elvariable id="error" type="java.lang.String"--%>
        <c:choose>
            <c:when test="${error == 'noMoyen'}">
                Vous n'avez pas rentré de moyen de paiement
            </c:when>
            <c:when test="${error == 'notPayed'}">
                Vous n'avez pas payé avant de sortir, veuillez payer
            </c:when>
            <c:when test="${error == 'expired'}">
                Vous avez mis trop de temps à sortir, veuillez compléter la somme
            </c:when>
            <c:when test="${error == 'entryPayout'}"> </c:when>
            <c:otherwise>
                Votre ticket est payé, allez à la sortie
            </c:otherwise>
        </c:choose>
        <br>
</p>
<c:if test="${!ticket.fullyPayed()}">
  <form method="get" action="Payer">
      <label>A payer : <fmt:formatNumber value="${ ticket.calcCostToPay() }" pattern="#0.00"/>€</label><br/>
      <label for="moyen">Veuillez choisir un moyen de payement :</label>
      <select name="moyen" id="moyen">
          <option value="">--- Choisir un moyen de payement ---</option>
          <option value="CB">Carte Bancaire</option>
          <option value="especes">Espèces</option>
      </select><br>
      <input type="hidden" name="amount" value="<fmt:formatNumber value="${ ticket.calcCostToPay() }" pattern="#0.00"/>">
      <input type="hidden" name="ticket" value="${ ticket.id }">
      <input type="submit" name="pay"  value="Payer le ticket">
  </form><br><br>
</c:if>
    <form method="get" action="ImprimerJustif">
        <input type="hidden" name="ticket" value="${ ticket.id }">
        <input type="submit" name="imprimj"  value="Imprimer justificatif">
    </form><br>
    <form method="get" action="AllerBorneSortie">
        <input type="hidden" name="ticket" value="${ ticket.id }">
        <input type="submit" name="create"  value="Aller à la sortie">
    </form>
    <form method="get" action="ContinuerStationnement">
        <input type="hidden" name="ticket" value="${ ticket.id }">
        <input type="submit" name="cancel"  value="Annuler">
    </form>

<br><br>
<form method="get" action="VoirAdmin">
    <input type="submit" name="adminView"  value="Admin View">
</form>
</body>
</html>