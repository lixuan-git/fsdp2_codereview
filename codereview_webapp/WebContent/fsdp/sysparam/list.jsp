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
	var operation = '<a href="#" onclick="showDetailDialog(\'' + row.paramid + '\');">[编辑]</a>&nbsp;';
	operation += '<a href="javascript:void(0)" onclick="finedo.action.doDelete(\'datagrid\',\'${ctx}/finedo/sysparam/deleteSysParam\',\'' + row.paramid + '\')">[删除]</a>&nbsp;';
	return operation;
}

function formatParamname(row){
	var operation = '<a href="#" onclick="showDetailDialog(\'' + row.paramid + '\');">'+row.paramname+'</a>&nbsp;';
	return operation;
}

function doQueryFast() {
	var param = {paramname: $('#paramname').val()};
	finedo.getgrid("datagrid").query(param);
}

function showAddDialog() {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'系统参数信息',
		'url':'${ctx }/finedo/sysparam/addpage'
	});
}

function showDetailDialog(paramid) {
	finedo.dialog.show({
		width:850,
		height:550,
		'title':'系统参数信息',
		'url':'${ctx}/finedo/sysparam/querySysParamById?id=' + paramid
	});
}
</script>
</head>

<body class="query-body">

<div>
	<!-- 工具栏  -->
	<div class="query-title">
		<!-- 标题 -->
    	<div class="query-title-name">系统参数管理 </div>
    	
    	<div class="query-boxbig">
    		<input type="button" class="query-btn-nextstep" onclick="showAddDialog();" value="新建系统参数">
    		
    		<div class="query-box">
	        	<input type="text" class="query-box-text" id="typename" value="">
	            <input type="button" class="query-box-magnifier" onclick="doQueryFast();">
	        </div>
            <input type="button" class="query-btn-nextstep" onclick="doQueryFast();" value="查询">  
    	</div>
   </div>
       
    <!-- 表格栏  -->
    <fsdp:grid id="datagrid" url="${ctx }/finedo/sysparam/querysysparam" selecttype="multi">
		<fsdp:field code="configtypename" name="参数类型" width="100"></fsdp:field>
		<fsdp:field code="paramname" name="参数名称" width="100" formatter="formatParamname"></fsdp:field>
		<fsdp:field code="paramvalue" name="参数值" width="500"></fsdp:field>
		<fsdp:field code="datatype" name="数据类型" width="80"></fsdp:field>
		<fsdp:field code="operation" name="操作" width="100" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
</div>

</body>
</html>