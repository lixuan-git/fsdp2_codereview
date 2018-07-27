<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
${finedo_upload_js }
<script>
$(document).ready(function() {
    finedo.getFileControl('uploaddiv');
});
</script>
</head>

<body>
<div>
	<div class="add-common-head">
    	<div class="add-common-name">上报问题详情<br/></div>
    </div>
    
    <div id="common_add_div" >
    	<div class="finedo-nav-title">基本信息</div>
	   	<ul class="finedo-ul">
			
			<li>
				<span class="finedo-label-title"><font color=red>*</font>问题标题</span>
				<span class="finedo-label-info">${sysquestion.title}</span>
			</li>

           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>问题上报人</span>
           		<span class="finedo-label-info">${sysquestion.initiatorname}[${sysquestion.initiator}]</span>
           	</li>

           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>问题发生时间</span>
           		<span class="finedo-label-info">${sysquestion.happentime}</span>
           	</li>

           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>问题上报时间</span>
           		<span class="finedo-label-info">${sysquestion.createtime}</span>
           	</li>
           	
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>问题描述</span>
           		<span class="finedo-label-info">${sysquestion.questiondesc}</span>
           	</li>
           	
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>状态</span>
           		<span class="finedo-label-info">${sysquestion.status}</span>
           	</li>
           	
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>问题处理人</span>
           		<span class="finedo-label-info">${sysquestion.dealmanname}[${sysquestion.dealman}]</span>
           	</li>
           	
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>问题处理时间</span>
           		<span class="finedo-label-info">${sysquestion.dealtime}</span>
           	</li>
           	
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>问题处理描述</span>
           		<span class="finedo-label-info">${sysquestion.dealdesc}</span>
           	</li>
           	<li>
        		<span class="finedo-label-title">附件</span>
				<input type="text" id="uploaddiv" name="uploaddiv" value="${sysquestion.attachment }" editable="false">
			</li>
		</ul>
    </div>
</div>
</body>
</html>
