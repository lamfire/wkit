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
	        	
	            <form id="submitform" action="${pageContext.request.contextPath}/post.do" method="post">
	                <div>
	                	<div><label>用户名称：</label><input name="username" type="text" class="G-text" placeholder="请输入用户名称"/></div>
	                    <div><label>级别：</label><select name="type"><option selected="selected" value="NORMAL">普通</option><option value="VIP">VIP</option></select></div>
	                    <div><label>所在区域：</label><select name="province"></select><select name="city"></select><select name="area"></select></div>
	                    <div><label>用户密码：</label><input name="password" type="password" class="G-text" placeholder="请输入用户密码"/></div>
	                    <div><label>状态：</label><select name="status"><option selected="selected" value="1">正常</option><option value="0">无效</option></select></div>
	                    <div><label>可用：</label><select name="enable"><option selected="selected" value="true">是</option><option value="false">否</option></select></div>
	                    <div><label>备注：</label><textarea name="remark"></textarea></div>
	                    <div><label>&nbsp;</label><button type="submit" >确定</button><button type="reset" >重设</button></div>
	                </div>
	            </form>
	            <div>JSP面页代码请见:WebRoot/actions/post.jsp</div>
	        	<div>Action处理代码请见:com.lamfire.wkit.demo.action.PostAction</div>
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