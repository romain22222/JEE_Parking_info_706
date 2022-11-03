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
     Payé : ${ ticket.lastPaiement() != null ? ticket.lastPaiement() : "NON" } <br>
     ${ ticket.fullyPayed()
     ? "Votre ticket est payé, allez à la sortie"
     : (ticket.lastPaiement() != null
        ? "Le temps de sortie est expiré, veuillez repayer"
        : "Vous n&apos;avez pas encore payé votre ticket"
     )
     } <br>
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
  <form method="get" action="ContinuerStationnement">
    <input type="hidden" name="ticket" value="${ ticket.id }">
    <input type="submit" name="cancel"  value="Annuler">
  </form>
</body>
</html>