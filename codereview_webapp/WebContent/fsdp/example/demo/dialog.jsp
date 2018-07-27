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
${finedo_choose_js }
<script type='text/javascript' src='${ctx}/resource/js/finedo.validate.js?version=${version }'></script>
<script type='text/javascript' src='${ctx}/resource/js/finedo.image.js?version=${version }'></script>
<script>
function dowait(){
	finedo.message.showwaiting();
}
function doTip(){
	finedo.message.tip("用户信息修改成功！");
}
function doInfo(){
	finedo.message.info('测试');
	//finedo.message.showWaiting('请等待请等待请等待请等待请等待请等待...');
	//finedo.dialog.show({'loadtype':'text','text':'合肥非度信息技术限公司合肥非度信息技术限公司合肥非度信息','eventName':'selectUser'});
	//finedo.dialog.show({'loadtype':'text','text':'合肥非度信息技术限公司合肥非度信息技术限公司合肥非度信息11111111','eventName':'selectCust'});
	//finedo.dialog.show({'loadtype':'text','text':'合肥非度信息技术限公司合肥非度信息技术限公司合肥非度信息22222222222','eventName':'selectOrg'});
	//finedo.validate({
	//	"username":{
	//		"val":"20:12:26","label":"用户名称","datatype":"time","minlength":"4","maxlength":"100","required":"false"
	//	}
	//});
}
function doWarn(){
	finedo.message.warning('测试');
	//alert(finedo.fn.getData('testdata'));
	//$(document).trigger('adddata',{data:{'name':'wufenglover'}});
}
function doError(){
	finedo.message.error('测试');
}
function doQuestion(){  
	finedo.message.question("合肥非度信息技术限公司合肥非度信息技术限公司合肥非度信息<br/>技术限公司合肥非度信息技术限公司<br/>合肥非度信息技术限公司合肥非度信<br/>息技术限公司合肥非度信息技术限公司", null, function(which){
		alert(which);
	}, null);
}

function doDialog(){
	/*finedo.dialog.show({'selecttype':'single',
		width:1000,height:600,
		'title':'弹出窗口',
		'loadtype':'iframe',
		'url':'${ctx }/fsdp/jsp/role/selectrole.jsp?selecttype=single',
		callback:function(data){
			alert(JSON.stringify(data));
		}
	});*/
	finedo.dialog.show({
		width:1000,height:600,
		'name':'wftestdialog',
		'title':'弹出窗口',
		'loadtype':'iframe',
		'url':'${ctx }/fsdp/example/demo/upload.jsp',
		callback:function(data){
			alert(JSON.stringify(data));
		}
	});
}
function closeDialog(){
	finedo.dialog.close('wftestdialog');
}
function doOrgSingle(){
	finedo.choose.singleOrg(function(data){
		alert(JSON.stringify(data));
	});
}
function doOrgMulti(){
	finedo.choose.multiOrg(function(data){
		alert(JSON.stringify(data));
	});
}
function doRoleSingle(){
	finedo.choose.singleRole(function(data){
		alert(JSON.stringify(data));
	});
}
function doRoleMulti(){
	finedo.choose.multiRole(function(data){
		alert(JSON.stringify(data));
	});
}
function doUserSingle(){
	finedo.choose.singleUser(function(data){
		alert(JSON.stringify(data));
	});
}
function doUserMulti(){
	finedo.choose.multiUser(function(data){
		alert(JSON.stringify(data));
	});
}
function imgpreview(){
	finedo.image.preview('${ctx}/fsdp/example/page_page.jpg');
}
</script>
</head>
<body>
<form id="frm" name="frm">
		<fsdp:button id="button1" label="等待" onclick="dowait()"></fsdp:button>&nbsp;
		<fsdp:button id="button1" label="提示" onclick="doTip()"></fsdp:button>&nbsp;
		<fsdp:button id="button1" label="信息" onclick="doInfo()"></fsdp:button>&nbsp;
		<fsdp:button id="button1" label="告警" onclick="doWarn()"></fsdp:button>&nbsp;
		<fsdp:button id="button1" label="错误" onclick="doError()"></fsdp:button>&nbsp;
		<fsdp:button id="button1" label="咨询" onclick="doQuestion()"></fsdp:button>&nbsp;
		<fsdp:button id="button1" label="iframe窗口" onclick="doDialog()"></fsdp:button>
		<fsdp:button id="button1" label="关闭窗口" onclick="closeDialog()"></fsdp:button>
		<fsdp:button id="button1" label="单选组织机构" onclick="doOrgSingle()"></fsdp:button>
		<fsdp:button id="button1" label="多选组织机构" onclick="doOrgMulti()"></fsdp:button>
		<fsdp:button id="button1" label="单选角色" onclick="doRoleSingle()"></fsdp:button>
		<fsdp:button id="button1" label="多选角色" onclick="doRoleMulti()"></fsdp:button>
		<fsdp:button id="button1" label="单选用户" onclick="doUserSingle()"></fsdp:button>
		<fsdp:button id="button1" label="多选用户" onclick="doUserMulti()"></fsdp:button>
		<fsdp:button id="button1" label="图片预览" onclick="imgpreview()"></fsdp:button>
	
	<fsdp:label label="label标签">
		<div>测试测试测试</div>
	</fsdp:label>
<iframe id="downframe" style="display:none"></iframe>
</form>
</body>
</html>