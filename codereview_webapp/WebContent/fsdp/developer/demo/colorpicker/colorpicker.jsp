<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>颜色选择器使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
${colorpicker_js }
</head>
<body>
	<h2>颜色选择器</h2>
	<p>颜色选择器可以通过 &lt;fsdp:colorpicker&gt;标签创建(页面需引入colorpicker_js)。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="2" style="overflow: hidden;border: 1px dashed orange;">
		&lt;fsdp:colorpicker name="color" id="color">&lt;/fsdp:colorpicker>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:colorpicker name="color" id="color"></fsdp:colorpicker>
	</div>
</body>
</html>