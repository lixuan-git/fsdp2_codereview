<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" %>
<%@ include file="/fsdp/common/taglibs2.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>403</title>
<style>
.prompy{ width:720px; height:360px; position:absolute; left:50%; top:50%; margin-left:-360px; margin-top:-180px; font-size:13px; font-family:Microsoft Yahei, SimSun; }
.prompy .img{ float:left; width:388px; height:360px; padding-right:35px; }
.prompy-con{ padding-top:110px; color:#727272; }
.prompy-con a{ color:#0581e9; text-decoration:none;  }
.prompy-con a:hover{ text-decoration:underline; }
.prompy-con p{ line-height:15px; }
</style>
</head>
<body>
<div class="prompy">
	<div class="img"><img src="${ctx }/fsdp/resource/themes/common/images/403.jpg" /></div>
    <div class="prompy-con">
    	<p>您可能没有权限用您提供的凭据查看此目录或网页</p>
        <p>请联系系统管理员</p>
        <p>>><a href="${ctx }/">返回系统首页</a></p>
    </div>
</div>
<div></div>
</body>
</html>
