<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>组织机构选择</title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_dialog_js }
${finedo_tree_js }

<script language="javascript">
$(document).ready(function() {
	finedo.getTree('treeid',{
		url:'${ctx}/finedo/organization/queryorgtree',
		data:{"id":"0"},
		async:true,
		selecttype:'${param.selecttype }'
	});
});
</script>
</head>
<body>
<ul id="treeid" class="fdtree"></ul>
</body>
</html>