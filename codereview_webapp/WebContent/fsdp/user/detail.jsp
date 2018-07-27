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
	
	<div class="finedo-nav-title">岗位角色信息</div> 
	<ul class="finedo-ul">
		<li>
			<fsdp:label label="基本岗位角色">${stdrole.rolename}</fsdp:label>
		</li>
          	
         <li>
         	<c:set value="" var="rolenames"></c:set>
			<c:forEach items="${otherrole}" var="rolell">
			<c:if test="${!empty rolenames}"><c:set value="${rolenames},${rolell.rolename }" var="rolenames"></c:set></c:if>
			<c:if test="${empty rolenames}"><c:set value="${rolell.rolename }" var="rolenames"></c:set></c:if>
			</c:forEach>
		
			<fsdp:label label="兼任岗位角色">${rolenames}</fsdp:label>
         </li>
	</ul>
</div>
</body>
</html>
