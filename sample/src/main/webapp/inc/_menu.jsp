<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!--左导航菜单-->
<div class="left">
	<div class="menu">
		<div>
			<strong>快速入门</strong>
			<ul>
				<li>
					<a href="${pageContext.request.contextPath}/main.jsp">框架介绍</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/actions/new_project.jsp">创建工程</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/actions/post.jsp">表单提交</a>
				</li>
			</ul>
		</div>
		<div>
			<strong>高级用法</strong>
			<ul>
				<li>
					<a href="${pageContext.request.contextPath}/actions/captcha.jsp">图片验证码</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/actions/upload.jsp">文件上传</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/actions/upload_ajax.jsp">文件上传(ajax)</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/actions/stream.jsp">输出文件流</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/actions/json.jsp">输出JSON数据</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/paging.do">分页显示</a>
				</li>
			</ul>
		</div>
	</div>
</div>