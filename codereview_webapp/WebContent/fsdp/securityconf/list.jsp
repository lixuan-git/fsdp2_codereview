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

function formatOperation(row){
	var operation = '<a href="#" onclick="showDialog(\'' + row.passwdtype + '\');">[修改]</a>&nbsp;';
	return operation;
}

function formatPasswdtype(row){
	var operation = '<a href="#" onclick="showDialog(\'' + row.passwdtype + '\');">' + row.passwdtype + '</a>&nbsp;';
	return operation;
}

function showDialog(passwdtype) {
	finedo.dialog.show({
		width:950,
		height:550,
		'title':'静态数据信息',
		'url':'${ctx }/finedo/securityconf/edit?passwdtype=' + passwdtype
	});
}
</script>
</head>

<body class="query-body">
  	
<div>
	<div class="query-title">
		<!-- 标题 -->
    	<div class="query-title-name">密码规则管理</div>
   	</div>
  
	<!-- 表格栏  -->
    <fsdp:grid id="datagrid" url="${ctx }/finedo/securityconf/querySecurityconf" selecttype="none">
    	
		<fsdp:field code="passwdtype" name="密码类型" width="80" formatter="formatPasswdtype"></fsdp:field>
		<fsdp:field code="validday" name="有效天数" width="60"></fsdp:field>
		<fsdp:field code="minlength" name="密码最小长度" width="90"></fsdp:field>
		<fsdp:field code="maxlength" name="密码最大长度" width="90"></fsdp:field>
		<fsdp:field code="specialnum" name="特殊字符最小数量" width="100"></fsdp:field>
		<fsdp:field code="capitalnum" name="大写字母最小数量" width="100"></fsdp:field>
		<fsdp:field code="lowercasenum" name="小写字母最小数量" width="100"></fsdp:field>
		<fsdp:field code="digitalnum" name="数字最小数量" width="100"></fsdp:field>
		<fsdp:field code="operation" name="操作" formatter="formatOperation"></fsdp:field>
	</fsdp:grid>
</div>
</body>
</html>