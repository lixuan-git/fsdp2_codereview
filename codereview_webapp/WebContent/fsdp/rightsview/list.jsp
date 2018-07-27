<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>权限视图管理</title>
${style_css }
${jquery_js }
${finedo_js }
${easyui_js }
</head>
<body class="easyui-layout" >
<div region="center" style="padding: 4px;padding-bottom: 0px;" border="false" >
	<fsdp:treegrid id="treegrid" url="${ctx }/finedo/rightsview/queryViewTreeGrid" 
	title="当前页面：权限资源管理 &gt;权限视图管理" 
	idfield="id" treefield="name" toolbar="#tb" pagination="false">
		 <fsdp:field code="" name="" checkbox="true"></fsdp:field>
		 <fsdp:field code="id" name="节点编号" width="100"></fsdp:field>
		 <fsdp:field code="name" name="节点名称" width="250" formatter="formatNodename"></fsdp:field>
		 <fsdp:field code="rightname" name="所属权限" width="100"></fsdp:field>
		 <fsdp:field code="rightentry" name="访问入口" width="150"></fsdp:field>
		 <fsdp:field code="isnavigation" name="作用于导航" width="100" formatter="formatNav"></fsdp:field>
		 <fsdp:field code="operation" name="操作" formatter="formatOperation"></fsdp:field>
	</fsdp:treegrid>

	<fsdp:toolbar id="tb">
		<fsdp:buttonbar>
			<fsdp:button iconcls="icon-add" name="添加" plain="true" onclick="location.href='${ctx }/finedo/rightsview/addpage'"></fsdp:button>
			<fsdp:button iconcls="icon-remove" name="删除" plain="true"  onclick="FINEDO.Action.doBatchDelete('treegrid','${ctx}/finedo/rightsview/delete','id',doSearch)" ></fsdp:button>
		</fsdp:buttonbar>
	
		<fsdp:searchbar>
              <fsdp:text name="nodename" id="nodename" style="width:120px;" label="节点名称"></fsdp:text>&nbsp;
              <fsdp:button name="查询" iconcls="icon-search" onclick="doSearch()"></fsdp:button>
		</fsdp:searchbar>
	</fsdp:toolbar>
</div>
<script>
function doSearch(){  
	var param = {nodename: $('#nodename').val()};
	FINEDO.Mode.create();
	$.post('${ctx }/finedo/rightsview/queryViewTreeGrid',param,function(data){
		FINEDO.Mode.destroy();
		$('#treegrid').treegrid('loadData',data);   
	},'json');
}  	

function formatNodename(val,row){
	var operation = '<a href="${ctx}/finedo/rightsview/queryId?nodeid='+row.id+'">'+row.name+'</a>';
	return operation;
}

function formatNav(val,row){
	if(row.isnavigation=='是'){
		return '<font color="green">是</font>';
	}else{
		return '<font color="orange">否</font>';
	}
	
}

function formatOperation(val,row){
	var operation = '<a href="${ctx}/finedo/rightsview/queryId?nodeid='+row.id+'">[编辑]</a>&nbsp;';
	operation += '<a href="javascript:void(0)" onclick="FINEDO.Action.doDelete(\'datagrid\',\'${ctx}/finedo/rightsview/delete\',\''+row.id+'\',doSearch)">[删除]</a>&nbsp;';
	return operation;
}
</script>
</body>
</html>