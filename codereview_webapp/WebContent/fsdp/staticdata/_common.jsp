<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>
	
	<fieldset style="border-color:orange;">
		<legend>数据类型</legend>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="input-table">
			<tr>
		    <th width="100">功能模块：</th>
		    <td >
		    	<fsdp:select datasource="功能模块" name="moduleid" selectedvalue="${staticdomain.statictype.moduleid}"></fsdp:select>
		    </td>
		    </tr>
		  <tr>
		    <th width="100">配置类型：</th>
		    <td >
		    	<c:set value="keyvalue" var="configtype"></c:set>
		    	<c:if test="${!empty staticdomain.statictype.configtype}">
		    		<c:set value="${staticdomain.statictype.configtype}" var="configtype"></c:set>
		    	</c:if>
		    	<fsdp:radiobox datasource="静态数据类型" name="configtype" selectedvalue="${configtype}" onclick="switchType()"></fsdp:radiobox>
		    </td>
		    </tr>
		  <tr>
			<th>类型名称：</th>
		    <td >
		    	<input type="hidden" name="datatype" value="字符串"/>
		    	<fsdp:text id="typename" name="typename" value="${staticdomain.statictype.typename}" datatype="Require" msg="类型名称不能为空，请填写类型名称" ></fsdp:text>
		    </td>

		  </tr>
		  <tr>
			<th>数据级别：</th>
		    <td >
		    	<fsdp:select datasource="静态数据级别" name="lvl" selectedvalue="${staticdomain.statictype.lvl}"></fsdp:select>
		    </td>

		  </tr>
		  <tr id="sqlTr" style="display: none;">
			<th>SQL语句：</th>
		    <td >
		    	<fsdp:text id="sqlscript" name="sqlscript" value="${staticdomain.staticsql.sqlscript}" ></fsdp:text>
		    </td>

		  </tr>
		  <tr>
		    <th>类型说明：</th>
		    <td><fsdp:textarea style="height: 50px" id="typedesc" name="typedesc" value="${staticdomain.statictype.typedesc}"></fsdp:textarea>
			</td>
		  </tr>
		</table>
		</fieldset>
		
		<fieldset style="border-color:orange;" id="dataItemField">
		<legend>数据项</legend>
		<div id="datadiv">
			<table width="98%" cellspacing="0" class="tbcss" id="dataitems"> 
			  <tr>
			    <th>数据标签</th>       
			    <th>数据值</th> 
			   <th>排序</th>
			    <th>备注</th> 
			    <th style="text-align: left;"><fsdp:button name="添加" onclick="additem();" iconcls="icon-add" plain="true"></fsdp:button></th> 
			  </tr>
			  <c:if test="${empty staticdomain.staticdatalist}">
			  <tr>         
			    <td class="nobg">     
					<fsdp:text name="data_attrname"  style="width:80%"></fsdp:text>
			    </td>
			    <td class="nobg">
			    	<fsdp:text name="data_attrvalue"  style="width:80%"></fsdp:text>
			    </td>  
			   <td class="nobg">
			    	<fsdp:text name="data_orderseq"  style="width:80%" onkeypress="checkInteger(this)" onblur="checkInteger(this)"></fsdp:text>
			    </td>
			    <td class="nobg">
			    	<fsdp:text name="data_remark" style="width:80%"></fsdp:text>
			    </td> 
			    <td class="nobg">
			    	&nbsp;
			    </td> 
			  </tr>
			  <tr>         
			    <td class="nobg">     
					<fsdp:text name="data_attrname" style="width:80%"></fsdp:text>
			    </td>
			    <td class="nobg">
			    	<fsdp:text name="data_attrvalue" style="width:80%"></fsdp:text>
			    </td>  
			      <td class="nobg">
			    	<fsdp:text name="data_orderseq"  style="width:80%" onkeypress="checkInteger(this)" onblur="checkInteger(this)"></fsdp:text>
			    </td>
			    <td class="nobg">
			    	<fsdp:text name="data_remark" style="width:80%"></fsdp:text>
			    </td> 
			    <td class="nobg">
			    	<fsdp:button name="删除" onclick="deleteitem(this);" iconcls="icon-cancel"  plain="true"></fsdp:button>
			    </td> 
			  </tr>
			  </c:if>
			   <c:if test="${!empty staticdomain.staticdatalist}">
			   <c:forEach items="${staticdomain.staticdatalist}" var="staticdata">
			   <tr>         
			    <td class="nobg">     
					<fsdp:text name="data_attrname" style="width:80%" value="${staticdata.attrname}"></fsdp:text>
			    </td>
			    <td class="nobg">
			    	<fsdp:text name="data_attrvalue"  style="width:80%" value="${staticdata.attrvalue}"></fsdp:text>
			    </td>  
			      <td class="nobg">
			    	<fsdp:text name="data_orderseq"  style="width:80%" value="${staticdata.orderseq}" onkeypress="checkInteger(this)" onblur="checkInteger(this)"></fsdp:text>
			    </td>
			    <td class="nobg">
			    	<fsdp:text name="data_remark" style="width:80%" value="${staticdata.remark}"></fsdp:text>
			    </td> 
			    <td class="nobg">
			    	<fsdp:button name="删除" onclick="deleteitem(this);" iconcls="icon-cancel"  plain="true"></fsdp:button>
			    </td> 
			  </tr>
			  </c:forEach>
			   </c:if>
			</table>
		</div>
		</fieldset>
	
<script type="text/javascript">
function additem(){
	var tr = $("#dataitems").find("tr:eq(2)").clone();
	$("#dataitems").append(tr);
}
function deleteitem(obj){
	var tr = $(obj).parent().parent();
	tr.remove();
}

function checkInteger(input){
	var obj = $(input);
	obj.val(obj.val().replace(/[^\d.]/g,""));//清除“数字”和“.”以外的字符   
	obj.val(obj.val().replace(/^\./g,""));//验证第一个字符是数字而不是.   
	obj.val(obj.val().replace(/\.{2,}/g,"."));//只保留第一个. 清除多余的.  
	obj.val(obj.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));
}

switchType();
function switchType(){
	var val = $("input[name='configtype']:checked").val();
	if(val=="sql"){
		$("#sqlTr").show();
		$("#dataItemField").hide();
	}else{
		$("#sqlTr").hide();
		$("#dataItemField").show();
	}
}

function save(){
	var form = $("#ajaxForm");
	if(Validator.Validate(form[0],4)){
		var val = $("input[name='configtype']:checked").val();
		if(val=="keyvalue"){
			var inputs = $("#dataItemField").find("input");
			var hasError = false;
			$.each(inputs,function(){
				var input = $(this);
				if(input.attr("type")=='text'&&input.attr("name")!="data_remark"){
					if(input.val()==""){
						input.css("border","1px solid red");
						hasError = true;
					}
				}
			});
			if(hasError){
				alert("请完整填写数据项信息！");
				return false;
			}
		}
		var options = {     
	        url:	   form.attr("action"),
	        success:   callback,
	        type:      'POST',
	        dataType:  "json",
		    clearForm: false
	    };	     
		form.ajaxSubmit(options);
	}
}
function callback(json){
	$.messager.confirm('提示', json.resultdesc+'，是否返回列表页', function(which){  
        if (which){ 	
        	window.parent.location.href='${ctx}/fsdp/staticdata/list.jsp';
        }  
    });  
}

</script>
