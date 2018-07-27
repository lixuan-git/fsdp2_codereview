<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/fsdp/common/taglibs2.jsp" %>

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
    	<div class="add-common-name">用户详细信息<br/>
            <ul>
            	<li class="add-link-li">全部</li> 
            </ul>
        </div>
    </div>
    
    <div class="finedo-nav-title">基本信息</div> 
	<ul class="finedo-ul">
		<li>
			<fsdp:label label="用户账号">${sysuser.usercode}</fsdp:label>
		</li>
		
        <li>
        	<fsdp:label label="用户姓名">${sysuser.personname}</fsdp:label>
       	</li>
        	
       	<li>
        		<fsdp:label label="生效日期">${sysuser.effdate}</fsdp:label>
		</li>
          	
        <li>
        	<fsdp:label label="失效日期">${sysuser.expdate}</fsdp:label>
        </li>
          	
		<li>
			<fsdp:label label="性别"><fsdp:dictionary datasource="性别" value="${sysuser.gender}"></fsdp:dictionary></fsdp:label>
		</li>
			 				
        <li>
        	<fsdp:label label="状态"><fsdp:dictionary datasource="用户状态" value="${sysuser.state}"></fsdp:dictionary></fsdp:label>
       	</li>
		
		<li>
			<fsdp:label label="手机号码">${sysuser.phoneno}</fsdp:label>
		</li>
          	
        <li>
          	<fsdp:label label="邮箱">${sysuser.email}</fsdp:label>
		</li>
          	
		<li>
			<fsdp:label label="地址">${sysuser.address}</fsdp:label>
		</li>
          	
		<li>
			<fsdp:label label="备注">${sysuser.remark}</fsdp:label>
		</li>
	</ul>
</div>
</body>
</html>