<%@ page contentType="text/html; charset=utf-16"
		 pageEncoding="utf-16"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Borne entrée</title>
	</head>
	<body>
	  <h2>Prendre un ticket</h2>
	  <form method="get" action="CreateTicket">
		<input type="submit" name="create"  value="Créer un ticket">
	  </form>
	  <br><br>
	  <form method="get" action="VoirAdmin">
		  <input type="submit" name="adminView"  value="Admin View">
	  </form>
	</body>
</html>