<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>异常日志查询</title>
${style_css }
${jquery_js }
${finedo_js }
${easyui_js }
${datepicker_js }
<script>
function doSearch(){  
	var param = {opttimebegin:$("opttimebegin").val(),opttimeend:$("opttimeend").val()};
	FINEDO.Action.doSearch('datagrid','${ctx }/finedo/log/queryLoginLog',param);
}  
</script>
</head>
<body class="easyui-layout" >
<div region="center" style="padding: 4px;padding-bottom: 0px;" border="false" fit="true">
	
	
	<fsdp:grid url="${ctx }/finedo/log/querySystemLog" title="当前页面：日志管理 &gt; 系统日志查询" toolbar="#tb">
		<fsdp:field code="optsn" name="业务流水号" width="110"></fsdp:field>
		<fsdp:field code="exceptiontype" name="异常类型" width="80"></fsdp:field>
		<fsdp:field code="exceptioncontent" name="异常内容" width="250"></fsdp:field>
		<fsdp:field code="logtime" name="异常发生时间" width="100"></fsdp:field>
		<fsdp:field code="opttime" name="操作时间" width="100"></fsdp:field>
	</fsdp:grid>
	
	<fsdp:toolbar id="tb">
		
		<fsdp:searchbar>
		<%-- 异常类型：<fsdp:select datasource="exceptiontype" style="150px" name="exceptiontype" id="exceptiontype"></fsdp:select>&nbsp; --%>
                    异常发生时间：<fsdp:date id="opttimebegin" name="opttimebegin" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\\'opttimeend\\')||\\'2020-10-01\\'}'})" style="width:120px"></fsdp:date> ~ <fsdp:date id="opttimeend" name="opttimeend" onfocus="WdatePicker({minDate:'#F{$dp.$D(\\'opttimebegin\\')}',maxDate:'2020-10-01'})" style="width:120px"></fsdp:date>&nbsp;
                    <fsdp:button name="查询" onclick="doSearch()"  iconcls="icon-search"></fsdp:button>
		</fsdp:searchbar>
	</fsdp:toolbar>
</div>
</body>
</html>