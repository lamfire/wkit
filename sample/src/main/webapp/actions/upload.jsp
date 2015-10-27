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
	        <h2>表单上传文件</h2>
	        <div class="view">
	        	
	            <form id="submitform" method="post" action="${pageContext.request.contextPath}/upload.do" enctype="multipart/form-data">
	                <div>
	                	<div><label>提示：</label>上传文件需要将form属性"enctype"设置为"multipart/form-data"</div>
	                	<div><label>文件：</label><input type="file" name="file"/></div>
	                    <div><label>&nbsp;</label><button type="submit" >上传</button></div>
	                </div>
	            </form>
	            <div>
	            	<c:if test="${url != null}">
	            		上传的文件为:<a target="_blank" href="${pageContext.request.contextPath}/${url}">${pageContext.request.contextPath}/${url}</a>
	            	</c:if>
	            </div>
	            
	            <div class="h20"></div>
	            <div>JSP面页代码请见:WebRoot/actions/upload.jsp</div>
	        	<div>Action处理代码请见:com.lamfire.wkit.demo.action.UploadAction</div>
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