$(function() {
	/* 注册radio事件 */
	finedo.commonui.registerradio();
		
	/* 注册checkbox事件 */
	finedo.commonui.registercheckbox();
	
	/* 注册下拉框事件 */
	finedo.commonui.registerselect();
	
	/* 注册开关事件 */
	finedo.commonui.registeronoff();
});

finedo.commonui = {
	/*
	 * 注册radio事件
	 */
	registerradio:function(){
		$('label[class="finedo-radio-checked"], label[class="finedo-radio-label"]').unbind('click');
		
		$('label[class="finedo-radio-checked"], label[class="finedo-radio-label"]').click(function(){
			var radioid=$(this).attr('for');
			$('label[for="' + radioid + '"]').removeClass();
			$(this).attr('class', 'finedo-radio-checked');
			
			$('#' + radioid).val($(this).attr('value'));
		});
	},
	/*
	 * 注册checkbox事件
	 */
	registercheckbox:function(){
		$('label[class="finedo-checkbox-label"], label[class="finedo-checkbox-checked"]').unbind('click');
		
		$('label[class="finedo-checkbox-label"], label[class="finedo-checkbox-checked"]').click(function(){
			if($(this).attr('class') == "finedo-checkbox-label") {
				$(this).removeClass();
				$(this).attr('class', "finedo-checkbox-checked");
			}else {
				$(this).removeClass();
				$(this).attr('class', "finedo-checkbox-label");
			}
			
			var checkboxvalues="";
			var checkboxid=$(this).attr('for');
			$('label[class="finedo-checkbox-checked"][for="' + checkboxid + '"]').each(function(){
				if(checkboxvalues.length > 0)
					checkboxvalues=checkboxvalues + ",";
				checkboxvalues=checkboxvalues + $(this).attr('value');
			});
			
			$('#' + checkboxid).val(checkboxvalues);
		});
	},
	/*
	 * 注册下拉框事件
	 */
	registerselect:function(){
		/************** 下拉框函数定义****************/
		$('.finedo-select').unbind('click');
		
		$('.finedo-select').click(function(e) {
			// 先关闭所有下拉框
			$('.finedo-select-hover').attr('class', 'finedo-select');
	        $('.finedo-select-con').hide();
	        
	    	$(this).attr('class', 'finedo-select-hover');
	    	
	    	var objid=$(this).attr('id') + "div";
	    	$('#' + objid).toggle();
			
	    	//阻止事件冒泡，否则事件会冒泡到下面的文档点击事件
			e.stopPropagation();
			
			// 如果存在class="finedo-hint-info"元素, 则显示
			var obj_hint_info=objid.split("_")[0] ;
			if($('#' + obj_hint_info).length > 0) {
				finedo.showhintinfo(obj_hint_info);
			}
	    });
		
		$(document).unbind('click');
	    $(document).click(function(e) {
	    	$('.finedo-select-hover').attr('class', 'finedo-select');
	        $('.finedo-select-con').hide();
	    });
	
	    $('.finedo-select-con li').unbind('click');
	    $('.finedo-select-con li').click(function(e) {
	    	var spanclass=$(this).children('span').attr('class');
	    	if(spanclass != 'finedo-select-multiple' && spanclass != 'finedo-select-multiple-yes') {
	    		// 单选
		    	$('.finedo-select-hover').attr('class', 'finedo-select');
		        $('.finedo-select-con').hide();
		        
		        var selectid=$(this).attr('for');
		        var selectnameid=selectid + "_name";
		    
		        var code=$(this).attr('val');
		        var value=$(this).text();
		        
		        $('#' + selectid).val(code);
		        $('#' + selectnameid).html(value);
	    	}else {
	    		// 多选标志
	    		var opflag=true;
	    		if(spanclass == "finedo-select-multiple"){
					$(this).children('span').attr("class","finedo-select-multiple-yes");
					// 选中
					opflag=true;
				}else{
					$(this).children('span').attr("class","finedo-select-multiple");
					// 去选中
					opflag=false;
				}
	    		
	    		var selectid=$(this).attr('for');
		        var selectnameid=selectid + "_name";
		    
		        var code=$(this).attr('val');
		        var value=$(this).text();
		        
		        finedo.commonui.changeselect(selectid, selectnameid, code, value, opflag);
		       		        		
	    		//  判断是否存在子节点，如果有，则选中父节点，子节点全部选中，如果去选父节点，则子节点全部去选
	    		var curspanmargin=finedo.commonui.getpxvalue($(this).children('span').css('margin-left'));
	    		
	    		// 遍历li的所有后面的同级li
	    		 $.each($(this).nextAll('li'), function() {
					var nextspanmargin=finedo.commonui.getpxvalue($(this).children('span').css('margin-left'));
					if(nextspanmargin > curspanmargin) {
						// 是子节点
						if(opflag) {
							// 选中
							$(this).children('span').attr("class","finedo-select-multiple-yes");
						}else {
							// 去选中
							$(this).children('span').attr("class","finedo-select-multiple");
						}
						
						code=$(this).attr('val');
		        		value=$(this).text();
						finedo.commonui.changeselect(selectid, selectnameid, code, value, opflag);
					}else {
						// 子节点已经结束
						return false;
					}
				});
	
	    		//阻止事件冒泡，否则事件会冒泡到下面的文档点击事件
				e.stopPropagation();
	    	}
	    });
	},
	/*
	 * 注册开关事件
	 */
	registeronoff:function(){
		$('input[class="finedo-on"], input[class="finedo-off"]').unbind('click');
		
		$('input[class="finedo-on"], input[class="finedo-off"]').click(function(){
			if($(this).attr("class") == "finedo-on") {
				$(this).removeClass();
				$(this).attr("class", "finedo-off");
				$(this).val("off");
			}else {
				$(this).removeClass();
				$(this).attr("class", "finedo-on");
				$(this).val("on");
			}
		});
	},
	/*
	 * select控件多选
	 */
	changeselect:function(selectid, selectnameid, code, value, opflag){
		// 剔重
        var codeexists=false;
        var selectcode=$('#' + selectid).val();
        var selectcodearr=selectcode.split(",");
        for(var i=0; i<selectcodearr.length; i++) {
        	if(code == selectcodearr[i]) {
        		codeexists=true;
        		break;
        	}
        }
        var selectname=$('#' + selectnameid).html();
        var selectnamearr=selectname.split(",");
       	
        if(opflag) {
        	// 选中
        	if(!codeexists) {
        		// 不存在
        		if(selectcode.length > 0) {
        			$('#' + selectid).val($('#' + selectid).val() + ',');
        			$('#' + selectnameid).html($('#' + selectnameid).html() + ",");
        		}
        		$('#' + selectid).val($('#' + selectid).val() + code);
        		$('#' + selectnameid).html($('#' + selectnameid).html() + value);
        	}else {
        		// 已存在	
        	}
        }else {
        	// 去选中
        	if(!codeexists) {
        		// 不存在
        	}else {
        		$('#' + selectid).val('');
        		for(var i=0; i<selectcodearr.length; i++) {
		        	if(code == selectcodearr[i]) {
		        		continue;
		        	}
		        	
		        	if($('#' + selectid).val().length > 0) {
		        		$('#' + selectid).val($('#' + selectid).val() + ',');
		        	}
		        	$('#' + selectid).val($('#' + selectid).val() + selectcodearr[i]);
		        }
        		
        		$('#' + selectnameid).html('');
        		for(var i=0; i<selectnamearr.length; i++) {
		        	if(value == selectnamearr[i]) {
		        		continue;
		        	}
		        	
		        	if($('#' + selectnameid).html().length > 0) {
		        		$('#' + selectnameid).html($('#' + selectnameid).html() + ",");
		        	}
		        	$('#' + selectnameid).html($('#' + selectnameid).html() + selectnamearr[i]);
		        }
        	}
        }
	},
	getpxvalue:function(pxstr){
		var len=pxstr.length;
		return pxstr.substring(0, len-2) - 0;
	}
};

/* 定义radio js组件  */
;(function($, undefined) {
	/*
	 * radio控件
	 */
	$.fn.radio = function(options){
		var $finedoradio = this;
		if(options){
			$finedoradio.data('options', options);
		}
		
		/*
		 * 设置值
		 */
		this.setvalue = function(value) {
			var id=$finedoradio.attr('id');
			$('#' + id).val(value);
			
			// 全部去选中
			$('label[for="' + id + '"]').attr("class","finedo-radio-label");
			// 选中
			$('label[for="' + id + '"][value="' + value + '"]').attr("class","finedo-radio-checked");
		};
		
		/*
		 * 获取值
		 */
		this.getvalue = function() {
			var id=$finedoradio.attr('id');
			return $('#' + id).val();
		};
		
        /**
         * 控件初始化
         */
        this.init = function(){
        	if($finedoradio.data('init') == true)
        		return $finedoradio;
        	$finedoradio.data('init', true);
        	
        	var id=$finedoradio.attr('id');
        	
        	// 重置input属性
        	$finedoradio.attr('name', id);
        	// 如下语句在IE8下不兼容
        	//$finedoradio.attr('type', 'hidden');
        	$finedoradio.css('display', 'none');
        	$finedoradio.val('');
        	
        	var rowdata=$finedoradio.data('options').data;
        	var radiovalue=$finedoradio.data('options').value;
        	if(typeof(radiovalue) == "undefined" || radiovalue == null)
        		radiovalue="";
        	
        	var radiodiv = '<div class="finedo-radio-div">';
        	for(var i=0; i<rowdata.length; i++) {
        		var code=rowdata[i].code;
        		var value=rowdata[i].value;
        		       		
        		radiodiv = radiodiv + '<label for="' + id + '" value="' + code + '" class="finedo-radio-label">' + value + '</label>&nbsp;&nbsp;';
        	}
        	radiodiv = radiodiv + '</div>';
        	
        	$finedoradio.after(radiodiv);
	        
        	// 设置radio默认值
        	$finedoradio.setvalue(radiovalue);
        	        	
	        // 注册事件
	        finedo.commonui.registerradio();
	       	
			return $finedoradio;
        };
        
		return this.init();
	};
})(jQuery);

/**
 * 定义radio控件获取函数
 */ 
finedo.getradio = finedo.getRadio = function(controlid, options){
	var radiocontrol = $('#'+controlid).radio(options);
	return radiocontrol;
};

/* 定义checkbox js组件 */
;(function($, undefined) {
	/**
	 * checkbox控件
	 */
	$.fn.checkbox = function(options){
		var $finedocheckbox = this;
		if(options){
			$finedocheckbox.data('options', options);
		}
		/*
		 * 设置值
		 */
		this.setvalue = function(value) {
			var id=$finedocheckbox.attr('id');
			$('#' + id).val(value);
			
			// 全部去选中
			$('label[for="' + id + '"]').attr("class","finedo-checkbox-label");
			
			var valuearr=value.split(",");
			for(var i=0; i<valuearr.length; i++) {
				// 选中
				$('label[for="' + id + '"][value="' + valuearr[i] + '"]').attr("class","finedo-checkbox-checked");
			}        
		};
		/*
		 * 获取值
		 */
		this.getvalue = function() {
			var id=$finedocheckbox.attr('id');
			return $('#' + id).val();
		};
        /**
         * 控件初始化
         */
        this.init = function(){
        	if($finedocheckbox.data('init') == true)
        		return $finedocheckbox;
        	$finedocheckbox.data('init', true);
        	
        	var id=$finedocheckbox.attr('id');
        	// 重置input属性
        	$finedocheckbox.attr('name', id);
        	// 如下语句在IE8下不兼容
        	//$finedocheckbox.attr('type', 'hidden');
        	$finedocheckbox.css('display', 'none');
        	$finedocheckbox.val('');
        	        	
        	var rowdata=$finedocheckbox.data('options').data;
        	var checkboxvalue=$finedocheckbox.data('options').value;
        	if(typeof(checkboxvalue) == "undefined" || checkboxvalue == null)
        		checkboxvalue="";
        	
        	var checkboxdiv='<div class="finedo-checkbox-div" >';
        	for(var i=0; i<rowdata.length; i++) {
        		var code=rowdata[i].code;
        		var value=rowdata[i].value;
        		
        		checkboxdiv=checkboxdiv + '<label class="finedo-checkbox-label" for="' + id + '" value="' + code + '">' + value + '</label>';
        	}
	        checkboxdiv = checkboxdiv + '</div>';
	        
	        $finedocheckbox.after(checkboxdiv);
	        
        	// 设置默认值
        	$finedocheckbox.setvalue(checkboxvalue);
        	
	        // 注册事件
	       	finedo.commonui.registercheckbox();
	       	
			return $finedocheckbox;
        };
        
		return this.init();
	};
})(jQuery);

/**
 * 定义checkbox控件获取函数
 */ 
finedo.getcheckbox = finedo.getCheckbox = function(controlid, options){
	var checkboxcontrol = $('#'+controlid).checkbox(options);
	return checkboxcontrol;
};

/* 定义select js组件 */
;(function($, undefined) {
	/*
	 * select控件
	 */
	$.fn.select = function(options){
		var $finedoselect = this;
		var finedoselectarray = [];
		if(options){
			$finedoselect.data('options', options);
		}
		
		/*
		 * 递归生成tree结构
		 */
		this.generatetree = function(top_code, level) {
			var id=$finedoselect.attr('id');
			var type=$finedoselect.data('options').type;
			if(typeof(type) == "undefined" || type == null)
				type="single";
			var rowdata=$finedoselect.data('options').data;
			for(var i=0; i<rowdata.length; i++) {
				var code=rowdata[i].code;
        		var value=rowdata[i].value;
				var parent=rowdata[i].parent;
				
				if(parent == top_code) {
					var marginleft=level*11;
					
					var lidiv="";
					if(type == "single") {
						// 单选
						lidiv='<li for="' + id + '" value="' + code + '" val="' + code + '"><span style=" margin-left:' + marginleft + 'px;"></span>' + value + '</li>';
					}else {
						// 多选
						lidiv='<li for="' + id + '" value="' + code + '" val="' + code + '"><span class="finedo-select-multiple" style=" margin-left:' + marginleft + 'px; "></span>' + value + '</li>';
					}
					finedodataarray.push(lidiv);
					
                  	$finedoselect.generatetree(code, level+1);
				}
			}
		};
		
		/*
		 * 设置值
		 */
		this.setvalue = function(selectedcode) {
			var id=$finedoselect.attr('id');
			var type=$finedoselect.data('options').type;
			if(typeof(type) == "undefined" || type == null)
				type="single";
			
			var selectid=id;
		    var selectnameid=selectid + "_name";
		    
		    var selectidstr=selectedcode;
		    var selectnameidstr="";
		    var rowdata=$finedoselect.data('options').data;
		    
		    if(type == 'multi') {
			    // 多选: 全部去选中
				$('li[for="' + id + '"]').children('span').attr("class","finedo-select-multiple");	
		    }
		    
		    for(var i=0; i<rowdata.length; i++) {
        		var code=rowdata[i].code;
        		var value=rowdata[i].value;
        		
	        	if(type == 'single') {
					// 单选
					if(selectedcode == code) {
						selectnameidstr=value;
				        break;
					}
				}else {
					// 多选			
					var selectedcodearr=selectedcode.split(",");
					for(var j=0; j<selectedcodearr.length; j++) {
						if(selectedcodearr[j] == code) {
							if(selectnameidstr.length > 0)
								selectnameidstr = selectnameidstr + ",";
							selectnameidstr = selectnameidstr + value;
							
							// 选中
							$('li[for="' + id + '"][val="' + code + '"]').children('span').attr("class","finedo-select-multiple-yes");
						}
					}				
				}
		    }
		    
		    $('#' + selectid).val(selectidstr);
			$('#' + selectnameid).html(selectnameidstr);
		};
		/*
		 * 获取值
		 */
		this.getvalue = function() {
			var id=$finedoselect.attr('id');
			return $('#' + id).val();
		};
        /*
         * 控件初始化
         */
        this.init = function(){
        	if($finedoselect.data('init') == true)
        		return $finedoselect;
        	$finedoselect.data('init', true);
        	
        	var id=$finedoselect.attr('id');
        	
        	// 重置input属性
        	$finedoselect.attr('name', id);
        	// 如下语句在IE8下不兼容
        	//$finedoselect.attr('type', 'hidden');
        	$finedoselect.css('display', 'none');
        	$finedoselect.val('');
        	        	
        	var type=$finedoselect.data('options').type;
        	var rowdata=$finedoselect.data('options').data;
			var selectedcode=$finedoselect.data('options').value;
			var style=$finedoselect.data('options').style;
			
			if(typeof(type) == "undefined" || type == null)
				type="single";
			if(typeof(selectedcode) == "undefined" || selectedcode == null)
				selectedcode="";
			
			var selectdiv="";
			if(typeof(style) == "undefined" || style == null) {
				selectdiv='<div class="finedo-select-list">';
			}else {
				selectdiv='<div class="finedo-select-list" style="' + style + '">';
			}
        	var selectdiv=selectdiv + 
							  '<div class="finedo-select" id="' + id + '_name"></div>' +
							  '<div class="finedo-select-con" id="' + id + '_namediv" style="display:none">' +
							  '<ul>';
			
			// 判断是否为分级显示, 是否存在"parent"属性
			var istree=false;
			for(var i=0; i<rowdata.length; i++) {
				var parent=rowdata[i].parent;
				if(typeof(parent) != "undefined" && parent != null) {
					istree=true;
					break;
				}
			}						
			
			if(istree) {
				// 递归生成tree
				finedodataarray=[];
				
				// 支持多个顶级节点
				for(var i=0; i<rowdata.length; i++) {
					var top_code=rowdata[i].code;
        			var top_value=rowdata[i].value;
					var parent=rowdata[i].parent;
					
					if(parent == "0") {
						var lidiv="";
						if(type == "single") {
							lidiv='<li for="' + id + '" value="' + top_code + '" val="' + top_code + '"><span class="finedo-select-arrow"></span>' + top_value + '</li>';
						}else {
							lidiv='<li for="' + id + '" value="' + top_code + '" val="' + top_code + '"><span class="finedo-select-multiple"></span>' + top_value + '</li>';
						}
						finedodataarray.push(lidiv);
						
						$finedoselect.generatetree(top_code, 2);
					}
				}
				for(var i=0; i<finedodataarray.length; i++) {
					selectdiv=selectdiv + finedodataarray[i];
				}
			}else {
	        	for(var i=0; i<rowdata.length; i++) {
	        		var code=rowdata[i].code;
	        		var value=rowdata[i].value;
	        		var img=rowdata[i].img;
	        		var parent=rowdata[i].parent;
	        		
	        		if(type == "single") {
		        		// 设置图标
		        		if(typeof(img) != "undefined" && img != null) {
		        			selectdiv=selectdiv + '<li for="' + id + '" value="' + code + '" val="' + code + '"><img src="' + img + '">' + value + '</li>';
		        		}else {        				
		        			selectdiv=selectdiv + '<li for="' + id + '" value="' + code + '" val="' + code + '">' + value + '</li>';
		        		}
	        		}else {
	        			selectdiv=selectdiv + '<li for="' + id + '" value="' + code + '" val="' + code + '"><span class="finedo-select-multiple"></span>' + value + '</li>';
	        		}
	        	}
			}
			
			selectdiv=selectdiv + '</ul></div></div>';
        	$finedoselect.after(selectdiv);
	        
        	// 设置默认值
        	$finedoselect.setvalue(selectedcode);
        	
	        // 注册事件
	       	finedo.commonui.registerselect();
	       	
			return $finedoselect;
        };
        
		return this.init();
	};
})(jQuery);

/**
 * 定义select控件获取函数
 */ 
finedo.getselect = finedo.getSelect = function(controlid, options){
	var selectcontrol = $('#'+controlid).select(options);
	return selectcontrol;
};




/************** 提示标签 ****************/
finedo.showhintinfo = function showhintinfo(id) {
	var spaninfoobj=$('span[class="finedo-hint-info"][for="' + id + '"]');
	var spanerrorobj=$('span[class="finedo-hint-error"][for="' + id + '"]');
	var spanrightobj=$('span[class="finedo-hint-right"][for="' + id + '"]');
	
	if(spaninfoobj.length > 0) {
		$(spaninfoobj).css('display', 'inline-block');
	}
	
	if(spanerrorobj.length > 0) {
		$(spanerrorobj).css('display', 'none');
	}
	
	if(spanrightobj.length > 0) {
		$(spanrightobj).css('display', 'none');
	}
};

finedo.showhinterror = function showhinterror(id, message) {
	var spaninfoobj=$('span[class="finedo-hint-info"][for="' + id + '"]');
	var spanerrorobj=$('span[class="finedo-hint-error"][for="' + id + '"]');
	var spanrightobj=$('span[class="finedo-hint-right"][for="' + id + '"]');
	if(spaninfoobj.length > 0) {
		$(spaninfoobj).css('display', 'none');
	}
	
	if(spanerrorobj.length > 0) {
		$(spanerrorobj).css('display', 'inline-block');
		$(spanerrorobj).html(message + '&nbsp;');
	}
	
	if(spanrightobj.length > 0) {
		$(spanrightobj).css('display', 'none');
	}
};

finedo.showhintright = function showhintright(id) {
	var spaninfoobj=$('span[class="finedo-hint-info"][for="' + id + '"]');
	var spanerrorobj=$('span[class="finedo-hint-error"][for="' + id + '"]');
	var spanrightobj=$('span[class="finedo-hint-right"][for="' + id + '"]');
	
	if(spaninfoobj.length > 0) {
		$(spaninfoobj).css('display', 'none');
	}
	
	if(spanerrorobj.length > 0) {
		$(spanerrorobj).css('display', 'none');
	}
	
	if(spanrightobj.length > 0) {
		$(spanrightobj).css('display', 'inline-block');
		$(spanrightobj).html('&nbsp;');
	}
};
/************** 提示标签 ****************/


/**
 * 数据验证
 * 	label  名称
	datatype 数据类型  email phone url date datetime time number digits 
	minlength 最小长度
	maxlength 最大长度
	required 是否必填 true/false
 */

finedo.validate = function(items, ispop){
	var isEmail = function(val){
		var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		return reg.test(val);
	};
	var isPhone = function(val){
		var reg = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|(0\d{2,3}-))?[1-9]\d{6,7}(\-\d{1,4})?$/;
		var reg1 = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
		return reg.test(val) || reg1.test(val);
	};
	var isUrl = function(val){
		var reg = /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
		return reg.test(val);
	};
	var isDate = function(val){
		var result = val.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
		if (result == null)
            return false;
		var d = new Date(result[1], result[3] - 1, result[4]);
		return (d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3] && d.getDate() == result[4]);;
	};
	var isDatetime = function(val){
		var result = val.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
		if (result == null)
            return false;
		var d = new Date(result[1], result[3] - 1, result[4], result[5], result[6], result[7]);
		return (d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3] && d.getDate() == result[4] && d.getHours() == result[5] && d.getMinutes() == result[6] && d.getSeconds() == result[7]);
	};
	var isTime = function(val){
		var result = val.match(/^(\d{1,2}):(\d{1,2}):(\d{1,2})$/);
		if (result == null)
            return false;
		return (result[1] >= 0 && result[1] < 24 && result[2] >= 0 && result[2] < 60 && result[3] >= 0 && result[3] < 60);
	};
	var isNumber = function(val){
		var reg = /^\d+$/;
		return reg.test(val);
	};
	var isDigits = function(val){
		var reg = /^[-\+]?\d+(\.\d+)?$/;
		return reg.test(val);
	};
	var isok = true;
	for(var itemid in items){
		var checkitem = items[itemid];
		var val = $.trim($('#'+itemid).val());
		//判断必填项是否为空
		if(finedo.fn.isTrue(checkitem.required) && finedo.fn.isNon(val)){
			if(ispop) {
				finedo.message.error(checkitem.label+'不能为空！');
				return false;
			}else {
				finedo.showhinterror(itemid, checkitem.label+'不能为空！');
				isok = false;
				continue;
			}
		}
		//非必填且为空，则不做验证
		if(!finedo.fn.isTrue(checkitem.required) && finedo.fn.isNon(val)){
			finedo.showhintright(itemid);
			continue;
		}
		//验证是否小于最小长度，长度度为大于0的正整数才做验证
		if(isNumber(checkitem.minlength) && checkitem.minlength > 0){
			if(val.length < checkitem.minlength){
				if(ispop) {
					finedo.message.error(checkitem.label+'【'+val+'】长度不能小于'+checkitem.minlength+'！');
					return false;
				}else {
					finedo.showhinterror(itemid, checkitem.label+'【'+val+'】长度不能小于'+checkitem.minlength+'！');
					isok = false;
					continue;
				}
			}
		}
		//验证是否大于最大长度，长度度为大于0的正整数才做验证
		if(isNumber(checkitem.maxlength) && checkitem.maxlength > 0){
			if(val.length > checkitem.maxlength){
				if(ispop) {
					finedo.message.error(checkitem.label+'【'+val+'】长度不能大于'+checkitem.maxlength+'！');
					return false;
				}else {
					finedo.showhinterror(itemid, checkitem.label+'【'+val+'】长度不能大于'+checkitem.maxlength+'！');
					isok = false;
					continue;
				}
			}
		}
		var checkitemok = true;
		//验证数据类型
		switch(checkitem.datatype){
			case "email":
				if(!isEmail(val)){
					if(ispop) {
						finedo.message.error(checkitem.label+'【'+val+'】不是有效的email地址！');
						return false;
					}else {
						finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的email地址！');
						checkitemok = false;
					}
				}
				break;
			case "phone":
				if(!isPhone(val)){
					if(ispop) {
						finedo.message.error(checkitem.label+'【'+val+'】不是有效的电话号码！');
						return false;
					}else {
						finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的电话号码！');
						checkitemok = false;
					}
				}
				break;
			case "url":
				if(!isUrl(val)){
					if(ispop) {
						finedo.message.error(checkitem.label+'【'+val+'】不是有效的网址！');
						return false;
					}else {
						finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的网址！');
						checkitemok = false;
					}
				}
				break;
			case "date":
				if(!isDate(val)){
					if(ispop) {
						finedo.message.error(checkitem.label+'【'+val+'】不是有效的日期！');
						return false;
					}else {
						finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的日期！');
						checkitemok = false;
					}
				}
				break;
			case "datetime":
				if(!isDatetime(val)){
					if(ispop) {
						finedo.message.error(checkitem.label+'【'+val+'】不是有效的日期时间！');
						return false;
					}else {
						finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的日期时间！');
						checkitemok = false;
					}
				}
				break;
			case "time":
				if(!isTime(val)){
					if(ispop) {
						finedo.message.error(checkitem.label+'【'+val+'】不是有效的时间！');
						return false;
					}else {
						finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的时间！');
						checkitemok = false;
					}
				}
				break;
			case "number": 
				if(!isNumber(val)){
					if(ispop) {
						finedo.message.error(checkitem.label+'【'+val+'】不是有效的整数！');
						return false;
					}else {
						finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的整数！');
						checkitemok = false;
					}
				}
				break;
			case "digits":
				if(!isDigits(val)){
					if(ispop) {
						finedo.message.error(checkitem.label+'【'+val+'】不是有效的数值！');
						return false;
					}else {
						finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的数值！');
						checkitemok = false;
					}
				}
				break;
			default :
				break;
		}
		
		if(ispop) {
			
		}else {
			if(checkitemok){
				finedo.showhintright(itemid);
			}else{
				isok = false;
			}
		}
	}
	if(ispop) {
		return true;
	}else {
		return isok;
	}
};

