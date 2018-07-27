<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>权限管理</title>
${style_css }
${jquery_js }
${finedo_js }
${easyui_js }
<script>
function doSearch(){  
	var param = {rightname: $('#rightname').val()};
	
	FINEDO.Action.doSearch('datagrid','${ctx }/finedo/rights/query',param);
}

function formatOperation(val,row){
	var operation = '<a href="${ctx}/finedo/rights/queryId?rightid='+row.rightid+'">[编辑]</a>&nbsp;';
	operation += '<a href="javascript:void(0)" onclick="FINEDO.Action.doDelete(\'datagrid\',\'${ctx}/finedo/rights/delete\',\''+row.rightid+'\',doSearch)">[删除]</a>&nbsp;';
	
	return operation;
}
function formatId(val,row){
	var operation = '<a href="${ctx}/finedo/rights/queryId?rightid='+row.rightid+'">'+row.rightid+'</a>&nbsp;';
	return operation;
}
function formatName(val,row){
	var operation = '<a href="${ctx}/finedo/rights/queryId?rightid='+row.rightid+'">'+row.rightname+'</a>&nbsp;';
	return operation;
}
</script>
</head>
<body class="easyui-layout" >
<div region="center" style="padding: 4px;padding-bottom: 0px;" border="false" fit="true">
	
	
	<fsdp:grid id="datagrid" url="${ctx }/finedo/rights/query" title="当前页面：权限资源管理 &gt;权限管理" toolbar="#tb">
		<fsdp:field code="" name="" checkbox="true"></fsdp:field>
		<fsdp:field code="rightid" name="权限编号" width="150" formatter="formatId"></fsdp:field>
		<fsdp:field code="rightname" name="权限名称" width="150" formatter="formatName"></fsdp:field>
		<fsdp:field code="modulename" name="所属模块" width="150"></fsdp:field>
		<fsdp:field code="operation" name="操作" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
	
	<fsdp:toolbar id="tb">
		<fsdp:buttonbar>
			<fsdp:button iconcls="icon-add" name="添加" plain="true" onclick="location.href='${ctx }/finedo/rights/queryModuleForAdd'"></fsdp:button>
			<fsdp:button iconcls="icon-remove" name="删除" plain="true" onclick="FINEDO.Action.doBatchDelete('datagrid','${ctx}/finedo/rights/delete','rightid',doSearch)"></fsdp:button>
		</fsdp:buttonbar>
		<fsdp:searchbar>
			 <fsdp:text name="rightname" id="rightname" style="width:120px;" label="权限名称"></fsdp:text>&nbsp; 
             
                    <fsdp:button name="查询" iconcls="icon-search" onclick="doSearch()"></fsdp:button>
		</fsdp:searchbar>
	</fsdp:toolbar>
</div>

<iframe id="downframe" style="display:none"/>
</body>
</html>