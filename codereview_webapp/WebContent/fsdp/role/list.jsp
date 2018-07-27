<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<!DOCTYPE html>
<html>
<head>
<title></title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_grid_js }
${finedo_dialog_js }
${finedo_tree_js }
${finedo_grid_js }
${finedo_commonui_js }
<script>
	$(document).ready(function() {
		finedo.getTree('treediv',{
			url:'${ctx}/finedo/organization/queryorgtree',
			async:true,
			selecttype:'single',
			data:{"id":"0"},
			onclick:function(data){
				orgid=data.id;
				var param = {orgnode:data.id};
				finedo.getgrid("datagrid").query(param);
			}
		});
	});
	var a=0;
	function importcallback(jsondata) {
		a=a+1;
		if(length==a){
			a=0;
			window.location.reload(true);
		}
	
		
	}
	var orgid="";
	function formatOperation(row){
		var operation = '<a href="#" onclick="showModifyDialog(\'' + row.roleid + '\');">[编辑]</a>&nbsp;';
		operation += '<a href="javascript:void(0)" onclick="finedo.action.doDelete(\'datagrid\',\'${ctx }/finedo/role/deleteRole\',\'' + row.roleid + '\')">[删除]</a>&nbsp;';
		return operation;
	}
	
	/************** 增加、导入、修改、删除、导出函数定义****************/
	function showAddDialog() {
		finedo.dialog.show({
			width:950,
			height:550,
			'title':'新增信息',
			'url':'${ctx}/finedo/role/addpage'
		});
	}

	function showModifyDialog(roleid) {
		finedo.dialog.show({
			width:950,
			height:550,
			'title':'修改信息',
			'url':'${ctx}/finedo/role/queryId?roleid=' + roleid
		});
	}
	
	function doQueryFast() {
		var text=$('#query-box-text').val();
		var param = {rolename:text};
		finedo.getgrid("datagrid").query(param);
	}

</script>
</head>
<div style=" width: 18%; float: left; border:1px solid #ddd; position: fixed; top:20px; left:10px; bottom:10px; ">
	<div class="tree-name">组织结构</div>

	<ul id="treediv" class="fdtree" style=" margin-left:5px;" ></ul>
</div>
<div style=" width: 78%; float: right; margin-top:20px;  margin-right:2%;">
	<div class="query-title">
		<!-- 标题 -->
    	<div class="query-title-name">岗位角色管理 </div>
    	
    	<div class="query-boxbig">
	    	<input type="button" class="query-btn-nextstep" onclick="showAddDialog();" value="新建岗位角色">
       	 	
    		<div class="query-fast">
	        	<input type="text" class="query-fast-text" id="query-box-text" value="">
	            <input type="button" class="query-fast-magnifier" onclick="doQueryFast();">
	        </div>
    	</div>
   	</div>
    <fsdp:grid id="datagrid" url="${ctx }/finedo/role/queryRole" selecttype="none" >
		<fsdp:field code="rolename" name="角色名称" width="200"></fsdp:field>
		<fsdp:field code="orgname" name="组织节点" width="200"></fsdp:field>
		<fsdp:field code="roletype" name="类型" width="100"></fsdp:field>
		<fsdp:field code="rolelvl" name="级别" width="80"></fsdp:field>
		<fsdp:field code="usercount" name="限定人数" width="80"></fsdp:field>
		<fsdp:field code="state" name="状态" width="100"></fsdp:field>
		<fsdp:field code="operation" name="操作" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
</div>
<iframe id="downiframe" name="downiframe" style="display:none" ></iframe>
<body>

</body>
</html>
