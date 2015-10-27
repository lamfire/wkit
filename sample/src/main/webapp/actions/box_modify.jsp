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
            <h2>编辑商家资料</h2>
            <div class="view">
                <form class="#">
                    <div class="G-form">
                    	<div><label>唯一标识：</label><input type="text" /></div>
                        <div><label>商家名称：</label><input type="text" /></div>
                        <div><label>绑定域名：</label><input type="text" /></div>
                        <div><label>过期时间：</label><input type="text" /></div>
                        <div><label>保证金额：</label><input type="text" /></div>
                        <div><label>级别：</label><select><option selected="selected">普通</option><option>VIP</option></select></div>
                        <div><label>所在区域：</label><select><option selected="selected">不限</option></select><select><option selected="selected">不限</option></select><select><option selected="selected">不限</option></select></div>
                        <div><label>管理员账号：</label><input type="text" /></div>
                        <div><label>管理员密码：</label><input type="text" /></div>
                        <div><label>状态：</label><select><option selected="selected">正常</option><option>无效</option></select></div>
                        <div class="btn">
                        	<label></label><button type="submit">确定</button><button id="cancel" type="button">取消</button>
                    	</div>
                    </div>
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
