<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>文件服务器独立部署测试</title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_common_js }
${finedo_upload_js }
${finedo_dialog_js }

<script>
var uploadFileControl;
function doTest(){
	/*var person = {usercode: '2008003', personname: '吴峰'};
	finedo.action.doJsonRequest("${ctx }/finedo/plan/test", person, function(data){
		finedo.message.info(data.resultdesc);
	});*/
	var filelist = finedo.getFileControl("userid").getFileList();
	finedo.message.info('测试='+filelist.length);
}

function fileCallback(data){
	//alert(data.filename);
}

function deleteCallback(data){
	//alert(JSON.stringify(data));
}

function oninitcomplete(data){
	//alert(JSON.stringify(data));
}

function doDelete(){
	var filelist = uploadFileControl.getFileList();
	for(var i = 0; i < filelist.length; i++){
		alert(filelist[i].fileid);
	}
}

function doDownload(){
	$('#iframe').attr('src','${ctx}/finedo/file/download?fileid=FD000000000000003376');
	/*$('#downloadfrm').ajaxSubmit({
		url: '${ctx}/finedo/file/download?fileid=FD000000000000003376'
		,type: 'get'
		,success: function(data){
			
		}
		,target: 'iframe'
	});
	$('#downloadfrm').attr('action', '${ctx}/finedo/file/download?fileid=FD000000000000003376');
	$('#downloadfrm').attr('action', 'iframe');
	$('#downloadfrm').submit();*/
}

function downloadSuccess(){
	//alert('ok');
}

$(document.body).ready(
	function(){
		uploadFileControl=finedo.getFileControl('uploaddiv');	
	}
);

</script>
</head>
<body>

	<input type="text" id="uploaddiv" name="uploaddiv" multiupload="true">
			
</body>
</html>



