<jsp:useBean id="ticket" scope="application" class="fr.usmb.tp.negro.salihi.ticket.jpa.Ticket"/>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
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
     Payé : ${ ticket.lastPaiement() ? ticket.lastPaiement() : "NON" } <br>
     ${ ticket.fullyPayed()
     ? "Votre ticket est payé, allez à la sortie"
     : (ticket.lastPaiement()
        ? "Le temps de sortie est expiré, veuillez repayer"
        : "Vous n&apos;avez pas encore payé votre ticket"
     )
     } <br>
</p>

<%--<form method="get" action="Payer">
    <input type="hidden" name="ticket" value="${ ticket.id }">
    <input type="submit" name="pay"  value="Payer le ticket">
  </form>--%>
  <form method="get" action="ContinuerStationnement">
    <input type="hidden" name="ticket" value="${ ticket.id }">
    <input type="submit" name="cancel"  value="Annuler">
  </form>
</body>
</html>