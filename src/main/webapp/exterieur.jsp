<%@ page contentType="text/html; chart=utf-16"
         pageEncoding="utf-16"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Extérieur</title>
</head>
<body>
<p>
  Vous êtes dehors
</p>

<form method="get" action="MenuExt">
  <input type="submit" name="create"  value="Aller à la borne d'entrée">
</form>
<br><br>
<form method="get" action="VoirAdmin">
  <input type="submit" name="adminView"  value="Admin View">
</form>
</body>
</html>