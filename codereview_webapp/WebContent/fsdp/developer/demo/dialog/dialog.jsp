<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>对话框使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
${easyui_portal_js}
</head>
<body>
	<h2>信息提示框</h2>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="margin:20px 0;">
		<a href="#" onclick="alerts()">Alert</a>
	</div>
	</textarea>
	<div style="margin:20px 0;">
		<a href="#" class="easyui-linkbutton" onclick="alerts('标题','点击我了!','')"> 提 示 </a>
		<a href="#" class="easyui-linkbutton" onclick="alerts('标题','点击我了!','error')"> 错 误 </a>
		<a href="#" class="easyui-linkbutton" onclick="alerts('标题','点击我了!','info')"> 输 出 </a>
		<a href="#" class="easyui-linkbutton" onclick="alerts('标题','点击我了!','question')"> 疑 问 </a>
		<a href="#" class="easyui-linkbutton" onclick="alerts('标题','点击我了!','warning')"> 警 告 </a>
	</div>
	
	<p class="demo-subtitle">2.javaScript</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<script type="text/javascript">
	function alerts(t,m,e){
		message(t,m,e);
	}
	</script>
	</textarea>
</body>
<script type="text/javascript">
	function alerts(t,m,e){
		message(t,m,e);
	}
</script>
</html>