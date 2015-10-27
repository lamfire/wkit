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
	        <h2>提交表单</h2>
	        <div class="view">
	        	<div>
					输入GIF动画 : <a href="${pageContext.request.contextPath}/image.do" target="_blank">输出图片</a>
	            </div>
	            <div class="h20"></div>
	            <div>JSP面页代码请见:WebRoot/actions/stream.jsp</div>
	        	<div>Action处理代码请见:com.lamfire.wkit.demo.action.ImageAction</div>
	        </div>
	    </div>
	</div>
</div>

<!--左导航菜单-->
<jsp:include page="/inc/_page_footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/placeholder.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/region.js"></script>
<script type="text/javascript">
	(function(){
		region("province","city","area");
	})();
</script>
</html>