<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>模块管理</title>
${style_css }
${jquery_js }
${finedo_js }
${easyui_js }
<script>
function doSearch(){  
	var param = {modulename: $('#modulename').val()};
	
	FINEDO.Action.doSearch('datagrid','${ctx }/finedo/module/query',param);
}

function formatOperation(val,row){
	var operation = '<a href="${ctx}/finedo/module/queryId?moduleid='+row.moduleid+'">[编辑]</a>&nbsp;';
	operation += '<a href="javascript:void(0)" onclick="FINEDO.Action.doDelete(\'datagrid\',\'${ctx}/finedo/module/delete\',\''+row.moduleid+'\',doSearch)">[删除]</a>&nbsp;';
	
	return operation;
}
function formatId(val,row){
	var operation = '<a href="${ctx}/finedo/module/queryId?moduleid='+row.moduleid+'">'+row.moduleid+'</a>&nbsp;';
	return operation;
}
function formatName(val,row){
	var operation = '<a href="${ctx}/finedo/module/queryId?moduleid='+row.moduleid+'">'+row.modulename+'</a>&nbsp;';
	return operation;
}
</script>
</head>
<body class="easyui-layout" >
<div region="center" style="padding: 4px;padding-bottom: 0px;" border="false" fit="true">
	
	
	<fsdp:grid id="datagrid" url="${ctx }/finedo/module/query" title="当前页面：权限资源管理 &gt;模块管理" toolbar="#tb">
		<fsdp:field code="" name="" checkbox="true"></fsdp:field>
		<fsdp:field code="moduleid" name="模块编号" width="150" formatter="formatId"></fsdp:field>
		<fsdp:field code="modulename" name="模块名称" width="150" formatter="formatName"></fsdp:field>
		<fsdp:field code="packagename" name="所属包" width="100"></fsdp:field>
		<fsdp:field code="state" name="模块状态" width="120"></fsdp:field>
		<fsdp:field code="version" name="当前版本" width="80"></fsdp:field>
		<fsdp:field code="moduledesc" name="模块说明" width="80"></fsdp:field>
		<fsdp:field code="operation" name="操作" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
	
	<fsdp:toolbar id="tb">
		<fsdp:buttonbar>
			<fsdp:button iconcls="icon-add" name="添加" plain="true" onclick="location.href='${ctx }/finedo/module/addpage'"></fsdp:button>
			<fsdp:button iconcls="icon-remove" name="删除" plain="true" onclick="FINEDO.Action.doBatchDelete('datagrid','${ctx}/finedo/module/delete','moduleid',doSearch)"></fsdp:button>
		</fsdp:buttonbar>
		<fsdp:searchbar>
			 <fsdp:text name="modulename" id="modulename" style="width:120px;" label="模块名称"></fsdp:text>&nbsp; 
             
                    <fsdp:button name="查询" iconcls="icon-search" onclick="doSearch()"></fsdp:button>
		</fsdp:searchbar>
	</fsdp:toolbar>
</div>

<iframe id="downframe" style="display:none"/>
</body>
</html>