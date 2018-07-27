<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp"%>
<!DOCTYPE html>
<html>
${jquery_js } 
${finedo_core_js } 
${finedo_commonui_js } 
${style_css }
${finedo_grid_js }
${finedo_dialog_js }
${finedo_tree_js }
<head>
<meta charset="UTF-8" />
<title>项目管理-创建项目</title>
<link rel="stylesheet" href="/codereview_webapp/codereview/codereview/project/css/style.css">
	<script type="text/javascript" src="/codereview_webapp/codereview/codereview/project/js/jquery-1.11.0.js"></script>
		<link href="${ctx}/resource/js/select2/css/select2.min.css" rel="stylesheet" />
	<script src="${ctx}/resource/js/select2/js/select2.full.min.js"></script>
	<script src="${ctx}/resource/js/select2/js/i18n/zh-CN.js"></script>
<script type="text/javascript">
var codeview={};
var projectindex = 0;
var firsttype = false;
 $(function (){
 searchPerson("projectmng", "请输入用户名", true);
	searchPerson("busimng", "请输入用户名", true); 
}); 
codeview.create=function(){
	if(finedo.fn.isNon($("#projectname").val())){
		 finedo.message.info = '请填写项目名称！';
		 return;
	}
	if(finedo.fn.isNon($("#projectmng").val())){
		 finedo.message.info = '请填写项目经理！';
		 return;
	}
 if(finedo.fn.isNon($("#busimng").val())){
		 finedo.message.info = '请填写商务经理！';
		 return;
	}
 if(finedo.fn.isNon($("#orgcode").val())){
		 finedo.message.info = '请填写归属部门！';
		 return;
	}
 if(finedo.fn.isNon($("#repupath").val())){
		 finedo.message.info = '请填写svn路径！';
		 return;
	}
		
		$.ajax({
		url: "/codereview_webapp/finedo/svncodeviewproject/create",
		type : "post",
		data : {
			projectname : $("#projectname").val(),
			projectmng :$("#projectmng").val(),
			busimng :$("#busimng").val(),
			orgcode :$("#orgcode").val(),
			repotype :$("#reputype").val(),
			svnpath :$("#repupath").val(),
			projectdescribe :$("#projectdescribe").val(),
			addoptuser : "${LOGINDOMAIN_KEY.sysuser.usercode}"
		},
		dataType : "json",
		success : function(data) {
			window.location.reload();
		}
	}); 
	}

codeview.back=function (){
	window.location.reload();
	}
 //根据员工姓名进行查询
searchPerson = function(componentname, tipmsg, multiple) {
	var ismultiple = false;
	var searchPersonOption = {
		url : "${ctx}/finedo/svncodeviewgroupuser/queryuserbyname",
		dataType : 'json',
		type : "POST",
		delay : 250,
		data : function(params) {
			return {
				username : params.term,
				state : "有效"
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var itemList = new Array();
			var userlst = data.rows;
			for (usrindx in userlst) {
				var oneuser = userlst[usrindx];
				itemList.push({
					"id" : oneuser.userid,
					"text" : oneuser.userid + ":" + oneuser.username
				});
			}
			return {
				results : itemList,
				pagination : {
					more : (params.page * 30) < userlst.size
				}
			};
		},

		cache : true
	};

	//此处是获取点击的数据
	select2component = $('#' + componentname).select2({
		ajax : searchPersonOption,
		placeholder : tipmsg,
		allowClear : true,
		language : "zh-CN",
		minimumInputLength : 1,
		multiple : ismultiple
	});
	return select2component;
}; 
</script>
</head>

<body style="background:#f7f7f7;">
<div class="pp-title" style="padding:10px 0; width:95%; margin:0 auto; box-sizing: border-box;"><p style="border-left:4px solid #15a0ee; color:#15a0ee; font-size:14px; padding-left:10px;">项目信息</p></div>
	<div class="main" style="width: 95%;margin: 0 auto;padding:0px 20px;box-sizing: border-box;background: #fff;">
		<table class="new-table">
			<tr>
				<td class="add-td1">项目名称：</td>
				<td class="add-td2"><input class="add-text" type="text" value="" id="projectname"></td>
				<td class="td3">*</td>
			</tr>
			<tr>
				<td class="add-td1">项目经理：</td>
				<td class="add-td2"><select name="projectmng" id="projectmng" class="add-text"  style="height: 40px;"></select></td>
				<td class="td3">*</td>
			</tr>
			<tr>
				<td class="add-td1">商务经理：</td>
				<td class="add-td2"><select name="busimng" id="busimng" class="add-text" ></select></td>
			</tr>
			<tr>
				<td class="add-td1">归属部门：</td>
				<td class="add-td2"><input class="add-text" type="text" id="orgcode"></td>
			</tr>
			<tr>
				<td class="add-td1">配置库类型：</td>
				<td class="add-td2"><select class="add-text" id="reputype">
				<option>svn</option>
				<option>git</option>
				<option>cvs</option>
				</select></td>	
				<td class="td3">*</td>
			</tr>
			<tr>
				<td class="add-td1">配置库uri：</td>
				<td class="add-td2"><input class="add-text" type="text" id="repupath"></td>
				<td class="td3">*</td>
			</tr>
			<tr>
				<td class="add-td1">项目描述：</td>
				<td class="add-td2"><textarea class="add-textarea" id="projectdescribe"></textarea></td>
			</tr>
		</table>
		<div class="main-btn">
			<input class="main-btn1 btn" type="button" value="提交" onclick="codeview.create()"  >    
			<input class="main-btn2 btn" type="button" value="取消" onclick="codeview.back()">
		</div>
	</div>
</body>
</html>