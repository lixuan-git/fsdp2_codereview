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
<meta charset="UTF-8" />
<title>项目管理</title>
<link rel="stylesheet" href="/codereview_webapp/codereview/codereview/project/css/style.css">
	<link href="${ctx}/resource/js/select2/css/select2.min.css" rel="stylesheet" />
	<script src="${ctx}/resource/js/select2/js/select2.full.min.js"></script>
	<script src="${ctx}/resource/js/select2/js/i18n/zh-CN.js"></script>
	<style type="text/css">
	.finedo-datagrid td {
    border-bottom: 1px solid #dcdcdc;
    padding: 5px 0;
}
.finedo-datagrid th {
    border-bottom: 1px solid #dcdcdc;
    padding: 5px 0;
    background: #f3f3f3;
    font-size: 12px;
    font-weight: 100;
}
	</style>
<script type="text/javascript">
var codeview = {};
var usercode= "${LOGINDOMAIN_KEY.sysuser.usercode}";
var cgid="";
var projectindex = 0;
var firsttype = false;
$(function(){
		$.ajax( {
		    url: "/codereview_webapp/finedo/svncodeviewproject/queryrole", 
		    type: "post",
			dataType : "json",
		    data:{
		    	projectmng:usercode,
		    },
		    success: function(ret){
		   if(ret.fail){
			   finedo.message.error = '后台错误！';
			   return;
		   }
		 if(ret.object.projectcode>0){
			 $("#conture").removeAttr("style");
			 $("#prjname").css("width","85%");
		 }
		    }
		});
		
	$("#indexshow").remove();
	$("#projectlist").append("<table id='indexshow'  selecttype='multi'  class='finedo-datagrid' ></table>");
	firsttype = true;
	codeview.query();
});
function formatPkey(row){
	if(projectindex == 0){
		cgid = row.cgid;
		projectindex = 'default';
		if(firsttype){
		 $("#downiframe").attr("src","/codereview_webapp/codereview/codereview/group/editgroup.jsp?cgid="+cgid);
			firsttype = false;
		}
		codeview.query();
	}
    return '<a href="javascript:void(0);" onclick="codeview.modifybefore(\'' + row.cgid + '\');">' + row.projectname + '</a>&nbsp;';
}
	function formatsvnpath(row){
        var svnpath = row.svnpath;
        var svnaddr = row.svnaddr;
        return svnaddr + svnpath;
    } 
	
	
codeview.query=function (){
	var prjname="";
if(finedo.fn.isNon($("#prjname").val())){
	prjname="";
}else{
	prjname=$("#prjname").val();
}
	$("#indexshow").remove();
	$("#projectlist").append("<table id='indexshow'  selecttype='multi'  class='finedo-datagrid' ></table>");
	 var options={
		       url: '/codereview_webapp/finedo/svncodeviewproject/queryproject',
		       pagesize: 10,
		       selectype: 'multi',
				pagination: true,
				queryparams:{"projectmng":usercode,
					"projectname":"%"+prjname+"%"
					},
		       columns: [
					{ code: 'projectname', title: '项目名称', width: 170,formatter:formatPkey},  	    
		       ]
				};
				finedo.getgrid('indexshow', options).load(); 
				
};
codeview.modifybefore=function (id) {
	if(finedo.fn.isNon(id)||id=="null"){
		finedo.message.info('该项目尚未开发！');
		return;
	}
	$("#downiframe").attr("src","/codereview_webapp/codereview/codereview/group/editgroup.jsp?cgid="+id);  
};
codeview.create=function(){
	$("#downiframe").attr("src","/codereview_webapp/codereview/codereview/project/CreateProject.jsp");
};
codeview.allgroup=function(){
	$.ajax( {
	    url: "/codereview_webapp/finedo/svncodeviewproject/querycgid", 
	    type: "post",
		dataType : "json",
	    success: function(ret){
	   if(ret.fail){
		   finedo.message.error = '后台错误！';
		   return;
	   }
		$("#downiframe").attr("src","/codereview_webapp/codereview/codereview/group/editgroup.jsp?cgid="+ ret.object.projectid);  
	    }
	});
};
codeview.del=function(){
	var data=finedo.getgrid('indexshow').getselecteditem();
	var ids="";
	for(var i=0;i<data.length;i++){
		ids+=data[i].projectid+",";
	}
	if(finedo.fn.isNon(ids)){
		finedo.message.info('请选择需要删除的项目！');
		return;
	}
	finedo.message.question('确定删除？', '删除项目', function(choose) {
		if (!choose)
			return;
	
		 $.ajax({
			url: "/codereview_webapp/finedo/svncodeviewproject/delete",
			type : "post",
			data : {
				projectid : ids.substring(0,ids.length-1)
			},
			dataType : "json",
			success : function(data) {
				window.location.reload();
			}
		}); 
	});

}

</script>
</head>
<body >
<div class="Dmain-l">
			<div class="Dmain-lt">
			项目列表
			</div>
		
		<div class="pp-btn p-btn3" style="display: none;" id="conture">
			<input class="btn-t btn-new" type="button" value="全局项目成员" id="allgroup" onclick="codeview.allgroup()" >  
			<input class="btn-t  btn-new " type="button" value="创建项目" id="creatbut" onclick="codeview.create()"> 
			<input class="btn-t  btn-del2" type="button" value="删除项目" onclick="codeview.del()">
		</div>
				<div class="pp-btn">
			<input class="sear fl" type="text" style="border-radius:4px;" id="prjname" >
			<input class="btn-t " type="button" value="查询"  onclick="codeview.query()" id="selectbut0" >  
			
		</div>
		<div class="mamin-con" >
		<div style="width:100%;" id="projectlist">
        </div>
	</div>
	</div>
	<div class="Dmain-r">
			<div class="Dmain-lt">
			项目详情
			</div>
	<iframe id="downiframe" name="downiframe"   width="100%" height="750px"  scrolling="none" frameborder="0"></iframe>
	</div>
		
</body>
</html>