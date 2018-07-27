<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_dialog_js }
${finedo_date_js }
${finedo_upload_js }
${finedo_choose_js }

<script>
$(document).ready(function() {
    finedo.getFileControl('uploaddiv');
});
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
		"title":{label:"问题标题", datatype:"string", required:true},
		"initiator":{label:"问题上报人", datatype:"string", required:true},
		"happentime":{label:"问题发生时间", datatype:"datetime", required:true},
		"questiondesc":{label:"问题描述", datatype:"string", required:true}
	}, true);
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
	    clearForm: true
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
		$("#initiator").val(data[0].user.userid);
		$("#initiatorname").val(data[0].user.personname);
	});
}
</script>
</head>

<body>
<div>
	<div class="add-common-head">
    	<div class="add-common-name-add">问题上报<br/></div>
        <input type="button" class="finedo-button-blue" style="float:right" value="提交" onclick="doNext();">
    </div>
    <div id="common_add_div" >
    	<div class="finedo-nav-title">基本信息</div>
	   	<ul class="finedo-ul">
    	<form method="post" action="${ctx }/finedo/question/add" id="ajaxForm" name="ajaxForm">
    		<input type="hidden" id="attachment" name="attachment">
			<li>
				<span class="finedo-label-title" style="width:20%;"><font color=red>*</font>问题标题</span>
				<fsdp:text id="title" style="width:80%;"/>
			</li>

           <li>
           		<span class="finedo-label-title" style="width:20%;"><font color=red>*</font>问题上报人</span>
           		<input type="hidden" id="initiator" name="initiator" value="${sessionScope.LOGINDOMAIN_KEY.sysuser.userid}">
           		<fsdp:text id="initiatorname" style="width:80%;" onclick="doSelectUser()" value="${sessionScope.LOGINDOMAIN_KEY.sysuser.personname}"/>
           	</li>

           <li>
           		<span class="finedo-label-title" style="width:20%;"><font color=red>*</font>问题发生时间</span>
           		<fsdp:date id="happentime" style="width:80%;" datefmt="yyyy-MM-dd HH:mm:ss"/>
           	</li>

           	<li>
           		<span class="finedo-label-title" style="width:20%;"><font color=red>*</font>问题描述</span>
           		<fsdp:textarea id="questiondesc" style="width:80%;"/>
           	</li>
    	</form>
        	<li>
				<input type="text" id="uploaddiv" style="width:100%;" name="uploaddiv" valueid="attachment" multiupload="true" maxfilenum="5">
			</li>
        </ul>
        <ul>
	    	<li class="add-center"><input type="button" class="finedo-button" value="提    交" onClick="dosubmit()" ></li>
	    </ul>
    </div>
</div>
</body>
</html>
