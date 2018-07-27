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
<title>评审小组管理</title>
	<link rel="stylesheet" href="/codereview_webapp/codereview/codereview/styles/groupstyle.css">
	<link href="${ctx}/resource/js/select2/css/select2.min.css" rel="stylesheet" />
	<script src="${ctx}/resource/js/select2/js/select2.full.min.js"></script>
	<script src="${ctx}/resource/js/select2/js/i18n/zh-CN.js"></script>
	</script>
	<style type="text/css">
	</style>
	<script>
	var cvgroup = {};
	//sm标识系统管理员 pm标识项目管理员
	var roleflag = '';
	//项目地址
	var svnpath = '';
	$(function(){
		cvgroup.querygroupuserrole('${sessionScope.LOGINDOMAIN_KEY.sysuser.usercode}');
		$(".box").hide();
		$(".box-del-s").click(function(){
			$(".box").hide();	
		}) 
		$(".btn-add").click(function(){
			$(".box-add").animate({right:'0'});	
		}) 
		$(".box-del-add").click(function(){
			$(".box-add").animate({right:'-614'});
			$("#groupname").val("");
			$("#groupdesc").val("");
			$('#projectname').val(null).trigger("change");
		}) 
	 	$(".td-user").click(function(){
			$(".box-con").animate({right:'0'});	
		})
		$(".box-del").click(function(){
			$(".box-con").animate({right:'-614'});
			$('#username').val(null).trigger("change");
		})
	})
	
	
	//根据小组名字查询相关信息
	function formatPkey(row){
		    return '<a href="javascript:void(0);" onclick="cvgroup.modifybefore(\'' + row.cgid + '\');">' + row.groupname + '</a>&nbsp;';
		}
	
	//对svnpath进行截取
 	function formatsvnpath(row){
	        var svnpath = row.svnpath;
	        var svnaddr = row.svnaddr;
	        return svnaddr + svnpath.substring(1);

	    } 
	
	//显示小组信息,显示total，rows，groupname，svnpath三个。注意要对svnpath进行处理
	 $(document).ready(function(){
		 var usercode= "${LOGINDOMAIN_KEY.sysuser.usercode}";
		 var options={
	        url: '/codereview_webapp/finedo/svncodeviewgroup/querygroupbyusercode',
	        pagesize: 10,
	        //selecttype: 'multi',
			pagination: true,
			queryparams:{"usercode":usercode},
	        columns: [
				{ code: 'groupname', title: '小组名称', width: 170,formatter:formatPkey},
	            { code: 'svnpath', title: '项目地址', width: 190,formatter:formatsvnpath},
	            { code: 'num', title: '成员个数', width: 170},      	          
	        ]
			};
		   
			finedo.getgrid('groupshow', options).load(); 
			searchProject("projectname", "请输入项目名", false, "", "");
			//添加项目的唯一性检测
			$("#projectname").on("select2:select",function(e){
				var svnpath=e.params.data.id;
				cvgroup.grouptest(svnpath);
			})
			
	 });
	
	//项目模糊查询
	searchProject=function(componentname,tipmsg,multiple, defaultValue, defaultText){
		var ismultiple=false;
		var projlist="";
		if(multiple){
			ismultiple=multiple;
		}
		var searchProjectOption={
				url: "/codereview_webapp/finedo/svnuserpermission/querysvnpath",
			    dataType: 'json',
			    type: "POST",
			    delay: 250,
			    data:function (params) {
				      return {
				    	  svnpath: params.term
				      };
				    },
				    processResults: cvgroup.success(data, params),
			   
			    cache: true
			  };


	     select2component= $('#'+componentname).select2({
	    	 ajax:searchProjectOption,
			 placeholder:tipmsg,
			 allowClear: true,
			 language: "zh-CN",
			 minimumInputLength: 2,
			 multiple: ismultiple,
	         initSelection: function (element, callback) {
	        	callback({id: defaultValue, text: defaultText}); //设置默认值
	         }
	     });
		return select2component;
	};

	
	cvgroup.success =function (data, params) {
	      params.page = params.page || 1;
	      var itemList =  new Array();
	      projlist = data.object;
	      for(prjindx in projlist){
	    	  var oneproj=projlist[prjindx];
	    	  var svnpath=oneproj.svnpath;
	    	  itemList.push({"id":svnpath, "text": svnpath});
          }
	      return {
	        results: itemList ,
	        pagination: {
	       	more:(params.page * 30) <projlist.size
	        } 
	      };
	    },
	//添加小组
	cvgroup.addgroup = function(){
		if(finedo.fn.isNon($("#groupname").val())){
			finedo.message.tip("请输入小组名称");
			return;
		}
		if(finedo.fn.isNon($("#projectname").val())){
			finedo.message.tip("项目地址不为空");
			return;
		}
		/* if(finedo.fn.isNon($("#groupdesc").val())){
			finedo.message.tip("请输入小组描述信息");
			return;
		} */
		$.ajax({
			url: "/codereview_webapp/finedo/svncodeviewgroup/add",
			type : "post",
			data : {
				groupname : $("#groupname").val(),
				svnpath :$("#projectname").val(),
				groupdesc :$("#groupdesc").val(),
				createperson:"${LOGINDOMAIN_KEY.sysuser.usercode}"
			},
			dataType : "json",
			success : function(data) {
			if(data.fail){
				cvgroup.addgroupnum($("#projectname").val());
				finedo.message.info('保存成功，是否返回项目列表页', '保存小组信息成功', function callback() {
					window.location.href ="/codereview_webapp/codereview/codereview/group/project.jsp"
				});
				
			}else{				
					finedo.message.tip("保存失败，请重试");	
					return;
				}
			}
		});
	}
	
	//评审小组的唯一性校验
	cvgroup.grouptest = function(svnpath){
		$.ajax({
			url: "/codereview_webapp/finedo/svncodeviewgroup/query",
			type : "post",
			data : {
				svnpath :svnpath,
			},
			dataType : "json",
			success : function(data) {
			if(data.rows.length>0){
				finedo.message.info('该项目评审小组已添加，请重新选择！');
				finedo.message.hideWaiting();
				$('#projectname').val(null).trigger("change");
				return;
			}
			}
		});
	}
	
	//获取待修改小组信息
	 cvgroup.modifybefore = function(cgid){
		 window.open("/codereview_webapp/codereview/codereview/group/editgroup.jsp?cgid=" + cgid);
	}
	
	//添加项目成员到codeviewperson表中
	cvgroup.addgroupnum = function(svnpath){
		$.ajax({
			type : "POST",
			url : "/codereview_webapp/finedo/svnuserpermission/addgroupnum",
			data : {
				svnpath:svnpath,
			},
			dataType : "json",
			async:false,
			success : function(data){
			}
		});
	}
	
	//删除评审小组
	cvgroup.delet = function(){
		var data=finedo.getgrid('groupshow').getselecteditem();
		if(data.length<=0){
			finedo.message.tip("你尚未勾选需要删除的评审小组！");
			return;
		}
		$(".box").show();
	}
	
	//删除小组信息
	cvgroup.delectgroup = function(){
		var data=finedo.getgrid('groupshow').getselecteditem();
		var ids="";
		for(var i=0;i<data.length;i++){
			ids+=data[i].cgid+",";
		}
		var param=finedo.getgrid("datagrid").getqueryparams();
		$("#downiframe").attr(
			'src','${ctx }/finedo/svncodeviewgroup/delete'+ (param ? '?' + $.param(param) : '')+'&cgid='+ ids.substring(0,ids.length - 1));
		$.ajax({
			type : "POST",
			url : ctx+"/finedo/svncodeviewgroup/delete",
			async: true,
			data : { 
				cgid:ids.substring(0,ids.length-1),
			},
			dataType : "json",
			success : function(data){
				if(data.fail){
					$(".box").hide();
					finedo.getgrid('groupshow').refresh();
				}else{				
					finedo.message.tip("删除失败，请重试");	
					return;
				}
			}
		});
	}
	
	//字符串截取辅助方法
	cvgroup.find = function(str, cha, num) {
		var x = str.indexOf(cha);
		for (var i = 0; i < num; i++) {
			x = str.indexOf(cha, x + 1);
		}
		return x;
	}
		
		
	
	//返回列表页
	cvgroup.back = function(){
		finedo.message.question('信息未保存，确定要返回列表页吗？', '返回列表页',
			function callback(flag) {
				if (flag == true) {
					window.location.href ="/codereview_webapp/codereview/codereview/group/project.jsp";
				}
			}
		)
	}
	
	//删除键返回页面
	cvgroup.backstart = function(){
		$(".box").hide();
	}
	
	cvgroup.querygroupuserrole = function(usercode) {
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
				//系统管理员，可以看到添加按钮
				if(ret.object.grouprole == 'sm'){
					roleflag = 'sm';
					$("#wholegroup").css("display","inline");
					$(".btn-add").css("display","none");
				}else{
					roleflag = 'pm';
					svnpath = ret.object.svnpath;
				}
			}
		})

	}
	
	//打开全局评审小组
	cvgroup.querywholegroup = function() {
		$.ajax({
			"url" : "/codereview_webapp/finedo/svncodeviewgroup/querywholegroup",
			"type" : "post",
			"contentType" : "application/json",
			"dataType" : "json",
			"success" : function(ret) {
				var cgid = ret.object.cgid;
				var svnpath = ret.object.svnpath;
				window.open("/codereview_webapp/codereview/codereview/group/editgroup.jsp?cgid=" + cgid+"&svnpath="+svnpath);
			}
		})
	}
	</script>
</head>

<body>
	<div class="p-btn">
		<input type="button" id="wholegroup" value="全局评审小组" style="border-radius:5px 5px 0 0;  cursor: pointer;padding: 0 30px; background:#56c3ff; color:#fff; height:35px; line-height: 35px; border:none; border-radius:5px; display:inline-block;float:left;margin-bottom: 15px;display:none;" onclick=cvgroup.querywholegroup();>
		<input class="btn btn-add" type="button" value="添加小组" style="display:none;">
		<input class="btn btn-del" type="button" onclick="cvgroup.delet();" value="删除小组" style="display:none;">
		<input type="hidden" value="" id="cgidchoose">
		<input type="hidden" value="" id="svnpath">
		<input type="hidden" value="" id="userid">
		<input type="hidden" value="" id="createperson">
		<input type="hidden" value="" id="svnaddr">
	</div>
	<div class="mamin-con">
		<div style="width:100%;">
           <table id="groupshow">
           </table>
        </div>
	</div>
	<!--弹框--删除-->
	<div class="box">
		<div class="box-con-del">
			<div class="box-title">确认删除 <img class="box-del-s" src="/codereview_webapp/codereview/codereview/img/box-del.png"></div>
			<div class="box-content">确认删除勾选的小组？</div>
			<div class="map-btn">
				<input type="button" class="map-btn3" value="取消" onclick="cvgroup.backstart();">
				<input type="button" class="map-btn4" value="确定" onclick="cvgroup.delectgroup();">
			</div>
		</div>
	</div>
	<!--弹框--添加-->	
	<div class="box-con box-add" id="addgrouptab">
		<div class="box-title">添加小组<img class="box-del-add" src="/codereview_webapp/codereview/codereview/img/box-del.png"></div>
		<div class="box-content">
			<table class="add-table">
				<tr>
					<td class="add-td1">名称：</td>
					<td class="add-td2"><input class="add-text" type="text" id="groupname" style="font-size: 14px;"></td>
					<td class="add-td3"><span style="color:green">*<strong>该小组的名称</strong></span></td>
				</tr>
				<tr>
					<td class="name2">项目地址：</td>
	    			<td class="write" >
	    			<li><select name="projectname" id="projectname" style="width:80%" ></select></li>
					</td>
					<td class="add-td3"><span style="color:green">*<strong>项目地址</strong></span></td>
				</tr>
				<tr>
					<td class="add-td1">描述信息：</td>
					<td class="add-td2"><textarea class="add-textarea" id="groupdesc" style="font-size: 13.5px;"></textarea></td>
					<td class="add-td3"><span style="color:green"><strong>小组描述信息</strong></span></td>
				</tr>
			</table>
		</div>
		<div class="map-btn">
			<input type="button" class="map-btn3" value="取消" onclick="cvgroup.back();">
			<input type="button" class="map-btn4" value="创建" onclick="cvgroup.addgroup();">
		</div>
	</div>
	</div>
		<iframe id="downiframe" name="downiframe" style="display:none"></iframe>
</body>
</html>
