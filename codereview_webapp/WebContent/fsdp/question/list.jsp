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
	var operation = '<a href="#" onclick="showModifyDialog(\'' + row.questionid + '\');">[编辑]</a>&nbsp;';
	operation += '<a href="#" onclick="finedo.action.doDelete(\'datagrid\',\'${ctx}/finedo/question/delete\',\'' + row.questionid + '\')">[删除]</a>&nbsp;';
	if(row.status=="待处理"){
		operation += '<a href="#" onclick="showDealDialog(\'' + row.questionid + '\');">[处理]</a>&nbsp;';
	}
	return operation;
}

// 单元格重载
function formatName(row){
	return '<a href="#" onclick="showDetailDialog(\'' + row.questionid + '\');">' + row.title + '</a>&nbsp;';
}
function formatStatus(row){
	if(row.status=="待处理"){
		return '<font color=red>'+row.status+'</font>';
	}
	return '<font color=blue>'+row.status+'</font>';
}
function formatUser(row){
	return row.initiatorname+'['+row.initiator+']';
}
function showAddDialog() {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'新增信息',
		'url':'${ctx}/finedo/question/addpage'
	});
}

function showDealDialog(id) {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'修改信息',
		'url':'${ctx}/finedo/question/dealpage?questionid=' + id
	});
}

function showModifyDialog(id) {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'修改信息',
		'url':'${ctx}/finedo/question/modifypage?questionid=' + id
	});
}

function showDetailDialog(id) {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'详情信息',
		'url':'${ctx}/finedo/question/detail?questionid=' + id
	});
}
</script>
</head>

<body class="query-body">
  	
<div style="width:100%;">
	<!-- 工具栏  -->
	<div class="query-title">
		<!-- 标题 -->
    	<div class="query-title-name">上报问题管理</div>
         
        <div class="query-boxbig">
       		<input type="button" class="query-btn-nextstep" onclick="showAddDialog();" value="上报问题">
        </div>       	
   	</div>
  
    <!-- 表格栏  -->
    <fsdp:grid id="datagrid" url="${ctx }/finedo/question/query" selecttype="none">
    	<fsdp:field code="title" name="上报问题标题" width="300" formatter="formatName"></fsdp:field>
		<fsdp:field code="initiator" name="问题上报人" width="100" formatter="formatUser"></fsdp:field>
		<fsdp:field code="createtime" name="问题上报时间" width="150"></fsdp:field>
		<fsdp:field code="happentime" name="问题发生时间" width="150"></fsdp:field>
		<fsdp:field code="status" name="状态" width="50" formatter="formatStatus"></fsdp:field>
		<fsdp:field code="operation" name="操作" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
</div>
</body>
</html>