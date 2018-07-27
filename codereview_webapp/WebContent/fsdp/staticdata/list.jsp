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

<script type="text/javascript">
function formatOperation(row){
	var operation = '<a href="#" onclick="showDetailDialog(\'' + row.typeid + '\');">[编辑]</a>&nbsp;';
	operation += '<a href="javascript:void(0)" onclick="finedo.action.doDelete(\'datagrid\',\'${ctx}/finedo/staticdata/deleteStaticdata\',\''+row.typeid+'\')">[删除]</a>&nbsp;';
	return operation;
}
function formatTypename(row){
	var operation = '<a href="#" onclick="showDetailDialog(\'' + row.typeid + '\');">' + row.typename + '</a>&nbsp;';
	return operation;
}

function doQueryFast() {
	var param = {typename: $('#typename').val()};
	finedo.getgrid("datagrid").query(param);
}

function showAddDialog() {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'静态数据信息',
		'url':'${ctx }/finedo/staticdata/addpage'
	});
}

function showDetailDialog(typeid) {
	finedo.dialog.show({
		width:850,
		height:550,
		'title':'静态数据信息',
		'url':'${ctx}/finedo/staticdata/queryStaticdataById?typeid=' + typeid
	});
}
</script>
</head>

<body class="query-body">

<div>
	<!-- 工具栏  -->
	<div class="query-title">
		<!-- 标题 -->
    	<div class="query-title-name">静态数据管理 </div>
    	
    	<div class="query-boxbig">
    		<input type="button" class="query-btn-nextstep" onclick="showAddDialog();" value="新建静态数据">
    		
    		<div class="query-box">
	        	<input type="text" class="query-box-text" id="typename" value="">
	            <input type="button" class="query-box-magnifier" onclick="doQueryFast();">
	        </div>
            <input type="button" class="query-btn-nextstep" onclick="doQueryFast();" value="查询">  
    	</div>
        
   </div>
       
    <!-- 表格栏  -->
    <fsdp:grid id="datagrid" url="${ctx }/finedo/staticdata/queryStaticdata" selecttype="multi">
		<fsdp:field code="typename" name="类型名称" width="150" formatter="formatTypename"></fsdp:field>
		<fsdp:field code="datatype" name="数据类型" width="100"></fsdp:field>
		<fsdp:field code="configtype" name="配置类型" width="100"></fsdp:field>
		<fsdp:field code="lvl" name="级别" width="150"></fsdp:field>
		<fsdp:field code="remark" name="数据项" width="150"></fsdp:field>
		<fsdp:field code="operation" name="操作" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
</div>

</body>
</html>