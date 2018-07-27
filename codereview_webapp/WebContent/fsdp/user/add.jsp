<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${jquery_base64_js }
${crypto_aes_js }
${finedo_commonui_js }
${finedo_dialog_js }
${finedo_date_js }
${finedo_upload_js }
${finedo_choose_js }

<script>
// 卡片显示与隐藏 
$(document).ready(function() {
    $('#common_add_card').click(function(e) {
    	$('#common_add_div').css('display', 'block');
    	$('#common_add_card').attr('class', 'add-link-li');
    	
    	$('#excel_add_div').css('display', 'none');
		$('#excel_add_card').removeClass();
		finedo.getFileControl('uploaddiv').reset(false);
    });
    
    $('#excel_add_card').click(function(e) {
    	$('#common_add_div').css('display', 'none');
    	$('#common_add_card').removeClass();
    	
    	$('#excel_add_div').css('display', 'block');
		$('#excel_add_card').attr('class', 'add-link-li');
    });
});

// Web组件初始化 
$(document).ready(function() {
    finedo.getFileControl('uploaddiv');
    
    // 加载角色数据
    /*$.ajax({
		type: "get",
		dataType:  "json",
		url: "${ctx }/finedo/role/queryRoleBySelect",
		success: function(data) {
			 data["type"]="single";
        	 finedo.getSelect("roleid", data);
        	 data["type"]="multi";
        	 data["style"]="width:500px";
        	 finedo.getSelect("roleids", data);
          }
      });*/
});
 
// 数据验证
function checkdata() {
   /**
 	* 	通用数据验证
 	* 	label  		名称
	*	datatype 	数据类型  string email phone url date datetime time number digits 
	*	minlength 	最小长度
	*	maxlength 	最大长度
	*	required 	是否必填 true/false
	*/
	var result=finedo.validate({
		"usercode":{label:"用户账号", datatype:"string", maxlength:30, required:true},
		"personname":{label:"用户姓名", datatype:"string", maxlength:50, required:true},
		"effdate":{label:"生效日期", datatype:"date", required:true},
		"expdate":{label:"失效日期", datatype:"date", required:true},
		"phoneno":{label:"手机号码", datatype:"phone", required:true},
		"email":{label:"邮箱", datatype:"email", required:false},
		"address":{label:"地址", datatype:"string", maxlength:200, required:false},
		"remark":{label:"备注", datatype:"string", maxlength:200, required:false},
		
		"loginpasswd":{label:"登录密码", datatype:"string", minlength:6, maxlength:15, required:true},
		"loginpasswd2":{label:"确认密码", datatype:"string", minlength:6, maxlength:15, required:true},
		
		"roleid":{label:"基本岗位角色", datatype:"string", required:true},
		"roleids":{label:"兼任岗位角色", datatype:"string", required:false}
	});
	
   	// 自定义数据验证
	var effdate=$('#effdate').val();
	var expdate=$('#expdate').val();
	if(expdate < effdate) {
		finedo.showhinterror('expdate', '失效日期不能小于生效日期');
		result=false;
	}
	
	// 密码必须由 4-16位字母、数字、特殊符号线组成
	var loginpasswd=$('#loginpasswd').val();
	var reg = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,15}');
	if(!reg.test(loginpasswd)) {
		finedo.showhinterror('loginpasswd', '登录密码必须由 6-15位字母、数字、特殊符号线组成');
		result=false;
	}
	
	var loginpasswd2=$('#loginpasswd2').val();
	if(loginpasswd != loginpasswd2) {
		finedo.showhinterror('loginpasswd2', '确认密码输入不正确，请重新输入');
		result=false;
	}
	
	// 兼任岗位角色不能包含基本岗位角色
	var roleid=$('#roleid').val();
	var roleids=$('#roleids').val();
	if(roleids.length > 0) {
		if(roleids.indexOf(roleid) >= 0) {
			finedo.showhinterror('roleids', '兼任岗位角色不能包含基本岗位角色');
			result=false;
		}
	}

	return result;
}

// 提交
function doNext() {
	if($('#common_add_div').css('display') == 'block') {
		dosubmit();
	}else {
		doimport();
	}
}

// 普通新建
function dosubmit() {
	if(!checkdata()) 
		return;
	var aeskey = CryptoJS.enc.Utf8.parse(aesvikey);
	var aesiv = CryptoJS.enc.Utf8.parse(aesvikey);
	var phonenoval = $("#phoneno").val();
	if(phonenoval){
		phonenoval = CryptoJS.AES.encrypt(phonenoval,aeskey,{iv:aesiv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding});
		$("#phoneno").val($.base64.btoa(phonenoval));
	}
	var emailval = $("#email").val();
	if(phonenoval){
		emailval = CryptoJS.AES.encrypt(emailval,aeskey,{iv:aesiv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding});
		$("#email").val($.base64.btoa(emailval));
	}
	var loginpasswdval = $("#loginpasswd").val();
	if(loginpasswdval){
		loginpasswdval = CryptoJS.AES.encrypt(loginpasswdval,aeskey,{iv:aesiv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding});
		$("#loginpasswd").val($.base64.btoa(loginpasswdval));
	}
	var form = $("#ajaxForm");
	var options = {     
        url:	   form.attr("action"),
        success:   submitcallback,
        type:      'POST',
        dataType:  "json",
	    clearForm: true
    };
	finedo.message.showWaiting();
	form.ajaxSubmit(options);
}

function submitcallback(jsondata){
	finedo.message.hideWaiting();
	finedo.message.info(jsondata.resultdesc, "新建用户信息");
}

// 批量导入
function doimport() {
	var fileControl=finedo.getFileControl('uploaddiv');
	var filearr=fileControl.getFileList();
	if(filearr.length != 1) {
		finedo.message.error("未上传Excel文件");
		return;
	}
	$('#fileid').val(filearr[0].fileid);
	
	var form = $("#importForm");
	var options = {     
        url:	   form.attr("action"),
        success:   importcallback,
        type:      'POST',
        dataType:  "json",
	    clearForm: false
    };
	finedo.message.showWaiting();
	form.ajaxSubmit(options);
}

function importcallback(jsondata){
	finedo.message.hideWaiting();

	$('#importresultdiv').css('display', 'block');
	var resulthtml="<li><span class='finedo-label-title'>导入情况</span><span class='finedo-label-info'><font color=red>" + jsondata.resultdesc + "   总记录数: " + jsondata.object.rowcount + " &nbsp;&nbsp; 成功记录数: " + jsondata.object.successcount + "&nbsp;&nbsp; 失败记录数: " + jsondata.object.failcount + "</font></span></li>";
	var faillist=eval(jsondata.object.faillist);
	for(var i=0; i<faillist.length; i++)  {
		resulthtml += "<li><span class='finedo-label-title'>失败明细</span><span class='finedo-label-info'>" + faillist[i].resultdesc + "</span></li>";
	}
	
	$('#importresultul').html(resulthtml);
}
function doChooseJbrole(){
	finedo.choose.singleRole(function(data){
		$("#roleid").val(data[0].roleid);
		$("#roleid_name").val(data[0].rolename);
	});
}
function doChooseJzrole(){
	finedo.choose.multiRole(function(data){
		var roleids='',roleidsname='';
		for(var i = 0; i < data.length; i++){
			if(i > 0){
				roleids += ",";
				roleidsname += ",";
			}
			roleids += data[i].roleid;
			roleidsname += data[i].rolename;
		}
		$("#roleids").val(roleids);
		$("#roleids_name").val(roleidsname);
	});
}
</script>
</head>

<body>
<div>
	<div class="add-common-head">
    	<div class="add-common-name-add">新建用户信息<br/>
            <ul>
            	<li id="common_add_card" class="add-link-li">普通新建</li> 
            	<li>|</li>
                <li id="excel_add_card">批量导入</li>
            </ul>
        </div>
        <input type="button" class="finedo-button-blue" style="float:right" value="提交" onclick="doNext();">
    </div>
    
    <form method="post" action="${ctx }/finedo/user/addUser" id="ajaxForm" name="ajaxForm">
    <div id="common_add_div" >
    	<div class="finedo-nav-title">基本信息</div>
	   	<ul class="finedo-ul">
			<li>
				<span class="finedo-label-title"><font color=red>*</font>用户账号</span>
				<fsdp:text id="usercode" hint="最大30个字符，支持大小写英文、数字、下划线格式" />
			</li>

           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>用户姓名</span>
           		<fsdp:text id="personname" />
           	</li>

           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>生效日期</span>
           		<fsdp:date id="effdate"/>
           	</li>
           	
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>失效日期</span>
           		<fsdp:date id="expdate"/>
           	</li>
           	            	
			<li>
				<span class="finedo-label-title"><font color=red>*</font>性别</span>
				<fsdp:radio id="gender" datasource="性别" selectedvalue="男"/>
				</li>
						
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>用户状态</span>
           		<fsdp:radio id="state" datasource="用户状态" selectedvalue="有效"/>
           	</li>
			
			<li>
				<span class="finedo-label-title"><font color=red>*</font>手机号码</span>
				<fsdp:text id="phoneno" hint="11位手机号码，支持移动、电信、联通号码" />
			</li>
           	
           	<li>
           		<span class="finedo-label-title">邮箱</span>
           		<fsdp:text id="email" hint="格式:xx@域名，如:test@finedo.cn" />
           	</li>
           	
           	<li>
           		<span class="finedo-label-title">地址</span>
           		<fsdp:text id="address" style="width:600px" />
           	</li>
			
			<li>
				<span class="finedo-label-title">备注</span>
				<fsdp:text id="remark" style="width:600px" />
			</li>
		</ul>
		
		<div class="finedo-nav-title">密码设置</div>
		<ul class="finedo-ul">
			<li>
				<span class="finedo-label-title"><font color=red>*</font>登录密码</span>
				<fsdp:password id="loginpasswd" hint="6-15位字符，必须包含大小写英文字母、数字、特殊符号" />
			</li>
			
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>确认密码</span>
           		<fsdp:password id="loginpasswd2" hint="再输入一次密码，并请记住该密码" />
           	</li>
        </ul> 
		
		<div class="finedo-nav-title">岗位角色设置</div>
		<ul class="finedo-ul">
			<li>
				<span class="finedo-label-title"><font color=red>*</font>基本岗位角色</span>
				<input type="hidden" id="roleid" name="roleid">
				<fsdp:text id="roleid_name" onclick="doChooseJbrole()"/>
			</li>
           	
           	<li>
           		<span class="finedo-label-title">兼任岗位角色</span>
				<input type="hidden" id="roleids" name="roleids">
				<fsdp:text id="roleids_name" onclick="doChooseJzrole()"/>
           	</li>
        </ul>
	    	    
	    <ul>
	    	<li class="add-center"><input type="button" class="finedo-button" value="提    交" onClick="dosubmit()" ></li>
	    </ul>
    </div>
    </form>
    
    <div id="excel_add_div" style="display:none">
    	<form method="post" action="${ctx }/finedo/user/importUser" id="importForm" name="importForm">
	    	<input type="hidden" id="fileid" name="fileid">
	    </form>
	    
   		<div class="finedo-nav-title">导入Excel</div>
		<ul class="finedo-ul">
			<li>
				<input type="text" id="uploaddiv" name="uploaddiv" filter=".xls,.xlsx" multiupload="false">
			</li>
        </ul>
        
		<ul>
    		<li class="add-center"><input type="button" class="finedo-button" value="提    交" onclick="doimport();"></li>
		</ul>
	    
	    <div id="importresultdiv" style="display:none">
	    	<div class="finedo-nav-title"><font color=red>导入Excel结果</font></div>
		    <div class="query-common-con">
		    	<ul id="importresultul" class="finedo-ul"></ul>
		    </div>
	    </div>
	    
	    <div class="finedo-nav-title">导入Excel格式说明</div>
		<ul class="finedo-ul">
			<li>
				 <span class="finedo-label-title">模板下载</span><span class="finedo-label-info"><a href="${ctx}/fsdp/user/import.xlsx" >用户信息批量导入Excel模板 </a></span>
			</li>
			<li>
				<span class="finedo-label-title">第一列：</span><span class="finedo-label-info">用户账号: 必填, 最大30个字符，支持大小写英文、数字、下划线格式</span>
			</li>
			<li>
				<span class="finedo-label-title">第二列：</span><span class="finedo-label-info">姓名: 必填</span>
			</li>
			<li>
				<span class="finedo-label-title">第三列：</span><span class="finedo-label-info">登录密码: 必填,6-15位字符，必须包含大小写英文字母、数字、特殊符号</span>
			</li>
			<li>
				<span class="finedo-label-title">第四列：</span><span class="finedo-label-info">基本岗位名称: 必填,必须设置一个基本岗位角色，基本岗位角色关联组织节点</span>
			</li>
			<li>
				<span class="finedo-label-title">第五列：</span><span class="finedo-label-info">兼任岗位名称</span>
			</li>
			<li>
				<span class="finedo-label-title">第六列：</span><span class="finedo-label-info">性别: 必填,只能填写:男/女</span>
			</li>
			<li>
				<span class="finedo-label-title">第七列：</span><span class="finedo-label-info">手机号码: 必填,11位手机号码，支持移动、电信、联通号码</span>
			</li>
			<li>
				<span class="finedo-label-title">第八列：</span><span class="finedo-label-info">邮箱: xx@域名，如:test@finedo.cn</span>
			</li>
		</ul>
    </div>
</div>
</body>
</html>
