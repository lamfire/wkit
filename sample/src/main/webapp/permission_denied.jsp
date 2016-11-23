<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/style.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/animation.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/res/css/login.css" type="text/css" />
    <title>欢迎使用WKIT-DEMO</title>
</head>
<body>
<h2>permission denied</h2>

<p>URL : ${url}</p>

<p>PERMISSIONS : ${permissions}</p>

<p><a href="${pageContext.request.contextPath}/login.jsp">点击这里登录</a></p>
</body>