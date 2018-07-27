<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>操作日志查询</title>
${style_css }
${jquery_js }
${finedo_js }
${easyui_js }
${datepicker_js }
<script>
function doSearch(){  
	var param =  {opttimebegin: $('#opttimebegin').val(),
			opttimeend:$("#opttimeend").val()};
	FINEDO.Action.doSearch('datagrid','${ctx }/finedo/log/queryLoginLog',param);
} 
</script>
</head>
<body class="easyui-layout" >
<div region="center" style="padding: 4px;padding-bottom: 0px;" border="false" fit="true">
	
	
	<fsdp:grid url="${ctx }/finedo/log/queryBusiLog" title="当前页面：日志管理 &gt; 操作日志查询" toolbar="#tb">
		<fsdp:field code="optsn" name="业务流水号" width="110"></fsdp:field>
		<fsdp:field code="optrname" name="操作人" width="80"></fsdp:field>
		<fsdp:field code="opname" name="操作名称" width="220"></fsdp:field>
		<fsdp:field code="opdesc" name="操作描述" width="350"></fsdp:field>
		<fsdp:field code="opttime" name="操作时间" width="150"></fsdp:field>
	</fsdp:grid>
	
	<fsdp:toolbar id="tb">
	
		<fsdp:searchbar>
		
		   操作时间：<fsdp:date id="opttimebegin" name="opttimebegin" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\\'opttimeend\\')||\\'2020-10-01\\'}'})" style="width:120px"></fsdp:date> ~ <fsdp:date id="opttimeend" name="opttimeend" onfocus="WdatePicker({minDate:'#F{$dp.$D(\\'opttimebegin\\')}',maxDate:'2020-10-01'})" style="width:120px"></fsdp:date>&nbsp; 
                    <fsdp:button name="查询" onclick="doSearch()" iconcls="icon-search"></fsdp:button>
		</fsdp:searchbar>
	</fsdp:toolbar>
</div>
</body>
</html>