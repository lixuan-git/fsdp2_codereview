<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_dialog_js }
${finedo_date_js }
${finedo_choose_js }

<script>
// 数据验证
function checkdata() {
   /**
 	* 	通用数据验证
 	* 	label  		名称
	*	datatype 	数据类型  string email phone url date datetime time number digits 
	*	minlength 	最小长度
	*	maxlength 	最大长度
	*	required 	是否必填 true/false
	*/
	var result=finedo.validate({
		"dealman":{label:"问题处理人", datatype:"string", required:true},
		"dealtime":{label:"问题处理时间", datatype:"datetime", required:true},
		"dealdesc":{label:"问题处理描述", datatype:"string", required:true}
	});
	return result;
}

// 提交
function doNext() {
	dosubmit();
}

// 普通新建
function dosubmit() {
	if(!checkdata()) 
		return;

	var form = $("#ajaxForm");
	var options = {     
        url:	   form.attr("action"),
        success:   submitcallback,
        type:      'POST',
        dataType:  "json",
	    clearForm: false
    };
	finedo.message.showWaiting();
	form.ajaxSubmit(options);
}

function submitcallback(jsondata){
	finedo.message.hideWaiting();
	finedo.message.info(jsondata.resultdesc, "问题上报");
}
function doSelectUser(){
	finedo.choose.singleUser(function(data){
		if(!data || data.length <=0)
			return;
		$("#dealman").val(data[0].user.userid);
		$("#dealmanname").val(data[0].user.personname);
	});
}
</script>
</head>

<body>
<div>
<div class="add-common-head">
    	<div class="add-common-name-add">问题上报修改<br/></div>
        <input type="button" class="finedo-button-blue" style="float:right" value="提交" onclick="doNext();">
    </div>
    <div id="common_add_div" >
    	<div class="finedo-nav-title">基本信息</div>
	   	<ul class="finedo-ul">
    	<form method="post" action="${ctx }/finedo/question/deal" id="ajaxForm" name="ajaxForm">
    		<input type="hidden" id="questionid" name="questionid" value="${sysquestion.questionid }">
			<li>
				<span class="finedo-label-title"><font color=red>*</font>问题处理人</span>
           		<input type="hidden" id="dealman" name="dealman" value="${sessionScope.LOGINDOMAIN_KEY.sysuser.userid}">
				<fsdp:text id="dealmanname" style="width:600px;" onclick="doSelectUser()" value="${sessionScope.LOGINDOMAIN_KEY.sysuser.personname}"/>
			</li>

           <li>
           		<span class="finedo-label-title"><font color=red>*</font>问题处理时间</span>
           		<fsdp:date id="dealtime" datefmt="yyyy-MM-dd HH:mm:ss"/>
           	</li>

           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>问题处理描述</span>
           		<fsdp:textarea id="dealdesc"/>
           	</li>
    	</form>
        </ul>
        <ul>
	    	<li class="add-center"><input type="button" class="finedo-button" value="提    交" onClick="dosubmit()" ></li>
	    </ul>
    </div>
</div>
</body>
</html>
