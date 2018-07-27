<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>权限管理</title>
${style_css }
${jquery_js }
${easyui_js }
${finedo_js }
${validator_js }
</head>
<body class="easyui-layout">
<div region="center" style="padding:5px;" border="false">
  <div class="easyui-panel" title="当前位置：权限资源管理  &gt; 权限管理 &gt; 修改权限" style="width:auto;padding:10px;" fit="true">
	<form method="post" action="${ctx }/finedo/rights/modify?rightid=${rights.rightid}" id="ajaxForm" >		
		<jsp:include page="/fsdp/rights/_common.jsp"></jsp:include>
	</form>
  </div>
</div>  
<div data-options="region:'south',border:false" style="text-align:right;padding:5px;">  
    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:save()">提交</a>  
   <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="window.history.go(-1)">取消</a>  
</div>  
</body>
</html>
