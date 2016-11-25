<html>
<body>
<h2>LOGIN</h2>
<hr>
<p style="color: #ff5500">${errorMessage}</p>
<form method="post" action="${pageContext.request.contextPath}/login">
    <table>
        <tr>
            <td>User : </td>
            <td><input name="username" type="text"></td>
        </tr>
        <tr>
            <td>Password :</td>
            <td><input name="password" type="password"></td>
        </tr>
        <tr>
            <td><button type="submit">Login</button></td>
            <td></td>
        </tr>

    </table>
</form>
</body>
</html>