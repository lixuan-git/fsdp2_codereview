<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>邮件查询</title>
${style_css }
${jquery_js }
${finedo_js }
${easyui_js }
${datepicker_js }
<script>
function doSearch(){  
	var param = {effdatebegin: $('#begintime').val(), effdateend: $('#endtime').val(), phoneno: $('#phoneno').val(), smscontent: $('#smscontent').val()};
	FINEDO.Action.doSearch('datagrid','${ctx }/finedo/sms/querySms',param);
}  
</script>
</head>
<body class="easyui-layout" >
<div region="center" style="padding: 4px;padding-bottom: 0px;" border="false" fit="true">
	<fsdp:grid id="datagrid" url="${ctx }/finedo/sms/querySms" title="当前页面：短信管理 &gt; 短信查询" toolbar="#tb">
		<fsdp:field code="phoneno" name="接收号码" width="100"></fsdp:field>
		<fsdp:field code="smscontent" name="发送内容" width="300"></fsdp:field>
		<fsdp:field code="effdate" name="发送时间" width="150"></fsdp:field>
		<fsdp:field code="state" name="状态" width="100"></fsdp:field>
	</fsdp:grid>
	
	<fsdp:toolbar id="tb">
		<fsdp:searchbar>
            <fsdp:text name="phoneno" id="phoneno" style="width:150px;" label="接收号码"></fsdp:text>&nbsp; 
            <fsdp:text name="smscontent" id="smscontent" style="width:150px;" label="短信内容"></fsdp:text>&nbsp; 
			发送时间：<fsdp:date id="begintime" name="begintime" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\\'endtime\\')||\\'2020-10-01\\'}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px"></fsdp:date> ~ <fsdp:date id="endtime" name="endtime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\\'begintime\\')}',maxDate:'2020-10-01',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px"></fsdp:date>&nbsp;
            <fsdp:button name="查询" iconcls="icon-search" onclick="doSearch()"></fsdp:button> 
		</fsdp:searchbar>
	</fsdp:toolbar>
</div>
</body>
</html>