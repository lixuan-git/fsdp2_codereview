<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_snapscreen_js }

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language="javascript">
	function snapscreen(){
		finedo.getsnapscreen({
			"hostname":"${pageContext.request.serverName}",
			"port":"${pageContext.request.serverPort}"
		}).snapscreen();
	}
</script>

</head>
<body>
<input type="button" value="截屏" onclick="snapscreen()">
</body>
</html>