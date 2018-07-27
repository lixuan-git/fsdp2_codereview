<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>复选框使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
<script type="text/javascript" src="${ctx}/fsdp/developer/demo/checkbox/js/checkbox.js"></script>

</head>
<script type="text/javascript">

function change(){
	
}
</script>
<body>
	<h2 style="background: url('resource/images/error.png') no-repeat">复选框</h2>
	<p>复选框可以通过 &lt;fsdp:checkbox&gt;标签创建。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:checkbox name="选择" dataSource="course"&gt;&lt;/fsdp:checkbox&gt;
	</div>
	</textarea>
	<div style="padding:5px 0;">
		科目：<fsdp:checkbox name="选择" datasource="course" ></fsdp:checkbox>&nbsp;
	</div>
	
	<p class="demo-subtitle">2.不可用</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:checkbox name="选择" dataSource="course" disabled="disabled" &gt;&lt;/fsdp:checkbox&gt;
	</div>
	</textarea>
	<div style="padding:5px 0;">
		科目：<fsdp:checkbox  name="选择" datasource="course" disabled="disabled"></fsdp:checkbox>&nbsp;
	</div>
	
	<p class="demo-subtitle">3.</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:checkbox name="选择" dataSource="course" disabled="disabled" &gt;&lt;/fsdp:checkbox&gt;
	</div>
	</textarea>
	<div style="padding:5px 0;">
		科目：<fsdp:checkbox name="选择" datasource="course" ></fsdp:checkbox>&nbsp;
	</div>
	
	
</body>
</html>