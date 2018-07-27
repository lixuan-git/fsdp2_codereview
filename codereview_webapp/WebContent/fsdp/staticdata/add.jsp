<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>静态数据新增</title>
${style_css }
${jquery_js }
${easyui_js }
${finedo_js }
${validator_js }
</head>
<body class="easyui-layout">
<div region="center" style="padding:5px;" border="false">
  <div class="easyui-panel" title="添加静态数据" style="width:auto;padding:10px;" fit="true">
	<form method="post" action="${ctx }/finedo/staticdata/addStaticdata" id="ajaxForm" name="ajaxForm">		
	<jsp:include page="/fsdp/staticdata/_common.jsp"></jsp:include>
	</form>
  </div>
</div>  
<div data-options="region:'south',border:false" style="text-align:right;padding:5px;">  
    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:save()">提交</a>
</div>  
</body>
</html>
