<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文本框使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
</head>
<body>
	<h2>文本框</h2>
	<p>文本框可以通过 &lt;fsdp:textarea&gt;标签创建。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:textarea name="username" id="username">&lt;/fsdp:textarea>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:textarea name="username" id="username" value="文本框使用示例"></fsdp:textarea>
	</div>
	
	<p class="demo-subtitle">2.设置宽高</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:textarea name="username" id="username" style="width:180px;height:80px;">&lt;/fsdp:textarea>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:textarea name="username" id="username" style="width:180px;height:80px;"></fsdp:textarea>
	</div>
	
	<p class="demo-subtitle">3.事件</p>
	<textarea rows="5" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		点击事件：&lt;fsdp:textarea name="username" id="username" onclick="alert('我被点击了')">&lt;/fsdp:textarea>
		得到焦点：&lt;fsdp:textarea name="username" id="username" onfocus="alert('我得到焦点了')">&lt;/fsdp:textarea>
		失去焦点：&lt;fsdp:textarea name="username" id="username" onblur="alert('我失去焦点了')">&lt;/fsdp:textarea>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		点击事件：<fsdp:textarea name="username" id="username" onclick="alert('我被点击了')"></fsdp:textarea>
		得到焦点：<fsdp:textarea name="username" id="username" onfocus="alert('我得到焦点了')"></fsdp:textarea>
		失去焦点：<fsdp:textarea name="username" id="username" onblur="alert('我失去焦点了')"></fsdp:textarea>
	</div>
</body>
</html>