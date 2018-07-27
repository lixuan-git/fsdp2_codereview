<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
${finedo_upload_js}

<link href="${ctx }/fsdp/app/style/style.css" rel="stylesheet">
<script type="text/javascript">
	var fileid = "";
	$(document).ready(function() {
		finedo.getfile('userfiles', {
			filter : ".xls,.xlsx",
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

	});

	function formatOperation(row) {
		var operation = '<a href="#" onclick="doDelete(\''+ row.objid + '\',\''+ row.appid + '\')">[删除]</a>&nbsp;';
		return operation;
	}

	// 文件上传成功后的回调函数，fileobj为上传文件的json对象
	function uploadcomplete(fileobj) {
		fileid = fileobj.fileid;
	}

	function doDelete(objid,appid) {
		data = {
			"objid" : objid,
			"appid" : appid
		};
		$.ajax({
			type : 'POST',
			url : "${ctx }/finedo/appmanage/deleteGray",
			data : data,
			success : deletcallback,
			dataType : 'json'
		});
	}
	
	function deletcallback(jsondata) {
		finedo.message.info(jsondata.resultdesc, '提示');
		finedo.getgrid('datagrid').refresh();
	}
	function add() {
		var objid = $('#objid').val();
		if (objid == "") {
			mes = "请输入灰度对象标识";
			finedo.message.info(mes, '提示');
			return;
		}
		var appid = "${param.addgrayid}";
		var url = "";
		var data;
		url = "${ctx }/finedo/appmanage/addGray";
		data = {
			"objid" : objid,
			"appid" : appid
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
		finedo.getgrid('datagrid').refresh();
	}

	function importFile() {
		if (fileid == "") {
			finedo.message.error("未上传Excel文件");
			return;
		}
		var appid = "${param.addgrayid}";
		$('#fileid').val(fileid);
		$('#appid').val(appid);

		var options = {
			url : $("#importForm").attr("action"),
			success : importcallback,
			type : 'POST',
			dataType : "json",
			clearForm : false
		};
		$("#importForm").ajaxSubmit(options);

	}
</script>
</head>

<body class="query-body">
	<div class="edit">
		<!-- 工具栏  -->
		<div class="query-title">
			<!-- 标题 -->
			<div class="query-title-name">灰度目标添加</div>
		</div>
		<div
			style="float: left; margin: 10px 0 10px 10px; border-left: 3px solid #0085d0; text-indent: 10px;">批量导入</div>
		<div style="float: left; width: 100%;">
			<div style="float: left; width: 100px; margin-left: 10px; text-align: right;">导入Excel：</div>
			<div style="float: left; width: 84%;">
				<input type="text" id="userfiles" name="userfiles">
			</div>
		</div>
		<div style="float: left; width: 100%; margin: 10px 0 0 0; margin-left: 110px;">
			<a href="/fsdp2_webapp/fsdp/app/import.xlsx">灰度对象标识批量导入Excel模板 </a>
		</div>
		<div style="float: left; width: 100%; margin: 10px 0 0 0;">
			<input style="margin-left: 110px;" class="edit-btn-add" type="button" value="批量导入" onclick="importFile()" id="button">
		</div>
		<div style="float: left; margin: 10px 0 10px 10px; border-left: 3px solid #0085d0; text-indent: 10px;">单个录入</div>
			<div style="float: left; width: 100%;">
			<div style="float: left; width: 100px; margin: 7px 0 0 10px; text-align: right;">灰度对象标识：</div>
			<div style="float: left; width: 84%;">
				<input type="text" style="width:50%; height:30px; border:1px solid #dadada; line-height:30px; outline:none; background:#fff; padding:0 5px; margin-left:10px; font-family:Microsoft Yahei, SimSun; font-size:12px; color:#000;" id=objid value=""> <span style="color:red">*</span>
			</div>
		</div>
		<div style="float: left; margin: 10px 0 0 100px;">
			<input class="edit-btn-add" type="button" value="添加" onclick="add()" id="button">
		</div>
		<div style="float: left; width: 100%; margin: 10px 0 0 10px;">
			<div style="float: left; width:90%; margin-right: 10px;">
				<fsdp:grid id="datagrid" url="${ctx }/finedo/appmanage/queryGray?appid=${param.addgrayid}">
					<fsdp:field code="objid" name="灰度对象标识" width="100"></fsdp:field>
					<fsdp:field code="operation" name="操作" width="100" formatter="formatOperation"></fsdp:field>
				</fsdp:grid>
			</div>
		</div>
	</div>

	<form method="post" action="${ctx }/finedo/appmanage/importGray" id="importForm" name="importForm">
		<input type="hidden" id="fileid" name="fileid">
		<input type="hidden" id="appid" name="appid">
	</form>
</body>
</html>


