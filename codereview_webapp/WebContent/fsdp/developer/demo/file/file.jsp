<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文件上传使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
</head>
<body>
	<h2>文件上传</h2>
	<p>文件上传可以通过 &lt;fsdp:file&gt;标签创建。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:file name="usefile" id="usefile">&lt;/fsdp:file>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:file name="usefile" id="usefile"></fsdp:file>
	</div>
	
	<p class="demo-subtitle">2.设置宽高</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:file name="usefile" id="usefile" style="width:280px;height:30px;">&lt;/fsdp:file>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:file name="usefile" id="usefile" style="width:280px;height:30px;"></fsdp:file>
		
	</div>
	
	<p class="demo-subtitle">3.事件</p>
	<textarea rows="5" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		点击事件：&lt;fsdp:file name="usefile" id="usefile" onclick="alert('我被点击了')">&lt;/fsdp:file>
		得到焦点：&lt;fsdp:file name="usefile" id="usefile" onfocus="alert('我得到焦点了')">&lt;/fsdp:file>
		失去焦点：&lt;fsdp:file name="usefile" id="usefile" onblur="alert('我失去焦点了')">&lt;/fsdp:file>
	</div>
	</textarea>
	<div style="padding:5px 0;">
	
		点击事件：<fsdp:file name="usefile" id="usefile" onclick="alert('我被点击了')"></fsdp:file><br/> 
		得到焦点：<fsdp:file name="usefile" id="usefile" onfocus="alert('我得到焦点了')"></fsdp:file> <br/> 
		失去焦点：<fsdp:file name="usefile" id="usefile" onblur="alert('我失去焦点了')"></fsdp:file> 
	</div>
</body>
</html>