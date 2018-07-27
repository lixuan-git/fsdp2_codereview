<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_dialog_js }
${finedo_date_js }

<script>
// 数据校验
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
		"usercode":{label:"用户账号", datatype:"string", maxlength:30, required:true},
		"effdate":{label:"生效日期", datatype:"datetime", required:true},
		"expdate":{label:"失效日期", datatype:"datetime", required:true},
		"remark":{label:"备注", datatype:"string", maxlength:200, required:false}
	});
	
  	//TODO: 自定义数据验证
	alert(result);

	return result;
}

// 提交
function donext() {
	dosubmit();
}

function dosubmit() {
	if(!checkdata()) 
		return;
alert("222");
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
	finedo.message.info(jsondata.resultdesc, "修改用户信息");
}
</script>
</head>

<body>
<div>
	<div class="add-common-head">
    	<div class="add-common-name-add">修改用户信息<br/>
            <ul>
            	<li id="common_add_card" class="add-link-li">全部</li>
            </ul>
        </div>
        <input type="button" class="finedo-button-blue" style="float:right;" value="提交" onclick="donext();">
    </div>
    
    <form method="post" action="${ctx }/finedo/example/modify" id="ajaxForm" name="ajaxForm">
    <input type="hidden" id="userid" name="userid" value="${sysuser.userid }"/>
    <input type="hidden" id="personid" name="personid" value="${sysuser.personid }"/>
    <div class="finedo-nav-title">基本信息</div>
	<ul class="finedo-ul">
		<li>
			<span class="finedo-label-title"><font color=red>*</font>用户账号</span>
			<fsdp:text id="usercode" hint="最大30个字符，支持大小写英文、数字、下划线格式" value="${sysuser.usercode }"/>
		</li>
					
       	<li>
       		<span class="finedo-label-title"><font color=red>*</font>生效日期</span>
       		<fsdp:date id="effdate" value="${sysuser.effdate }" />
       	</li>
            	
       	<li>
       		<span class="finedo-label-title"><font color=red>*</font>失效日期</span>
       		<fsdp:date id="expdate" value="${sysuser.expdate }" />
       	</li>
            	
      	<li>
      		<span class="finedo-label-title"><font color=red>*</font>用户状态</span>
      		<fsdp:select id="state" datasource="用户状态" selectedvalue="${sysuser.state }"/>
      	</li>
				
		<li>
			<span class="finedo-label-title">备注</span>
			<fsdp:text id="remark" style="width:600px" value="${sysuser.remark }"/>
		</li>
	</ul>
	
	<ul>
    	<li class="add-center"><input type="button" class="finedo-button" value="提    交" onClick="dosubmit()" ></li>
    </ul>
    </form>
</div>
</body>
</html>
