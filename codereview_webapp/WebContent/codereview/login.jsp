<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs.jsp" %>
<!DOCTYPE html>
<head>
<title>非度信息代码走查</title>
${jquery_js}
${finedo_md5_js }
<link href="${ctx }/codereview/resource/styles/loginstyle.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">
function inputfocus(obj){
	if(obj.name == "account") {
		if(obj.value == "用户名")
			obj.value="";
	}

	if(obj.name == "verifycode") {
		if(obj.value == "验证码")
			obj.value="";
	}
}

function inputblur(obj){
	if(obj.name == "account") {
		if(obj.value == "")
			obj.value="用户名";
	}

	if(obj.name == "verifycode") {
		if(obj.value == "")
			obj.value="验证码";
	}
}

function login(){


	if($("#account").val() == "" || $("#account").val() == "用户名"){
		alert("用户名不能为空，请录入用户名！");
		$("#account")[0].focus();
		return;
	}
	
	if($("#passwd").val() == "" || $("#passwd").val() == "密码"){
		alert("密码不能为空，请录入密码！");
		$("#passwd")[0].focus();
		return;
	}
	if($("#verifycode").val() == "" || $("#verifycode").val() == "验证码"){
		alert("验证码不能为空，请录入验证码！");
		$("#verifycode")[0].focus();
		return;
	}
	$("#LoginForm").submit();	
}

function button_onkeypress(event) {
	if(event.keyCode == 13)
		login();
}

function changeImage(obj){
	var url = $(obj).attr("src");
	var date = new Date();
	url = url.changeQuery("time",date.getTime());
	$(obj).attr("src",url);
}

/**
 * 字符串扩展方法，用于在URL中追加或者修改参数
 * @param name
 * @param value
 * @return
 */
String.prototype.changeQuery = function(name,value) { 
	var reg = new RegExp("(^|)"+ name +"=([^&]*)(|$)"); 
	var tmp = name + "=" + value; 
	if(this.match(reg) != null) { 
		return this.replace(eval(reg),tmp); 
	} else { 
		if(this.match("[\?]")) { 
			return this + "&" + tmp; 
		} else { 
			return this + "?" + tmp; 
		} 
	} 
};


</script>

<body  onkeydown="button_onkeypress(event);">

<!--------登录页面-------->
<div class="login-head">
	<div class="lh-con"><img class="login-logo" src="${ctx }/codereview/resource/images/login_logo.png" />非度信息FSDP2.0代码走查</div>
</div>
<div class="login-con">
	<div class="mid">
		<div class="mid-page"><img src="${ctx }/codereview/resource/images/login_page.jpg" /></div>
      <form id="LoginForm" name="LoginForm" method="post" action="${ctx}/finedo/codereview/login/auth">
      <input type="hidden" name="portalpage" id="portalpage" value="fsdpportalpage"/>
      <input type="hidden" name="loginpage" id="loginpage" value="fsdploginpage"/>
        <div class="mid-con">
        	<div class="login-name">公司OA账号登录</div>
            <div class="mid-name">
            	<p><strong>登录名：</strong></p>
                <p><input type="text" class="text-name"  id="account" name="account"   /></p>
            </div>
            <div class="mid-name">
            	<p><strong>登录密码：</strong></p>
                <p><input type="password" id="passwd" name="passwd" class="text-psd" value=""  /></p>
                
                <p class="cheak"><input type="checkbox" name="rememberoptrid"	id="rememberoptrid" /><label for="rememberoptrid" style="cursor:pointer">记住用户名</label></p>
            </div>
          
				 <p style="color: red">${resultdesc }</p>
		
            <div class="mid-btn"><input class="login-btn" type="button" value="欢迎登录" onclick="login()" /></div>
        </div>
        </form>
    </div>
</div>
<div class="copy">
	<p><a href="http://finedo.cn/finedosite/t/new/article-info.jsp?classid=CCT13110000001579">关于非度</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="http://finedo.cn/">官方网站</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="#">官方微博</a></p>
    <p>版权所有：&copy;非度信息技术有限公司&nbsp;&nbsp;&nbsp;&nbsp;授权信息：系统License为免费版本！</p>
</div>

</body>
</html>
