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
<!--头-->
<jsp:include page="/inc/_page_header.jsp"></jsp:include>

<!--身-->
<div class="page-main">
	<!--左导航菜单-->
    <jsp:include page="/inc/_menu.jsp"></jsp:include>
    <!--主显示区域-->
    <div class="main" id="imOutterArea">
        <div class="wapper">
            <h2>查找商家</h2>
            <div class="view">
				<div class="tab-option">
                    <ul class="tabBnt" id="tabBar">
                        <li class="act"><a href="#">简单查询</a> </li>
                        <li><a href="#">高级查询</a></li>
                    </ul>
                    <div class="tabBox">
                        <div class="tabCtn processTable">
                        	<textarea  class="xheditor {width:'900px',height:'400px'}" ></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--页脚-->
<jsp:include page="/inc/_page_footer.jsp"></jsp:include>
</body>
<script src="${pageContext.request.contextPath}/res/xheditor/xheditor-1.1.11-zh-cn.min.js"></script>
</html>
