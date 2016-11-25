<html>
<body>
<h2>Permission Denied</h2>
<hr>
<table>
    <tr>
        <td>URL : </td>
        <td>${url}</td>
    </tr>
    <tr>
        <td>NEED PERMISSIONS :</td>
        <td>${permissions}</td>
    </tr>
    <tr>
        <td>USER : </td>
        <td>${pageContext.request.remoteUser}</td>
    </tr>

</table>
<br>
<a href="${pageContext.request.contextPath}/login.jsp">click to login</a>
</body>
</html>