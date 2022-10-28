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
  id : ${ ticket.id } <br>
  Arriv� � : <fmt:formatDate value="${ ticket.dateEntree }" pattern="dd/MM/yyyy HH:mm:ss" /> <br>
  Pay� : ${ ticket.lastPaiement() ? ticket.lastPaiement() : "NON" } <br>
</p>

  <form method="get" action="SortirParking">
    <input type="hidden" name="ticket" value="${ticket.id}">
    <input type="submit" name="checkTicket"  value="Sortir">
  </form>
</body>
</html>