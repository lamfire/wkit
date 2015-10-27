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
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery.1.6.1.min.js"></script>
<!--[if lt IE 7]>
	<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/pngfix.js"></script>
<![endif]-->
</head>
<body>
<div style="height:150px"></div>
<div class="login-bg">
	<div class="loginBar clearfix" >
		<div id="logo" class="logo">
			<img alt="logo" src="${pageContext.request.contextPath}/res/images/logo.png">
		</div>
		<div id="loginBox" class="loginbox radius reflect gradient">
			<h1>欢迎使用WKIT-DEMO</h1>
		    <form id="loginForm" action="${pageContext.request.contextPath}/main.jsp">
		        <p>
		            <label>账号：</label>
		            <input type="text" name="username" placeholder="请输入您的账号" />
		        </p>
		        <p>
		            <label>密码：</label>
		            <input type="password" name="password" placeholder="请输入您的密码" />
		        </p>
		        <p>
		            <label>验证码：</label>
		            <input type="text" style="width:80px;" name="captcha" placeholder="验证码"/>
		            <span><img src="${pageContext.request.contextPath}/captcha" /><a href="javascript:flushCaptcha();" title="看不清？">看不清？</a></span>
		        </p>
		        <p>
		        	<label></label>
		            <span>
		                <button type="submit">登录</button>
		            </span>
		        </p>
		        <br/>
		        <p class="center text-shadow">请使用Chrome,Firefox,Safari或IE9以获得最佳浏览体验</p>
				<p class="center text-shadow">最佳浏览屏幕分辨率1440x900</p>
		    </form>
		</div>
	</div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/placeholder.js"></script>
<script type="text/javascript">
(function(){
    /*出现效果*/
    $(function(){
        $("#loginBox").delay(300).animate({
            left:"50%",
            opacity:1
        },300);
    });
})();

function flushCaptcha(){
	var url = "${pageContext.request.contextPath}/captcha?"+Math.random();
	$("#captcha").attr("src",url);
}
</script>
</html>