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
            <h2>删除商家</h2>
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
	            <form action="#">
	                <div><button type="submit">确定</button><button id="cancel" type="button">取消</button></div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#cancel").bind("click",function(){
			parent.$.fancybox.close();
		});
	});
</script>
</html>
