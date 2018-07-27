<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>单选按钮使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
</head>
<body>
	<h2>单选按钮</h2>
	<p>单选按钮可以通过 &lt;fsdp:radiobox&gt;标签创建。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:radiobox name="radioname" id="radioid" datasource="" >&lt;/fsdp:textarea>
	</div>
	</textarea>
	<div style="padding:5px 0;">
	 	<label><input name="Fruit" type="radio" value="" />1</label> 
 		<label><input name="Fruit" type="radio" value="" />2</label> 
 		<label><input name="Fruit" type="radio" value="" />3</label> 
	</div>
	
	
	<p class="demo-subtitle">3.事件</p>
	<textarea rows="5" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		点击事件：&lt;fsdp:radiobox name="radioname" id="radioid"  datasource=""  onclick="alert('我被点击了')">&lt;/fsdp:radiobox>
		内容改变事件：&lt;fsdp:radiobox name="radioname" id="radioid"  datasource=""  onchange="alert('我的内容改变了')">&lt;/fsdp:radiobox>
	</div>
	</textarea>
	<div style="padding:5px 0;">
	 <label><input name="Fruit" type="radio" value="" 	onclick="alert('我被点击了')" />我被点击了 </label> 
	 <label><input name="Fruit" type="radio" value=""  onchange="alert('我的内容改变了')" />我的内容改变了 </label> 
	</div>
</body>
</html>