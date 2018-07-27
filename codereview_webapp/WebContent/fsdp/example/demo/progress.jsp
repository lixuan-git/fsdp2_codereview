<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>进度条控件</title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_progress_js }

</head>
<body>
<input type="button" value="测试" onclick="doTest1();">
<fsdp:progress id="progressdiv" label="合肥非度信息技术有限公司"></fsdp:progress>

<script>
var progress = 0;
var iTimerID;
function doTest(){
	progress ++;
	try{
		finedo.getProgress('progressdiv').setProgress(progress);
	}catch(e){}
	if(progress > 60){
		clearTimeout(iTimerID);
		return;
	}
	iTimerID = setTimeout("doTest()", 10);
}
function doTest1(){
	iTimerID = setTimeout("doTest()", 10);
}
</script>
</body>
</html>