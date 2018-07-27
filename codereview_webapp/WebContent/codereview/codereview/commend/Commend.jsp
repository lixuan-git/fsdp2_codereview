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
${finedo_tree_js }
<head>
<meta charset="UTF-8" />
<title>代码推荐</title>
<link rel="stylesheet" href="/codereview_webapp/codereview/codereview/commend/style/style.css">
	<link href="${ctx}/resource/js/select2/css/select2.min.css" rel="stylesheet" />
	<script src="${ctx}/resource/js/select2/js/select2.full.min.js"></script>
	<script src="${ctx}/resource/js/select2/js/i18n/zh-CN.js"></script>
	<script type='text/javascript'
	src='/codereview_webapp/fsdp/resource/js/My97DatePicker/WdatePicker.js'></script>
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
#detildiv input {
border: none;
}
.no-xm {
    text-align: center;
    font-size: 14px;
    line-height: 40px;
    height: 40px;
    margin-top: 50px;
    }
</style>
	<script type="text/javascript">
	var usercode= "${LOGINDOMAIN_KEY.sysuser.usercode}";
	var pollnumber=0;
	var pollid="";
	var firsttype = false;
	$(function(){
		   $("#detildiv").hide();
		   $("#creatediv").hide();
		   $("#headdiv").html("<img src='images/wdxm.png'>详情"); 
		var myDate = new Date();
		var day =myDate.getDate();
		var month=myDate.getMonth()+1;
		var year=myDate.getFullYear();
		if(day>0&&day<10){
			day="0"+day.toString();
		}
		if(month>0&&month<10){
			month="0"+month.toString();
		}
		$("#begintime").val(year+"-"+month+"-01");
		$("#endtime").val(year+"-"+month+"-"+day); 
		firsttype = true;
		codeview.query();
		codeview.querynumber();
	});
	var codeview={};
	//查询推荐代码列表
	codeview.query=function (){
		var prjname="";
	if(finedo.fn.isNon($("#prjname").val())){
		prjname="";
	}else{
		prjname=$("#prjname").val();
	}
		$("#indexshow").remove();
		$("#commendlist").append("<table id='indexshow'  selecttype='none'  class='finedo-datagrid' ></table>");
		 var options={
			       url: '/codereview_webapp/finedo/svncommend/querylist',
			       pagesize: 15,
			       selectype: 'none',
					pagination: true,
					queryparams:{
						"begintime":$("#begintime").val(),
						"endtime":$("#endtime").val(),
						"groupname":"%"+prjname+"%"
						},
			       columns: [
						{ code: 'groupname', title: '已推荐代码', width: 200,formatter:formatPkey},  	    
						{ code: 'pollnumber', title: '票数', width: 50,},
						{ code: 'commend', title: '推荐人', width: 70,},
			       ]
					};
					finedo.getgrid('indexshow', options).load(); 
					
	};
	function formatPkey(row){
		if(firsttype){
				codeview.modifybefore(row.commendid);
				firsttype = false;
			}
	    return '<a href="javascript:void(0);" onclick="codeview.modifybefore(\'' + row.commendid + '\');">' + row.orgcoed+row.groupname+"--" +row.commend+ '</a>&nbsp;';
	}
	//查询是否是评审人员剩余投票次数
	codeview.querynumber=function(){
		//先判断是否是评审小组成员
		$.ajax( {
			  url: "/codereview_webapp/finedo/svncommend/querynumber", 
			    type: "post",
				dataType : "json",
			    data:{
			    	creatuser:"${LOGINDOMAIN_KEY.sysuser.usercode}",
			    },
			    success: function(ret){
			   if(ret.fail){
				   finedo.message.error = '后台错误！';
				   return;
			   }
			   if(ret.resultdesc=="没有投票权限"){
				   $("#butndiv").hide();
			   }
			   if(ret.resultdesc=="有投票权限"){
				   pollnumber=ret.object.pollnumber;
				   $("#butndiv").show();
				   $("#poll").show();
				   $("#createbut").css("display","inline");
			   }
		    }
		});
	};
	//查询详情
	codeview.modifybefore=function(commendid){
		 $("#headdiv").html("<img src='images/wdxm.png'>详情"); 
		pollid=commendid;
		codeview.querynumber();
		//展示详情
		$.ajax( {
		    url: "/codereview_webapp/finedo/svncommend/querydetail", 
		    type: "post",
			dataType : "json",
		    data:{
		    	commendid:commendid,
		    },
		    success: function(ret){
		   if(ret.fail){
			   finedo.message.error = '后台错误！';
			   return;
		   }
		   $("#detildiv").show();
		   $("#creatediv").hide();
		   $("#nodiv").hide();
		   $("#commend0").val(ret.object.commend);
		   $("#groupname0").val(ret.object.groupname);
		   $("#orgcoed0").val(ret.object.orgcoed);
		   $("#groupuser0").val(ret.object.groupuser);
		   $("#commendreason0").val(ret.object.commendreason);
		   $("#code0").val(ret.object.code); 
		    }
		}); 
	};
	//新建推荐代码
	codeview.create=function (){
		 $("#headdiv").html("<img src='images/wdxm.png'>新增"); 
		   $("#commendsel").val("");
		   $("#groupname").val("");
		   $("#orgcoed").val("");
		   $("#groupuser").val("");
		   $("#commendreason").val("");
		   $("#code").val(""); 
		   $("#detildiv").hide();
		   $("#creatediv").show();
		   $("#nodiv").hide();
		   searchPerson("commendsel", "请输入用户名", true);
	};
	codeview.commit=function (){
		 if(finedo.fn.isNon($("#commendsel").val())){
			  finedo.message.info('请填写推荐人！');
			 return;
		} 
		if(finedo.fn.isNon($("#groupname").val())){
			 finedo.message.info('请填写项目名称！');
			 return;
		}
		if(finedo.fn.isNon($("#orgcoed").val())){
			 finedo.message.info('请填写归属部门！');
			 return;
		}
		if(finedo.fn.isNon($("#groupuser").val())){
			 finedo.message.info('请填写团队成员！');
			 return;
		}
		if(finedo.fn.isNon($("#code").val())){
			 finedo.message.info('请填写推荐代码！');
			 return;
		}
		//展示详情
		var parastr=$("#code").val();
		parastr= parastr.replace(/'/g, "&#180;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
		$.ajax( {
		    url: "/codereview_webapp/finedo/svncommend/create", 
		    type: "post",
			dataType : "json",
		    data:{
		    	commend:$("#commendsel").val(),
		    	groupname:$("#groupname").val(),
		    	orgcoed:$("#orgcoed").val(),
		    	groupuser:$("#groupuser").val(),
		    	commendreason:$("#commendreason").val(),
		    	code:parastr,
		    	creatuser:usercode
		    },
		    success: function(ret){
		   if(ret.fail){
			   finedo.message.error = '后台错误！';
			   return;
		   }
			finedo.message.question('推荐成功！', '', function(choose) {
				 window.location.reload();
			});
		    }
		}); 
	};
	//投票
	   codeview.poll=function(){
		   if(pollnumber<=0){
			   finedo.message.info('剩余投票次数为0 ！');
			   return;
		   }
			$.ajax( {
			    url: "/codereview_webapp/finedo/svncommend/poll", 
			    type: "post",
				dataType : "json",
			    data:{
			    	commendid:pollid,
			    	creatuser:usercode,
			    },
			    success: function(ret){
			   if(ret.fail){
				   finedo.message.error = '后台错误！';
				   return;
			   }
			   codeview.query();
			   codeview.querynumber();
			   finedo.message.info('投票成功！');
			    }
			}); 
	   }
	   //根据员工姓名进行查询
	   searchPerson = function(componentname, tipmsg, multiple) {
	   	var ismultiple = false;
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
	   codeview.close=function(){
		   window.location.reload();
	   };
	   codeview.back=function (){
		 //  /codereview_webapp/codereview/codereview/platform.jsp
		   window.close();
	   }
	</script>
	</head>
	<body>
		<div class="body-main">
			<div class="Dmain-l" id="ismobile">
				<div class="Dmain-lt"><img src="images/cyps.png">代码推荐列表</div>
				<div class="Dmain-lc">
					<span>项目名称：</span><input type="text" class="com-text" id="prjname" >
					<span>时间：</span>
					<input class="finedo-date" style="width: 15%;" type="text" value="" id="begintime" name="begintime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					<input class="finedo-date" style="width: 15%;"  type="text" value="" id="endtime" name="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					<input type="button" value="查询" class="com-btn2" style="margin-left: 5px;" onclick="codeview.query()">
						<input type="button" value="新增" class="com-btn"    style="margin-left: 5px;display: none;"  onclick="codeview.create()" id="createbut">
				</div>
				<div class="Dmain-ld" >
			<div style="width:100%;" id="commendlist">
        	</div>
			</div>
			</div>

			<div class="Dmain-c"></div>

			<div class="Dmain-r" id="alldiv">
				<div class="Dmain-lt" id="headdiv"><img src="images/wdxm.png">详情</div>
						<div class="no-xm"  id="nodiv"><img src="/codereview_webapp/codereview/codereview/img/hint.png" style="vertical-align: middle;">暂无详情！</div>
				<div class="Dmain-rd" id="detildiv" >
					<table class="new-tab" cellpadding="0" cellspacing="0" style="width: 100%">
						<tr>
							<td class="name">推荐人：</td>
							<td id="seletd">
							<input type="text" class="new-text" id="commend0" readonly="readonly"> 
							</td>
							<td class="name">项目名称：</td>
							<td><input type="text" class="new-text" id="groupname0" readonly="readonly"></td>
						</tr>
						<tr>
							<td class="name">归属部门：</td>
							<td><input type="text" class="new-text" id="orgcoed0" readonly="readonly"></td>
							<td class="name">团队成员：</td>
							<td><input type="text" class="new-text" id="groupuser0" readonly="readonly"></td>
						</tr>
						<tr>
							<td class="name">推荐理由：</td>
							<td colspan="3"><input type="text" class="new-text" id="commendreason0" readonly="readonly"></td>
						</tr>
							<tr>
							<td class="name">代码展示：</td>
							<td></td><td></td><td></td>
						</tr>
						<tr>
							<td class="chossebox" colspan="4"><textarea class="new-textarea" style="height: 420px;font-size: 12px;" id="code0" readonly="readonly"></textarea></td>
						</tr>
					</table>
					<div class="new-btn" id="butndiv"  style="display: none;" > 
						<input type="button" value="返回" class="cansle"  onclick="codeview.back()" />
						<input type="button" value="投票" class="goon"  id="poll" onclick="codeview.poll()"/>
					</div>
				</div>
				<div class="Dmain-rd"  id="creatediv">
					<table class="new-tab" cellpadding="0" cellspacing="0" style="width: 100%">
						<tr>
							<td class="name">推荐人：</td>
								<td id="seletd0">
							<select name="commendsel" id="commendsel" class="add-text"  style="height: 40px;width: 100%;display: none;"></select>
							</td>
						</tr>
						<tr>
							<td class="name">项目名称：</td>
							<td><input type="text" class="new-text" id="groupname" ></td>
						</tr>
						<tr>
							<td class="name">归属部门：</td>
							<td><input type="text" class="new-text" id="orgcoed" ></td>
						</tr>
						<tr>
							<td class="name">团队成员：</td>
							<td><input type="text" class="new-text" id="groupuser" ></td>
						</tr>
						<tr>
							<td class="name">推荐理由：</td>
							<td><input type="text" class="new-text" id="commendreason" ></td>
						</tr>
							<tr>
							<td class="name">代码展示：</td>
							<td></td>
						</tr>
						<tr>
							<td class="chossebox" colspan="2"><textarea class="new-textarea" style="height: 345px;font-size: 12px;" id="code" ></textarea></td>
						</tr>
					</table>
					<div class="new-btn"  > 
						<input type="button" value="取消" class="cansle"  onclick="codeview.close()" />
						<input type="button" value="提交" class="goon"   id="commit"  onclick="codeview.commit()"/> 
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</body>

</html>