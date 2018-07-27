<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp"%>
<!DOCTYPE html>
<html>
${jquery_js } 
${finedo_core_js } 
${finedo_commonui_js } 
${style_css }
${finedo_grid_js }
${finedo_dialog_js }
<head>
<meta charset="utf-8">
<title>编辑项目</title>
	<link rel="stylesheet" href="/codereview_webapp/codereview/codereview/styles/groupstyle.css">
	<link href="${ctx}/resource/js/select2/css/select2.min.css" rel="stylesheet" />
	<script src="${ctx}/resource/js/select2/js/select2.full.min.js"></script>
	<script src="${ctx}/resource/js/select2/js/i18n/zh-CN.js"></script>
	<style>
	.box-content .add-table tr td{
		padding-top:0px;
	}
	</style>
	<script>
	var cvgroup = {};
	var id=" ";
	$(document).ready(function(){
		$("#remoptuser").val("${LOGINDOMAIN_KEY.sysuser.usercode}");
		id="${param.cgid}";
		 $.ajax({
				type : "POST",
				url : "/codereview_webapp/finedo/svncodeviewgroup/query",
				data : {
					cgid:id,
				},
				dataType : "json",
				async:false,
				success : function(data){
					$("#eidtgroupname").val(data.rows[0].groupname);
					$("#projectmng").val(data.rows[0].usercode);
					$("#eidtgroupdesc").val(data.rows[0].groupdesc);
					$("#cgidchoose").val(data.rows[0].cgid);
					$("#svnpath").val(data.rows[0].svnpath);
					$("#createperson").val(data.rows[0].createperson);
					$("#svnaddr").val(data.rows[0].svnaddr);
					$("#groupsvnpath").val(data.rows[0].svnaddr+data.rows[0].svnpath.substring(1));
					$("#repotype").val(data.rows[0].repotype);
					$("#busimng").val(data.rows[0].busimng);
					
					var projectmng = data.rows[0].usercode;
 					if(finedo.fn.isNon(projectmng)){
						projectmng = "无";
					} 
					var data1= [{id:projectmng,name:projectmng}];
					cvgroup.returnvalue("#username1",data1);
					
 					var busimng = data.rows[0].busimng;
 					if(finedo.fn.isNon(busimng)){
						busimng = "无";
					} 
					var data2= [{id:busimng,name:busimng}];
					cvgroup.returnvalue("#username2",data2); 
					cvgroup.serchgroupnum(id);
					
				}
			});
			searchPerson("username", "请输入添加成员的用户名", true);
			searchPerson1("username1", "", true);
			searchPerson2("username2", "", true);
			
/* 			//添加单个成员
			$("#username").on("select2:select",function(e){
				var usercode=e.params.data.id;
				var id=$("#cgidchoose").val();
				cvgroup.addanotherpeo(id,usercode);
			}) */
			
			//需求变更 通过按钮触发
			
			
	 });
	
	
	//保存修改小组信息
	 cvgroup.modifysave = function(){
		var id=$("#cgidchoose").val();
		if(finedo.fn.isNon($("#eidtgroupname").val())){
			finedo.message.tip("请输入小组项目名称");
			return;
		}
		/*  if(finedo.fn.isNon($("#editprojectname").val())){
			finedo.message.tip("配置库URI不为空");
			return;
		}  */
		/* if(finedo.fn.isNon($("#eidtgroupdesc").val())){
			finedo.message.tip("请输入项目简介信息");
			return;
		} */
		$.ajax({
			type : "POST",
			url : "/codereview_webapp/finedo/svncodeviewgroup/updategroup",
			data : {
				cgid:id,
				groupname:$("#eidtgroupname").val(),
				svnpath:$("#editprojectname").val(),
				groupdesc:$("#eidtgroupdesc").val(),
				createperson:$("#createperson").val(),
				svnaddr:$("#svnaddr").val(),
			},
			dataType : "json",
			async:false,
			success : function(data){
			if("SUCCESS" == data.resultcode){
				finedo.message.info('修改成功，是否返回项目列表页', '保存小组信息成功', function callback() {
					window.location.href ="/codereview_webapp/codereview/codereview/group/project.jsp";
				});
			}else{				
					finedo.message.tip("修改失败，请重试");	
					return;
				}
			}
		});
	}
	
	//评审小组人员退出按钮
	 function formatOperation(row){
			var operation = ''; 
			if(row.userstate=="1"){
				operation += '<a href="#" onclick="cvgroup.doexit(\'' + row.userid + '\',\''+row.username +'\')">[退出]</a>&nbsp;';
			}else{
				operation += '<a href="#" onclick="cvgroup.returnback(\'' + row.userid + '\',\''+row.username +'\')">[重新加入]</a>&nbsp;';
			}
			
			return operation;
		}
	
	//用户状态格式化
	 function formatuserstate(row){
			var operation = ''; 
			if(row.userstate=="1"){
				operation += '有效';
			}else{
				operation += '已退出';
			}
			return operation;
		}
	
	//获取项目成员
	cvgroup.serchgroupnum = function(id){
		//加载项目成员信息
		var options={
	        url: '/codereview_webapp/finedo/svncodeviewgroupuser/querynum',
	        pagesize: 10,
	        queryparams:{"cgid":id},
	        columns: [
	            { code: 'username', title: '姓名', width: 170},
				{ code: 'userid', title: '工号', width: 170},
	            { code: 'email', title: '邮箱', width: 170},
	            { code: 'addtime', title: '添加时间', width: "20%"},
	            { code: 'remtime', title: '退出时间', width: "20%"},
	          	{ code: 'userstate', title: '当前状态', width: 170,formatter:formatuserstate},
	            { code: 'operation', title: '操作', width:170,formatter:formatOperation},
	        ]
	};
			finedo.getgrid('datagrid', options).load();
	}
	
	//退出 某个项目成员
	cvgroup.doexit = function(userid,username){
	$("#userid").val(userid);
	
	finedo.message.question('是否使' + username+"退出该评审小组？","退出成员", callback); 
	
	function callback(flag) {
		var userid=$("#userid").val();
		var remoptuser=$("#remoptuser").val();
		if(flag){
			$.ajax({
				type : "POST",
				url : ctx+"/finedo/svncodeviewgroupuser/delete",
				data : {
					userid : userid,
					cgid:$("#cgidchoose").val(),
					remoptuser:remoptuser,
					}, 
				dataType : "json",
				success : function(data){
					if("SUCCESS" == data.resultcode){
					finedo.message.hideWaiting();
					finedo.getgrid('datagrid').refresh();
					finedo.getgrid('groupshow').refresh();
					}
				}
		 });
		}
	}
	}
	
	//重新加入某个项目成员
	cvgroup.returnback= function(userid,username){
	$("#userid").val(userid);
	finedo.message.question('是否使' + username+"重新该评审小组？","加入成员", callback); 
	
	function callback(flag) {
		var userid=$("#userid").val();
		if(flag){
			$.ajax({
				type : "POST",
				url : ctx+"/finedo/svncodeviewgroupuser/returnback",
				data : {
					userid : userid,
					cgid:$("#cgidchoose").val()
					}, 
				dataType : "json",
				success : function(data){
					if("SUCCESS" == data.resultcode){
					finedo.message.hideWaiting();
					finedo.message.info("重新加入小组成功!");
					finedo.getgrid('datagrid').refresh();
					}
				}
		 });
		}
	}
	}

		//添加单个或者多个评审小组成员
		cvgroup.addanotherpeo = function(id, usercode) {

			var addoptuser = $("#remoptuser").val();
			$.ajax({
				type : "POST",
				url : ctx + "/finedo/svncodeviewperson/add",
				data : {
					usercode : usercode,
					cgid : id,
					addoptuser : addoptuser,
				},
				dataType : "json",
				success : function(ret) {
					if (ret.fail) {
						finedo.message.info("成员已存在，请重新添加！");
						return;
					}
					finedo.message.hideWaiting();
					finedo.message.info("添加成功!");
					finedo.getgrid('datagrid').refresh();
					$('#username').val(null).trigger("change");
				}
			});
		}

		//根据员工姓名进行查询
		searchPerson = function(componentname, tipmsg, multiple) {
			var ismultiple = false;
			if (multiple) {
				ismultiple = multiple;
			}
			var searchPersonOption = {
				url : "${ctx}/finedo/svncodeviewgroupuser/queryuserbyname",
				dataType : 'json',
				type : "POST",
				delay : 250,
				data : function(params) {
					return {
						username : params.term,
						state : "有效"
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var itemList = new Array();
					var userlst = data.rows;
					for (usrindx in userlst) {
						var oneuser = userlst[usrindx];
						itemList.push({
							"id" : oneuser.userid,
							"text" : oneuser.userid + ":" + oneuser.username
						});
					}
					return {
						results : itemList,
						pagination : {
							more : (params.page * 30) < userlst.size
						}
					};
				},

				cache : true
			};

			//此处是获取点击的数据
			select2component = $('#' + componentname).select2({
				ajax : searchPersonOption,
				placeholder : tipmsg,
				allowClear : true,
				language : "zh-CN",
				minimumInputLength : 1,
				multiple : ismultiple
			});
			return select2component;
		};
		
		//根据员工姓名进行查询
		searchPerson1 = function(componentname, tipmsg, multiple) {
			var ismultiple = false;
			if (multiple) {
				ismultiple = multiple;
			}
			var searchPersonOption = {
				url : "${ctx}/finedo/svncodeviewgroupuser/queryuserbyname",
				dataType : 'json',
				type : "POST",
				delay : 250,
				data : function(params) {
					return {
						username : params.term,
						state : "有效"
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var itemList = new Array();
					var userlst = data.rows;
					for (usrindx in userlst) {
						var oneuser = userlst[usrindx];
						itemList.push({
							"id" : oneuser.userid,
							"text" : oneuser.userid + ":" + oneuser.username
						});
					}
					return {
						results : itemList,
						pagination : {
							more : (params.page * 30) < userlst.size
						}
					};
				},

				cache : true
			};

			//此处是获取点击的数据
			select2component = $('#' + componentname).select2({
				ajax : searchPersonOption,
				placeholder : tipmsg,
				allowClear : true,
				language : "zh-CN",
				minimumInputLength : 1,
				multiple : ismultiple
			});
			//$("#username1").empty();
			return select2component;
		};
		
		//根据员工姓名进行查询
		searchPerson2 = function(componentname, tipmsg, multiple) {
			var ismultiple = false;
			if (multiple) {
				ismultiple = multiple;
			}
			var searchPersonOption = {
				url : "${ctx}/finedo/svncodeviewgroupuser/queryuserbyname",
				dataType : 'json',
				type : "POST",
				delay : 250,
				data : function(params) {
					return {
						username : params.term,
						state : "有效"
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var itemList = new Array();
					var userlst = data.rows;
					for (usrindx in userlst) {
						var oneuser = userlst[usrindx];
						itemList.push({
							"id" : oneuser.userid,
							"text" : oneuser.userid + ":" + oneuser.username
						});
					}
					return {
						results : itemList,
						pagination : {
							more : (params.page * 30) < userlst.size
						}
					};
				},

				cache : true
			};

			//此处是获取点击的数据
			select2component = $('#' + componentname).select2({
				ajax : searchPersonOption,
				placeholder : tipmsg,
				allowClear : true,
				language : "zh-CN",
				minimumInputLength : 1,
				multiple : ismultiple
			});
			//$("#username2").empty();
			return select2component;
		};

		//返回列表页
		cvgroup.back = function() {
			finedo.message.question('信息未保存，确定要返回列表页吗？', '返回列表页', function callback(flag) {
				if (flag == true) {
					window.location.href = "/codereview_webapp/codereview/codereview/group/project.jsp";
				}
			})
		}

		cvgroup.addperson = function(obj) {
			var usercodearr = new Array();
			var arr = $(".sear .select2-selection__choice");
			for (var i = 0; i < arr.length; i++) {
				var title = $(arr[i]).attr("title");
				var usercode = title.split(":")[0];
				usercodearr.push(usercode);
			}
			var usercodestr = usercodearr.toString();
			var id = $("#cgidchoose").val();
			cvgroup.addanotherpeo(id, usercodestr);
		}
		
		cvgroup.canclemodify = function(){
			window.location.reload();
		}
		
		cvgroup.modifyproject = function(){
			var projectname = $("#eidtgroupname").val();
			var busimng = $("#username2").val();
			var projectmng = $("#username1").val();
			var projectdescribe = $("#eidtgroupdesc").val();
			
			
			if(finedo.fn.isNon(projectmng)){
				projectmng = new Array("");
			}/* else if(projectmng[0].toString().length>1){
				projectmng[0] = projectmng[0].substring(projectmng[0].indexOf('"'),projectmng[0].indexOf(':'));
			} */
			if(finedo.fn.isNon(busimng)){
				busimng = new Array("");
			}/* else if(busimng[0].toString().length>1){
				busimng[0] = busimng[0].substring(busimng[0].indexOf('"'),busimng[0].indexOf(':'));
			} */
			if(projectmng.length >1 || busimng.length >1){
				finedo.message.info("项目经理或商务经理最多为一人！");
				return;
			}
			if(projectmng[0].toString().length>1 && projectmng[0].toString().indexOf(":")>0){
				projectmng[0] = projectmng[0].substring(projectmng[0].indexOf('"'),projectmng[0].indexOf(':'));
			}
			if(busimng[0].toString().length>1 && busimng[0].toString().indexOf(":")>0){
				busimng[0] = busimng[0].substring(busimng[0].indexOf('"'),busimng[0].indexOf(':'));
			}
			var para ={"projectid":id,"projectname":projectname,"busimng":busimng[0],"projectmng":projectmng[0],"projectdescribe":projectdescribe};
			var parastr = JSON.stringify(para);
			
			finedo.message.question('确定修改项目信息？', '确认修改', function(choose) {
				if (!choose){
					window.location.reload();
					return;
				}
					
			
				$.ajax({
					"url" : "/codereview_webapp/finedo/svncodeviewproject/modify",
					"type" : "post",
					"data" : parastr,
					"contentType" : "application/json",
					"dataType" : "json",
					"success" : function(ret){
						if(ret.fail){
							finedo.message.error = '修改项目信息失败';
							return;
						}
						window.location.reload();
					},
				});
			});
		}
		
		 cvgroup.returnvalue = function(dom,value){
/* 			 $("#username1").empty();
			 $("#username2").empty(); */
			// $('#username1').val(null);
			 $.each(value,function(index,value){
			        $(dom).append(new Option(value.name, value.id, false, true));
			    });
			    $(dom).trigger("change");
		} 
		 
		 
/* 		 cvgroup.toSelect = function(obj){
			 var parent = $(obj).parent();
			 parent.empty();
			 parent.append("<select name='username1' id='username1' style='width:65%' ></select>");
			 searchPerson1("username1", "", true);
		 }
		 
		 cvgroup.toSelect1 = function(obj){
			 var parent = $(obj).parent();
			 parent.empty();
			 parent.append("<select name='username2' id='username2' style='width:65%' ></select>");
			 searchPerson1("username2", "", true);
		 } */
	</script>
</head>

<body style="background:#f7f7f7;">
	<div class="edit-maincon">
		<input type="hidden" value="" id="cgidchoose">
		<input type="hidden" value="" id="svnpath">
		<input type="hidden" value="" id="userid">
		<input type="hidden" value="" id="createperson">
		<input type="hidden" value="" id="svnaddr">
		<input type="hidden" value="" id="remoptuser">
		<div class="pp-title" style="padding:10px 0; width:95%; margin:0 auto; box-sizing: border-box;"><p style="border-left:4px solid #15a0ee; color:#15a0ee; font-size:14px; padding-left:10px;">项目信息</p></div>
		<div class="box-content" style=" width:95%; margin:0 auto; padding:10px;box-sizing: border-box; background:#fff;">
			
			<table class="add-table">
				<tr>
					<td class="add-td2" style="width: 100px;">项目名称：</td>
					<td class="add-td2"><input class="add-text" type="text" id="eidtgroupname" style="font-size: 14px;" ></td>
					</tr>
					<tr><td class="add-td2" style="width: 100px;">配置库类型：</td>
					<td class="add-td2"><input class="add-text" type="text" id="repotype" style="font-size: 14px;border: 0px solid #ddd;" readonly="readonly"></td>
				</tr>
				<tr>
					<td class="add-td2" style="width: 100px;">项目经理：</td>
					 <!-- <td class="add-td2"><input class="add-text" type="text" id="projectmng" style="font-size: 14px;" onclick = cvgroup.toSelect(this); ></td> -->
					   <td class="add-td2"><select name="username1" id="username1" style="width:65%" ></select></td>   
				</tr>
				<tr>
					<td class="add-td2" style="width: 100px;">商务经理：</td>
					<!--<td class="add-td2"><input class="add-text" type="text" id="busimng" style="font-size: 14px;" onclick = cvgroup.toSelect1(this); ></td> -->
					 <td class="add-td2"><select name="username2" id="username2" style="width:65%" ></select></td>  
				</tr>
				<tr>
					<td class="add-td2" style="width: 100px;">配置库URI：</td>
					<td class="add-td2" colspan="3"><input id="groupsvnpath" class="add-text" type="text"  style="font-size: 14px;border: 0px solid #ddd;" readonly="readonly"></td>
				</tr>
				<tr>
					<td class="add-td2" style="width: 100px;">项目描述：</td>
					<!-- <td class="add-td2" colspan="3"><input class="add-text" id="eidtgroupdesc" type="text" style="font-size: 14px;border: 1px solid #ddd;" readonly="readonly"></td> -->
					<!-- <td class="add-td2"><textarea class="add-textarea" id="eidtgroupdesc" style="font-size: 14px;" rows="1" ></textarea></td> -->
					<td class="add-td2" colspan="3"><input id="eidtgroupdesc" class="add-text" type="text"  style="font-size: 14px;"></td>
				</tr>
				<tr>
					<td class="add-td2" colspan="3"><div class="map-btn map-born" style=" width:100%; float: left; margin:20px 0 0 0; ">
				<input type="button" class="map-btn3" style=" height:30px; line-height: 30px;" value="取消" onclick="cvgroup.canclemodify();">
				<input type="button" class="map-btn4" style=" height:30px; line-height: 30px; margin-left:30px; " value="提交" onclick="cvgroup.modifyproject();">
			</div> </td>
				</tr>
			</table>
			
		</div>
		<div class="pp-title" style="padding:10px 0; width:95%; margin:0 auto; box-sizing: border-box;"><p style="border-left:4px solid #15a0ee; color:#15a0ee; font-size:14px; padding-left:10px;">人员信息</p></div>
		<div class="table-ta" style="width:95%; overflow:hidden; margin:0 auto; padding:20px;box-sizing: border-box; background:#fff;">
			
				<div class="sear" style="margin-top:4px;">
					<select name="username" id="username" style="width:65%" ></select>
					<input id="addperson" type="button" class="finedo-button-blue" value="确认" onclick = cvgroup.addperson() style="border-radius:4px;" />
				</div>
			<div style="width:100%;margin-top:6px;line-height:22%; margin: 0 auto; float: left;">
	                <table id="datagrid" style="width:100%; margin-top:20px;">
	                </table>
	       	</div> 
       	</div>
<!-- 		<div class="map-btn map-born" style=" width:100%; float: left; margin:20px 0 0 0; ">
			<input type="button" class="map-btn3" style=" height:30px; line-height: 30px;" value="取消" onclick="cvgroup.back();">
			<input type="button" class="map-btn4" style=" height:30px; line-height: 30px; margin-left:30px; " value="提交" onclick="cvgroup.modifysave();">
		</div> -->
	</div>
	
</body>
</html>
