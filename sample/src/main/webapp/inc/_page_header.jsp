<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!--页头-->
<div class="page-header">
	<div class="logo">
		<img alt="logo" src="${pageContext.request.contextPath}/res/images/logo.png">
	</div>
	<span class="title">欢迎使用WKIT-DEMO</span>
    <div class="wellcome">
    </div>
</div>
<script type="text/javascript">
(function(){
    $("a#chpassword").fancybox({
				'padding'			: 0,
				'scrolling'			:'no',
				'autoScale'			: false,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'width'				: '50',
				'height'			: '35',
				'type'				: 'iframe'
		});
})();
</script>
