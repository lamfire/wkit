<html>
<body>
<h1>
Welcome [<a href="${pageContext.request.contextPath}/logout">${pageContext.request.remoteUser}</a>]
</h1>
<hr>
<form method="post" action="${pageContext.request.contextPath}/post">
    <table>
        <tr>
            <td>Title : </td>
            <td><input name="title" type="text"></td>
        </tr>
        <tr>
            <td>Name :</td>
            <td><input name="name" type="text"></td>
        </tr>
        <tr>
            <td><button type="submit">POST</button></td>
            <td></td>
        </tr>

    </table>

</form>
<br>
<p><a href="${pageContext.request.contextPath}/user/add">ADD</a></p>
<p><a href="${pageContext.request.contextPath}/user/del">DEL</a></p>
<p>
    <form method="post" action="${pageContext.request.contextPath}/file" enctype="multipart/form-data">
        <input type="file" name="file">
        <button type="submit">Submit</button>
    </form>
</p>
</body>
</html>