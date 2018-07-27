<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp"%>

<!doctype html>
<html>
${jquery_js } ${finedo_core_js } ${finedo_commonui_js } ${style_css }
${finedo_dialog_js } ${finedo_tree_js }
${ friendone_js}
<head>
<meta charset="UTF-8">
<script type="text/javascript"
	src="/codereview_webapp/codereview/codereview/highlight.pack.js"></script>
<link rel="stylesheet"
	href="/codereview_webapp/codereview/codereview/styles/docco.css">
<script>
	var treecomment = {};
	//项目名称
	var svnpath = '';
	var cgid = '';
	$(document).ready(function() {
		//初始化项目树状目录
		treecomment.init();
	});

	treecomment.init = function() {
 		var loc = location.href;
		var n1 = loc.length;
		var n2 = loc.indexOf("?") + 1;
		var params = decodeURI(loc.substr(n2));
		svnpath = params.split("&")[0].split("=")[1];
		cgid = params.split("&")[1].split("=")[1];
		$("#main").append("<iframe id='commemtiframe' name='commemtiframe' src='/codereview_webapp/codereview/codereview/comment.jsp?svnlogid=default&projectid="+cgid+"' frameborder='0' height=100% width=100% scrolling='yes' style='float: right;'></iframe>");
		finedo.getTree('treediv', {
			url : '/codereview_webapp/finedo/svnmng/querySvnlogForTree',
			data : {
				"id" : "0",
				"maxlvl" : 30,
				"svnpath" : svnpath
			},
			async : true,
			selecttype : 'single',
			onclick : function(node) {
				$("#svnid").val(node.orgnode);
				$("#svnpath").val(node.optsn);
				$("#cursvnaddr").html(node.directoryUrl);
				queryforsvn(node.optsn);
				$("#addsvnpath").hide();
				$("#addproject").hide();
				$("#setsvnprojdiv").hide();
			}
		});
		$("#treediv_fsdpsvn_span").click(function(){
			window.location.reload();
		}); 
	}
	
	//树状目录点击事件，查询文件信息
	treecomment.dotreeclick = function(ele) {
		if(ele.optsn==$("#treediv_projectsvn_a span").html() || ele.optsn==$("#treediv_fsdpsvn_a span").html()){
			$("#main").empty().append("<iframe id='commemtiframe' name='commemtiframe' src='/codereview_webapp/codereview/codereview/comment.jsp?svnlogid=default&projectid="+cgid+"' frameborder='0' height=100% width=100% scrolling='yes' style='float: right;'></iframe>");
		}
		var svnid = ele.orgnode;
		var filename = ele.optsn;
		var para = {
			"svnid" : svnid,
			"filename" : filename,
			"svnpath" : svnpath
		};

		if (JSON.stringify(filename).indexOf(".") < 0) {
			return;
		}
		finedo.message.showWaiting();
		var parastr = JSON.stringify(para);
		$.ajax({
			"url" : "/codereview_webapp/finedo/svnmng/readSvnFileForTree",
			"type" : "post",
			"data" : parastr,
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				finedo.message.hideWaiting();
				var svnlogid = "";
				if (ret.fail) {
					svnlogid = "error";
				} else {
					svnlogid = ret.object.svnlogid;
				}
				$("#main").empty().append("<iframe src='comment.jsp?svnlogid=" + svnlogid + "&projectid="+cgid+"' frameborder='0' height=100% width=100% scrolling='yes' style='float: right;'></iframe>");
				
			}
		})
	}
</script>
</head>
<body>
	<div
		style="height: 100%; width: 21.5%; overflow: hidden; position: fixed; left: 0; bottom: 0; overflow: auto; padding-left: 10px;">
		<ul id='treediv' class='fdtree' onclick='treecomment.dotreeclick' />
	</div>
	<div id="main"
		style="float: right; width: 78%; height: 100%; position: fixed; right: 0; bottom: 0;">
		<!--  <iframe id="commemtiframe" name="commemtiframe" src="/codereview_webapp/codereview/codereview/comment.jsp?svnlogid=default&projectid="+projectid
			frameborder="0" height=100% width=100% scrolling="yes"
			style="float: right;"></iframe> -->
	</div>

</body>

</html>

