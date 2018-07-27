<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${SYSPARAM_KEY['系统标题'] }</title>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_dialog_js }

<script type="text/javascript">
function registershowevent(object, defaultval) {
	object.onfocus=function(){
		if(this.value == defaultval)
			this.value="";
	};
	
	object.onblur=function(){
		 if(this.value == "")
			 this.value=defaultval;
	};
}

window.onload=function(){
	var accountobj=document.getElementById('account');
	var passwdobj=document.getElementById('passwd');
	var verifycodeobj=document.getElementById('verifycode');
	
	registershowevent(accountobj, "登陆账号");
	registershowevent(passwdobj, "登陆密码");
	registershowevent(verifycodeobj, "验证码");
	
	if($.cookie("account")!=null && $.cookie("account")!=""){
		$("#account").attr("value",$.cookie("account"));
		$("#passwd").focus();
	}
}

function login(){
	if($("#account").val() == ""){
		finedo.message.error("错误提示","登陆账号不能为空，请录入！");
		return;
	}
	if($("#passwd").val() == ""){
		$.messager.alert("错误提示","登陆密码不能为空，请录入登陆密码！");
		return;
	}
	if($("#verifycode").val() == ""){
		$.messager.alert("错误提示","验证码不能为空，请录入验证码！");
		return;
	}
	
	// 记忆登录账号与密码
	$.cookie("account", $("#account").val());
	$.cookie("passwd", $("#passwd").val());
	
	$("#LoginForm").submit();	
}

function button_onkeypress(event) {
	if(event.keyCode == 13)
		login();
}

function changeImage(obj){
	var url = $(obj).attr("src");
	var date = new Date();
	url = finedo.fn.replaceUrl(url, "time", date.getTime());
	$(obj).attr("src",url);
}

// 重新加载登录页面，避免首页部分iframe页面失败后局部加载到登录页问题
function loadUrlToTopWin() {
	if(window.top != window)
		window.top.location.href=window.location.href;
};
loadUrlToTopWin();

// 忘记密码对话框
function show(){
	document.getElementById('retrieve-password').style.display ='block'
}

function hide(){
	document.getElementById('retrieve-password').style.display ='none'
}

function showandhide(){
	var obj=document.getElementById('common-con').style.display;
	if(obj=='block'){
		document.getElementById('common-con').style.display='none'
		document.getElementById('openstop').style.backgroundPosition='12px 0'
	}else{
		document.getElementById('common-con').style.display='block'
		document.getElementById('openstop').style.backgroundPosition='0'
	}
}
</script>
</head>

<body class="login-bg">
	<form id="LoginForm" name="LoginForm" method="post" action="${ctx}/finedo/auth/login">
	<input type="hidden" name="portalpage" id="portalpage" value="/fsdp/frame/index.jsp" />
	<input type="hidden" name="loginpage" id="loginpage" value="/fsdp/login.jsp" />
	<div class="login">
		<div class="login-logo">${SYSPARAM_KEY['版本号'] }</div>
	    <div class="login-con">
	    	<input class="login-text login-usename" value="登陆账号" type="text" id="account" name="account">
	        <input class="login-text login-password" value="登陆密码" type="password" id="passwd" name="passwd">
	        <p><input type="text" class="login-num" value="验证码" id="verifycode" name="verifycode" onkeypress="button_onkeypress(event)">
	        <img src="${ctx}/finedo/oper/graphicscode?opcode=login" onclick="changeImage(this)" title="点击刷新验证码" width="100" height="22" style="vertical-align: middle;margin-left:2px;cursor: pointer;"/></p>
	       
	       <p><font color="red">${resultdesc }</font></p>
	        <input class="login-login" value="登录" type="button" onclick="login()">
	    </div>
	</div>
	</form>
	
	<div class="login-copyright">
		<a target='_blank' href="http://finedo.cn">关于非度</a>|
	    <a target='_blank' href="http://finedo.cn">官方网站</a>|
	    <a href="#">官方微博</a>|
	    <a href="#">官方微信</a><br/>
	    版权所有©2008-2015非度信息技术有限公司
	</div>
</body>
</html>