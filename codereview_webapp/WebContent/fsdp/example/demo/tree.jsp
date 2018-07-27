<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_grid_js }
${finedo_upload_js }
${finedo_dialog_js }
${finedo_tree_js }
<script>
var zNodes =[
     	{ id:1, pid:0, name:"父节点1 - 展开", open:true},
     	{ id:11, pid:1, name:"父节点11 - 折叠"},
     	{ id:111, pid:11, name:"叶子节点111"},
     	{ id:112, pid:11, name:"叶子节点112"},
     	{ id:113, pid:11, name:"叶子节点113"},
     	{ id:114, pid:11, name:"叶子节点114"},
     	{ id:12, pid:1, name:"父节点12 - 折叠"},
     	{ id:121, pid:12, name:"叶子节点121"},
     	{ id:122, pid:12, name:"叶子节点122"},
     	{ id:123, pid:12, name:"叶子节点123"},
     	{ id:124, pid:12, name:"叶子节点124"},
     	{ id:13, pid:1, name:"父节点13 - 没有子节点", isParent:true},
     	{ id:2, pid:0, name:"父节点2 - 折叠", open:true},
     	{ id:21, pid:2, name:"父节点21 - 展开", open:true},
     	{ id:211, pid:21, name:"叶子节点211"},
     	{ id:212, pid:21, name:"叶子节点212"},
     	{ id:213, pid:21, name:"叶子节点213"},
     	{ id:214, pid:21, name:"叶子节点214"},
     	{ id:22, pid:2, name:"父节点22 - 折叠"},
     	{ id:221, pid:22, name:"叶子节点221"},
     	{ id:222, pid:22, name:"叶子节点222"},
     	{ id:223, pid:22, name:"叶子节点223"},
     	{ id:224, pid:22, name:"叶子节点224"},
     	{ id:23, pid:2, name:"父节点23 - 折叠"},
     	{ id:231, pid:23, name:"叶子节点231"},
     	{ id:232, pid:23, name:"叶子节点232"},
     	{ id:233, pid:23, name:"叶子节点233"},
     	{ id:234, pid:23, name:"叶子节点234"},
     	{ id:3, pid:0, name:"父节点3 - 没有子节点", isParent:true}
];
function parseChildren(data, node){
	for(var i = 0; i < data.length; i++){
		if(data[i].pid != node.id){
			continue;
		}
		if(finedo.fn.isNon(node.children)){
			node.children = [];
		}
		node.children.push(data[i]);
		parseChildren(data, data[i]);
	}
}

function doTest(){
	var formatdata = [];
	for(var i = 0; i < zNodes.length; i++){
		if(zNodes[i].pid != '0' || finedo.fn.isNotNon(zNodes[i].pid)){
			continue;
		}
		formatdata.push(zNodes[i]);
		parseChildren(zNodes, zNodes[i]);
	}
	alert(JSON.stringify(formatdata));
}
function doTreeClick(data){
	alert(JSON.stringify(data));
}
function doTree(){
	var tree = finedo.getTree('treediv',{
		simple:true,
		//selecttype:'multi',
		onclick1:function(data){
			alert(JSON.stringify(data));
		}
	});
	tree.test(zNodes);
}
$(document).ready(function() {
	//doTree();
	finedo.getTree('treediv',{
		url:'${ctx}/finedo/organization/queryorgtree',
		data:{"id":"0"},
		async:true,
		selecttype:'single'
	});
});

</script>
</head>
<body>
<form id="frm" name="frm">
<ul id="treediv" class="fdtree"></ul>
</form>
</body>
</html>