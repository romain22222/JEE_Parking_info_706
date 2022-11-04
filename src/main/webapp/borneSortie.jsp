<%--@elvariable id="ticket" type="fr.usmb.tp.negro.salihi.ticket.jpa.Ticket"--%>
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
  id : ${ ticket.id } <br>
  Arrivé à : <fmt:formatDate value="${ ticket.dateEntree }" pattern="dd/MM/yyyy HH:mm:ss" /> <br>
  Payé : ${ ticket.lastPaiement() != null ? ticket.lastPaiement() : "NON" } <br>
</p>

  <form method="get" action="SortirParking">
    <input type="hidden" name="ticket" value="${ticket.id}">
    <input type="submit" name="checkTicket"  value="Sortir">
  </form>
<br><br>
<form method="get" action="VoirAdmin">
  <input type="submit" name="adminView"  value="Admin View">
</form>
</body>
</html>