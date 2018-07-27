<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>按钮使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
</head>
<body>
	<h2>按钮</h2>
	<p>按钮可以通过 &lt;fsdp:button&gt;标签创建。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="7" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:button name="添加" iconcls="icon-add"&gt;&lt;/fsdp:button&gt;
		&lt;fsdp:button name="移除" iconcls="icon-remove"&gt;&lt;/fsdp:button&gt;
		&lt;fsdp:button name="保存" iconcls="icon-save"&gt;&lt;/fsdp:button&gt;
		&lt;fsdp:button name="剪切" iconcls="icon-cut" disabled="true"&gt;&lt;/fsdp:button&gt;
		&lt;fsdp:button name="纯文本按钮"&gt;&lt;/fsdp:button&gt;
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:button name="添加" iconcls="icon-add"></fsdp:button>&nbsp;
		<fsdp:button name="移除" iconcls="icon-remove"></fsdp:button>&nbsp;
		<fsdp:button name="保存" iconcls="icon-save"></fsdp:button>&nbsp;
		<fsdp:button name="剪切" iconcls="icon-cut" disabled="true"></fsdp:button>&nbsp;
		<fsdp:button name="纯文本按钮"></fsdp:button>
	</div>
	
	<p class="demo-subtitle">2.设置宽度</p>
	<textarea rows="7" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:button name="搜索" iconcls="icon-search" style="width:80px;">&lt;/fsdp:button>&nbsp;
		&lt;fsdp:button name="打印" iconcls="icon-print" style="width:80px;">&lt;/fsdp:button>&nbsp;
		&lt;fsdp:button name="刷新" iconcls="icon-reload" style="width:80px;">&lt;/fsdp:button>&nbsp;
		&lt;fsdp:button name="帮助" iconcls="icon-help" disabled="true" style="width:80px;">&lt;/fsdp:button>&nbsp;
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:button name="搜索" iconcls="icon-search" style="width:80px;"></fsdp:button>&nbsp;
		<fsdp:button name="打印" iconcls="icon-print" style="width:80px;"></fsdp:button>&nbsp;
		<fsdp:button name="刷新" iconcls="icon-reload" style="width:80px;"></fsdp:button>&nbsp;
		<fsdp:button name="帮助" iconcls="icon-help" disabled="true" style="width:80px;"></fsdp:button>&nbsp;
	</div>
	
	<p class="demo-subtitle">3.图标位置</p>
	通过设置标签的“iconalign”属性即可，有“top”、“bottom”、“left”(默认)、“right”四种定位。<br/><br/>
	<textarea rows="7" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:button name="添加" iconcls="icon-add"&gt;&lt;/fsdp:button&gt;
		&lt;fsdp:button name="移除" iconcls="icon-remove"&gt;&lt;/fsdp:button&gt;
		&lt;fsdp:button name="保存" iconcls="icon-save"&gt;&lt;/fsdp:button&gt;
		&lt;fsdp:button name="剪切" iconcls="icon-cut" disabled="true"&gt;&lt;/fsdp:button&gt;
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:button name="添加" iconcls="icon-add" iconalign="top"></fsdp:button>&nbsp;
		<fsdp:button name="移除" iconcls="icon-remove" iconalign="bottom"></fsdp:button>&nbsp;
		<fsdp:button name="保存" iconcls="icon-save" iconalign="left"></fsdp:button>&nbsp;
		<fsdp:button name="剪切" iconcls="icon-cut" iconalign="right"></fsdp:button>&nbsp;
	</div>
	
	<p class="demo-subtitle">4.按钮型号</p>
	<textarea rows="7" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:button name="图片" iconcls="icon-large-picture" iconalign="top" size="large">&lt;/fsdp:button>&nbsp;
		&lt;fsdp:button name="剪纸" iconcls="icon-large-clipart" iconalign="top" size="large">&lt;/fsdp:button>&nbsp;
		&lt;fsdp:button name="形状" iconcls="icon-large-shapes" iconalign="top" size="large">&lt;/fsdp:button>&nbsp;
		&lt;fsdp:button name="设计" iconcls="icon-large-smartart" iconalign="top" size="large">&lt;/fsdp:button>&nbsp;
		&lt;fsdp:button name="图表" iconcls="icon-large-chart" iconalign="top" size="large">&lt;/fsdp:button>&nbsp;
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:button name="图片" iconcls="icon-large-picture" iconalign="top" size="large"></fsdp:button>&nbsp;
		<fsdp:button name="剪纸" iconcls="icon-large-clipart" iconalign="top" size="large"></fsdp:button>&nbsp;
		<fsdp:button name="形状" iconcls="icon-large-shapes" iconalign="top" size="large"></fsdp:button>&nbsp;
		<fsdp:button name="设计" iconcls="icon-large-smartart" iconalign="top" size="large"></fsdp:button>&nbsp;
		<fsdp:button name="图表" iconcls="icon-large-chart" iconalign="top" size="large"></fsdp:button>&nbsp;
	</div>
	
	<p class="demo-subtitle">5.事件</p>
	<textarea rows="7" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:button name="禁用" iconcls="icon-add" disabled="true">&lt;/fsdp:button>&nbsp;
		&lt;fsdp:button name="选中" iconcls="icon-remove" selected="true">&lt;/fsdp:button>&nbsp;
		&lt;fsdp:button name="点击" iconcls="icon-save" onclick="alert('我被点击了')">&lt;/fsdp:button>&nbsp;
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:button name="禁用" iconcls="icon-add" disabled="true"></fsdp:button>&nbsp;
		<fsdp:button name="选中" iconcls="icon-remove" selected="true"></fsdp:button>&nbsp;
		<fsdp:button name="点击" iconcls="icon-save" onclick="alert('我被点击了')"></fsdp:button>&nbsp;
	</div>
</body>
</html>