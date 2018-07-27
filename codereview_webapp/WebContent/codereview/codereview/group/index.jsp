<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp"%>
<!DOCTYPE html>
<html>
${jquery_js } ${finedo_core_js } ${finedo_commonui_js } ${style_css }
${finedo_dialog_js }
<head>
<meta charset="utf-8">
<title>评审小组首页</title>
	<link rel="stylesheet" href="/codereview_webapp/codereview/codereview/styles/groupstyle.css">
	<style type="text/css">
		
	</style>
</head>
<script type="text/javascript">
var cvgroup = {};
cvgroup.cancleDeleteGroup = function() {
	$(".box-con-del").css('display','none');
	$(".box").css('background', 'initial');
}

cvgroup.showDeleteGroup = function() {
	$(".box-con-del").css('display','block');
	$(".box").css('background', 'rgba(0,0,0,.6)');
}
</script>
<body>
	<div class="p-btn">
		<input class="btn btn-add" type="button" value="添加小组">
		<input class="btn btn-del" type="button" value="删除小组" onclick="cvgroup.showDeleteGroup()">
	</div>
	<div class="mamin-con">
		<table class="main-table" cellpadding="0" cellspacing="0">
			<tr>
				<th class="td1" style="color:#333;"><input type="checkbox">成员姓名</th>
				<th class="td2">项目工程</th>
				<th class="td3">成员人数</th>
			</tr>
			<tr>
				<td class="td1 td-top"><img class="icon" src="/codereview_webapp/codereview/codereview/img/people.png">All Users</td>
				<td class="td2">Global</td>
				<td class="td3">3</td>
			</tr>
			<tr>
				<td class="td1"><input type="checkbox"><img class="icon" src="/codereview_webapp/codereview/codereview/img/people.png">Developer Code</td>
				<td class="td2">Developer</td>
				<td class="td3">3</td>
			</tr>
			<tr>
				<td class="td1"><input type="checkbox"><img class="icon" src="/codereview_webapp/codereview/codereview/img/people.png">Developer Code</td>
				<td class="td2">Developer</td>
				<td class="td3">3</td>
			</tr>
			<tr>
				<td class="td1"><input type="checkbox"><img class="icon" src="/codereview_webapp/codereview/codereview/img/people.png">Developer Code</td>
				<td class="td2">Developer</td>
				<td class="td3">3</td>
			</tr>
		</table>
	</div>
	<!--弹框--删除-->
	<div class="box">
		<div class="box-con-del">
			<div class="box-title">确认删除 <img class="box-del" src="/codereview_webapp/codereview/codereview/img/box-del.png" onclick="cvgroup.cancleDeleteGroup()"></div>
			<div class="box-content">确认删除小组？</div>
			<div class="map-btn">
				<input type="button" class="map-btn3" value="取消" onclick="cvgroup.cancleDeleteGroup()">
				<input type="button" class="map-btn4" value="确定">
			</div>
		</div>
	</div>
</body>
</html>
