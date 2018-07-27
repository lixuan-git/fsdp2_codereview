<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_dialog_js }
${finedo_grid_js }
</head>
<%
String id=request.getParameter("id");
request.setAttribute("id", id);

%>
<body class="query-body">
  	
<div style="width:100%;">
	<!-- 工具栏  -->
	<div class="query-title">
		<!-- 标题 -->
    	<div class="query-title-name">定时器执行日志</div>
   	</div>
  
    <!-- 表格栏  -->
    <fsdp:grid id="datagrid" url="${ctx }/finedo/timer/querylog?id=${id }" selecttype="none">
    	<fsdp:field code="exectime" name="执行时间" width="200"></fsdp:field>
		<fsdp:field code="state" name="执行结果状态" width="150"></fsdp:field>
		<fsdp:field code="retmsg" name="执行结果描述" width="300"></fsdp:field>
	</fsdp:grid>
</div>
</body>
</html>