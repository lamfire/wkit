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
	        <h2>创建WEB工程</h2>
	        <div class="view">
	            <h3>1.新建web工程</h3>
	            <p><img src="${pageContext.request.contextPath}/res/images/project_1.png"/> </p>
	            <div class="h20"></div>
	            
	            <h3>2.添加依懒jar包,并新增一个action包目录.我这里为"com.lamfire.wkit.demo.action".</h3>
				<p><img src="${pageContext.request.contextPath}/res/images/project_2.png"/> </p>
				<div class="h20"></div>
				
				<h3>3.配置web.xml</h3>
				<p><img src="${pageContext.request.contextPath}/res/images/project_3.png"/> </p>
				<p class="red">需要注意,web.xml配置项中的初始化参数"action.root",必须要和第二步建立的包目录一致.</p>
				<div class="h20"></div>
				
				<h3>4.到此为止项目框架的配置及准备工作完成.下面便可以进入到具体业务功能开发中去了...</h3>
	        </div>
	    </div>
	</div>
</div>

<!--左导航菜单-->
<jsp:include page="/inc/_page_footer.jsp"></jsp:include>
</body>
</html>