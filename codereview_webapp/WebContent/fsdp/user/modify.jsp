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
${finedo_choose_js }

<script>

// Web组件初始化 
$(document).ready(function() {
	$("#roleid").val('${stdrole.roleid }');
	$("#roleid_name").val('${stdrole.rolename }');
	
	<c:set value="" var="roleids"></c:set>
	<c:set value="" var="roleids_name"></c:set>
	<c:forEach items="${otherrole}" var="rolell">
    	<c:if test="${!empty roleids}"><c:set value="${roleids},${rolell.roleid }" var="roleids"></c:set></c:if>
    	<c:if test="${empty roleids}"><c:set value="${rolell.roleid }" var="roleids"></c:set></c:if>
    	
    	<c:if test="${!empty roleids_name}"><c:set value="${roleids_name},${rolell.rolename }" var="roleids_name"></c:set></c:if>
    	<c:if test="${empty roleids_name}"><c:set value="${rolell.rolename }" var="roleids_name"></c:set></c:if>
    </c:forEach>
	$("#roleids").val('${roleids}');
	$("#roleids_name").val('${roleids_name}');
		
    /*$.ajax({
		type: "get",
		dataType:  "json",
		url: "${ctx }/finedo/role/queryRoleBySelect",
		success: function(data) {
			 data["type"]="single";
			 data["value"]=stdroleid;
        	 finedo.getSelect("roleid", data);
        	 
        	 data["type"]="multi";
        	 data["style"]="width:500px";
        	 data["value"]=otherroleids;
        	 finedo.getSelect("roleids", data);
          }
      });*/
});

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

/************** 数据校验  ****************/
function doNext() {
	dosubmit();
}

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
	var form = $("#ajaxForm");
	var options = {     
        url:	   form.attr("action"),
        success:   submitcallback,
        type:      'POST',
        dataType:  "json",
	    clearForm: false
    };
	finedo.message.showWaiting();
	form.ajaxSubmit(options);
}

function submitcallback(jsondata){
	finedo.message.hideWaiting();
	finedo.message.info(jsondata.resultdesc, "修改用户信息");
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
    	<div class="add-common-name-add">修改用户信息<br/>
            <ul>
            	<li id="common_add_card" class="add-link-li">全部</li>
            </ul>
        </div>
        <input type="button" class="finedo-button-blue" style="float:right;" value="提交" onclick="doNext();">
    </div>
    
    <form method="post" action="${ctx }/finedo/user/modifyUser" id="ajaxForm" name="ajaxForm">
    <input type="hidden" id="userid" name="userid" value="${sysuser.userid }"/>
    <input type="hidden" id="personid" name="personid" value="${sysuser.personid }"/>

    <div class="finedo-nav-title">基本信息</div>
	<ul class="finedo-ul">
		<li>
			<span class="finedo-label-title"><font color=red>*</font>用户账号</span>
			<fsdp:text id="usercode" hint="最大30个字符，支持大小写英文、数字、下划线格式" value="${sysuser.usercode }"/>
		</li>
			
          	<li>
          		<span class="finedo-label-title"><font color=red>*</font>用户姓名</span>
          		<fsdp:text id="personname" value="${sysuser.personname }"/>
          	</li>
          	
          	<li>
          		<span class="finedo-label-title"><font color=red>*</font>生效日期</span>
          		<fsdp:date id="effdate" value="${sysuser.effdate }"/>
          	</li>
          	
          	<li>
          		<span class="finedo-label-title"><font color=red>*</font>失效日期</span>
          		<fsdp:date id="expdate" value="${sysuser.expdate }"/>
          	</li>
          	            	
		<li>
			<span class="finedo-label-title"><font color=red>*</font>性别</span>
			<fsdp:radio id="gender" datasource="性别" selectedvalue="${sysuser.gender }"/>
		</li>
			 				
          	<li>
          		<span class="finedo-label-title"><font color=red>*</font>用户状态</span>
          		<fsdp:radio id="state" datasource="用户状态" selectedvalue="${sysuser.state }"/>
          	</li>
		
		<li>
			<span class="finedo-label-title"><font color=red>*</font>手机号码</span>
			<fsdp:text id="phoneno" hint="11位手机号码，支持移动、电信、联通号码" value="${sysuser.phoneno }"/>
		</li>
          	
          	<li>
          		<span class="finedo-label-title">邮箱</span>
          		<fsdp:text id="email" hint="格式:xx@域名，如:test@finedo.cn" value="${sysuser.email }"/>
          	</li>
          	
          	<li>
          		<span class="finedo-label-title">地址</span>
          		<fsdp:text id="address" style="width:600px" value="${sysuser.address }"/>
          	</li>
		
		<li>
			<span class="finedo-label-title">备注</span>
			<fsdp:text id="remark" style="width:600px" value="${sysuser.remark }"/>
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
    </form>
</div>
</body>
</html>
