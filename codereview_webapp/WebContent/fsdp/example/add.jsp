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
${finedo_upload_js }
${finedo_choose_js }

<script>
/************** 卡片显示与隐藏  ****************/
$(document).ready(function() {
    $('#common_add_card').click(function(e) {
    	$('#common_add_div').css('display', 'block');
    	$('#common_add_card').attr('class', 'add-link-li');
    	
    	$('#excel_add_div').css('display', 'none');
		$('#excel_add_card').removeClass();
		finedo.getFileControl('uploaddiv').reset(false);
    });
    
    $('#excel_add_card').click(function(e) {
    	$('#common_add_div').css('display', 'none');
    	$('#common_add_card').removeClass();
    	
    	$('#excel_add_div').css('display', 'block');
		$('#excel_add_card').attr('class', 'add-link-li');
    });
});
/************** 卡片显示与隐藏  ****************/

// Web组件初始化 
$(document).ready(function() {
    finedo.getFileControl('uploaddiv');
});

// 选择人员
function chooseperson() {
	finedo.choose.singleUser(function(row) {
		//alert(JSON.stringify(row));
		if(row.length > 0) {
			$('#personid').val(row[0].user.personid);
			$('#personidname').val(row[0].user.personname);
		}
	});
}

function clearperson() {
	$('#personid').val('');
	$('#personidname').val('');
}

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
		"personid":{label:"所属人员", datatype:"string", maxlength:50, required:true},
		"effdate":{label:"生效日期", datatype:"datetime", required:true},
		"expdate":{label:"失效日期", datatype:"datetime", required:true},
		"loginpasswd":{label:"登录密码", datatype:"string", minlength:6, maxlength:15, required:true},
		"remark":{label:"备注", datatype:"string", maxlength:200, required:false}
	});
	
   	//TODO: 自定义数据验证
	

	return result;
}

// 提交
function donext() {
	if($('#common_add_div').css('display') == 'block') {
		dosubmit();
	}else {
		doimport();
	}
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
	finedo.message.info(jsondata.resultdesc, "新建用户信息");
}

// 批量导入
function doimport() {
	var fileControl=finedo.getFileControl('uploaddiv');
	var filearr=fileControl.getFileList();
	if(filearr.length != 1) {
		finedo.message.error("未上传Excel文件");
		return;
	}
	$('#fileid').val(filearr[0].fileid);
	
	var form = $("#importForm");
	var options = {     
        url:	   form.attr("action"),
        success:   importcallback,
        type:      'POST',
        dataType:  "json",
	    clearForm: false
    };
	finedo.message.showWaiting();
	form.ajaxSubmit(options);
}


function importcallback(jsondata){
	finedo.message.hideWaiting();

	$('#importresultdiv').css('display', 'block');
	var resulthtml="<li><span class='finedo-label-title'>导入情况</span><span class='finedo-label-info'><font color=red>" + jsondata.resultdesc + "   总记录数: " + jsondata.object.rowcount + " &nbsp;&nbsp; 成功记录数: " + jsondata.object.successcount + "&nbsp;&nbsp; 失败记录数: " + jsondata.object.failcount + "</font></span></li>";
	var faillist=eval(jsondata.object.faillist);
	for(var i=0; i<faillist.length; i++)  {
		resulthtml += "<li><span class='finedo-label-title'>失败明细</span><span class='finedo-label-info'>" + faillist[i].resultdesc + "</span></li>";
	}
	
	$('#importresultul').html(resulthtml);
}
</script>
</head>

<body>
<div>
	<div class="add-common-head">
    	<div class="add-common-name-add">新建用户信息<br/>
            <ul>
            	<li id="common_add_card" class="add-link-li">普通新建</li> 
            	<li>|</li>
                <li id="excel_add_card">批量导入</li>
            </ul>
        </div>
        <input type="button" class="finedo-button-blue" style="float:right" value="提交" onclick="donext();">
    </div>
    
    <form method="post" action="${ctx }/finedo/example/add" id="ajaxForm" name="ajaxForm">
  	<div id="common_add_div" >
    	<div class="finedo-nav-title">基本信息</div>
	   	<ul class="finedo-ul">
		
			<li>
				<span class="finedo-label-title"><font color=red>*</font>用户账号</span>
				<fsdp:text id="usercode" hint="最大30个字符，支持大小写英文、数字、下划线格式" />
			</li>
		
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>所属人员</span>
           		
           		<input type="hidden" id="personid" name="personid" value="">
				<input type="text" class="finedo-text" id="personidname" name="personidname" value="">
				
				<input type="button" class="finedo-button" value="选择" onclick="chooseperson();">
				<input type="button" class="finedo-button" value="清空" onclick="clearperson();">
			</li>
           	
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>生效日期</span>
           		<fsdp:date id="effdate" />
           	</li>
           	
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>失效日期</span>
           		<fsdp:date id="expdate" />
           	</li>
           	            	
			<li>
				<span class="finedo-label-title"><font color=red>*</font>用户状态</span>
           		<fsdp:radio id="state" datasource="用户状态" selectedvalue="有效"/>
           	</li>
			
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>登录密码</span>
				<fsdp:password id="loginpasswd" hint="6-15位字符，必须包含大小写英文字母、数字、特殊符号" />
			</li>
			
			<li>
				<span class="finedo-label-title">备注</span>
				<fsdp:text id="remark" style="width:600px" />
			</li>
		</ul>
		
		<ul>
	    	<li class="add-center"><input type="button" class="finedo-button" value="提    交" onClick="dosubmit()" ></li>
	    </ul>
	</div>
	</form>
	    
    <div id="excel_add_div" style="display:none"> 
   		<form method="post" action="${ctx }/finedo/example/importexcel" id="importForm" name="importForm">
   			<input type="hidden" id="fileid" name="fileid">
   		</form>
    	
    	<div class="finedo-nav-title">导入Excel</div>
		<ul class="finedo-ul">
			<li>
				<input type="text" id="uploaddiv" name="uploaddiv" filter=".xls,.xlsx" multiupload="false">
			</li>
        </ul>
        
        <ul>
    		<li class="add-center"><input type="button" class="finedo-button" value="提    交" onclick="doimport();"></li>
		</ul>
       
       	<div id="importresultdiv" style="display:none">
	    	<div class="finedo-nav-title"><font color=red>导入Excel结果</font></div>
		    <div class="query-common-con">
		    	<ul id="importresultul" class="finedo-ul"></ul>
		    </div>
	    </div>
	    	   
	    <div class="finedo-nav-title">导入Excel格式说明</div>
		<ul class="finedo-ul">
			<li>
				<span class="finedo-label-title">模板下载</span><span class="finedo-label-info"><a href="${ctx}/fsdp/jsp/example/import.xlsx" >用户信息批量导入Excel模板 </a></span>
			</li>
			<li>
				<span class="finedo-label-title">第一列：</span><span class="finedo-label-info">用户账号</span>
			</li>
			<li>
				<span class="finedo-label-title">第二列：</span><span class="finedo-label-info">所属人员ID</span>
			</li>
			<li>
				<span class="finedo-label-title">第三列：</span><span class="finedo-label-info">生效日期</span>
			</li>
			<li>
				<span class="finedo-label-title">第四列：</span><span class="finedo-label-info">失效日期</span>
			</li>
			<li>
				<span class="finedo-label-title">第五列：</span><span class="finedo-label-info">用户状态</span>
			</li>
			<li>
				<span class="finedo-label-title">第六列：</span><span class="finedo-label-info">登录密码</span>
			</li>
			<li>
				<span class="finedo-label-title">第七列：</span><span class="finedo-label-info">备注</span>
			</li>
		  </ul>
    </div>
</div>
</body>
</html>

