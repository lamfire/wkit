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
    <!--主显示区域-->
    <div class="winbox">
        <div class="wapper">
            <h2>商家资料</h2>
            <div id="scrollbar" style="widht:300px;height:200px;">
	            <div class="view">
	            	<div><label>唯一标识:</label>meifubao</div>
	                <div><label>商家名称:</label>美肤宝</div>
	                <div><label>绑定域名:</label>无</div>
	                <div><label>过期时间:</label>2011-2-3</div>
	                <div><label>保证金额:</label>500</div>
	                <div><label>级别:</label>普通</div>
	                <div><label>所在区域:</label>中国，广东，广州</div>
	                <div><label>管理员账号:</label>admin</div>
	                <div><label>状态:</label>正常</div>
	            </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
$(function() {
		$('#scrollbar').rollbar({zIndex:100,wheelSpeed:10});
	});
</script>
</html>
