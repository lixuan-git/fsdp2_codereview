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
${finedo_grid_js }

<link href="${ctx }/fsdp/app/style/style.css" rel="stylesheet">
<script type="text/javascript">
	var platform="";
	$(document).ready(function() {
		platform='${param.platform}';
		var sp=platform.split(",");
		var a=document.getElementsByName('id');
		for(var i=0;i<a.length;i++){
			if(sp.length==2){
				a[i].checked=true;
			}else if(platform=='android'){
				a[0].checked=true;
			}else if(platform=='ios'){
				a[1].checked=true;
			}
		 
		}
		
	});

	function add() {
		platform="";
		var isFirst=true;
		$('input[name="id"]:checked').each(function(){ 
			if(isFirst){
				 platform=platform+$(this).val();
				 isFirst=false;
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
		url = "${ctx }/finedo/appmanage/update";
		data = {
			"appname" : appname,
			"platform" : platform,
			"founder" : founder,
			"appdesc" : content,
			"appid"  : appid
		};
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
</script>
</head>

<body class="query-body">
	<div class="edit">
		<!-- 工具栏  -->
		<div class="query-title">
			<!-- 标题 -->
			<div class="query-title-name">应用详情</div>
		</div>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="edit-name">应用名称：</td>
				<td>
					<input type="text" class="edit-text" id="appname" value="${param.appname}"><span>*</span>
				</td>
			</tr>
			<tr>
				<td class="edit-name">应用类型：</td>
				<td>
					<input type="checkbox" name="id" value="android" id="Checkbox1"   style=" margin-left:10px;"><img style=" margin:0 5px; width:13px;  " class="edit-icon-app" src="${ctx }/fsdp/app/image/index_icon_android.png">android&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="checkbox" name="id" value="ios" ID="Checkbox2"  style=" margin-left:10px;"><img style=" margin:0 5px; width:13px;  " class="edit-icon-app" src="${ctx }/fsdp/app/image/ios.png">ios&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="edit-name">应用描述：</td>
				<td><textarea class="edit-textarea" id="content">${param.appdesc}</textarea></td>
			</tr>
		</table>
		<div class="edit-btn">
			<input class="edit-btn-add" type="button" value="修改" onclick="add()" id="button">
		</div>
		<div
			style="float: left; width: 100%; padding-top: 20px; display: inline-block; text-indent: 10px;">
		</div>
	</div>
	<!-- 表格栏  -->
	<fsdp:grid id="datagrid1" url="${ctx }/finedo/appmanage/queryAppUpgrade?appid=${param.appid}">
		<fsdp:field code="appname" name="应用名称" width="100"></fsdp:field>
		<fsdp:field code="platform" name="平台" width="100"></fsdp:field>
		<fsdp:field code="demand" name="升级要求" width="100"></fsdp:field>
		<fsdp:field code="versiontype" name="版本类型" width="100"></fsdp:field>
		<fsdp:field code="uptype" name="升级类型" width="100"></fsdp:field>
		<fsdp:field code="publishtime" name="发布时间" width="200"></fsdp:field>
		<fsdp:field code="content" name="更新描述" width="200"></fsdp:field>
	</fsdp:grid>
</body>
</html>


