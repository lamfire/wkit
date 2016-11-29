<html>
<body>
<h2>Welcome ${pageContext.request.remoteUser}</h2>
<hr>
<br>
${pageContext.request.remoteUser eq null ?'<a href="./login.jsp">click to login</a>':'<a href="./logout">click to logout</a>'}
</body>
</html>