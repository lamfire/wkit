<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/wkit" prefix="wk"%>
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
						分页
					</h2>
					<div class="view">
						<table class="thinLineTable" width="100%" border="1" cellpadding="1" cellspacing="1" bordercolor="#000" background="#333">
					      <thead>
					          <tr>
					              <th>ID</th>
					              <th>TITLE</th>
					              <th>PRICE</th>
					              <th>AUTHOR</th>
					              <th>STATUS</th>
					          </tr>
					      </thead>
					      <tbody>
					      	<c:forEach items="${paging.items}" var="item" varStatus="index">
					          <tr class="${index.count % 2 ==0?'trow1':'trow2' }">
					              <td>${item.id}</td>
					              <td>${item.title}</td>
					              <td>${item.price}</td>
					              <td>${item.author}</td>
					              <td>${item.status}</td>
					          </tr>
					          </c:forEach>
					      </tbody>
					  </table>
					  <div class="h20"></div>
				      <div class="pager">
					       <wk:Paging paging="${paging}" callback="callback" titles="首页|前一页|下一页|末页"/>
					  </div>
					</div>
				</div>
			</div>
		</div>

		<!--左导航菜单-->
		<jsp:include page="/inc/_page_footer.jsp"></jsp:include>
	</body>
<script type="text/javascript">
	  	function callback(index){
	  		var url = "${pageContext.request.contextPath}/paging.do?index="+index;
	  		location.href=url;
	  	}
</script>
</html>