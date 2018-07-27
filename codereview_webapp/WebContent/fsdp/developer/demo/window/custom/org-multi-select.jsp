<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>多机构选择窗口使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
</head>
<body>
	<h2>多机构选择窗口</h2>
	<p>多机构选择窗口可以通过 &lt;fsdp:orgmultiselect&gt;标签创建。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">基本用法</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
		&lt;fsdp:orgmultiselect id="org" idfield="orgid" namefield="orgname" >&lt;/fsdp:orgmultiselect>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:orgmultiselect id="org" idfield="orgid" namefield="orgname" ></fsdp:orgmultiselect>
	</div>

</body>
</html>