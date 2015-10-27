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
	        <h2>首页</h2>
	        <div class="view">
	            <h3>开源框架wkit简介</h3>
	            <p> wkit是一个完全开源且经量级的J2EE WEB MVC框架.建立在Servlet之上,处理HTTP请求和响应.所有的请求都会被它的前端控制器（FilterDispatcher）截获,前端控制器对请求的数据进行包装，初始化上下文数据，根据命名约定查找请求URL对应的Action类，执行Action，将执行结果转发到相应的展现页面。处理流程及用法与struts 2极为相拟.</p>
				<p>	wkit简洁、灵活功能强大，它是一个标准的Vistor模式框架实现，并且完全从web层脱离出来。</p>
				<p>	wkit特别擅长移动互联网后台服务接口的实现.能大大简化传统webservice的开发流程,使得程序员只需专注于具体业务功能实现,无需繁杂的项目配置等.</p>
				<p>	更重要的一点,使用TA完全不用担心struts带来的漏洞安全隐患,使得开发人员可以从容面对安全漏洞问题,使得整个研发团队可以在全世界都忙于打补丁之际,以逸待劳高枕无忧.</p>
	        </div>
	    </div>
	</div>
</div>

<!--左导航菜单-->
<jsp:include page="/inc/_page_footer.jsp"></jsp:include>
</body>
</html>