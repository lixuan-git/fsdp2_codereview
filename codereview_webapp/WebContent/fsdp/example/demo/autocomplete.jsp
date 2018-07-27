<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_dialog_js }
${finedo_autocomplete_js }
<script> 
function getPosition(ctrl) {  
    var p = kingwolfofsky.getInputPositon(ctrl);  
    $("#xylocation").html("left="+p.left + "px"+",top="+p.bottom + "px");  
}
    
$(document).ready(function() {
	finedo.getautocomplete('text1', {
		//'url':'${ctx}/finedo/example/autocomplete',
		'datasource':['wufeng','lover','myself','java','javascript']
	});
	finedo.getautocomplete('textarea1', {
		'datasource':['wufeng','lover','myself','java','javascript']
	});
});

function test(){
	//getPosition(document.getElementById('textarea1'));
}
</script>
</head>
<body>
<ul class="finedo-ul">
<li>
	<span class="finedo-label-title">按钮</span>
	<fsdp:button id="button1" label="按钮点击事件" onclick="test();"></fsdp:button>
</li>
<li>
	<span class="finedo-label-title">文本域</span>

	<fsdp:textarea id="textarea1" style="width:700px"></fsdp:textarea>
</li>

<li>
	<span class="finedo-label-title">文本</span>
	<input class="finedo-text" type="text" onfocus="this.select()" id="text1" name="text1" value="" style="width:500px"><span for="text1" class="finedo-hint-info"></span><span for="text1" class="finedo-hint-error"></span><span for="text1" class="finedo-hint-right"></span>
</li>
</ul>
</body>
</html>