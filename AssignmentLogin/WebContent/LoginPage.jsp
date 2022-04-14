<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<h1>Accedi all'area personale:</h1>
	<form action="LoginServlet" method="post">
		<label for="username">Username: </label><br />
		<input type="text" id="username" name="username" /><br />
		<label for="password">Password: </label><br />
		<input type="password" name="password" id="password" /><br /><br />
		<input type="submit" value="login" />
	</form>	
</body>
</html>