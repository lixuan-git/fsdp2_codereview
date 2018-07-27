
/**
 * finedo核心类库
 * 全局对象定义
 */ 
var finedo = {};
var httpjsonkey = "HTTPJSONKEY";
/**
 * 成功值定义
 */ 
finedo.resultcode = {
	success:'SUCCESS'
};
/**
 * 提供服务请求快捷方法
 */
finedo.action = {
	/**
	 * 一般性的命令操作，用于GET方式提交
	 * url:调用的路径
	 * callback:执行完成后的回调函数
	 * alertmsg:执行完成后是否弹出返回信息
	 */
	doCommand:function(url,callback,alertmsg){
		finedo.message.showWaiting();
		$.getJSON(url, function(data){
			finedo.message.hideWaiting();
			if(alertmsg){
				finedo.message.info(data.resultdesc);
			}
			if($.isFunction(callback)){
				callback(data);
			}
	
		});
	}
	
	/**
	 * 传递复杂的json对象，通过post方式传递
	 * url:调用的路径
	 * jsondata:查询参数
	 * callback:处理后的回调函数
	 */
	,doJsonRequest:function(url, jsondata,callback){
		var reqdata = {};
		reqdata[httpjsonkey] = JSON.stringify(jsondata);
		$.ajax({
	        type: "post",
	        url: url,
	        data: reqdata,
	        dataType: 'json',
	        success: function (data) {
	        	if($.isFunction(callback)){
	        		callback(data);
				}
	        },
	        error: function () {
	        	finedo.message.error("系统错误，请联系管理员！");
	        }
	    });
	}
};

/**
 * 提供的函数
 */ 
finedo.fn = {
	/**
	 * 是否为true
	 */
	isTrue:function(val){
		if(val == true || val == 'true')
    		return true;
    	return false;
	},
	/**
	 * 判断是否为图片格式
	 * 根据扩展名判断：图片格式：.png、.jpg、.gif、.bmp、.jpeg
	 */
	isPicture:function(val){
		val = val.toLowerCase();
		var pictypes = '.png,.jpg,.gif,.bmp,.jpeg';
		if(pictypes.indexOf(val) == -1)
			return false;
		return true;
	},
	/**
	 * 保存全局数据
	 * 以隐藏div保存到top窗口,定义div的id为finedodata
	 */
	saveData:function(key, value){
		$(document).data(key, value);
	},
	_saveData:function(key, value){
		try{
			window.parent.finedo.fn.saveData(key, value);
		}catch(e){}
	},
	/**
	 * 获取全局数据
	 */
	getData:function(key){
		return $(document).data(key);
	},
	_getData:function(key){
		try{
			return window.parent.finedo.fn.getData(key);
		}catch(e){}
	},
	/**
	 * 删除全局数据
	 */
	removeData:function(key){
		$(document).removeData(key);
	},
	_removeData:function(key){
		try{
			window.parent.finedo.fn.removeData(key);
		}catch(e){}
	},
	/**
	 * 绑定事件
	 */
	bindEvent:function(eventName, func){
		$(document).bind(eventName,function(event, data){
			func(data);
		});
	},
	_bindEvent:function(eventName, func){
		try{
			window.parent.finedo.fn.bindEvent(eventName, func);
		}catch(e){}
	},
	/**
	 * 取消事件绑定
	 */
	unbindEvent:function(eventName){
		$(document).unbind(eventName);
	},
	_unbindEvent:function(eventName){
		try{
			window.parent.finedo.fn.unbind(eventName);
		}catch(e){}
	},
	/**
	 * 触发事件
	 */
	triggerEvent:function(eventName, data){
		$(document).trigger(eventName, data);
	},
	_triggerEvent:function(eventName, data){
		try{
			window.parent.finedo.fn.triggerEvent(eventName, data);
		}catch(e){}
	},
	/**
	 * 获取window.location.href的参数
	 */
	getQueryString:function(name){
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) {
			return unescape(r[2]);
		}
		return undefined;
	},
	/**
	 * 根据指定的属性，判断两个json对象是否相等
	 */
	jsonEqual:function(data1, data2, codeAry){
		if(codeAry && typeof array1 === "object" &&array1.constructor === Array){
			for(var codeIndex = 0; codeIndex < codeAry.length; codeIndex++){
				if(finedo.fn.getValue(data1, codeAry[codeIndex]) != finedo.fn.getValue(data2, codeAry[codeIndex]))
					return false;
			}
		}else{
			if(JSON.stringify(data1) == JSON.stringify(data2))
				return true;
			return finedo.fn.compObj(data1, data2);
		}
		return true;
	},
	/**
	 * 比较两数组
	 */
	compArray:function(array1,array2){
		if((array1 && typeof array1 === "object" &&array1.constructor === Array) && (array2 && typeof array2 === "object" && array2.constructor === Array)) {
			if(array1.length==array2.length) {
				for(var i=0;i<array1.length;i++) {
					var ggg=finedo.fn.compObj(array1[i],array2[i]);
					if(!ggg) {
						return false;
					}
	        
				}
	       
			} else {
				return false;
			}
		} else {
			return false;
		}
	    return true;
	},
	/**
	 * 比较json对象
	 */
	compObj:function(obj1,obj2){
		if((obj1&&typeof obj1==="object")&&((obj2&&typeof obj2==="object"))) {
			var count1=finedo.fn.propertyLength(obj1);
			var count2=finedo.fn.propertyLength(obj2);
			if(count1==count2) {
				for(var ob in obj1) {
					if(obj1.hasOwnProperty(ob)&&obj2.hasOwnProperty(ob)) {
						if((finedo.fn.isNon(obj1[ob]) && finedo.fn.isNotNon(obj2[ob])) || (finedo.fn.isNon(obj2[ob]) && finedo.fn.isNotNon(obj1[ob])))
							return false;
						if(typeof obj1[ob]==="string"&&typeof obj2[ob]==="string") {//纯属性
							if(obj1[ob]!=obj2[ob]) {
								return false;
							}
						} else if(typeof obj1[ob]==="object"&&typeof obj2[ob]==="object") {//属性是对象
							if(!finedo.fn.compObj(obj1[ob],obj2[ob])) {
								return false;
							}
						} else if(obj1[ob].constructor==Array&&obj2[ob].constructor==Array) {//如果属性是数组
							if(!finedo.fn.compArray(obj1[ob],obj2[ob])) {
								return false;
							};
						} else{
							return false;
						}
					} else {
						return obj1[ob]==obj2[ob];
					}
				}
			} else {
				return false;
			}
		}
		return true;
	},
	/**
	 * json对象属性数量
	 */
	propertyLength:function(obj){//获得对象上的属性个数，不包含对象原形上的属性
		var count=0;
		if(obj&&typeof obj==="object") {
			for(var ooo in obj) {
				if(obj.hasOwnProperty(ooo)) {
					count++;
				}
			}
			return count;
		}
		return count;
	},
	/**
	 * 对象在数组中的位置
	 */
	inArray:function(data, items, codeAry){
		for(var i = 0; i < items.length; i++){
			if(finedo.fn.jsonEqual(data, items[i], codeAry))
				return i;
		}
		return -1;
	},
	/**
	 * 判断对象是否为空：''、null、undefined，如果为数组则数组长度为0，如果为对象，则对象属性长度为0
	 */
	isNon:function(arg){
		if(!arg)
			return true;
		if(typeof arg==="string") {//纯属性
			if(arg.length == 0) {
				return true;
			}
		} else if(typeof arg==="object") {//属性是对象
			if(finedo.fn.propertyLength(arg) == 0) {
				return true;
			}
		} else if(arg.constructor==Array) {//如果属性是数组
			if(arg.length == 0) {
				return true;
			};
		} else{
			return false;
		}
		return false;
	},
	/**
	 * 判断对象不为空
	 */
	isNotNon:function(arg){
		return !finedo.fn.isNon(arg);
	},
	
	/**
	 * Url字符串替换
	 */
	replaceUrl:function(url, name, value) {
		var reg = new RegExp("(^|)"+ name +"=([^&]*)(|$)"); 
		var tmp = name + "=" + value; 
		if(url.match(reg) != null) { 
			return url.replace(eval(reg),tmp); 
		} else { 
			if(url.match("[\?]")) { 
				return url + "&" + tmp; 
			} else { 
				return url + "?" + tmp; 
			} 
		}
	},
	/**
	 * 获取json对象中多层属性结构的值
	 */
	getValue:function(json, key){
		try{
			return eval('json.'+key);
		}catch(e){}
		return '';
	},
	/**
	 * 判断字符串是否为函数
	 */
	isFunction:function(func){
		try{
			return $.isFunction(eval(func));
		}catch(e){}
		return false;
	},
	/**
	 * 判断字符串如果为null，则替换成空
	 */
	replaceNull:function(str) {
		if(str == null)
			return "";
		else
			return str;
	}
};


/************** 下拉框函数定义****************/
$(function() {
    $('.select').click(function(e) {
    	$(this).attr('class', 'select-hover');
    	
    	var objid=$(this).attr('id') + "div";
    	$('#' + objid).toggle();
		
    	//阻止事件冒泡，否则事件会冒泡到下面的文档点击事件
		e.stopPropagation();
    });

    $(document).click(function() {
    	$('.select-hover').attr('class', 'select');
        $('.select-con').hide();
    });

    $('.select-con li').click(function() {
    	$('.select-hover').attr('class', 'select');
        $('.select-con').hide();
        
        var selectid=$(this).attr('value');
        var selectnameid=selectid + "_name";
    
        var code=$(this).attr('title');
        var value=$(this).text();
        
        $('#' + selectid).val(code);
        $('#' + selectnameid).html(value);
    });
});
/************** 下拉框函数定义****************/

/************** radio控件  ****************/
$(function() {
	$('label[class="radio-checked"], label[class="radio-label"]').click(function(){
		var radioid=$(this).attr('for');
		$('label[for="' + radioid + '"]').removeClass();
		$(this).attr('class', 'radio-checked');
		$('#' + radioid).val($(this).attr('title'));
	});
});
/************** radio控件  ****************/


/**************  展开与隐藏 搜索条件框 ****************/
function opquerycond(){
	var divdisplay=$('#advanced-search-con').css('display');
	
	if(divdisplay == 'block'){
		$('#advanced-search-con').css('display', 'none');
		$('#advanced-search').attr('class', 'as-link');
	
	}else{
		$('#advanced-search-con').css('display', 'block');
		$('#advanced-search').attr('class', 'as-hover');
	}
}
/**************  展开与隐藏 搜索条件框 ****************/

/**************  展开与隐藏按钮组 ****************/
$(function() {
    $('.btn-more').click(function(e) {
    	$('.selectmenu-con').toggle();
		
    	//阻止事件冒泡，否则事件会冒泡到下面的文档点击事件
		e.stopPropagation();
    });

    $(document).click(function() {
        $('.selectmenu-con').hide();
    });

    $('.selectmenu-con li').click(function() {
        $('.selectmenu-con').hide();
    });
});
/**************  展开与隐藏按钮组 ****************/
