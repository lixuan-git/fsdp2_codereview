<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
${style_css }
${jquery_js }
${easyui_js }
${finedo_js }
${validator_js }

${finedo_core_js }
${finedo_dialog_js }
${finedo_choose_js }
</head>
<body class="easyui-layout">
<div region="center" style="padding:5px;" border="false">
  <div class="easyui-panel" title="修改岗位角色" style="width:auto;padding:4px;" fit="true">
	<form method="post" action="${ctx }/finedo/role/modifyRole?roleid=${role.sysrole.roleid}" id="ajaxForm" name="ajaxForm">	
	<jsp:include page="/fsdp/role/_common.jsp"></jsp:include>
	</form>
  </div>
</div>  
<div data-options="region:'south',border:false" style="text-align:right;padding:5px;">  
    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:save()">提交</a>  
</div>  
</body>
</html>
