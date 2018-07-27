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

<script>
// 表格"操作"单元格重载
function formatOperation(row){
	var operation = '<a href="#" onclick="showModifyDialog(\'' + row.id + '\');">[编辑]</a>&nbsp;';
	operation += '<a href="#" onclick="finedo.action.doDelete(\'datagrid\',\'${ctx}/finedo/timer/delete\',\'' + row.id + '\')">[删除]</a>&nbsp;';
	operation += '<a href="#" onclick="showLogDialog(\'' + row.id + '\');">[查看执行日志]</a>&nbsp;';
	return operation;
}

// 单元格重载
function formatName(row){
	return '<a href="#" onclick="showDetailDialog(\'' + row.id + '\');">' + row.name + '</a>&nbsp;';
}

function showAddDialog() {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'新增信息',
		'url':'${ctx}/finedo/timer/addpage'
	});
}

function showModifyDialog(id) {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'修改信息',
		'url':'${ctx}/finedo/timer/modifypage?id=' + id
	});
}

function showDetailDialog(id) {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'详情信息',
		'url':'${ctx}/finedo/timer/detail?id=' + id
	});
}

function showLogDialog(id) {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'详情信息',
		'url':'${ctx}/finedo/timer/timelogpage?id=' + id
	});
}
</script>
</head>

<body class="query-body">
  	
<div style="width:100%;">
	<!-- 工具栏  -->
	<div class="query-title">
		<!-- 标题 -->
    	<div class="query-title-name">定时器管理</div>
         
        <div class="query-boxbig">
       		<input type="button" class="query-btn-nextstep" onclick="showAddDialog();" value="新建定时器">
        </div>       	
   	</div>
  
    <!-- 表格栏  -->
    <fsdp:grid id="datagrid" url="${ctx }/finedo/timer/query" selecttype="none">
    	<fsdp:field code="name" name="定时任务名称" width="200" formatter="formatName"></fsdp:field>
		<fsdp:field code="beanname" name="Bean类名" width="150"></fsdp:field>
		<fsdp:field code="method" name="Bean类方法名" width="150"></fsdp:field>
		<fsdp:field code="cron" name="CRON" width="200"></fsdp:field>
		<fsdp:field code="optdate" name="创建时间" width="150"></fsdp:field>
		<fsdp:field code="operation" name="操作" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
</div>
</body>
</html>