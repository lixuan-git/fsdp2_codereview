<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp"%>

<!doctype html>
<html>
<head>
${style_css } ${jquery_js } ${finedo_core_js } ${finedo_commonui_js }
${finedo_dialog_js } ${finedo_grid_js } ${finedo_date_js }
${finedo_upload_js}

<link href="${ctx }/fsdp/app/style/style.css" rel="stylesheet">
<script type="text/javascript">
	var fileid = "";
	$(document).ready(function() {

		
		
	});


	function add() {
		var platform=""
		var isFirst=true;
		$('input[name="id"]:checked').each(function(){ 
			if(isFirst){
				 platform=platform+$(this).val();
				 isFirst=false
			}else{
				 platform=platform+','+$(this).val();
			}
			
			}); 
		var founder='${LOGINDOMAIN_KEY.sysuser.userid}';

		var appname = $('#appname').val();
		var content = $('#content').val();
		var mes = "";

		if (appname == "") {
			mes = "请输入应用名称";
			finedo.message.info(mes, '提示');

			return;
		} else if (platform == "") {
			mes = "请输入应用平台";
			finedo.message.info(mes, '提示');
			return;
		} 

		var appid = "${param.appid}";
		var url = "";
		var data;

			url = "${ctx }/finedo/appmanage/add";
			data = {
				"appname" : appname,
				"platform" : platform,
				"founder" : founder,
				"appdesc" : content
			
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
		//		finedo.message.hideWaiting();
		finedo.message.info(jsondata.resultdesc, '提示');

	}
</script>
</head>

<body class="query-body">
	<div class="edit">
		<!-- 工具栏  -->
		<div class="query-title">
			<!-- 标题 -->
			<div class="query-title-name">添加应用</div>

		</div>
		<table cellpadding="0" cellspacing="0">
	
			<tr>
				<td class="edit-name">应用名称：</td>
				<td><input type="text" class="edit-text" id="appname"
					value=""><span>*</span></td>
			</tr>
			<tr>
				<td class="edit-name">应用类型：</td>
				<td>
					<input type="checkbox" name="id" value="android" id="Checkbox1"  style=" margin-left:10px;"><img style=" margin:0 5px; width:13px;  " class="edit-icon-app" src="${ctx }/fsdp/app/image/index_icon_android.png">android&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox" name="id" value="ios" ID="Checkbox1" style=" margin-left:10px;"><img style=" margin:0 5px; width:13px;  " class="edit-icon-app" src="${ctx }/fsdp/app/image/ios.png">ios&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>

			<tr>
				<td class="edit-name">应用描述：</td>
				<td><textarea class="edit-textarea" id="content">${param.content}</textarea></td>
			</tr>
		</table>
		<div class="edit-btn">
			<input class="edit-btn-add" type="button" value="添加" onclick="add()"
				id="button">
		</div>
	</div>








</body>
</html>


