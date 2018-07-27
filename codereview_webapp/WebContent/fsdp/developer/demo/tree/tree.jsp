<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>树形组件使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
</head>
<body>
	<h2>树形组件</h2>
	<p>树形组件可以通过 &lt;fsdp:tree&gt;标签创建。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.基本用法</p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:tree id="tt" url="${ctx}/fsdp/jsp/developer/demo/tree/tree_data1.json" >&lt;/fsdp:tree>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:tree id="tt" url="${ctx}/fsdp/developer/demo/tree/tree_data1.json"></fsdp:tree>
	</div>
	
	
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">2.定义是否在每个节点前边显示 checkbox </p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:tree id="tt" url="${ctx}/fsdp/developer/demo/tree/tree_data1.json" checkbox='true'>&lt;/fsdp:tree>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:tree id="tt" url="${ctx}/fsdp/developer/demo/tree/tree_data1.json" checkbox="true"></fsdp:tree>
		
	</div>
		
		
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">3.行线显示 </p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:tree id="tt" url="${ctx}/fsdp/developer/demo/tree/tree_data1.json" checkbox='true' lines='true'>&lt;/fsdp:tree>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:tree id="tt" url="${ctx}/fsdp/developer/demo/tree/tree_data1.json" checkbox="true" lines="true"></fsdp:tree>
	</div>
	
	
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">4.当节点展开折叠时是否显示动画效果 </p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:tree id="tt" url="${ctx}/fsdp/developer/demo/tree/tree_data1.json" checkbox='true' lines='true' animate='true'>&lt;/fsdp:tree>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:tree id="tt" url="${ctx}/fsdp/developer/demo/tree/tree_data1.json" checkbox="true" lines="true" animate="true"></fsdp:tree>
	</div>
	
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">5.折叠所有节点效果 </p>
	<textarea rows="3" style="overflow: hidden;border: 1px dashed orange;">
	<div style="padding:5px 0;">
		&lt;fsdp:tree id="tt" url="${ctx}/fsdp/developer/demo/tree/tree_data1.json" state='closed'>&lt;/fsdp:tree>
	</div>
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:tree id="tt" url="${ctx}/fsdp/developer/demo/tree/tree_data1.json" state="closed"></fsdp:tree>
	</div>

<!-- 测试使用	
	<div style="padding:5px 0;">
		<ul class="easyui-tree" data-options="url:'${ctx}/fsdp/developer/demo/tree/tree_data1.json',state:'closed',checkbox:'false',method:'get'"></ul>
	</div>
 -->	
	
	<p>更EasyUI-tree使用指南请访问”<a href="http://www.jeasyui.com/demo/main/index.php?plugin=Tree&theme=ui-cupertino&dir=ltr&pitem=" target="_blank">官方网站 </a>“。</p>

<script type="text/javascript">
function test(){
	alert(t);
}

</script>
</body>
</html>