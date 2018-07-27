/**
 * checkbox标签所用的方法
 * /
/**
 * 用于封装的checkbox tag里面自动获取选中的值，而不需要手动调用js
 * @param name
 * @return
 */
function setCheckBoxValue(name)
{
	alert(getCheckedVal(name));
	$("#"+name).val(getCheckedVal(name));
}
/**获取checkbox或radio所有选中项的value，并拼成一个字符串val1,val2,val3...
 * @author zhengwm
 * @param name checkbox或radio中name属性值
 * @return string val1,val2,val3...
 */
function getCheckedVal(name)
{
	var checkedvals = "";
	$("input[name='"+name+"']").each(function(i){
	    if($(this).attr("checked")) {
			var val = $(this).val();
			checkedvals += val+",";
	    }
	});
	
	if(checkedvals !=="")
	{
		checkedvals = checkedvals.substring(0,checkedvals.length-1);
	}
	return checkedvals;
}