<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp"%>

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
	function formatOperation(row) {
		var operation = '<a href="#" onclick="showModifyDialog(\'' + row.appid+ '\',\'' + row.appname+ '\',\'' + row.platform+ '\',\'' + row.appdesc+ '\');">[详情]</a>&nbsp;';
		operation += '<a href="#" onclick="publish(\'' + row.appid
				+ '\');">[发布]</a>&nbsp;';
		operation += '<a href="#" onclick="addGrayObject(\'' + row.appid
				+ '\');">[灰度]</a>&nbsp;';
		operation += '<a href="#" onclick="finedo.action.doDelete(\'datagrid1\',\'${ctx}/finedo/appmanage/delete\',\''
				+ row.appid + '\')">[删除]</a>&nbsp;';
		return operation;
	}
	function showModifyDialog(appid,appname,platform,appdesc) {

		$("#appname").val(appname);
		$("#platform").val(platform);
		$("#appid").val(appid);
		$("#appdesc").val(appdesc);
		$("#forsubmit").submit();
	}
	
	function publish(appid) {
		$("#publishid").val(appid);
		$("#publish").submit();
	}
	function addGrayObject(appid) {
		$("#addgrayid").val(appid);
		$("#addgray").submit();
	}
	function queryAPP() {
		var usercode = $('#typename1').val();

		var queryparams = {
			"appname" : usercode
		};
		var options = {
			"queryparams" : queryparams
		};
		finedo.getgrid('datagrid1', options).load();
	}
	function queryAPPLog() {
		var usercode = $('#typename2').val();

		var queryparams = {
			"appname" : usercode
		};
		var options = {
			"queryparams" : queryparams
		};
		finedo.getgrid('datagrid2', options).load();
	}
</script>
</head>

<body class="query-body">

	<div style="width: 100%;">
		<!-- 工具栏  -->
		<div class="query-title">
			<!-- 标题 -->
			<div class="query-title-name">应用列表</div>

		</div>
		<div
			style="float: left; width: 100%; padding-bottom: 10px; display: inline-block; text-indent: 10px;">
			应用名称：<input class="query-text" id="typename1" type="text" value="">&nbsp;
			<input type="button" class="query-btn" onclick="queryAPP();"
				value="查询">

		</div>
		<!-- 表格栏  -->
		<fsdp:grid id="datagrid1" url="${ctx }/finedo/appmanage/query">
			<fsdp:field code="appname" name="应用名称" width="100"></fsdp:field>
			<fsdp:field code="platform" name="平台" width="100"></fsdp:field>
			<fsdp:field code="appdesc" name="应用描述" width="200"></fsdp:field>
			<fsdp:field code="operation" name="操作" width="100"
				formatter="formatOperation"></fsdp:field>
		</fsdp:grid>
	</div>


	<form action="${ctx }/finedo/appmanage/appdetailpage" id="forsubmit"
		method="post">
		<input name="appname" id="appname" type="hidden"> <input
			name="platform" id="platform" type="hidden"> <input
			name="appdesc" id="appdesc" type="hidden"> <input
			name="appid" id="appid" type="hidden">
	</form>
	<form action="${ctx }/finedo/appmanage/apppublishpage" id="publish"
		method="post">
		<input name="publishid" id="publishid" type="hidden">
	</form>

	<form action="${ctx }/finedo/appmanage/addgraypage" id="addgray"
		method="post">
		<input name="addgrayid" id="addgrayid" type="hidden">
	</form>
</body>
</html>


