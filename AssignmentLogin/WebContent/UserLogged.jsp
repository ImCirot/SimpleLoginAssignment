<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
	<h1>You're now logged in!</h1>
	<p>
		<% 
			String name = (String) request.getAttribute("name");
			String surname = (String) request.getAttribute("surname");
		%> 
		Hi <%out.print(name + " " + surname);%>,
		welcome to your personal area! 
	</p>
</body>
</html>