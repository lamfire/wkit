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
					<h2>
						验证码
					</h2>
					<div class="view">
						<p>
							原验证码：${captcha}
						</p>
						<p>
							您的输入：${input}
						</p>
						<div>
							JSP面页代码请见:WebRoot/actions/captcha_result.jsp
						</div>
						<div>
							Action代码请见:com.lamfire.wkit.demo.action.CaptchaCheckAction
						</div>
					</div>
				</div>
			</div>
		</div>

		<!--左导航菜单-->
		<jsp:include page="/inc/_page_footer.jsp"></jsp:include>
	</body>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/res/js/placeholder.js"></script>
	<script type="text/javascript">
	function flushCaptcha() {
		var url = "${pageContext.request.contextPath}/captcha?" + Math.random();
		$("#captcha").attr("src", url);
	}
</script>
</html>