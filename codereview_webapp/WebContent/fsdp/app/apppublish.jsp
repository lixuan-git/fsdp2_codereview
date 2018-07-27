<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp"%>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_dialog_js }
${finedo_upload_js}

<link href="${ctx }/fsdp/app/style/style.css" rel="stylesheet">
<script type="text/javascript">
	var fileid = "";
	var plistid = "";
	$(document).ready(function() {
		finedo.getfile('userfiles', {
			filter : ".apk,.ipa",
			value : "${param.downloadurl}",
			showicon : "true",
			showname : "true",
			showsize : "true",
			multiupload : "false",
			multidown : "false",
			editable : "true",
			entityid : "",
			onuploadcomplete : uploadcomplete
		});
		
		finedo.getfile('userfiles1', {
			filter : ".plist",
			value : "${param.downloadurl}",
			showicon : "true",
			showname : "true",
			showsize : "true",
			multiupload : "false",
			multidown : "false",
			editable : "true",
			entityid : "",
			onuploadcomplete : uploadcomplete1
		});

	});
	
	// 文件上传成功后的回调函数，fileobj为上传文件的json对象
	function uploadcomplete(fileobj) {
		fileid = fileobj.fileid;
	}

	// 文件上传成功后的回调函数，fileobj为上传文件的json对象
	function uploadcomplete1(fileobj) {
		plistid= fileobj.fileid;
	}

	function add() {
		var publisher = $('#publisher').val();
		var version = $('#version').val();
		var content = $('#content').val();
		var mes = "";
		var versiontype="";
		var platform="";
		var demand="";
		var uptype="";
		 platform = $("input[name='radio1']:checked").val();
		
		 demand = $("input[name='demand']:checked").val();
		
		 versiontype = $("input[name='versiontype']:checked").val();
		 uptype  = $("input[name='uptype']:checked").val();
		if (platform == "") {
			mes = "请选择应用类型";
			finedo.message.info(mes, '提示');

			return;
		} else if (demand == "") {
			mes = "请选择升级要求";
			finedo.message.info(mes, '提示');
			return;
		}else if (versiontype == "") {
			mes = "请选择版本类型";
			finedo.message.info(mes, '提示');
			return;
		}else if (version == "") {
			mes = "请输入版本号";
			finedo.message.info(mes, '提示');
			return;
		}else if (uptype == "") {
			mes = "请选择升级类型";
			finedo.message.info(mes, '提示');
			return;
		}else if (publisher == "") {
			mes = "请输入项目负责人";
			finedo.message.info(mes, '提示');
			return;
		}else if (fileid == "") {
			mes = "请上传安装包";
			finedo.message.info(mes, '提示');
			return;
		}
		if(platform=="ios"){
			if(plistid==""){
				mes = "请上传plist文件";
				finedo.message.info(mes, '提示');
				return;
			}
		}
		var appid = "${param.publishid}";
		var url = "";
		var data;

		url = "${ctx }/finedo/appmanage/addAPP";
		data = {
			"platform" : platform,
			"versiontype" : versiontype,
			"demand" : demand,
			"publisher" : publisher,
			"downloadurl" : fileid,
			"uptype" : uptype,
			"appid" : appid,
			"version" : version,
			"content" : content

		}

		$.ajax({
			type : 'POST',
			url : url,
			data : data,
			success : importcallback,
			dataType : 'json'
		});
	}
	function importcallback(jsondata) {
		finedo.message.info(jsondata.resultdesc, '提示');

	}
	function dochange() {
		var obj;
		obj = document.getElementsByName('radio1');
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				if(i==0){
					document.getElementById("plist").style.display="none";
				}else{
					document.getElementById("plist").style.display="";
				}
			}
		}
	}
</script>
</head>

<body class="query-body">
	<div class="edit">
		<!-- 工具栏  -->
		<div class="query-title">
			<!-- 标题 -->
			<div class="query-title-name">安装包发布</div>
		</div>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="edit-name">安装包类型：</td>
				<td onchange="dochange()">
					<input type="radio" value="android" name="radio1" style="margin-left: 10px;">
					<img style="margin: 0 5px; width: 13px;" class="edit-icon-app" src="${ctx }/fsdp/app/image/index_icon_android.png">android&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="ios" name="radio1" style="margin-left: 10px;">
					<img style="margin: 0 5px; width: 13px;" class="edit-icon-app" src="${ctx }/fsdp/app/image/ios.png">ios&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</tr>

			<tr>
				<td class="edit-name">升级要求：</td>
				<td>
					<input type="radio" value="可选升级" name="demand" style="margin-left: 10px;">&nbsp;可选升级&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="必选升级" name="demand" style="margin-left: 10px;">&nbsp;必选升级&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="edit-name">版本类型：</td>
				<td>
					<input type="radio" value="正式版本" name="versiontype" style="margin-left: 10px;">&nbsp;正式版本&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="灰度版本" name="versiontype" style="margin-left: 10px;">&nbsp;灰度版本&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="edit-name">升级类型：</td>
				<td>
					<input type="radio" value="增量" name="uptype" style="margin-left: 10px;">&nbsp;增量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="全量" name="uptype" style="margin-left: 10px;">&nbsp;全量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="edit-name">版本号：</td>
				<td><input type="text" class="edit-text" id="version" value=""><span>*</span></td>
			</tr>
			<tr>
				<td class="edit-name">项目负责人：</td>
				<td><input type="text" class="edit-text" id="publisher" value=""><span>*</span></td>
			</tr>
			<tr>
				<td class="edit-name">更新内容：</td>
				<td><textarea class="edit-textarea" id="content"></textarea></td>
			</tr>
			<tr>
				<td class="edit-name">应用包上传：</td>
				<td>
					<input  type="text" id="userfiles" name="userfiles">
				</td>
			</tr>
			<tr style="display: none;" id="plist">
				<td class="edit-name">plist文件上传：</td>
				<td>
					<input  type="text" id="userfiles1" name="userfiles1">
				</td>
			</tr>
		</table>
		<div class="edit-btn">
			<input class="edit-btn-add" type="button" value="添加" onclick="add()" id="button">
		</div>
	</div>
</body>
</html>


