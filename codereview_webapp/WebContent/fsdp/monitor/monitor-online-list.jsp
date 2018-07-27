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
${finedo_date_js }

</head>

<body class="query-body">
  	
<div style="width:100%;">
	<!-- 工具栏  -->
	<div class="query-title">
		<!-- 标题 -->
    	<div class="query-title-name">在线用户监控</div> 	
   	</div>
  	
    <!-- 表格栏  -->
    <fsdp:grid id="datagrid" url="${ctx }/finedo/monitor/queryOnline" selecttype="none" servorder="true">
    	<fsdp:field code="usercode" name="用户账号" width="100"></fsdp:field>
    	<fsdp:field code="username" name="用户名称" width="150"></fsdp:field>
		<fsdp:field code="logintime" name="登录时间" width="150" order="true"></fsdp:field>
		<fsdp:field code="token" name="TOKEN" width="300"></fsdp:field>
	</fsdp:grid>
</div>
</body>
</html>