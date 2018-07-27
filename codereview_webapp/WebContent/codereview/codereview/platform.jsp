<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp"%>
<!DOCTYPE html>
<html>
${jquery_js } ${finedo_core_js } ${finedo_commonui_js } ${style_css }
${finedo_dialog_js }
${finedo_tree_js }
<head>
<meta charset="UTF-8" />
<title>代码评审</title>
<link rel="stylesheet" type="text/css"
	href="/codereview_webapp/codereview/codereview/styles/style.css" />
<script type='text/javascript'
	src='/codereview_webapp/fsdp/resource/js/My97DatePicker/WdatePicker.js'></script>
<style type="text/css">
.wlc-con {
	display: block;
}

.wlc-l {
	padding-left: 20px;
}

</style>
<script type="text/javascript">
	var userflag = '';
	//树状类型：treetype和列表类型：listtype
	var treelisttype = 'listtype';
	//项目名
	var briefsvnpath = '';
	var svnpath = '';
	//项目编号
	var groupid = '';
	$(document).ready(function() {
		//var usercode = ${sessionScope.LOGINDOMAIN_KEY.sysuser.usercode}+"";
		//codeview.queryuser('${sessionScope.LOGINDOMAIN_KEY.sysuser.usercode}');
		//根据日期查询文件更新
		//第一个参数标识查询一天，一周。第二个参数标识页面左右的日期，0左侧，1右侧。
		codeview.querygroupuserrole('${sessionScope.LOGINDOMAIN_KEY.sysuser.usercode}');
		codeview.getDate(1, 0);
		codeview.getDate(7, 1);
		
	})

	var codeview = {};
	//查询svn日志信息
	codeview.querySvnlog = function() {
		//添加树状处理
		if(treelisttype == 'treetype'){
			codeview.querySvnlogForTree();
			return;
		}
		var start = $("#effdate:first").val();
		var end = $("#expdate:first").val();
		var svnaddr = $('.com-text').val();

		var para = {
			"start" : start,
			"end" : end,
			"svnaddr" : svnaddr
		};
		$(".Dmain-ld:first").empty();
		finedo.message.showWaiting();
		$.ajax({
			"url" : "/codereview_webapp/finedo/svnmng/querySvnlog",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : codeview.querySvnlogCallback,
			"error" : function() {
				finedo.message.error = '查询失败！';
			}
		});
	};

	//查询svn日志信息的回调函数，渲染前端数据
	codeview.querySvnlogCallback = function(ret) {
		finedo.message.hideWaiting();
		if (ret.fail) {
			$('.Dmain-ld:first').append("<div class='no-xm' style='display:block;'><img src='/codereview_webapp/codereview/codereview/img/hint.png'></div>");
			$('.Dmain-ld:first .no-xm').append(ret.resultdesc);
			return;
		}
		var projectsvnloglist = ret.object;
		for (var j = 0; j < projectsvnloglist.length; j++) {
			var projectname = projectsvnloglist[j].svnpath;
			var unsolvedcount = projectsvnloglist[j].unsolvedcount;
			var filesize = projectsvnloglist[j].filesize;
			//var projectfiles = projectsvnloglist[j].dopsSvnlogList;
			//var type = projectfiles[0].type;
			
			var cgid = projectsvnloglist[j].cgid;
 			var ismanager = projectsvnloglist[j].ismanager;
			var imgdisplay = ismanager == false ? 'none' : 'inline';
			
			var type = 'company';
			var class1 = "com-btn";
			var class2 = "com-btn";
			//'company' 公司级别的项目 'dept'部门级别的项目
			if (type == 'company') {
				class1 = 'com-btnactive';
			} else if (type == 'dept') {
				class2 = 'com-btnactive';
			} 

			briefprojectname = codeview.getSubstringForProjectname(projectname, 15);
			$(".Dmain-ld:first").append(
					"<div><div class='Dcon-t' style='position: relative;'><img src='/codereview_webapp/codereview/codereview/img/tree1.png' title='树形视图'  style='cursor:pointer;float: left; position:absolute; z-index:999; left:0; top:3px; margin-top: 2px;margin-left:4px;' onclick='codeview.querytree("+JSON.stringify(briefprojectname)+","+JSON.stringify(projectname)+","+JSON.stringify(cgid)+")' /><img title='编辑项目成员' src='/codereview_webapp/codereview/codereview/img/member.png' style='cursor:pointer; top:3px; position:absolute; z-index:999; left:30px;float: left;margin-top: 2px;margin-left:4px;display:"+imgdisplay+";' onclick='codeview.openeditgroup("+JSON.stringify(cgid)+")'/><div class='Dcon-t-cut' style='position:absolute; left:0; right:0; width:100%; z-index:1; background:none; ' onclick='codeview.showhiddenAndQuery(this)'><div class='Dcon-tl' style='display: inline-block; float:left; text-indent:55px;' title='"+projectname+"'>" + briefprojectname + "</div><div class='Dcon-tr'><img src='/codereview_webapp/codereview/codereview/img/jt2.png' /></div><div style='font-size:12px;color:#888;float:right'>未解决数：" + unsolvedcount + "&nbsp;&nbsp;</div><div style='font-size:12px;color:#888;float:right'>提交数：" + filesize + "&nbsp;&nbsp;</div><div class='sx' style='display:none;'><input type='button' value='公司' onclick='codeview.changeSvnpathtype(" + JSON.stringify(projectname) + ",this)' class='" + class1 + "' ><input type='button' value='部门' onclick='codeview.changeSvnpathtype(" + JSON.stringify(projectname) + ",this)' class='" + class2
							+ "' ></div></div></div><div class='Dcon-d' style='display: none;'><div class='wlc-con'><table class='com-table2'><tr><td>名称</td><td>问题数目</td><td>未解决</td><td>作者</td><td>版本号</td><td>提交时间</td></tr></table></div></div></div>");
			if (userflag != '') {
				$(".sx").css('display', 'block');
			}
		}
	};
	
	//查询树状svn数据
	codeview.querySvnlogForTree = function() {
		window.open("/codereview_webapp/codereview/codereview/treecomment.jsp?svnpath="+svnpath+"&projectid="+groupid);
		treelisttype = 'listtype';
	}

	//根据日期，查询我评审的和被评审的
	codeview.queryJoin = function(type, ele) {
		var start = $("#effdate1:first").val();
		var end = $("#expdate1:first").val();
		var type = type;
		var para = {
			"start" : start,
			"end" : end,
			"type" : type
		};
		if ($(ele).attr('class') == 'com-btn') {
			$(ele).attr('class', 'com-btnactive');
			$(ele).siblings().attr('class', 'com-btn');
		}
		//根据日期，查询我评审的和被评审的，前端渲染数据
		$.ajax({
			"url" : "/codereview_webapp/finedo/svnmng/queryJoin",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				//查看评审时，根据类型清空自身原有数据，同时确定好将要操作的divclass
				var divclass = '';
				if (type == 'commentby') {
					$('.other-coument').nextUntil('.my-coument').remove();
					divclass = 'other-coument';
				} else if (type == 'icomment') {
					$('.my-coument').nextAll().remove();
					divclass = 'my-coument';
				}
				if (ret.fail) {
					$('.' + divclass).after("<div class='no-xm' style='display:block;'><img src='/codereview_webapp/codereview/codereview/img/hint.png'></div>");
					$('.' + divclass).next().append(ret.resultdesc);
					return;
				}
				var projectcommentlist = ret.object;
				for (var j = 0; j < projectcommentlist.length; j++) {
					var projectname = projectcommentlist[j].svnpath;
					var projectfiles = projectcommentlist[j].dopsSvncommentList;
					var filesize = projectcommentlist[j].filesize;
					var unsolvedcount = projectcommentlist[j].unsolvedcount;
					$('.' + divclass).after("<div><div class='Dcon-t' onclick='codeview.showhidden(this)'><div class='Dcon-tl' title='"+projectname+"'>" + codeview.getSubstringForProjectname(projectname, 15) + "</div><div class='Dcon-tr'><img src='/codereview_webapp/codereview/codereview/img/jt2.png' /></div><div style='font-size:12px;color:#888;float:right'>未解决数：" + unsolvedcount + "&nbsp;&nbsp;</div><div style='font-size:12px;color:#888;float:right'>文件数：" + filesize + "&nbsp;&nbsp;</div></div><div class='Dcon-d' style='display: none;'><div class='wlc-con'><table class='com-table2'><tr><td>名称</td><td>问题数目</td><td>未解决</td><td>作者</td><td>版本号</td><td>提交时间</td></tr></table></div></div></div>");
					var projecttr = '';
					for (var i = 0; i < projectfiles.length; i++) {
						var fileobj = projectfiles[i];
						var filename = fileobj.filename.substring(codeview.find(fileobj.filename, '/', 2));
						var svnlogid = fileobj.svnlogid;
						projecttr += "<tr style='cursor:pointer;' onclick='codeview.openfilecomment(" + JSON.stringify(svnlogid) + ")' ><td title='"+filename+"'>" + codeview.getSubstring(filename) + "</td><td>" + fileobj.count + "</td><td>" + fileobj.unsolvedcount + "</td><td>" + fileobj.submitperson + "</td><td>" + fileobj.revision + "</td><td title='"+fileobj.lastchangeddate+"'>" + codeview.getSubStringFordate(fileobj.lastchangeddate) + "</td></tr>";
					}
					if (type == 'commentby') {
						//eq(0)表示遍历的当前项目
						$('.other-coument').nextUntil('.my-coument').eq(0).find("table").append(projecttr);
					} else if (type == 'icomment') {
						$('.my-coument').nextAll().eq(0).find("table").append(projecttr);
					}
				}
			},
			"error" : function() {
				finedo.message.error = '查询文件出错！';
			}
		});
	}

	//根据类型，查询文件的评审信息
	codeview.queryRightJoin = function() {
		//var querytype = ($('.com-btnactive').val()) == '被评审的'?'commentby':'icomment';
		var querytype = $('#icomment').attr('class') == 'com-btnactive' ? 'icomment' : 'commentby';
		codeview.queryJoin(querytype);
	}

	//打开文件的代码评审页面
	codeview.openfilecomment = function(svnlogid) {
		window.open("/codereview_webapp/codereview/codereview/comment.jsp?svnlogid=" + svnlogid+"&projectid="+groupid);
	}

	//工程div的收拉效果
 	codeview.showhidden = function(ele) {
		$(ele).next().slideToggle();
		if ($(ele).find("img").attr("src") == "/codereview_webapp/codereview/codereview/img/jt.png") {
			$(ele).find("img").attr("src", "/codereview_webapp/codereview/codereview/img/jt2.png");
		} else if ($(ele).find("img").attr("src") == "/codereview_webapp/codereview/codereview/img/jt2.png") {
			$(ele).find("img").attr("src", "/codereview_webapp/codereview/codereview/img/jt.png");
		}

	} 

	//工程div的收拉效果 并附带svn日志查询
	codeview.showhiddenAndQuery = function(ele) {
		//$(ele).next().slideToggle();
		$(ele).parent().next().slideToggle();
		if ($(ele).find("img").attr("src") == "/codereview_webapp/codereview/codereview/img/jt.png") {
			$(ele).find("img").attr("src", "/codereview_webapp/codereview/codereview/img/jt2.png");
		} else if ($(ele).find("img").attr("src") == "/codereview_webapp/codereview/codereview/img/jt2.png") {
			$(ele).find("img").attr("src", "/codereview_webapp/codereview/codereview/img/jt.png");
			codeview.querySvnlogDetail(ele);
		}

	}

	codeview.querySvnlogDetail = function(ele) {
		//需求修改，在此处查询该项目的svn日志。
		var start = $("#effdate:first").val();
		var end = $("#expdate:first").val();

		//清空该项目的表格数据
		$(ele).parent().parent().find("tr:gt(0)").remove();
		var fullpath = $(ele).find(".Dcon-tl").attr("title");
		var index = codeview.find(fullpath, '/', 2);
		var svnaddr = fullpath.substring(0, index + 1);
		var svnpath = fullpath.substring(index);
		var para = {
			"start" : start,
			"end" : end,
			"svnaddr" : svnaddr,
			"svnpath" : svnpath
		};
		$.ajax({
			"url" : "/codereview_webapp/finedo/svnmng/querySvnlogDetail",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				var projectfiles = ret.object[0].dopsSvnlogList;
				//groupid = ret.object[0].cgid; 
				console.log(JSON.stringify(projectfiles.length));
				var projecttr = '';
				for (var i = 0; i < projectfiles.length; i++) {
					var fileobj = projectfiles[i];
					var filename = fileobj.filename.substring(codeview.find(fileobj.filename, '/', 2));
					var svnlogid = fileobj.svnlogid;
					projecttr += "<tr style='cursor:pointer;' onclick='codeview.openfilecomment(" + JSON.stringify(svnlogid) + ")' ><td title='"+filename+"'>" + codeview.getSubstring(filename) + "</td><td>" + fileobj.count + "</td><td>" + fileobj.unsolvedcount + "</td><td>" + fileobj.submitperson + "</td><td>" + fileobj.revision + "</td><td title='"+fileobj.lastchangeddate+"'>" + codeview.getSubStringFordate(fileobj.lastchangeddate) + "</td></tr>";
				}
				$(ele).parent().parent().find("table").append(projecttr);
			},
			"error" : function() {
				finedo.message.error = '查询失败！';
			}
		});
	}

	//日期函数处理，并在回调中查询文件信息
	codeview.getDate = function(para1, para2, ele) {
		//参数1：标识天，周。参数2：区分左右侧。参数3：当前点击元素。
		var para = {
			"start" : para1,
			"type" : para2
		};
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncomment/handleTime",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				var start = ret.object[0];
				var end = ret.object[1];
				if (0 == para2) {
					var start = $("#effdate").val(start);
					var end = $("#expdate").val(end);
				} else if (1 == para2) {
					var start = $("#effdate1").val(start);
					var end = $("#expdate1").val(end);
				}
				//设置样式
				if ($(ele).attr('class') == 'com-btn2') {
					$(ele).attr('class', 'com-btn2active');
					$(ele).siblings().attr('class', 'com-btn2');
				}
				if (para2 == 1) {
					//var querytype = ($('.com-btnactive').val()) == '被评审的'?'commentby':'icomment';
					var querytype = $('#icomment').attr('class') == 'com-btnactive' ? 'icomment' : 'commentby';
					//codeview.queryJoin(querytype);
					codeview.queryJoin('icomment');
					codeview.queryJoin('commentby');
				} else if (para2 == 0) {
					codeview.querySvnlog();
				}
			}
		});
	}

	//字符串截取辅助方法
	codeview.find = function(str, cha, num) {
		var x = str.indexOf(cha);
		for (var i = 0; i < num; i++) {
			x = str.indexOf(cha, x + 1);
		}
		return x;
	}

	//字符串截取辅助方法
	codeview.getSubStringFordate = function(s) {
		//s为截取的字符串
		//2018-06-08 10:00:00截取成06-08 10:00
		return s.substring(5, 16);
	}

	//字符串截取辅助方法
	codeview.getSubstring = function(s) {
		//截取文件名
		s = '...' + s.substring(s.lastIndexOf('/'));
		return s;
	}

	//字符串截取辅助方法
	codeview.getSubstringForProjectname = function(s, n) {
		//s为截取字符窜 n为截取长度
		var arr = s.split('/');
		var p = arr[arr.length - 1];
		//截取固定长度
		if (p.length > n) {
			return '...' + p.substring(p.length - n, p.length);
		}
		return p;
	}

	//修改项目级别
	codeview.changeSvnpathtype = function(projectname, obj) {
		var svnpath = projectname.substring(codeview.find(projectname, '/', 2));
		var type = $(obj).val() == '公司' ? 'company' : 'dept';
		var para = {
			"svnpath" : svnpath,
			"type" : type
		};
		$.ajax({
			"url" : "/codereview_webapp/finedo/svnpathtype/modify",
			"type" : "post",
			"data" : JSON.stringify(para),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.fail) {
					finedo.message.error("后台错误!");
					return;
				}
				if ($(obj).attr('class') == 'com-btn') {
					$(obj).attr('class', 'com-btnactive');
					$(obj).siblings().attr('class', 'com-btn');
				}

			}
		})
	};

	//查询是否是高工人员
	codeview.queryuser = function(usercode) {
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncodeviewuser/query",
			"type" : "post",
			"data" : JSON.stringify({
				"usercode" : usercode
			}),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.length > 0) {
					//标识这个用户是高工人员
					userflag = 'high';
				}
			}
		})

	}
	
	//查询项目成员权限
	codeview.querygroupuserrole = function(usercode) {
		$.ajax({
			"url" : "/codereview_webapp/finedo/svnmng/querygroupuserrole",
			"type" : "post",
			"data" : JSON.stringify({
				"usercode" : usercode
			}),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if(ret.fail){
					return;
				}
				//$(".sus-main ul li:last").css("display","inline");
				$("#projectGL").css("display","inline");

			}
		})

	}

	//查看报表
	codeview.queryReport = function(url) {
		//每次打开查看报表，都会重新生成校验的id
		var validatestr = codeview.guid();
		console.log("validatestr:" + validatestr);
		$.ajax({
			"url" : "/codereview_webapp/finedo/svnmng/setCheckidForReport",
			"type" : "post",
			"data" : JSON.stringify({
				"validatestr" : validatestr
			}),
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.fail) {
					finedo.message.error(ret.resultdesc);
					return;
				}
				window.open(url + '&validatestr=' + validatestr + '&userid=${sessionScope.LOGINDOMAIN_KEY.sysuser.usercode}');
			}
		})
	}

	//js生成juid
	codeview.guid = function() {
		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
			return v.toString(16);
		});
	}

	//点击设置图标，选择下拉内容
	codeview.showreport = function() {
		var obj = document.getElementById("showpart");
		if (obj.style.display == "block") {
			obj.style.display = 'none';
		} else {
			obj.style.display = 'block';
		}
		return false;
	}

	//打开项目管理首页
	codeview.opengroup = function() {
		window.open("/codereview_webapp/codereview/codereview/group/project.jsp");
	}

	//查询树状svn briefprojectname,projectname
	codeview.querytree = function(briefprojectname,projectname,cgid) {
		treelisttype = "treetype";
		briefsvnpath = briefprojectname;
		var index = codeview.find(projectname,"/",2);
		svnpath = projectname.substring(index);
		groupid = cgid;
		codeview.querySvnlog();
	}

	//查询列表状svn
	codeview.querylist = function(ele) {
		$(ele).attr('class', 'listnow');
		$("#svntree").attr('class', 'treehis');
		treelisttype = "listtype";
		codeview.querySvnlog();
	}

	codeview.dotreeclick = function(ele) {
		var svnid = ele.orgnode;
		var filename = ele.optsn;
		var para = {
			"svnid" : svnid,
			"filename" : filename
		};
		if (JSON.stringify(filename).indexOf(".") < 0) {
			return;
		}
		var parastr = JSON.stringify(para);
		$.ajax({
			"url" : "/codereview_webapp/finedo/svnmng/readSvnFileForTree",
			"type" : "post",
			"data" : parastr,
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				if (ret.fail) {
					finedo.message.error(ret.resultdesc);
					return;
				}
				var svnlogid = ret.object.svnlogid;
				window.open("/codereview_webapp/codereview/codereview/comment.jsp?svnlogid=" + svnlogid);
			}
		})
	}

	//关闭弹框
	codeview.cancletree = function() {
		$(".box").remove();
	}
	
	//编辑小组成员
	codeview.openeditgroup = function(cgid){
		window.open("/codereview_webapp/codereview/codereview/group/editgroup.jsp?cgid=" + cgid);
	}
	//代码推荐点击事件 
	codeview.commend=function(){
		window.open("/codereview_webapp/codereview/codereview/commend/Commend.jsp");
	};
	//项目管理点击事件
	codeview.openproject = function() {
		window.open("/codereview_webapp/codereview/codereview/project/Index.jsp");
	};
	
</script>
</head>
<body>
<div class="sus-main">
					<ul>
						<li class="li1 fl"><img
							src="/codereview_webapp/codereview/codereview/img/img-1-1.png"><a
							href="javascript:void(0);"
							onclick="codeview.queryReport('http://demo.finedo.cn/fdreportweb/finedo/sso/cReport?id=7b1cfb68-7ef1-4ada-83b0-d91d2409d761&systemid=FD000000000000463990&identifier=b7769bf2-c9e3-4f55-8e35-a7a8eaf513c1');">走查情况统计</a></li>
						<li class="fl"><img
							src="/codereview_webapp/codereview/codereview/img/img2.png"><a
							href="javascript:void(0);"
							onclick="codeview.queryReport('http://demo.finedo.cn/fdreportweb/finedo/sso/cReport?id=5fc257f7-addc-4953-be00-20c193661b10&systemid=FD000000000000463990&identifier=b7769bf2-c9e3-4f55-8e35-a7a8eaf513c1');">覆盖率统计</a></li>
						<li class="last fl"><img
							src="/codereview_webapp/codereview/codereview/img/img4.png"><a
							href="javascript:void(0);" 
							onclick="codeview.queryReport('http://demo.finedo.cn/fdreportweb/finedo/sso/cReport?id=70d19d49-2dc0-4121-afdd-c662e176ea99&systemid=FD000000000000463990&identifier=b7769bf2-c9e3-4f55-8e35-a7a8eaf513c1');">个人评审统计</a></li>
						<li class="last fl"  id="commend"><img
							src="/codereview_webapp/codereview/codereview/img/img5.png"><a
							href="javascript:void(0);" onclick="codeview.commend();">优秀代码推荐</a></li>
						<li class="last fl" style="display:none;" id="projectGL"><img
							src="/codereview_webapp/codereview/codereview/img/img3.png"><a
							href="javascript:void(0);" onclick="codeview.openproject();">项目管理</a></li>
						
					</ul>
				</div>
	<div class="body-main">
		<div class="Dmain-l">
			<div class="Dmain-lt">
				<img src="/codereview_webapp/codereview/codereview/img/cyps.png">待评审代码
				<!--  
				<div id="svntree" class='treehis' onclick='codeview.querytree(this);' title='树状视图'></div>
				<div id="svnlist" class='listnow' onclick='codeview.querylist(this);' title='列表视图'></div> -->
				<!-- <ul id="treediv" class="fdtree" /> -->
			</div>
			<div class="Dmain-lc">
				<span>项目名称：</span><input type="text" class="com-text" style="border-radius:4px;">123</input> <span>时间：</span>
				<!-- 					<select class="com-text">
						<option>请选择起始时间</option>
					</select>
					<select class="com-text">
						<option>请选择结束时间</option>
					</select> -->
				<input class="finedo-date" type="text" value="" id="effdate"
					name="effdate" style="width: 100px;border-radius:4px;"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"> <input
					class="finedo-date" type="text" value="" id="expdate"
					name="expdate" style="width: 100px; margin-right: 10px;border-radius:4px;"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"> <span
					style="font-size: 0"><input type="button" value="最近一天"
					class="com-btn2active"
					style="border-top-left-radius: 4px; border-bottom-left-radius: 4px;"
					onclick="codeview.getDate(1,0,this)"> <input type="button"
					value="最近一周" class="com-btn2"
					style="border-top-right-radius: 4px; border-bottom-right-radius: 4px;"
					onclick="codeview.getDate(7,0,this)"></span> <input type="button"
					class="" value="查询"
					style="background: #30b5ff; padding: 5px 10px; border: 1px solid #3ac7e9; outline: none; cursor: pointer; font-size: 12px; margin-top: 12px; margin-left: 10px; color: #fff;border-radius:4px;"
					onclick="codeview.querySvnlog()" />
			</div>
			<div class="Dmain-ld"></div>
		</div>
		<div class="Dmain-c"></div>
		<div class="Dmain-r">
			<div class="Dmain-lt">
				<img src="/codereview_webapp/codereview/codereview/img/wdxm.png">工作台
<!-- 				<div class='baobiaork2' onclick='codeview.showreport();'></div>
				<div class='menucon' id='showpart' style='display: none' onmouseleave='codeview.removethree(this);'>
					<div class='menusuqre'></div>
					<ul>
						<li><img src="/codereview_webapp/codereview/codereview/img/coin2.png"><a href="javascript:void(0);" onclick="codeview.queryReport('http://demo.finedo.cn/fdreportweb/finedo/sso/cReport?id=7b1cfb68-7ef1-4ada-83b0-d91d2409d761&systemid=FD000000000000463990&identifier=b7769bf2-c9e3-4f55-8e35-a7a8eaf513c1');">走查情况统计</a></li>
						<li><img src="/codereview_webapp/codereview/codereview/img/coin3.png"><a href="javascript:void(0);" onclick="codeview.queryReport('http://demo.finedo.cn/fdreportweb/finedo/sso/cReport?id=5fc257f7-addc-4953-be00-20c193661b10&systemid=FD000000000000463990&identifier=b7769bf2-c9e3-4f55-8e35-a7a8eaf513c1');">覆盖率统计</a></li>
						<li><img src="/codereview_webapp/codereview/codereview/img/coin4.png"><a href="javascript:void(0);" onclick="codeview.opengroup();">项目管理</a></li>
					</ul>
				</div> -->
			</div>
			<div class="Dmain-lc">
				<span>时间：</span>
				<!--  					<select class="com-text">
						<option>请选择起始时间</option>
					</select>
					<select class="com-text">
						<option>请选择结束时间</option>
					</select> -->

				<input class="finedo-date" type="text" value="" id="effdate1"
					name="effdate1" style="width: 100px;border-radius:4px;"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"> <input
					class="finedo-date" type="text" value="" id="expdate1"
					name="expdate1" style="width: 100px; margin-right: 10px;border-radius:4px;"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"> <span
					style="font-size: 0"><input type="button" value="最近一天"
					class="com-btn2"
					style="border-top-left-radius: 4px; border-bottom-left-radius: 4px;"
					onclick="codeview.getDate(1,1,this)"> <input type="button"
					value="最近一周" class="com-btn2active"
					style="margin-right: 10px; border-top-right-radius: 4px; border-bottom-right-radius: 4px;"
					onclick="codeview.getDate(7,1,this)"></span> <input type="button"
					class="" value="查询"
					style="background: #30b5ff; padding: 5px 10px; border: 1px solid #3ac7e9; outline: none; cursor: pointer; font-size: 12px; margin-top: 12px; margin-left: 0px; color: #fff;border-radius:4px;"
					onclick="codeview.queryRightJoin()" />
			</div>
			<div class="Dmain-ld">
				<div class='other-coument'>被评审的</div>
				<div class='my-coument'>我评审的</div>
			</div>
		</div>
	</div>
</body>
</html>