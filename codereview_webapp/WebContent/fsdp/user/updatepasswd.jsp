<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>
<%
	String uuid = java.util.UUID.randomUUID().toString();
	request.setAttribute("sessionid", uuid);
	request.setAttribute("jsessionid", request.getSession().getId());
%>
<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${jquery_base64_js }
${crypto_aes_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_dialog_js }

<script language="javascript">
function dosavepwd() {

	var result=finedo.validate({
		"lastbptime":{label:"原密码", datatype:"string", maxlength:30, required:true},
		"lastlptime":{label:"新密码", datatype:"string", maxlength:30, required:true},
		"loginpasswd":{label:"确认密码", datatype:"string", maxlength:30, required:true}
	});
	if(!result) {
		return;
	}
	
	// 密码必须由 4-16位字母、数字、特殊符号线组成
	var lastlptime=$('#lastlptime').val();
	var reg = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,}');
	if(!reg.test(lastlptime)) {
		finedo.showhinterror('lastlptime', '登录密码必须由至少 8位字母、数字、特殊符号线组成');
		return;
	}
	
	var loginpasswd=$('#loginpasswd').val();
	if(lastlptime != loginpasswd) {
		finedo.showhinterror('loginpasswd', '确认密码输入不正确，请重新输入');
		return;
	}
	var aeskey = CryptoJS.enc.Utf8.parse(aesvikey);
	var aesiv = CryptoJS.enc.Utf8.parse(aesvikey);
	var usercodeval = $("#usercode").val();
	usercodeval = CryptoJS.AES.encrypt(usercodeval,aeskey,{iv:aesiv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding});
	var lastbptimeval = $("#lastbptime").val();
	lastbptimeval = CryptoJS.AES.encrypt(lastbptimeval,aeskey,{iv:aesiv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding});
	var lastlptimeval = $("#lastlptime").val();
	lastlptimeval = CryptoJS.AES.encrypt(lastlptimeval,aeskey,{iv:aesiv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding});
	var loginpasswdval = $("#loginpasswd").val();
	loginpasswdval = CryptoJS.AES.encrypt(loginpasswdval,aeskey,{iv:aesiv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding});
	var reqdata={
		"usercode":$.base64.btoa(usercodeval),
		"lastbptime":$.base64.btoa(lastbptimeval),
		"lastlptime":$.base64.btoa(lastlptimeval),
		"loginpasswd":$.base64.btoa(loginpasswdval),
		"remark":$.trim($("#remark").val()),
		"requestid":$.trim($("#requestid").val()),
		"opcode":'modifypasswd',
		"sessionid":'${sessionid }',
		"verifycode":$.trim($("#verifycode").val())
	};
	var form = $("#ajaxPassWordForm");
	var action = form.attr("action")+"?"+$.param(reqdata);
	finedo.action.doCommand(action,submitcallback);
}
function submitcallback(jsondata){
	finedo.message.hideWaiting();
	finedo.message.info(jsondata.resultdesc, "密码修改");
	$("#verifycodebtn").trigger("click");
}
function changeImage(obj){
	var url = $(obj).attr("src");
	var date = new Date();
	url = replaceUrl(url, "time", date.getTime());
	$(obj).attr("src",url);
}
//Url字符串替换
function replaceUrl(url, name, value) {
	var reg = new RegExp("(^|)"+ name +"=([^&]*)(|$)"); 
	var tmp = name + "=" + value; 
	if(url.match(reg) != null) { 
		return url.replace(eval(reg),tmp); 
	} else { 
		if(url.match("[\?]")) { 
			return url + "&" + tmp; 
		} else { 
			return url + "?" + tmp; 
		}
	}
}
</script>
</head>
<body>
<div>
   	<form method="post" action="${ctx }/finedo/config/modifypassword" id="ajaxPassWordForm" name="ajaxPassWordForm">
   	<input type="hidden" id="usercode" name="usercode" value="${param.usercode }">
   	<input type="hidden" id="requestid" name="requestid" value="${jsessionid }">
  		<div class="finedo-nav-title">密码设置</div>
		<ul class="finedo-ul">
		<li>
			<span class="finedo-label-title"><font color=red>*</font>原密码</span>
			<fsdp:password id="lastbptime" hint="输入原密码"></fsdp:password>
		</li>			
			  
		<li>
			<span class="finedo-label-title"><font color=red>*</font>新密码</span>
			<fsdp:password id="lastlptime" hint="至少 8位字符，必须包含大小写英文字母、数字、特殊符号"></fsdp:password>
		</li>
		
		<li>
			<span class="finedo-label-title"><font color=red>*</font>确认密码</span>
			<fsdp:password id="loginpasswd" hint="再输入一次密码，并请记住该密码"></fsdp:password>
		</li>
		
		<li>
			<span class="finedo-label-title">密码修改原因</span>
			<fsdp:text id="remark"></fsdp:text>
		</li>
		<li>
			<span class="finedo-label-title"><font color=red>*</font>验证码</span>
			<fsdp:text id="verifycode"></fsdp:text>
	        <img src="${ctx}/finedo/oper/graphicscode?opcode=modifypasswd&sessionid=${sessionid }" id="verifycodebtn" onclick="changeImage(this)" title="点击刷新验证码" width="100" height="22" style="vertical-align: middle;margin-left:2px;cursor: pointer;"/>
		</li>
       </ul>
       </form>
	<ul>
   		<li class="add-center"><input type="button" class="finedo-button" value="提    交" onclick="dosavepwd();"></li>
	</ul>
   </div>
</body>
</html>