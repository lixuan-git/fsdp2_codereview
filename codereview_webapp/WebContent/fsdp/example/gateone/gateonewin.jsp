<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${jquery_js }

</head>
<body>

<script>
$(document).ready(function() {
	alert("111");
	
	var options="height=600, width=600, menubar=no, resizable=no, scrollbars=no, titlebar=no, toolbar=no, z-look=yes, location=no";
	window.open("gateoneshell.jsp", "_self", options);
});
</script>

</body>
</html>

