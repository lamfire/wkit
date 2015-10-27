<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc/_html_head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/inc/_page_header.jsp"></jsp:include>

<div class="page-main">
	<!--左导航菜单-->
	<jsp:include page="/inc/_menu.jsp"></jsp:include>
	
	<!--主显示区域-->
	<div class="main">
	    <div class="wapper">
	        <h2>生成验证码</h2>
	        <div class="view">
	            <form id="loginForm" action="${pageContext.request.contextPath}/captchaCheck.do">
	            <p>
		            <span><img id="captcha" src="${pageContext.request.contextPath}/captcha.do" /><a href="javascript:flushCaptcha();" title="看不清？">看不清？</a></span>
		        </p>
		        <p>
		                          验证码：<input type="text" style="width:80px;" name="input" placeholder="验证码"/>
		        </p>
		        <p>
		            <span>
		                <button type="submit">提交</button>
		            </span>
		        </p>
		    </form>
	            <div>JSP面页代码请见:WebRoot/actions/captcha.jsp</div>
	        	<div>验证码生成代码请见:com.lamfire.wkit.demo.action.CaptchaAction</div>
	        </div>
	    </div>
	</div>
</div>

<!--左导航菜单-->
<jsp:include page="/inc/_page_footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/placeholder.js"></script>
<script type="text/javascript">
function flushCaptcha(){
	var url = "${pageContext.request.contextPath}/captcha.do?"+Math.random();
	$("#captcha").attr("src",url);
}
</script>
</html>