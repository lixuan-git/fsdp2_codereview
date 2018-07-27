<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_grid_js }
${finedo_dialog_js }
${finedo_tree_js }
<c:set var="roletype" value=""></c:set>
</head>
<body>
<c:if test="${param.selecttype eq 'multi'}">
<c:set var="roletype" value="标准岗位角色"></c:set>
</c:if>
<c:if test="${param.selecttype eq 'single'}">
<c:set var="roletype" value="组织岗位角色"></c:set>
<div style="width:200px;">
<ul id="treeid" class="fdtree"></ul>
</div>
</c:if>
<input type="hidden" id="orgid" name="orgid"/>
<div <c:if test="${param.selecttype eq 'single'}">style="margin-left:200px;" </c:if>>
	<table id="datagrid" class="finedo-datagrid" colortr="finedo-colortr" url="${ctx }/finedo/role/queryRole" rownumbers="true" pagination="true" pagesize="10" oncerequest="false" servorder="false" selecttype="${param.selecttype}" scrollload="false">
	<thead>
		<tr>
			<th code="rolename" width="80" edit="false">角色名称</th>
			<th code="orgname" width="80" edit="false">组织节点</th>
			<th code="roletype" width="50" edit="false">类型</th>
			<th code="rolelvl" width="50" edit="false">级别</th>
			<th code="usercount" width="50" edit="false">限定人数</th>
			<th code="state" width="50" edit="false">状态</th>
		</tr>
	</thead>
	</table>
</div>

<script>
function doSearch(){  
	var param = {roletype: $('#roletype').val(), rolename: $('#rolename').val(), 
			orgid: $('#orgid').val()};
	finedo.getgrid("datagrid").query(param);
}  
function clickTree(node){
	if (node){
		var s = node.id;
		$("#orgid").val(s);
		doSearch();
	}
}
$(document).ready(function() {
	<c:if test="${param.selecttype eq 'single'}">
	finedo.getTree('treeid',{
		url:'${ctx}/finedo/organization/queryorgtree',
		data:{"id":"0"},
		async:true,
		onclick:clickTree
	});
	</c:if>
	
	finedo.getgrid('datagrid',{
		'queryparams':{'roletype':'${roletype }'}
	}).load();
});
</script>
</body>
</html>