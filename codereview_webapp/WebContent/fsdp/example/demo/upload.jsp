<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_grid_js }
${finedo_upload_js }
${finedo_dialog_js }

<script>
var uploadFileControl;
function doTest(){
	/*var person = {usercode: '2008003', personname: '吴峰'};
	finedo.action.doJsonRequest("${ctx }/finedo/plan/test", person, function(data){
		finedo.message.info(data.resultdesc);
	});
	var filelist = finedo.getFileControl("userid").getFileList();
	finedo.message.info('测试='+filelist.length);*/
	//finedo.dialog.close('wftestdialog');
	finedo.getfile('userid').clear();
	finedo.getfile('userid').load([{"fileid":"FD201611000000005962"}]);
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
$(document.body).ready(function(){
	//"callback":function(data){alert(data.filename);}
	//alert(data.filename+","+data.fileid+","+data.optsn+","+data.filepath);
	/*$("#uploaddiv").upload().init({
		"onclick":function(data){alert(data.filename+","+data.fileid+","+data.optsn+","+data.filepath);}
		,"onuploadcomplete":function(data){}
	});*/
	//
	//uploadFileControl = finedo.getFileControl('userid1');
	//uploadFileControl.setOnclick(fileCallback);
	//finedo.getFileControl('uploaddiv').setOnuploadcomplete(fileCallback);
	//uploadFileControl.setMulti(false);
	//uploadFileControl.setOninitcomplete(oninitcomplete);
	//uploadFileControl.load([{"id":"WUFENGRELID"},{"fileid":"FD000000000000000842"},{"fileid":"FD000000000000000643"},{"fileid":"FD000000000000000634"},{"fileid":"FD000000000000000635"},{"fileid":"FD000000000000000642"}]);
	//uploadFileControl.setFilter(".png,.jpg");
	//uploadFileControl.setMulti(false/true);
	//uploadFileControl.load([{"fileid":""},{"fileid":""}]);
	//uploadFileControl.deleteAll([{"fileid":""},{"fileid":""}]);
	//uploadFileControl.getFileList();

	finedo.getfile('userid', {
		showicon:"true",
		showname:"true",
		showsize:"true",
		multiupload:"true",
		multidown:"true",
		editable:"true"
	});
});
//${ctx }/finedo/user/queryUser 
</script>
</head>
<body>
<input type="button" value="测试 " onclick="doTest()">

<input type="text" id="userid" name="userid">
<br/>
<br/>

</body>
</html>





