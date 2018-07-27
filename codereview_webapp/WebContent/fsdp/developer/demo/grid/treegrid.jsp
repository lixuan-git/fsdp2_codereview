<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文本域使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
</head>
<body>
	<h2>树形表格</h2>
	<p>树形表格可以通过 &lt;fsdp:treegrid&gt;及&lt;fsdp:field&gt;标签创建。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="7" style="overflow: hidden;border: 1px dashed orange;">
		&lt;fsdp:treegrid idfield="id" treefield="name" url="${ctx }/fsdp/developer/demo/grid/treegrid_data1.json" title="文件浏览" height="220">
			&lt;fsdp:field code="name" name="文件夹" width="200">&lt;/fsdp:field>
			&lt;fsdp:field code="size" name="大小" width="100">&lt;/fsdp:field>
			&lt;fsdp:field code="date" name="修改日期" width="150">&lt;/fsdp:field>
		&lt;/fsdp:treegrid>
	</textarea>
	<div style="padding:5px 0;height: 220px;">
		<fsdp:treegrid idfield="id" treefield="name" url="${ctx }/fsdp/developer/demo/grid/treegrid_data1.json" title="文件浏览" height="220">
			<fsdp:field code="name" name="文件夹" width="200"></fsdp:field>
			<fsdp:field code="size" name="大小" width="100"></fsdp:field>
			<fsdp:field code="date" name="修改日期" width="150"></fsdp:field>
		</fsdp:treegrid>
	</div>
	
	<p class="demo-subtitle">2.设置宽高</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:text name="username" id="username" style="width:180px;height:30px;">&lt;/fsdp:text>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:text name="username" id="username" style="width:180px;height:30px;"></fsdp:text>
	</div>
	
	<p class="demo-subtitle">3.事件</p>
	<textarea rows="5" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		点击事件：&lt;fsdp:text name="username" id="username" onclick="alert('我被点击了')">&lt;/fsdp:text>
		得到焦点：&lt;fsdp:text name="username" id="username" onfocus="alert('我得到焦点了')">&lt;/fsdp:text>
		失去焦点：&lt;fsdp:text name="username" id="username" onblur="alert('我失去焦点了')">&lt;/fsdp:text>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		点击事件：<fsdp:text name="username" id="username" onclick="alert('我被点击了')"></fsdp:text>
		得到焦点：<fsdp:text name="username" id="username" onfocus="alert('我得到焦点了')"></fsdp:text>
		失去焦点：<fsdp:text name="username" id="username" onblur="alert('我失去焦点了')"></fsdp:text>
	</div>
</body>
</html>