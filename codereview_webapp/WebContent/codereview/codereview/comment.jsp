<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp"%>

<!doctype html>
<html>
${style_css } ${jquery_js } ${finedo_core_js } ${finedo_commonui_js }
${finedo_dialog_js }
${finedo_grid_js }
<head>
<meta charset="UTF-8">
<script type="text/javascript"
	src="/codereview_webapp/codereview/codereview/highlight.pack.js"></script>
<link rel="stylesheet"
	href="/codereview_webapp/codereview/codereview/styles/docco.css">
<style>
pre {
	position: relative;
	margin-bottom: 24px;
	border-radius: 3px;
}

code {
	display: block;
	padding: 12px 24px;
	font-weight: 300;
	font-family: Menlo, monospace;
	font-size: 1.2em;
}

code.has-numbering {
	margin-left: 0px;
}

.pre-numbering {
	position: absolute;
	top: 0;
	left: 0;
	width: 20px;
	border-right: 1px solid #C3CCD0;
	border-radius: 3px 0 0 3px;
	background-color: #EEE;
	text-align: right;
	font-family: Menlo, monospace;
	font-size: 1.2em;
	color: #AAA;
}

.mask {
	position: fixed;
	top: 0px;
	background-color: #f3f3f3;
	z-index: 1002;
	width: 20%;
	left: 60%;
}

.hljs {
	width: 100%;
	overflow-x: visible;
}

.hljs ol {
	margin: 0px 0px 0 40px !important;
}

.hljs ol li {
	list-style: decimal-leading-zero;
	margin: 0 !important;
}

.hljs ol li:nth-of-type(even) {
	
}

.hljs ul {
	margin: 0px 0px 0 40px !important;
}

.hljs ul li {
	list-style: decimal-leading-zero;
	padding: 3px !important;
	margin: 0 !important;
}

.hljs ul li:nth-of-type(even) {
	
}

.title-nav {
	width: 100%;
	box-sizing: border-box;
	padding: 0 10px;
	line-height: 20px;
	font-size: 12px;
	color: #00a5fc;
	background: #fff;
	border-bottom: 1px solid #ddd;
}

.title-nav span {
	border-left: 3px solid #00a5fc;
	padding-left: 8px;
}

.title-nav2 {
	width: 100%;
	box-sizing: border-box;
	padding: 0 10px;
	line-height: 20px;
	font-size: 12px;
	color: #999;
	border-bottom: 1px solid #ddd;
}

.title-nav2 span {
	border-left: 3px solid #999;
	padding-left: 8px;
}

#historydiv {
	padding: 0 7px;
	width: 100%;
	box-sizing: border-box;
}

#historydiv p {
	width: 100%;
	margin-bottom: 5px;
	color: #666;
	background: #fff;
	padding: 5px 0;
	font-size: 14px;
}

#historydiv p span {
	display: block;
	margin-bottom: 7px;
	color: #000;
	position: relative;
}

#historydiv p span i {
	position: absolute;
	right: 3.5px;
	font-weight: 400;
	color: #777070;
	font-size: 12px;
	font-style: normal;
}

#historydiv p span img {
	width: 12px;
	margin-left: 5px;
	vertical-align: middle;
}

#historydiv b {
	background: #56c3ff;
	color: #fff;
	font-style: normal;
	font-weight: 400;
	padding-left: 5px;
	display: inline-block;
	font-size: 13px;
}

#historydiv b.plcon {
	background: none;
	color: #000;
	font-size: 13px;
	margin-right: 20px;
}

#historydiv1 {
	padding: 0 7px;
	width: 99%;
	background: #ffffe1;
	box-sizing: border-box;
}

#historydiv1 p {
	width: 100%;
	color: #666;
	background: #ffffe1;
	padding: 5px 0;
	border-bottom: 1px solid #ddd;
}

#historydiv1 p span {
	display: block;
	margin-bottom: 5px;
	font-weight: 700;
	color: #000;
}

#historydiv1 p span img {
	width: 12px;
	margin-left: 5px;
	vertical-align: middle;
}

.yesimg {
	position: absolute;
	right: 10px;
	width: 13px;
}

.item22 {
	width: 100%;
	border-bottom: 1px solid #ddd;
	padding-bottom: 10px;
}

.item22 span {
	display: inline-block;
	height: 20px;
	line-height: 20px;
	font-size: 12px;
	border: 1px solid #ddd;
	padding: 0 15px;
	background: #fff;
	color: #888;
	cursor: pointer;
	margin-right: 10px;
}

.item22 span.selectedstyle {
	background:
		url(/codereview_webapp/codereview/codereview/img/icon_select.jpg)
		bottom right no-repeat;
	border: 1px solid #ff7f18;
	color: #ff7f18;
}

.com-btnactive {
	background: #3ac7e9;
	padding: 6px 10px;
	border: none;
	outline: none;
	color: #fff;
	cursor: pointer;
	font-size: 12px;
}

.com-btn {
	background: none;
	padding: 5px 10px;
	border: 1px solid #3ac7e9;
	outline: none;
	color: #3ac7e9;
	cursor: pointer;
	font-size: 12px;
}

.com-btn2active {
	background: #eb982b;
	padding: 6px 10px;
	border: none;
	outline: none;
	color: #fff;
	cursor: pointer;
	font-size: 12px;
}

.com-btn2 {
	background: none;
	padding: 5px 10px;
	border: 1px solid #eb982b;
	outline: none;
	color: #eb982b;
	cursor: pointer;
	font-size: 12px;
}

.add-con {
	box-sizing: border-box;
	padding: 10px;
}

.add-comson {
	background: #fff;
	border-bottom: 1px dashed #efefef;
	overflow: hidden;
	padding: 10px 0;
	font-family: "microsoft yahei";
}

.add-comson-l {
	float: left;
	width: 32px;
	margin-right: 10px;
	margin-top: 4px;
}

.add-comson-r {
	float: left;
}

.add-com-son-rt {
	font-weight: bold;
	font-size: 16px;
	color: #21affb;
}

.add-com-son-rd span {
	font-size: 12px;
	color: #aaa;
	margin-right: 6px;
}

/*下拉框样式*/
 select {
	width: 80px;
	height: 24px;
	font-family: "微软雅黑";
	font-size: 12px;
	border: 1px #1a1a1a solid;
	border-radius: 5px;
	margin-right: 10px;
	cursor:pointer;
}
</style>
<script>
	//标记当前需要突出显示的元素
	var gotofileEle = '';
	var gotofileEleForOffice = '';
	//queryCommentWords方法根据此变量的值，确定是否需要评审跳转代码。0不跳转，1跳转。
	var isgotofile = '0';
	var isScrollBottom = false;
	var projectid = '';
	var isOfficefile = false;
	var filename = '';
	
	//保存鼠标按下，抬起的坐标
	var mousedownX = "";
	var mousedownY = "";
	var mouseupX = "";
	var mouseupY = "";
	
	//页面初始化，高亮插件初始化，添加代码鼠标松开事件
	$(document).ready(function() {
		codecomment.queryMouseOffset = function(obj){
			var scrollTop = $(obj).scrollTop();
			var scrollHeight = $("pre").height();
			var windowHeight = $(document).height();
			if(scrollTop + windowHeight >= scrollHeight){
				$("#originaldiv").unbind('scroll');
				isScrollBottom = true;
				codecomment.makesurequery();
			}
		} 
		
		var startrownumber = '';

		hljs.initHighlightingOnLoad();

		$("#GetSVNFile").submit();

		$("pre code").bind("contextmenu", function() {
			return false;
		});
		$("pre code").mouseup(function(e) {

			if (3 == e.which)
				return;
			var txt;
			var parentOffset = $(this).offset();
			var x = e.pageX - parentOffset.left;
			var y = e.pageY - parentOffset.top;
			txt = window.getSelection();
			if (txt.toString().length > 1) {
				el = e.target;//鼠标每经过一个元素，就把该元素赋值给变量el
				var divel = $(el).parents(".originaldiv")[0];
				var max_width = 0;
				var index = 0;
				
				if (isOfficefile) {
					mouseupX = x;
					mouseupY = y;
/* 					codecomment.refreshRightdiv();
					$(".finedo-ul span:eq(0)").text('请输入评审内容...');
					$(".finedo-ul").css('display', 'block');
					//第一步：获取canvas元素
					var canvasDom = document.getElementById("mainCanvas");
					//第二步：获取上下文
					var context = canvasDom.getContext('2d');
					var height = document.getElementById("codeblock").offsetHeight + 50;
					var downPoint = codecomment.windowToCanvas(canvasDom, mousedownX, mousedownY, height);
					var upPoint = codecomment.windowToCanvas(canvasDom, mouseupX, mouseupY, height);
					codecomment.drawReviewRect(downPoint.x, downPoint.y + (upPoint.y - downPoint.y) / 2, upPoint.x - downPoint.x, 16.0 * canvasDom.height / height, "#ff7f18");

					return; */
					//第一步：获取canvas元素
					 var canvasDom = document.getElementById("mainCanvas");
					 //第二步：获取上下文
					 var context = canvasDom.getContext('2d');
	/* 				 alert(mousedownX);
					 alert(mousedownY);
					 alert(mouseupX);
					 alert(mouseupY); */
					 var height = document.getElementById("codeblock").offsetHeight;
					 var downPoint = codecomment.windowToCanvas(canvasDom, mousedownX, mousedownY, height);
					 var upPoint = codecomment.windowToCanvas(canvasDom, mouseupX, mouseupY, height);
					 //以下演示填充矩形。
					 //context.fillStyle = "yellow";			 
					 //context.fillRect(downPoint.x, downPoint.y + 8, upPoint.x-downPoint.x, 16.0*canvasDom.height/height);
					 //codecomment.removeReviewRect(downPoint.x, downPoint.y + 8, upPoint.x-downPoint.x, 16.0*canvasDom.height/height)
					 //codecomment.drawReviewRect(downPoint.x, downPoint.y + 8, upPoint.x-downPoint.x, 16.0*canvasDom.height/height);
					//最新有效代码：codecomment.drawReviewRect(downPoint.x, downPoint.y + 12, upPoint.x-downPoint.x, 16.0*canvasDom.height/height);
					//codecomment.drawReviewRect(downPoint.x, downPoint.y + (upPoint.y-downPoint.y)/2, upPoint.x-downPoint.x, 16.0*canvasDom.height/height,"#ff7f18");
					var rectHeight = (mouseupY>mousedownY) ? (mouseupY-mousedownY) : (mousedownY-mouseupY);
					rectHeight = rectHeight<17 ? 17 : rectHeight;
					
					//codecomment.drawReviewRect(downPoint.x, downPoint.y, upPoint.x-downPoint.x, rectHeight*canvasDom.height*1.0/height,"#ff7f18");
					// 把获取到的高度设置给class类名为zuobian的元素
					var canvas = document.getElementById("mainCanvas");
					codecomment.drawReviewRect(0, downPoint.y, canvas.clientWidth, rectHeight*canvasDom.height*1.0/height,"#ff7f18");
					//alert(1);
					codecomment.removeReviewRect(0, downPoint.y, canvas.clientWidth, rectHeight*canvasDom.height*1.0/height);

					//var selectAnchorNode = txt.anchorNode;
	                //var selectFocusNode = txt.focusNode;
	                //alert("selectAnchorNode=" + selectAnchorNode + "     selectFocusNode="+selectFocusNode);
	                //var star = txt.anchorOffset;
	                //var end = txt.focusOffset;
	                //alert("star=" + star + "     end="+end);
	                //selectedText = "<span style='background:red'>"+txt.toString()+"</span>"; 
	                
	                //var textObj = $(".html:first").html().toString();
	                //var tempStr1 = textObj.substring(0,star); 
	                //var tempStr2 = textObj.substring(end); 

	                //$(".html:first").html(tempStr1 + selectedText + tempStr2) ; 
					codecomment.refreshRightdiv();
					$(".finedo-ul span:eq(0)").text('请输入评审内容...');
					$(".finedo-ul").css('display', 'block');
					return;
				}

				$('ol li').each(function() {
					var offset = $(this).offset();
					var myTop = offset.top;
					var myBottom = myTop + 17;
					if (y + parentOffset.top >= myTop && y + parentOffset.top < myBottom) {
						codecomment.refreshRightdiv();
						if (startrownumber == (index + 1)) {
							$("#rownumber").val(startrownumber);
							$("#startrownumber").val(startrownumber);
							$(".finedo-ul span:eq(0)").text('请输入第' + startrownumber + '行的评审内容...');
						} else {
							//处理倒着选择多行
							if (startrownumber > (index + 1)) {
								$("#rownumber").val(startrownumber);
								$("#startrownumber").val(index + 1);
							} else {
								$("#rownumber").val(index + 1);
								$("#startrownumber").val(startrownumber);
							}
							startrownumber = $("#startrownumber").val();
							var endrownumber = $("#rownumber").val();
							$(".finedo-ul span:eq(0)").text('请输入第' + startrownumber + '行到' + endrownumber + '行的评审内容...');
						}
						$(".finedo-ul").css('display', 'block');
						var filename = $("#filename").val();
						var revision = $("#revision").val();
						codecomment.queryCommentWordsForSameline(JSON.stringify({
							"filename" : filename,
							"rownumber" : index + 1,
							"revision" : revision
						}));
					}
					index = index + 1;
				})
			}
		});
		$("pre code").mousedown(function(e) {
			if (3 == e.which)
				return;
			var txt;
			var parentOffset = $(this).offset();
			var x = e.pageX - parentOffset.left;
			var y = e.pageY - parentOffset.top;
			if (isOfficefile) {
				mousedownX = x;
				mousedownY = y;
				
			}
			//alert("开始x："+x+"，y"+y);
			txt = window.getSelection();
			el = e.target;//鼠标每经过一个元素，就把该元素赋值给变量el
			var divel = $(el).parents(".originaldiv")[0];
			var max_width = 0;
			var index = 0;
			$('ol li').each(function() {
				var offset = $(this).offset();
				var myTop = offset.top;
				var myBottom = myTop + 17;
				if (y + parentOffset.top >= myTop && y + parentOffset.top < myBottom) {
					startrownumber = index + 1;
				}
				index = index + 1;
			})
		});
		codecomment.init();
	});
	var codecomment = {};

	//加载文件信息及内容
	codecomment.init = function() {
		/* 		var loc = location.href;
		 var n1 = loc.length;
		 var n2 = loc.indexOf("?") + 1;
		 var params = decodeURI(loc.substr(n2));
		 var svnlogid = params.split("=")[1]; */
		var svnlogid = "${param.svnlogid}";
		projectid = "${param.projectid}";
		/* 		if ( finedo.fn.isNon(projectid) ) {  
		 return; 
		 }else {  
		 projectid = projectid.replaceAll("<","&lt;");  
		 projectid = projectid.replaceAll(">","&gt;"); 
		 }
		 if ( finedo.fn.isNon(svnlogid) ) {  
		 return; 
		 }else {  
		 svnlogid = svnlogid.replaceAll("<","&lt;");  
		 svnlogid = svnlogid.replaceAll(">","&gt;"); 
		 } */
		if (svnlogid == 'error') {
			//$("#originaldiv").empty();
			//与treecomment.jsp页面关联，返回error说明服务异常，添加页面提醒。
			$("pre").remove();
			$("#originaldiv").append("<div id='hint' style='padding-top: 40px;margin-left: 20px;font-size: 14px;text-align: center;color:#ff7f18;'><img src='/codereview_webapp/codereview/codereview/img/hint-r.png' style='vertical-align: middle;'>文件格式不支持或者服务异常！</div>");
			return;
		}
		var para = {
			"svnlogid" : svnlogid
		};
		var parastr = JSON.stringify(para);
		finedo.message.showWaiting();
		$.ajax({
			"url" : "/codereview_webapp/finedo/svnmng/readSvnFile",
			"type" : "post",
			"data" : parastr,
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : codecomment.initcall,
			"error" : function() {
				finedo.message.error = '后台错误！';
			}
		});

	}

	//加载文件信息及内容的回调方法，添加行号，查询评审。
	codecomment.initcall = function(ret) {

		finedo.message.hideWaiting();
		//本页面为复用页面，treecomment.jsp加载本页面时，添加了默认参数default
		if (ret.resultdesc == 'default') {
			//添加默认时的评审提醒 需求变更为：展示项目的信息
			$("pre").remove();
			//请打开需要评审的文件
			$("#originaldiv").empty().append("<iframe src='group/editgroupForTree.jsp?cgid=" + projectid + "' frameborder='0' height=100% width=100% scrolling='yes' style='float: right;'></iframe>");
			return;
		}
		codecomment.refreshRightdiv();
		if (ret.object.codecontent == "") {
			$(".java:first").text("未获取到文件信息！");
			return;
		}
		$("#revision").val(ret.object.revision);
		$("#author").val(ret.object.submitperson);
		$("#personname").val(ret.object.personname);
		$("#filename").val(ret.object.filename);
		$("#repurl").val(ret.object.repurl);
		$("#svnpath").val(ret.object.projectname);

		filename = $("#filename").val();
		codecomment.checkOfficefile(filename);

		var lastchangeddate = ret.object.lastchangeddate;
		var codecontent = ret.object.codecontent;
		//filename.substr(filename.lastIndexOf("."))
		var filename = ret.object.filename;
		if (filename.substr(filename.lastIndexOf(".")) == ".jsp") {
			$("code").removeClass("java");
			$("code").addClass("html");
			$(".html:first").text(codecontent);
		} else if (isOfficefile) {
			//$(".java:first").text(codecontent);
			$("code").removeClass("java");
			$("code").addClass("html");
			$(".html:first").html(codecontent);
			$("code div").css("margin-left", "0px");
			$("code div").css("margin-right", "0px");
			
			var codeBlock = document.getElementById("codeblock");
			var height = codeBlock.offsetHeight;
			var width = codeBlock.offsetWidth;
			// 把获取到的高度设置给class类名为zuobian的元素
			var canvas = document.getElementById("mainCanvas");
			canvas.setAttribute("height",canvas.clientHeight);
	    	canvas.setAttribute("width",canvas.clientWidth);
	    	
	    	var canvasDiv = document.getElementById("canvasdiv");
	    	canvasDiv.style.height = height + 'px';
	    	canvasDiv.style.width = width + 'px';
		} else {
			$(".java:first").text(codecontent);
		}

		//添加查询评审统计  
		if ($(document).height() >= $("pre").height()) {
			isScrollBottom = true;
			codecomment.makesurequery();
		}

		//添加行号
		if(!isOfficefile){
			codecomment.addCount();
		}
		

		var part1 = $("#repurl").val();
		var part2 = $("#filename").val();
		var fullpath = part1.substring(0, part1.length - 1) + part2;
		var info = "(" + $("#revision").val() + ")" + " submitted by " + $("#author").val() + "(" + $("#personname").val() + ") " + "on " + lastchangeddate + ",    " + fullpath;
		$("#newversion-span").html("版本号 " + info);
		hljs.initHighlighting.called = false;
		hljs.initHighlighting();
		var filename = $("#filename").val();
		var revision = $("#revision").val();
		isgotofile = '1';
		codecomment.queryCommentWords(JSON.stringify({
			"filename" : filename,
			"revision" : revision
		}));

/* 		var height = document.getElementById("codeblock").offsetHeight + 50;
		// 把获取到的高度设置给class类名为zuobian的元素
		var canvas = document.getElementById("mainCanvas");
		canvas.setAttribute("height", canvas.clientHeight);
		canvas.setAttribute("width", canvas.clientWidth);

		var canvasDiv = document.getElementById("canvasdiv");
		canvasDiv.style.height = (height + 16) + 'px'; */
	}

	//查询当前文件的评审信息
	codecomment.queryCommentWords = function(parastr) {
		//添加查询评审统计 
		codecomment.makesurequery();
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/query",
			"type" : "post",
			"data" : parastr,
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : codecomment.queryCommentWordsCallback,
			"error" : function() {
				finedo.message.error = '后台错误！';
			}
		})
	};
	//查询当前文件的评审信息回调方法
	codecomment.queryCommentWordsCallback = function(ret) {
		codecomment.refreshRightdiv();

		for (var i = 0; i < ret.length; i++) {
			//office文件
			if (isOfficefile) {
				codecomment.drawOneComment(ret[i].mousedownx, ret[i].mousedowny, ret[i].mouseupx, ret[i].mouseupy, "#ff7f18");
			}
			//

			var filecommentObj = ret[i];
			var optdate = codecomment.datehandle(filecommentObj.optdate);
			var rownumber = filecommentObj.rownumber;
			var startrownumber = filecommentObj.startrownumber;
			//评审展示行号
			var linemessage = codecomment.lineMessage(rownumber, startrownumber);
			var filename = filecommentObj.filename;
			var state = filecommentObj.finishstate == 0 ? '未解决' : '已解决';
			var yesorno = state == '未解决' ? 'no' : 'yes';
			//拒绝接受的样式
			var class4 = "";
			var class5 = "";
			//从后端获取评审类型
			var commenttype = filecommentObj.commenttype;
			if (filecommentObj.acceptstate == 'refused') {
				class4 = "selectedstyle";
			} else if (filecommentObj.acceptstate == 'accepted') {
				class5 = "selectedstyle";
			}
			var color1 = '';
			var color2 = '';
			var ismustmodify = filecommentObj.ismustmodify == 'yes' ? '[必]' : '[建]';
			var iscommon = filecommentObj.iscommon == 'yes' ? '[共]' : '[非共]';
			if (ismustmodify == '[必]') {
				color1 = '#ff7f18';
			} else {
				color1 = '#3ac7e9';
			}
			if (iscommon == '[共]') {
				color2 = '#ff7f18';
			} else {
				color2 = '#3ac7e9';
			}
			$(".commentdiv").append(
					"<div ondblclick='codecomment.gotofile(" + JSON.stringify(filecommentObj) + ")'><p id='query"+i+"' class='"+i+"' ><span>" + filecommentObj.personname + "<img title='编辑' src='/codereview_webapp/codereview/codereview/img/1.png' onclick='codecomment.modifycomment(" + JSON.stringify(filecommentObj) + "," + i + ")' /><img title='跳转到代码' src='/codereview_webapp/codereview/codereview/img/2.png' onclick='codecomment.gotofile(" + JSON.stringify(filecommentObj) + ")' /> <i> " + optdate + "<img title='删除评审' src='/codereview_webapp/codereview/codereview/img/delet.png' onclick='codecomment.deletecomment(" + JSON.stringify(filecommentObj) + "," + i + ")'/></i></span><b>line" + linemessage + "：</b><b class='plcon'><font style='color:"+color1+";'>" + ismustmodify
							+ "</font><font id='iscommon"+i+"' style='color:"+color2+";display:none;'>" + iscommon + "</font>" + filecommentObj.codecomment + "</b><img title='" + state + "' class='yesimg' src='/codereview_webapp/codereview/codereview/img/icon_" + yesorno + ".png' onclick='codecomment.changeState(" + JSON.stringify(filecommentObj) + ",this)'/></p><div class='item22'><select id='cmselect" + i + "' onchange='codecomment.changecommenttype(" + JSON.stringify(filecommentObj) + ",this)' ><option value='codestyle'>代码规范</option><option value='codeunnece'>代码冗余</option><option value='sqlspecs'>数据库规范</option><option value='bug'>缺陷BUG</option><option value='security'>安全问题</option><option value='performance'>性能问题</option><option value='officespecs'>文档规范</option><option value='other'>其他</option></select><span class='" + class5
							+ "' style='margin-right:0;' onclick='codecomment.acceptOrRefuse(" + JSON.stringify(filecommentObj) + ",this)'>接受</span><span class='" + class4 + "' style='margin-right:0;' onclick='codecomment.acceptOrRefuse(" + JSON.stringify(filecommentObj) + ",this)'>拒绝</span></div></div>");
			//设置选中的值
			$("#cmselect" + i + " option[value='" + commenttype + "']").attr("selected", "selected");
			if (iscommon == '[共]') {
				$("#iscommon" + i).css('display', 'inline');
			}
			var acceptstate = filecommentObj.acceptstate;
			if (acceptstate == 'refused') {
				$("p[id='query" + i + "']").next().append("<p style='font-size: 12px;'>" + filecommentObj.submitperson + "拒绝了该条评审！拒绝原因：" + filecommentObj.remark + "</p>");
			}
			//添加代码中的评审
			if (isgotofile == '1') {
				codecomment.gotofileWithoutScroll(filecommentObj);
			}

		}
	}

	//查询当前行号的评审信息
	codecomment.queryCommentWordsForSameline = function(parastr) {
		$(".commentdiv .finedo-ul").nextAll().remove();
		$(".finedo-ul span:eq(0)").val("");
		$(".finedo-ul textarea").val("");
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/query",
			"type" : "post",
			"data" : parastr,
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : codecomment.queryCommentWordsForSamelineCallback
		})
	};
	//查询当前行号的评审信息的回调函数
	codecomment.queryCommentWordsForSamelineCallback = function(ret) {
		for (var i = 0; i < ret.length; i++) {
			var filecommentObj = ret[i];
			var optdate = codecomment.datehandle(filecommentObj.optdate);
			var rownumber = filecommentObj.rownumber;
			var startrownumber = filecommentObj.startrownumber;
			//评审展示行号
			var linemessage = codecomment.lineMessage(rownumber, startrownumber);
			var state = filecommentObj.finishstate == 0 ? '未解决' : '已解决';
			var yesorno = state == '未解决' ? 'no' : 'yes';
			//拒绝接受的样式
			var class4 = "";
			var class5 = "";
			if (filecommentObj.acceptstate == 'refused') {
				class4 = "selectedstyle";
			} else if (filecommentObj.acceptstate == 'accepted') {
				class5 = "selectedstyle";
			}
			var color1 = '';
			var color2 = '';
			var ismustmodify = filecommentObj.ismustmodify == 'yes' ? '[必]' : '[建]';
			var iscommon = filecommentObj.iscommon == 'yes' ? '[共]' : '[非共]';
			if (ismustmodify == '[必]') {
				color1 = '#ff7f18';
			} else {
				color1 = '#3ac7e9';
			}
			if (iscommon == '[共]') {
				color2 = '#ff7f18';
			} else {
				color2 = '#3ac7e9';
			}
			$(".commentdiv").append(
					"<div ondblclick='codecomment.gotofile(" + JSON.stringify(filecommentObj) + ")'><p id='query"+i+"' class='"+i+"' ><span>" + filecommentObj.personname + "<img title='编辑' src='/codereview_webapp/codereview/codereview/img/1.png' onclick='codecomment.modifycomment(" + JSON.stringify(filecommentObj) + "," + i + ")' /><img title='跳转到代码' src='/codereview_webapp/codereview/codereview/img/2.png' onclick='codecomment.gotofile(" + JSON.stringify(filecommentObj) + ")' />  <i>" + optdate + "<img title='删除评审' src='/codereview_webapp/codereview/codereview/img/delet.png' onclick='codecomment.deletecomment(" + JSON.stringify(filecommentObj) + "," + i + ")'/></i></span><b>line" + linemessage + "：</b><b class='plcon'><font style='color:"+color1+";'>" + ismustmodify
							+ "</font><font id='iscommonForSameline"+i+"' style='color:"+color2+";display:none;'>" + iscommon + "</font>" + filecommentObj.codecomment + "</b><img title='" + state + "' class='yesimg' src='/codereview_webapp/codereview/codereview/img/icon_" + yesorno + ".png' onclick='codecomment.changeState(" + JSON.stringify(filecommentObj) + ",this)'/></p><div class='item22'><select id='cmselect" + i + "' onchange='codecomment.changecommenttype(" + JSON.stringify(filecommentObj)
							+ ",this)' ><option value='codestyle'>代码规范</option><option value='codeunnece'>代码冗余</option><option value='sqlspecs'>数据库规范</option><option value='bug'>缺陷BUG</option><option value='security'>安全问题</option><option value='performance'>性能问题</option><option value='other'>其他</option></select><span class='" + class5 + "' style='margin-right:0;' onclick='codecomment.acceptOrRefuse(" + JSON.stringify(filecommentObj) + ",this)'>接受</span><span class='" + class4 + "' style='margin-right:0;' onclick='codecomment.acceptOrRefuse(" + JSON.stringify(filecommentObj) + ",this)'>拒绝</span></div></div>");
			if (iscommon == '[共]') {
				$("#iscommonForSameline" + i).css('display', 'inline');
			}
			var acceptstate = filecommentObj.acceptstate;
			if (acceptstate == 'refused') {
				$("p[id='query" + i + "']").next().next().append("<p style='font-size: 12px;'>" + filecommentObj.submitperson + "拒绝了该条评审！拒绝原因：" + filecommentObj.remark + "</p>");
			}

		}
	}

	//添加代码行号
	codecomment.addCount = function() {
		$(".codeblock").each(function() {
			$(this).html("<ol><li>" + $(this).html().replace(/\n/g, "\n</li><li>") + "\n</li></ol>");
			$(this).addClass('has-numbering').parent();

		});
	}

	//提交评审信息
	codecomment.submitcomment = function() {
		var comment = $("textarea[name='comment']").val();
		if (codecomment.isnull(comment)) {
			finedo.message.error("不允许输入无效评审！");
			return;
		}
		var commenttype = $("#initselect").val();
		var ismustmodify = "no";
		var iscommon = "no";
		/* 			if(checkvalues.indexOf("ismustmodify")>-1){
		 ismustmodify = "yes";
		 }
		 if(checkvalues.indexOf("iscommon")>-1){
		 iscommon = "yes";
		 } */
		if ($(".finedo-checkbox-div label:first").attr('class') == 'finedo-checkbox-checked') {
			ismustmodify = "yes";
		}
		if ($(".finedo-checkbox-div label:last").attr('class') == 'finedo-checkbox-checked') {
			iscommon = "yes";
		}
		finedo.message.showWaiting();
		var repurl = $("#repurl").val();
		var filename = $("#filename").val();
		var projectname = filename.substr(codecomment.find(filename, '/', 1) + 1, codecomment.find(filename, '/', 1) - 1);
		var revision = $("#revision").val();
		var rownumber = $("#rownumber").val();
		var startrownumber = $("#startrownumber").val();
		var author = $("#author").val();
		var lastchangeddate = $("#lastchangeddate").val();
		var svnpath = $("#svnpath").val();
		var para = {
			"repurl" : repurl,
			"filename" : filename,
			"revision" : revision,
			"rownumber" : rownumber,
			"startrownumber" : startrownumber,
			"codecomment" : comment,
			"submitperson" : author,
			"commenttype" : commenttype,
			"ismustmodify" : ismustmodify,
			"iscommon" : iscommon,
			"projectname" : svnpath,
			"mousedownx" : mousedownX,
			"mousedowny" : mousedownY,
			"mouseupx" : mouseupX,
			"mouseupy" : mouseupY
		};
		var parastr = JSON.stringify(para);
		//转义字符处理
		parastr = parastr.replace(/'/g, "&#180;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
		var prehtml = $(".commentdiv").html();
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/addcomment",
			"type" : "post",
			"data" : parastr,
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				finedo.message.hideWaiting();
				var para1 = {
					"filename" : filename,
					"revision" : revision
				};
				var parastr1 = JSON.stringify(para1);
				isgotofile = '0';
				codecomment.queryCommentWords(parastr1);
				var para2 = {
					"rownumber" : rownumber,
					"startrownumber" : startrownumber,
					"filename" : filename,
					"revision" : revision
				}
				//codecomment.gotofile(rownumber, startrownumber, filename, revision);
				codecomment.gotofile(para2);
				//还原默认值
				$("#initselect").val('codestyle');
			},
			"error" : function() {
				finedo.message.error = '后台错误！';
			}
		})
	};
	//取消评审
	codecomment.cancle = function() {
		if(isOfficefile){
			codecomment.removebycanclesubmit(mousedownX, mousedownY, mouseupX, mouseupY);
		}
		var repurl = $("#repurl").val();
		var filename = $("#filename").val();
		var revision = $("#revision").val();
		var para = {
			"filename" : filename,
			"revision" : revision
		};
		var parastr = JSON.stringify(para);
		codecomment.queryCommentWords(parastr);
	}

	//字符串截取辅助方法
	codecomment.find = function(str, cha, num) {
		var x = str.indexOf(cha);
		for (var i = 0; i < num; i++) {
			x = str.indexOf(cha, x + 1);
		}
		return x;
	}

	//将评审显示到代码行号上，锚点效果。
	codecomment.gotofile = function(obj) {
		var rownumber = obj.rownumber;
		var startrownumber = obj.startrownumber;
		var filename = obj.filename;
		var revision = obj.revision;
		//office文档
		if (isOfficefile) {
			codecomment.drawOneComment(gotofileEleForOffice.mousedownx, gotofileEleForOffice.mousedowny, gotofileEleForOffice.mouseupx, gotofileEleForOffice.mouseupy, "#ff7f18");
			var mousedownx = obj.mousedownx;
			var mousedowny = obj.mousedowny;
			var mouseupx = obj.mouseupx;
			var mouseupy = obj.mouseupy;
			codecomment.drawOneComment(mousedownx, mousedowny, mouseupx, mouseupy, "#56c3ff");
			gotofileEleForOffice = obj;
			var clientHeight = document.documentElement.clientHeight;
			//var topOffsetPx = $("#originaldiv code li:eq(0)").offset().top;
			//var domOffsetPx = $("#goto" + rownumber).offset().top;
			//var scrollTopPx = (domOffsetPx - topOffsetPx) - (clientHeight / 2) + $("#originaldiv code li").height() * 2;
			var scrollTopPx = (mouseupy) - (clientHeight / 2) + $("#originaldiv code li").height() * 2;
			//$("#originaldiv").scrollTop(scrollTopPx);
			//alert(scrollTopPx);
			$("#originaldiv").animate({
				scrollTop : scrollTopPx
			}, 1000);
			return;
		}
		//
		$(gotofileEle).css("background-color", "#f9efc9");
		//多行选择
		if (startrownumber != rownumber && startrownumber != null) {
			if (startrownumber == '1') {
				var element = $("ol li").eq(0).nextUntil($("ol li").eq(rownumber));
				element.splice(0, 0, $("ol li").eq(0)[0]);
			} else {
				var element = $("ol li").eq(startrownumber - 2).nextUntil($("ol li").eq(rownumber));
			}
		} else {
			var element = $("ol li").eq(rownumber - 1);
		}
		$(element).css("background-color", "#f9efc9").css("border-left", "1px solid #ff7f18").css("border-right", "1px solid #ff7f18");
		$(element).first().css("background-color", "#f9efc9").css("border-top", "1px solid #ff7f18");
		$(element).last().css("background-color", "#f9efc9").css("border-bottom", "1px solid #ff7f18");
		$("li div").css("color", "black");
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/query",
			"type" : "post",
			"data" : JSON.stringify({
				"rownumber" : rownumber,
				"filename" : filename,
				"revision" : revision
			}),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {

				$(".goto" + rownumber).remove();

				var linemessage = codecomment.lineMessage(rownumber, startrownumber);
				for (var i = 0; i < ret.length; i++) {
					var optdate = codecomment.datehandle(ret[i].optdate);
					$(element).last().append("<div id='goto"+rownumber+"' class='historydiv1 goto"+rownumber+"'><p style='font-family: Microsoft Yahei, SimSun;padding:0 10px;'><b style='font-weight:bold;'>" + ret[i].personname + "</b><span style='color:#ff7f18;font-weight:bold;'>line" + linemessage + "</span>：" + ret[i].codecomment + "  <span style='color:#999;font-size:13px;'>" + optdate + "</span></p></div>");
				}
				//当前跳转的代码突出样式
				$(element).css("background-color", "rgb(215, 234, 239)");
				gotofileEle = $(element);
				var clientHeight = document.documentElement.clientHeight;
				var topOffsetPx = $("#originaldiv code li:eq(0)").offset().top;
				var domOffsetPx = $("#goto" + rownumber).offset().top;
				var scrollTopPx = (domOffsetPx - topOffsetPx) - (clientHeight / 2) + $("#originaldiv code li").height() * 2;
				//$("#originaldiv").scrollTop(scrollTopPx);
				$("#originaldiv").animate({
					scrollTop : scrollTopPx
				}, 1000);

			}
		})

	};

	//取消滚动效果
	codecomment.gotofileWithoutScroll = function(obj) {
		var rownumber = obj.rownumber;
		var startrownumber = obj.startrownumber;
		var filename = obj.filename;
		var revision = obj.revision;
		if (startrownumber != rownumber && startrownumber != null) {
			if (startrownumber == '1') {
				var element = $("ol li").eq(0).nextUntil($("ol li").eq(rownumber));
				element.splice(0, 0, $("ol li").eq(0)[0]);
			} else {
				var element = $("ol li").eq(startrownumber - 2).nextUntil($("ol li").eq(rownumber));
			}
		} else {
			var element = $("ol li").eq(rownumber - 1);
		}
		$(element).css("background-color", "#f9efc9").css("border-left", "1px solid #ff7f18").css("border-right", "1px solid #ff7f18");
		$(element).first().css("background-color", "#f9efc9").css("border-top", "1px solid #ff7f18");
		$(element).last().css("background-color", "#f9efc9").css("border-bottom", "1px solid #ff7f18");
		$("li div").css("color", "black");
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/query",
			"type" : "post",
			"data" : JSON.stringify({
				"rownumber" : rownumber,
				"filename" : filename,
				"revision" : revision
			}),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				$(".goto" + rownumber).remove();
				var linemessage = codecomment.lineMessage(rownumber, startrownumber);
				for (var i = 0; i < ret.length; i++) {
					var optdate = codecomment.datehandle(ret[i].optdate);
					$(element).last().append("<div id='goto"+rownumber+"' class='historydiv1 goto"+rownumber+"'><p style='font-family: Microsoft Yahei, SimSun;padding:0 10px;'><b style='font-weight:bold;'>" + ret[i].personname + "</b><span style='color:#ff7f18;font-weight:bold;'>line" + linemessage + "</span>：" + ret[i].codecomment + "  <span style='color:#999;font-size:13px;'>" + optdate + "</span></p></div>");
				}
			}
		})

	};

	//修改评审内容
	codecomment.modifycomment = function(rowmessage, i) {
		$("#textareadiv").remove();
		$("p[class='" + i + "']").append("<div id='textareadiv' style='text-align:center; margin-top:5px; padding-bottom:5px;'><textarea class='" + i + "' id='comment' name='comment' rows='3' style=' margin: 3px 0 3px 0; width:98%; border:1px solid #e9e9e2; padding:5px 3px; font-size:12px; font-family: Microsoft Yahei, SimSun;'>" + rowmessage.codecomment + "</textarea><input type='button' id='" + i + "' class='finedo-button-blue' value='确认' onClick='codecomment.submitmodify(" + JSON.stringify(rowmessage) + "," + i + ")' /><input type='button' style='margin-left:10px;' id='" + i + "' class='finedo-button-grey' value='取消' onClick='codecomment.canclemodify(" + i + ")' /></div>");

	}

	//删除评审
	codecomment.deletecomment = function(rowmessage, i) {
		finedo.message.question('确定删除？', '删除评审', function(choose) {
			if (!choose)
				return;
			var para = {
				"commentid" : rowmessage.commentid
			};
			$.ajax({
				"url" : "/codereview_webapp/finedo/svncomment/delete",
				"type" : "post",
				"data" : JSON.stringify(para),
				"contentType" : "application/json",
				"dataType" : "json",
				"success" : function(ret) {
					if (ret.fail) {
						finedo.message.error(ret.resultdesc);
						return;
					}
					codecomment.queryCommentWords(JSON.stringify({
						"filename" : rowmessage.filename,
						"revision" : rowmessage.revision
					}));
				},
				"error" : function() {
					finedo.message.error = '后台错误！';
				}
			})
		});
	}

	//取消修改评审内容
	codecomment.canclemodify = function(i) {
		$("input[id='" + i + "']").remove();
		$("#textareadiv").remove();
	}

	//提交修改的评审内容
	codecomment.submitmodify = function(r, i) {
		var comment = $("textarea[class='" + i + "']").val();
		if (codecomment.isnull(comment)) {
			finedo.message.error("不允许输入无效评审！");
			return;
		}
		var para = {
			"commentid" : r.commentid,
			"codecomment" : comment
		};
		var parastr = JSON.stringify(para);
		//处理异常字符
		parastr = parastr.replace(/'/g, "&#180;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/modify",
			"type" : "post",
			"data" : parastr,
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.fail) {
					finedo.message.error(ret.resultdesc);
					return;
				}
				//$(".commentdiv").html("");
				var revision = $("#revision").val();
				isgotofile = '0';
				codecomment.queryCommentWords(JSON.stringify({
					"filename" : r.filename,
					"revision" : revision
				}));
				codecomment.gotofile(r);
			},
			"error" : function() {
				finedo.message.error = '后台错误！';
			}
		})
	};

	//修改评审是否解决的状态
	codecomment.changeState = function(r, obj) {
		var statemodified = obj.title == '未解决' ? '1' : '0';
		var para = {
			"commentid" : r.commentid,
			"finishstate" : statemodified,
			"submitperson" : r.submitperson
		};
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/modify",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.fail) {
					finedo.message.error(ret.resultdesc);
					return;
				}
				if ($(obj).attr("title") == '未解决') {
					$(obj).attr('title', "已解决");
					$(obj).attr('src', '/codereview_webapp/codereview/codereview/img/icon_yes.png');
				} else if ($(obj).attr("title") == '已解决') {
					$(obj).attr('title', "未解决");
					$(obj).attr('src', '/codereview_webapp/codereview/codereview/img/icon_no.png');
				}
			}
		})
	};

	//修改评审的类型
	codecomment.modifytype = function(r, obj) {
		$(obj).siblings().attr('class', '');
		var commenttype = "";
		if ($(obj).attr("class") == undefined || $(obj).attr("class") == "") {
			commenttype = $(obj).text();
		} else if ($(obj).attr("class") == "selectedstyle") {
			commenttype = "null";
		}
		var para = {
			"commentid" : r.commentid,
			"commenttype" : commenttype
		};
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/modify",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.fail) {
					finedo.message.error("后台错误!");
					return;
				}
				if ($(obj).attr("class") == undefined || $(obj).attr("class") == "") {
					$(obj).siblings().removeClass("selectedstyle");
					$(obj).addClass("selectedstyle");
				} else if ($(obj).attr("class") == "selectedstyle") {
					$(obj).removeClass("selectedstyle");
				}

			}
		})
	};

	//接受和拒绝评审
	codecomment.acceptOrRefuse = function(r, obj) {
		var acceptstate = "";
		if ($(obj).attr("class") == undefined || $(obj).attr("class") == "") {
			acceptstate = $(obj).text() == '接受' ? 'accepted' : 'refused';
			if (acceptstate == 'refused') {
				$(obj).nextAll().remove();
				//$(obj).siblings().removeClass("selectedstyle");
				$(obj).parent().append("<textarea id='refuse' class='finedo-textarea' style=' width:94%; border:solid 1px #E066FF' rows='8' placeholder='请填写拒绝原因...'></textarea><div style='text-align:center; height:35px;line-height:35px;'><input class='finedo-button-blue' value='确认' type='button' onclick='codecomment.refusesubmit(" + JSON.stringify(r) + ",this)'><input class='finedo-button-grey' style=' margin-left:10px; ' value='取消' type='button' onclick='codecomment.refusecancle(this)'></div>");
				return;
			} else {
				$(obj).siblings().attr('class', '');
				$(obj).next().nextAll().remove();
			}
		} else if ($(obj).attr("class") == "selectedstyle") {
			acceptstate = "to be confirmed";
		}
		var para = {
			"commentid" : r.commentid,
			"acceptstate" : acceptstate,
			"submitperson" : r.submitperson,
			"remark" : ""
		};
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/modify",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.fail) {
					finedo.message.error(ret.resultdesc);
					return;
				}
				if ($(obj).attr("class") == undefined || $(obj).attr("class") == "") {
					$(obj).siblings().attr('class', '');
					$(obj).addClass("selectedstyle");
				} else if ($(obj).attr("class") == "selectedstyle") {
					$(obj).removeClass("selectedstyle");
				}
				//如果取消了拒绝，清除拒绝原因
				$(obj).parent().find("p").remove();
			}
		})

	};

	//查询当前版本当前文件名下的评审信息
	codecomment.querynow = function(ele) {
		if ($(ele).attr('class') == 'com-btn') {
			$(ele).attr('class', 'com-btnactive');
			//$(ele).siblings().attr('class', 'com-btn');
			$(ele).next().attr('class', 'com-btn');
		}
		var filename = $("#filename").val();
		var revision = $("#revision").val();
		codecomment.queryCommentWords(JSON.stringify({
			"filename" : filename,
			"revision" : revision
		}));
	};

	//查询当前文件的所有历史版本信息
	codecomment.queryall = function(ele) {
		if ($(ele).attr('class') == 'com-btn') {
			$(ele).attr('class', 'com-btnactive');
			//$(ele).siblings().attr('class', 'com-btn');
			$(ele).prev().attr('class', 'com-btn');
		}
		codecomment.refreshRightdiv();
		var filename = $("#filename").val();
		var para = {
			"filename" : filename
		};
		$.ajax({
			"url" : "/codereview_webapp/finedo/svnmng/queryFileAllRevision",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.fail) {
					finedo.message.error("查询失败！");
					return;
				}
				$(".commentdiv").append("<div class='add-con'></div>");
				for (var i = 0; i < ret.object.length; i++) {
					var svnlog = ret.object[i];
					$(".commentdiv .add-con").append(
							"<div class='add-comson' style='cursor:pointer;' onclick='codecomment.openrevision(" + JSON.stringify(svnlog) + ")'><img src='img/add.png' class='add-comson-l' style='width:9%;'><div class='add-com-son-r' style='float: left;width:88%;'><div class='add-com-son-rd' style='font-size: 14px;'><span style='color: #333;font-size: 14px;'>" + svnlog.revision + "</span><span style='font-size: 12px;'>" + svnlog.message + "</span></div><div class='add-com-son-rd' style='line-height: 21px;'>被评审数:" + "<font style='color: #21affb;font-size: 18px;'>" + svnlog.count + "</font> 未解决数:<font style='color: #ff7f18;font-size: 18px;'>" + svnlog.unsolvedcount + "</font></br><font style='color:#999;'>" + "submitted by " + svnlog.submitperson + "(" + svnlog.personname + ") " + "on "
									+ svnlog.lastchangeddate + "</font></div></div></div>");
				}

			},
			"error" : function() {
				finedo.message.error = '后台错误！';
			}
		})

	};

	//打开各个版本的文件评审页面
	codecomment.openrevision = function(obj) {
		window.open("/codereview_webapp/codereview/codereview/comment.jsp?svnlogid=" + obj.svnlogid);
	}

	//辅助类，用于判断无效字符串
	codecomment.isnull = function(str) {
		if (str == "")
			return true;
		var regu = "^[ ]+$";
		var re = new RegExp(regu);
		return re.test(str);
	}

	//清空评审div里渲染的数据
	codecomment.refreshRightdiv = function() {
		$(".finedo-ul").css('display', 'none');
		$(".finedo-checkbox-div label:first").removeClass().addClass("finedo-checkbox-checked");
		$(".finedo-checkbox-div label:last").removeClass().addClass("finedo-checkbox-label");
		$(".finedo-ul span:eq(0)").val("");
		$(".finedo-ul textarea").val("");
		$(".commentdiv .finedo-ul").nextAll().remove();
	}

	//取消拒绝
	codecomment.refusecancle = function(obj) {
		$(obj).parent().prev().remove();
		$(obj).parent().remove();
	}

	//提交拒绝
	codecomment.refusesubmit = function(r, obj) {
		var remark = $(obj).parent().prev().val();
		remark = remark.replace(/'/g, "&#180;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
		var para = {
			"commentid" : r.commentid,
			"acceptstate" : "refused",
			"submitperson" : r.submitperson,
			"remark" : remark
		};
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/modify",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.fail) {
					finedo.message.error(ret.resultdesc);
					return;
				}
				var font = $(obj).parent().parent();
				font.find("span:eq(0)").removeClass("selectedstyle");
				font.find("span:eq(1)").addClass("selectedstyle");
				codecomment.refusecancle(obj);
				//添加拒绝原因
				font.append("<p style='font-size: 12px;'>" + r.submitperson + "拒绝了该条评审！拒绝原因：" + remark + "</p>");

			}
		})
	};

	//行号统一处理
	codecomment.lineMessage = function(rownumber, startrownumber) {
		var linemessage = rownumber;
		if (startrownumber != rownumber && startrownumber != null) {
			linemessage = startrownumber + '到' + rownumber;
		}
		return linemessage;
	}

	codecomment.datehandle = function(datestr) {
		var str = '';
		var val = Date.parse(datestr); //时间戳，单位毫秒
		var jsdate = new Date(val); //获取到日期

		var dt = new Date();
		var valnow = dt.getTime(); //格林尼治时间戳 单位毫秒
		var second = parseInt((valnow - val) / 1000);
		if (second < 60) {
			str = second + "秒前";
		} else if (second / 60 < 60) {
			// 多少分钟多少秒
			var min = parseInt(second / 60);
			var sec = second - min * 60;
			str = min + "分" + sec + "秒前";
		} else if (second / 60 > 60) {
			if (second / 60 / 60 > 24) {
				str = datestr;
			} else {
				// 多少小时多少分钟多少秒
				var hou = parseInt(second / 3600);
				var min = parseInt((second - hou * 3600) / 60);
				var sec = second - hou * 3600 - min * 60;
				str = hou + "时" + min + "分" + sec + "秒前";
			}

		}
		return str;
	}

	codecomment.changecommenttype = function(r, obj) {
		//需要获取选择后的值
		var commenttype = $(obj).val();
		var para = {
			"commentid" : r.commentid,
			"commenttype" : commenttype
		};
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/modify",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				//通过样式修改前台的展示
				$(obj).find("option[value='" + r.commenttype + "']").attr("selected", false);
				$(obj).find("option[value='" + commenttype + "']").attr("selected", true);
			}
		})
	};

	codecomment.makesure = function() {
		$("#makesure").removeClass("finedo-button-blue").addClass("finedo-button-grey");
		$("#makesure").attr("disabled", true);

		var para = {
			"repurl" : $("#repurl").val(),
			"filename" : $("#filename").val(),
			"revision" : $("#revision").val(),
			"rownumber" : "0",
			"startrownumber" : "0",
			"codecomment" : "##$$%%",//标识已确认评审
			"submitperson" : $("#author").val(),
			"commenttype" : "0",
			"ismustmodify" : "0",
			"iscommon" : "0",
			"projectname" : $("#svnpath").val()
		};
		var parastr = JSON.stringify(para);
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/addcommentforcount",
			"type" : "post",
			"data" : parastr,
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				$("#makesure").val("已评审");
			}
		})
	};

	codecomment.makesurequery = function() {
		var filename = $("#filename").val();
		var revision = $("#revision").val();
		var parastr = JSON.stringify({
			"filename" : filename,
			"revision" : revision
		});
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/querycommentforcount",
			"type" : "post",
			"data" : parastr,
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.fail) {
					finedo.message.error = '查询评审确认信息失败！';
					return;
				}
				if (finedo.fn.isNon(ret.object.datalist) && isScrollBottom) {
					//没有评审，滑到了底部
					$("#makesure").css("display", "inline");
					return;
				}
				if (finedo.fn.isNotNon(ret.object) && finedo.fn.isNotNon(ret.object.datalist)) {
					//，修改样式
					$("#makesure").removeClass("finedo-button-blue").addClass("finedo-button-grey");
					$("#makesure").attr("disabled", true);
					$("#makesure").val("已评审");
					$("#makesure").css("display", "inline");
				}

			}
		})
	};

	codecomment.windowToCanvas = function(canvas, x, y, divheight) {
		//y= y+20;
	    var bbox = canvas.getBoundingClientRect();
	    //alert("y=" + y + ",  bbox.top=" + bbox.top + ",   bbox.height="+bbox.height+ ",   canvas.height="+canvas.height);
	    return {
	        x : (x - bbox.left) * (canvas.width / bbox.width),
	        //y : (y - bbox.top) * (canvas.height / bbox.height)
	        //y : ((y * 1.0 / divheight) * canvas.height)
	        y : y * (canvas.height / bbox.height)
	    };
	};

	codecomment.removeReviewRect = function(x, y, width, height) {
		//第一步：获取canvas元素
		var canvasDom = document.getElementById("mainCanvas");
		//第二步：获取上下文
		var context = canvasDom.getContext('2d');
		//context.clearRect(x, y, width, height);
		width = canvasDom.clientWidth;
		context.clearRect(0, y, width, height);
	};
	
	codecomment.removebycanclesubmit = function(downX, downY, upX, upY){
		
		//画上去
		//第一步：获取canvas元素
		var canvasDom = document.getElementById("mainCanvas");
		//第二步：获取上下文
		var context = canvasDom.getContext('2d');
		var height = document.getElementById("codeblock").offsetHeight;
		var downPoint = codecomment.windowToCanvas(canvasDom, downX, downY, height);
		var upPoint = codecomment.windowToCanvas(canvasDom, upX, upY, height);
		//codecomment.drawReviewRect(downPoint.x, downPoint.y + (upPoint.y - downPoint.y) / 2, upPoint.x - downPoint.x, 16.0 * canvasDom.height / height, "#ff7f18");
		//codecomment.removeReviewRect(downPoint.x, downPoint.y + (upPoint.y - downPoint.y) / 2, upPoint.x - downPoint.x, 16.0 * canvasDom.height / height, "#ff7f18");
		
		var rectHeight = (upY>downY) ? (upY-downY) : (downY-upY);
		rectHeight = rectHeight<17 ? 17 : rectHeight;
		codecomment.removeReviewRect(0, downPoint.y, canvasDom.clientWidth, rectHeight*canvasDom.height*1.0/height);
	
	}

	/*  	codecomment.drawReviewRect = function(x, y, width, height){
	 //alert("drawReviewRect");
	 //第一步：获取canvas元素
	 var canvasDom = document.getElementById("mainCanvas");
	 //第二步：获取上下文
	 var context = canvasDom.getContext('2d');
	 context.fillStyle = "yellow";
	 context.fillRect(x, y, width, height);
	 };  */

	codecomment.drawReviewRect = function(x, y, width, height, color) {
		 alert(222);
		 var canvas = document.getElementById("mainCanvas");
		 x=0;
		 width = canvas.clientWidth;
		//alert("drawReviewRect");
		//第一步：获取canvas元素
		var canvasDom = document.getElementById("mainCanvas");
		//第二步：获取上下文
		var context = canvasDom.getContext('2d');
		context.fillStyle = color;
		context.fillRect(x, y, width, height);
	};

	codecomment.drawOneComment = function(mousedownX, mousedownY, mouseupX, mouseupY, color) {
	/* 	//画上去
		//第一步：获取canvas元素
		var canvasDom = document.getElementById("mainCanvas");
		//第二步：获取上下文
		var context = canvasDom.getContext('2d');
		var height = document.getElementById("codeblock").offsetHeight + 40;
		var downPoint = codecomment.windowToCanvas(canvasDom, downX, downY, height);
		var upPoint = codecomment.windowToCanvas(canvasDom, upX, upY, height);
		var rectHeight = (upY>downY) ? (upY-downY) : (downY-upY);
		rectHeight = ((rectHeight<16) ? 16 : rectHeight);
		//以下演示填充矩形。
		codecomment.drawReviewRect(downPoint.x, downPoint.y + 12, upPoint.x - downPoint.x, rectHeight * canvasDom.height * 1.0 / height,color); */
	
		//第一步：获取canvas元素
		 var canvasDom = document.getElementById("mainCanvas");
		 //第二步：获取上下文
		 var context = canvasDom.getContext('2d');

		 var height = document.getElementById("codeblock").offsetHeight;
		 var downPoint = codecomment.windowToCanvas(canvasDom, mousedownX, mousedownY, height);
		 var upPoint = codecomment.windowToCanvas(canvasDom, mouseupX, mouseupY, height);
		
		var rectHeight = (mouseupY>mousedownY) ? (mouseupY-mousedownY) : (mousedownY-mouseupY);
		rectHeight = rectHeight<17 ? 17 : rectHeight;
		
		//codecomment.drawReviewRect(downPoint.x, downPoint.y, upPoint.x-downPoint.x, rectHeight*canvasDom.height*1.0/height,"#ff7f18");
		// 把获取到的高度设置给class类名为zuobian的元素
		var canvas = document.getElementById("mainCanvas");
		codecomment.drawReviewRect(0, downPoint.y, canvas.clientWidth, rectHeight*canvasDom.height*1.0/height,color);
	}

	codecomment.checkOfficefile = function(filename) {
		var arr = [ ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf" ];
		//取出上传文件的扩展名
		var index = filename.lastIndexOf(".");
		var ext = filename.substr(index);
		//循环比较
		for (var i = 0; i < arr.length; i++) {
			if (ext == arr[i]) {
				isOfficefile = true; //一旦找到合适的，立即退出循环
				break;
			}
		}
	}
</script>
</head>
<body>
	<%-- <form id="GetSVNFile" name="GetSVNFile" method="post" action="${ctx}/finedo/svnmng/getSVNFile">
	</form> --%>
	<input type="hidden" name="rownumber" id="rownumber" value="0">
	<input type="hidden" name="startrownumber" id="startrownumber"
		value="0">
	<input type="hidden" name="revision" id="revision" value="0">
	<input type="hidden" name="author" id="author" value="0">
	<input type="hidden" name="personname" id="personname" value="0">
	<input type="hidden" name="filename" id="filename" value="0">
	<input type="hidden" name="repurl" id="repurl" value="0">
	<input type="hidden" name="svnpath" id="svnpath" value="0">
	<!-- 	<div style="width: 20%;height: 100%; position:fixed;overflow-y:auto;  overflow-x:auto">
		<div class="title-nav"><span>svn目录</span></div>
		<ul id="treediv" class="fdtree" onclick="doTreeClick" style="margin-left: 5px;"></ul>
	</div> -->

<!--  	<div class="originaldiv" id="originaldiv"
		style="width: 70%; height: 100%; background: #f8f8ff; position: fixed; left: 0; overflow-y: auto; overflow-x: auto;">
		<div class="title-nav" id="newest-version">
			<span id="newversion-span">版本号 </span>
		</div>
		<pre>
			<code id="codeblock" class="java code1" style="cursor: pointer;"></code>
		</pre>
	</div>  -->
<!-- 	<div class="originaldiv" id="originaldiv" onscroll = codecomment.queryMouseOffset(this)
		style="width: 70%; height: 100%; background: #f8f8ff; position: fixed; left: 0; overflow-y: auto; overflow-x: auto;">
		<div class="title-nav" id="newest-version"
			style="z-index: 99999999; top: 0; position: fixed; left: 0; width: 67.5%;margin-left:10px;">
			<span id="newversion-span">版本号 </span>
		</div>
 		<div style="position:fixed; width:100%; bottom:0; top:0; overflow-y: auto;">
			<div id="canvasdiv" style=" width: 100%;position: absolute; z-index: 2;">
				<canvas id="mainCanvas" style="position:relative;z-index:999;background: rgba(153,255,153,0.1);width:100%;height:100%" > </canvas>
			</div>
			
			<pre style="padding-top: 40px; opacity:0.5; position: absolute; z-index: 3;">
				<code id="codeblock" class="java codeblock" style="cursor: pointer;z-index:-1;"></code>
			</pre>
		</div> 
		
		<pre style="padding-top: 40px">
			<code id="codeblock" class="java codeblock" style="cursor: pointer;"></code>
		</pre> 
	</div> -->
	
		<div class="originaldiv" id="originaldiv" onscroll = codecomment.queryMouseOffset(this)
		style="width: 70%; right:30%; height: 100%; background: #f8f8ff; position: fixed; left: 0; overflow-y: auto; ">
		<div class="title-nav" id="newest-version"
			style="z-index: 99999999; top: 0; position: fixed; left: 0; width: 67.5%;margin-left:10px;">
			<span id="newversion-span">版本号 </span>
		</div>
 		<div style="/* position:fixed; */ width:70%; bottom:0; top:0; overflow-y: auto;">
			<div id="canvasdiv" style="padding-top: 40px; width: 100%;position: absolute; z-index: 2;">
				<canvas id="mainCanvas" style="position:relative;z-index:999;background: rgba(153,255,153,0.1);width:100%;height:100%;" > </canvas>
			</div>
			
			<pre style="padding-top: 40px; opacity:0.5; position: absolute; z-index: 3;width:100%; /* overflow-y: auto; */">
				<code id="codeblock" class="java codeblock" style="cursor: pointer;z-index:-1;"></code>
			</pre>
		</div> 
  
								 
																			   
		 
	</div>

	<div class="mask commentdiv" id="historydiv"
		style="height: 100%; width: 30%; left: 70%; background: #fff; border-left: 1px solid #ddd; position: fixed; overflow-y: auto; overflow-x: auto;">
		<span style="font-size: 0"> <input type="button" value="当前版本评审"
			class="com-btnactive"
			style="border-top-left-radius: 4px; border-bottom-left-radius: 4px;"
			onclick="codecomment.querynow(this)"> <input type="button"
			value="所有版本" class="com-btn"
			style="border-top-right-radius: 4px; border-bottom-right-radius: 4px;"
			onclick="codecomment.queryall(this)">
			<input type="button" id="makesure" class="finedo-button-blue" value="评审确认" onclick='codecomment.makesure()' style="float:right;display:none;" />
		</span>
		<ul class="finedo-ul" style="display: none">
			<li><span></span>
			<textarea id="comment" name="comment" class="finedo-textarea"
					style="width: 94%; border: solid 1px #E066FF" rows="8"></textarea></li>
			<li>
				<div class="finedo-checkbox-div">
					<input type="hidden" id="city" name="city" value=""> <label
						class="finedo-checkbox-checked" for="city" value="ismustmodify">必须修改</label>
					<label class="finedo-checkbox-label" for="city" value="iscommon">共性问题</label>
					<select id='initselect'><option value='codestyle'>代码规范</option><option value='codeunnece'>代码冗余</option><option value='sqlspecs'>数据库规范</option><option value='bug'>缺陷BUG</option><option value='security'>安全问题</option><option value='performance'>性能问题</option><option value='other'>其他</option></select>
				</div>
			</li>
			<div id="cm"
				style="text-align: center; height: 35px; line-height: 35px;">
				<input type="button" class="finedo-button-blue" value="确认"
					onClick="codecomment.submitcomment()" /><input type="button"
					class="finedo-button-grey" style="margin-left: 10px;" value="取消"
					onclick="codecomment.cancle()" />
			</div>
		</ul>
	</div>


</body>
</html>

