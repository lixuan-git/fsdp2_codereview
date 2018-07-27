<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>富文本使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/fsdp/developer/demo/demo.css">
<style type="text/css">
   div{
       width:100%;
   }
</style>
${style_css}
${jquery_js }
${easyui_js }
${finedo_js}
${ueditor_js}
</head>
<body>
	<h2>富文本</h2>
	<p>富文本可以通过 &lt;fsdp:htmleditor&gt;标签创建(页面需引入ueditor_js)。</p>
	<div style="margin:10px 0 40px 0;"></div>
	<p class="demo-subtitle">1.实例化编辑器</p>
   	<textarea rows="2" style="overflow: hidden;border: 1px dashed orange;">
   	&lt;fsdp:htmleditor id="editor" &gt;&lt;/fsdp:htmleditor&gt;
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:htmleditor id="editor" ></fsdp:htmleditor>&nbsp;
	</div>
	<p class="demo-subtitle">2.设置宽度和高度</p>
   	<textarea rows="2" style="overflow: hidden;border: 1px dashed orange;">
   	&lt;fsdp:htmleditor id="editor" style="width:900px;height:500px;" &gt;&lt;/fsdp:htmleditor&gt;
	</textarea>
	<div style="padding:5px 0;">
		<fsdp:htmleditor id="editor"   style="width:900px;height:900px;"></fsdp:htmleditor>&nbsp;
	</div>
	<p class="demo-subtitle">3.获取内容</p>
   	<textarea rows="7" style="overflow: hidden;border: 1px dashed orange;">
   	  function getContent() {
        var arr = [];
        arr.push("获取的内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }
	</textarea>
	<div style="padding:5px 0;">
		 <button onclick="getContent()">获得内容</button>
	</div>
	<p class="demo-subtitle">4.隐藏编辑器</p>
   	<textarea rows="2" style="overflow: hidden;border: 1px dashed orange;">
   	   <button onclick=" UE.getEditor('editor').setHide()">隐藏编辑器</button>
	</textarea>
	<div style="padding:5px 0;">
		  <button onclick=" UE.getEditor('editor').setHide()">隐藏编辑器</button>
	</div>
	<p class="demo-subtitle">5.显示编辑器</p>
   	<textarea rows="2" style="overflow: hidden;border: 1px dashed orange;">
   	   <button onclick=" UE.getEditor('editor').setShow()">显示编辑器</button>
	</textarea>
	<div style="padding:5px 0;">
		  <button onclick=" UE.getEditor('editor').setShow()">显示编辑器</button>
	</div>
<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');

    function getContent() {
        var arr = [];
        arr.push("获取的内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }
    
</script>
</body>
</html>