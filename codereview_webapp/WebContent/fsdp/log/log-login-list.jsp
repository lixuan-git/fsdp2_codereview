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

<script type="text/javascript">
function doQueryFast() {
	var param = {logindatebegin: $("#logindatebegin").val(),logindateend: $("#logindateend").val()};
	finedo.getgrid("datagrid").query(param);
}
</script>
</head>

<body class="query-body">
  	
<div style="width:100%;">
	<!-- 工具栏  -->
	<div class="query-title">
		<!-- 标题 -->
    	<div class="query-title-name">登录日志查询</div>
         
        <div class="query-boxbig">
        	<input class="query-fast-date" type="text" style="width:160px;" id="logindatebegin" name="logindatebegin" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			-
			<input class="query-fast-date" type="text" style="width:160px;" id="logindateend" name="logindateend" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
        	<input type="button" class="query-btn-nextstep" onclick="doQueryFast();" value="查询">  
        </div>       	
   	</div>
  
    <!-- 表格栏  -->
    <fsdp:grid id="datagrid" url="${ctx }/finedo/log/queryLoginLog" selecttype="none">
    	<fsdp:field code="userid" name="用户ID" width="60"></fsdp:field>
    	<fsdp:field code="username" name="用户名称" width="60"></fsdp:field>
		<fsdp:field code="state" name="状态" width="60"></fsdp:field>
		<fsdp:field code="ipaddr" name="IP地址" width="100"></fsdp:field>
		<fsdp:field code="logindate" name="登录时间" width="100"></fsdp:field>
		<fsdp:field code="logoutdate" name="退出时间" width="100"></fsdp:field>
		<fsdp:field code="token" name="TOKEN" width="60"></fsdp:field>
	</fsdp:grid>
</div>
</body>
</html>