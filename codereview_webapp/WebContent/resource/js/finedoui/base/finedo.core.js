/**
 * finedo核心类库
 * 全局对象定义
 */ 
var finedo = {};
/**
 * 成功值定义
 */ 
finedo.resultcode = {
	success:'SUCCESS'
};
/**
 * http json请求键值名
 */
finedo.httpjsonkey = "HTTPJSONKEY";
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
		finedo.message.showwaiting();
		$.getJSON(url, function(data){
			finedo.message.hidewaiting();
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
		reqdata[finedo.httpjsonkey] = JSON.stringify(jsondata);
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
	        	finedo.message.error("系统错误，请联系系统管理员！");
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
	istrue:function(val){
		if(val == true || val == 'true')
    		return true;
    	return false;
	},
	isTrue:function(val){
		return finedo.fn.istrue(val);
	},
	/**
	 * 判断是否为图片格式
	 * 根据扩展名判断：图片格式：.png、.jpg、.gif、.bmp、.jpeg
	 */
	ispicture:function(val){
		val = val.toLowerCase();
		var pictypes = '.png,.jpg,.gif,.bmp,.jpeg';
		if(pictypes.indexOf(val) == -1)
			return false;
		return true;
	},
	isPicture:function(val){
		return finedo.fn.ispicture(val);
	},
	/**
	 * 缓存dialog全局数据
	 * 用于dialog跨页面的数据传输
	 */
	setdialogdata:function(val){
		finedo.fn._triggerevent("selectdata", val);
	},
	/**
	 * 绑定事件
	 */
	bindevent:function(eventName, func){
		$(document).bind(eventName,function(event, data){
			func(data);
		});
	},
	bindEvent:function(eventName, func){
		finedo.fn.bindevent(eventName, func);
	},
	_bindevent:function(eventName, func){
		try{
			window.parent.finedo.fn.bindevent(eventName, func);
		}catch(e){}
	},
	_bindEvent:function(eventName, func){
		finedo.fn._bindevent(eventName, func);
	},
	/**
	 * 取消事件绑定
	 */
	unbindevent:function(eventName){
		$(document).unbind(eventName);
	},
	unbindEvent:function(eventName){
		finedo.fn.unbindevent(eventName);
	},
	_unbindevent:function(eventName){
		try{
			window.parent.finedo.fn.unbind(eventName);
		}catch(e){}
	},
	_unbindEvent:function(eventName){
		finedo.fn._unbindevent(eventName);
	},
	/**
	 * 触发事件
	 */
	triggerevent:function(eventName, data){
		$(document).trigger(eventName, data);
	},
	triggerEvent:function(eventName, data){
		finedo.fn.triggerevent(eventName, data);
	},
	_triggerevent:function(eventName, data){
		try{
			window.parent.finedo.fn.triggerevent(eventName, data);
		}catch(e){}
	},
	_triggerEvent:function(eventName, data){
		finedo.fn._triggerevent(eventName, data);
	},
	/**
	 * 根据指定的属性，判断两个json对象是否相等
	 */
	jsonequal:function(data1, data2, codeAry){
		if(codeAry && typeof array1 === "object" &&array1.constructor === Array){
			for(var codeIndex = 0; codeIndex < codeAry.length; codeIndex++){
				if(finedo.fn.getvalue(data1, codeAry[codeIndex]) != finedo.fn.getvalue(data2, codeAry[codeIndex]))
					return false;
			}
		}else{
			if(JSON.stringify(data1) == JSON.stringify(data2))
				return true;
			return finedo.fn.compobj(data1, data2);
		}
		return true;
	},
	jsonEqual:function(data1, data2, codeAry){
		return finedo.fn.jsonequal(data1, data2, codeAry);
	},
	/**
	 * 比较两数组
	 */
	comparray:function(array1,array2){
		if((array1 && typeof array1 === "object" &&array1.constructor === Array) && (array2 && typeof array2 === "object" && array2.constructor === Array)) {
			if(array1.length==array2.length) {
				for(var i=0;i<array1.length;i++) {
					var ggg=finedo.fn.compobj(array1[i],array2[i]);
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
	compArray:function(array1,array2){
		return finedo.fn.comparray(array1,array2);
	},
	/**
	 * 比较json对象
	 */
	compobj:function(obj1,obj2){
		if((obj1&&typeof obj1==="object")&&((obj2&&typeof obj2==="object"))) {
			var count1=finedo.fn.propertylength(obj1);
			var count2=finedo.fn.propertylength(obj2);
			if(count1==count2) {
				for(var ob in obj1) {
					if(obj1.hasOwnProperty(ob)&&obj2.hasOwnProperty(ob)) {
						if((finedo.fn.isnon(obj1[ob]) && finedo.fn.isnotnon(obj2[ob])) || (finedo.fn.isnon(obj2[ob]) && finedo.fn.isnotnon(obj1[ob])))
							return false;
						if(typeof obj1[ob]==="string"&&typeof obj2[ob]==="string") {//纯属性
							if(obj1[ob]!=obj2[ob]) {
								return false;
							}
						} else if(typeof obj1[ob]==="object"&&typeof obj2[ob]==="object") {//属性是对象
							if(!finedo.fn.compobj(obj1[ob],obj2[ob])) {
								return false;
							}
						} else if(obj1[ob].constructor==Array&&obj2[ob].constructor==Array) {//如果属性是数组
							if(!finedo.fn.comparray(obj1[ob],obj2[ob])) {
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
	compObj:function(obj1,obj2){
		return finedo.fn.compobj(obj1, obj2);
	},
	/**
	 * json对象属性数量
	 */
	propertylength:function(obj){//获得对象上的属性个数，不包含对象原形上的属性
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
	inarray:function(data, items, codeAry){
		for(var i = 0; i < items.length; i++){
			if(finedo.fn.jsonequal(data, items[i], codeAry))
				return i;
		}
		return -1;
	},
	inArray:function(data, items, codeAry){
		return finedo.fn.inarray(data, items, codeAry);
	},
	/**
	 * 判断对象是否为空：''、null、undefined，如果为数组则数组长度为0，如果为对象，则对象属性长度为0
	 */
	isnon:function(arg){
		if(!arg)
			return true;
		if(typeof arg==="string") {//纯属性
			if(arg.length == 0) {
				return true;
			}
		} else if(typeof arg==="object") {//属性是对象
			if(finedo.fn.propertylength(arg) == 0) {
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
	isNon:function(arg){
		return finedo.fn.isnon(arg);
	},
	/**
	 * 判断对象不为空
	 */
	isnotnon:function(arg){
		return !finedo.fn.isnon(arg);
	},
	isNotNon:function(arg){
		return finedo.fn.isnotnon(arg);
	},
	ctlisnon:function(ctlid){
		return $.trim($("#"+ctlid).val()) == '';
	},
	/**
	 * 获取json对象中多层属性结构的值
	 */
	getvalue:function(json, key){
		try{
			return eval('json.'+key);
		}catch(e){}
		return '';
	},
	getValue:function(json, key){
		return finedo.fn.getvalue(json, key);
	},
	/**
	 * 判断字符串是否为函数
	 */
	isfunction:function(func){
		try{
			return $.isFunction(eval(func));
		}catch(e){}
		return false;
	},
	isFunction:function(func){
		return finedo.fn.isfunction(func);
	},
	/**
	 * 判断字符串如果为null，则替换成空
	 */
	replacenull:function(str) {
		if(str == null)
			return "";
		else
			return str;
	},
	replaceNull:function(str){
		return finedo.fn.replacenull(str);
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
	replaceurl:function(url, name, value) {
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
	getBrowserAndVer:function(){
		var userAgent = navigator.userAgent, 
		rMsie = /(msie\s|trident\/7)([\w.]+)/, 
		rTrident = /(trident)\/([\w.]+)/, 
		rFirefox = /(firefox)\/([\w.]+)/, 
		rOpera = /(opera).+version\/([\w.]+)/, 
		rNewOpera = /(opr)\/(.+)/, 
		rChrome = /(chrome)\/([\w.]+)/, 
		rEdge = /(edge)\/([\w.]+)/,
		rSafari = /version\/([\w.]+).*(safari)/;
		var matchBS,matchBS2;
		var ua = userAgent.toLowerCase();
		var uaMatch = function(ua) {
			matchBS = rMsie.exec(ua);
			if (matchBS != null) {
				matchBS2 = rTrident.exec(ua);
				if (matchBS2 != null){
					switch (matchBS2[2]){
						case "4.0": return { browser : "ie", version : "8" };break;
		          		case "5.0": return { browser : "ie", version : "9" };break;
		          		case "6.0": return { browser : "ie", version : "10" };break;
		          		case "7.0": return { browser : "ie", version : "11" };break;
		          		default:return { browser : "ie", version : undefined };
					}
				}else
					return { browser : "ie", version : matchBS[2] || "0" };
			}
			matchBS = rFirefox.exec(ua);
			if ((matchBS != null)&&(!(window.attachEvent))&&(!(window.chrome))&&(!(window.opera))) {
				return { browser : matchBS[1] || "", version : matchBS[2] || "0" };
			}
			matchBS = rOpera.exec(ua);
			if ((matchBS != null)&&(!(window.attachEvent))) {
				return { browser : matchBS[1] || "", version : matchBS[2] || "0" };
			}
			matchBS = rEdge.exec(ua);
			if (matchBS != null) {
				return { browser : "edge", version : matchBS[2] || "0" };
			}
			matchBS = rChrome.exec(ua);
			if ((matchBS != null)&&(!!(window.chrome))&&(!(window.attachEvent))) {
				matchBS2 = rNewOpera.exec(ua);
				if(matchBS2 == null)
					return { browser : matchBS[1] || "", version : matchBS[2] || "0" };
				else
					return { browser : "opera", version : matchBS2[2] || "0" };
			}
			matchBS = rSafari.exec(ua);
			if ((matchBS != null)&&(!(window.attachEvent))&&(!(window.chrome))&&(!(window.opera))) {
				return { browser : matchBS[2] || "", version : matchBS[1] || "0" };
			}
			if (matchBS != null) {
				return { browser : undefined, version : undefined};
			}
		}
		var browserMatch = uaMatch(userAgent.toLowerCase());
		return browserMatch;
	}
};
//字符串格式化
String.prototype.format = function(args) {
    var result = this;
/*    console.log(arguments.length+"..."+JSON.stringify(arguments));
*/ 
    	if (arguments.length > 0) {    
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if(args[key]!=undefined){
                    var reg = new RegExp("({" + key + "})", "gm");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
                    var reg = new RegExp("({[" + i + "]})", "gm");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
};

