/************** 数据校验显示 ****************/
finedo.showhintinfo = function showhintinfo(obj) {
	var obj_hint_info=$(obj).attr('id') + "_hint_info";
	if($('#' + obj_hint_info).length > 0) {
		$('#' + obj_hint_info).css('display', 'inline');
	}
	
	var obj_hint_error=$(obj).attr('id') + "_hint_error";
	if($('#' + obj_hint_error).length > 0) {
		$('#' + obj_hint_error).css('display', 'none');
	}
	
	var obj_hint_right=$(obj).attr('id') + "_hint_right";
	if($('#' + obj_hint_right).length > 0) {
		$('#' + obj_hint_right).css('display', 'none');
	}
};

finedo.showhinterror = function showhinterror(objid, message) {
	var obj_hint_info=objid + "_hint_info";
	if($('#' + obj_hint_info).length > 0) {
		$('#' + obj_hint_info).css('display', 'none');
	}
	
	var obj_hint_error=objid + "_hint_error";
	if($('#' + obj_hint_error).length > 0) {
		$('#' + obj_hint_error).css('display', 'inline');
		$('#' + obj_hint_error).html('<span class="hint-error"></span>' + message);
	}
	
	var obj_hint_right=objid + "_hint_right";
	if($('#' + obj_hint_right).length > 0) {
		$('#' + obj_hint_right).css('display', 'none');
	}
};

finedo.showhintright = function showhintright(objid) {
	var obj_hint_info=objid + "_hint_info";
	$("#" + obj_hint_info).css('display', 'none');
	
	var obj_hint_error=objid + "_hint_error";
	$("#" + obj_hint_error).css('display', 'none');
	
	var obj_hint_right=objid + "_hint_right";
	$("#" + obj_hint_right).css('display', 'inline');
	$("#" + obj_hint_right).html('<span class="hint-right"></span>');
};
/************** 数据校验显示 ****************/

/**
 * 数据验证
 * 	label  名称
	datatype 数据类型  email phone url date datetime time number digits 
	minlength 最小长度
	maxlength 最大长度
	required 是否必填 true/false
 */

finedo.validate = function(items){
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
			finedo.showhinterror(itemid, checkitem.label+'不能为空！');
			isok = false;
			continue;
		}
		//非必填且为空，则不做验证
		if(!finedo.fn.isTrue(checkitem.required) && finedo.fn.isNon(val)){
			finedo.showhintright(itemid);
			continue;
		}
		//验证是否小于最小长度，长度度为大于0的正整数才做验证
		if(isNumber(checkitem.minlength) && checkitem.minlength > 0){
			if(val.length < checkitem.minlength){
				finedo.showhinterror(itemid, checkitem.label+'【'+val+'】长度不能小于'+checkitem.minlength+'！');
				isok = false;
				continue;
			}
		}
		//验证是否大于最大长度，长度度为大于0的正整数才做验证
		if(isNumber(checkitem.maxlength) && checkitem.maxlength > 0){
			if(val.length > checkitem.maxlength){
				finedo.showhinterror(itemid, checkitem.label+'【'+val+'】长度不能大于'+checkitem.maxlength+'！');
				isok = false;
				continue;
			}
		}
		var checkitemok = true;
		//验证数据类型
		switch(checkitem.datatype){
			case "email":
				if(!isEmail(val)){
					finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的email地址！');
					checkitemok = false;
				}
				break;
			case "phone":
				if(!isPhone(val)){
					finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的电话号码！');
					checkitemok = false;
				}
				break;
			case "url":
				if(!isUrl(val)){
					finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的网址！');
					checkitemok = false;
				}
				break;
			case "date":
				if(!isDate(val)){
					finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的日期！');
					checkitemok = false;
				}
				break;
			case "datetime":
				if(!isDatetime(val)){
					finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的日期时间！');
					checkitemok = false;
				}
				break;
			case "time":
				if(!isTime(val)){
					finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的时间！');
					checkitemok = false;
				}
				break;
			case "number": 
				if(!isNumber(val)){
					finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的整数！');
					checkitemok = false;
				}
				break;
			case "digits":
				if(!isDigits(val)){
					finedo.showhinterror(itemid, checkitem.label+'【'+val+'】不是有效的数值！');
					checkitemok = false;
				}
				break;
			default :
				break;
		}
		if(checkitemok){
			finedo.showhintright(itemid);
		}else{
			isok = false;
		}
	}
	return isok;
};
