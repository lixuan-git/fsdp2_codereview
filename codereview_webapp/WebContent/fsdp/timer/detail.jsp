<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs2.jsp" %>

<!doctype html>
<html>
<head>
${style_css }
${jquery_js }
${finedo_core_js }
${finedo_commonui_js }
</head>

<body>
<div>
	<div class="add-common-head">
    	<div class="add-common-name">定时器详情<br/></div>
    </div>
    
    <div id="common_add_div" >
    	<div class="finedo-nav-title">基本信息</div>
	   	<ul class="finedo-ul">
	   		<li>
				<span class="finedo-label-title"><font color=red>*</font>技术说明</span>
				<span class="finedo-label-info">1) 定时器类必须声明@Component、@Control、@Service注解, 否则Spring不会加载该类</span>
				<span class="finedo-label-info">2) 新增、修改、删除定时器重启应用服务器才能生效</span>
			</li>
			
			<li>
				<span class="finedo-label-title"><font color=red>*</font>定时任务名称</span>
				<span class="finedo-label-info">${timertask.name}</span>
			</li>

           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>Bean类名</span>
           		<span class="finedo-label-info">${timertask.beanname}</span>
           	</li>

           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>Bean类方法名</span>
           		<span class="finedo-label-info">${timertask.method}</span>
           	</li>
           	
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>CRON</span>
           		<span class="finedo-label-info">${timertask.cron}</span>
           	</li>
           	
           	<li>
           		<span class="finedo-label-title"><font color=red>*</font>创建时间</span>
           		<span class="finedo-label-info">${timertask.optdate}</span>
           	</li>
		</ul>
    </div>
</div>
</body>
</html>
