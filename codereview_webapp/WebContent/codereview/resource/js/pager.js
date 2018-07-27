/**
 * Class Pager
 * 
 * @author chif
 * 2011-10-03 代码精简
 */
 
 /**
	 * constructor
	 * 
	 * @param form
	 *            a form object
	 * @param recordCount
	 *            record count
	 * @param pageSize
	 *            page size
	 * @param currentPage
	 *            current page
	 * @param lang
	 *            default is zh_CN
	 */
function Pager(formname, recordCount, pageSize, currentPage, lang) {
    this.form = formname;
    this.recordCount = parseInt(recordCount);
    this.pageSize = parseInt(pageSize);
    this.currentPage = parseInt(currentPage);
    this.lang = lang || "cn" ;
}

/**
 * go to a specific page
 * 
 * @param the
 *            page
 */
Pager.prototype.gotoPage = function(page) {
	this.form.page.value = page;
	this.form.submit();
};

/**
 * pager string
 */
Pager.prototype.toString = function (){
	var pagerStrings = [];

	var pageCount = Math.ceil(this.recordCount / this.pageSize);

	var previousPage = this.currentPage - 1;
	var nextPage = parseInt(this.currentPage) + 1;
	var lastPage = pageCount;
	
	pagerStrings[pagerStrings.length] = '共<span style="color:#FF0000"> ' + this.recordCount + ' </span>条记录 ';

	pagerStrings[pagerStrings.length] = '每页分<span style="color:#FF0000"> ' + this.pageSize + ' </span>条 ';

	pagerStrings[pagerStrings.length] = '分<span style="color:#FF0000"> ' + pageCount + ' </span>页 ';

	if (this.currentPage == 1 || pageCount <= 1) {
		pagerStrings[pagerStrings.length] = '<span style="color:#CCCCCC;"> 首页 上一页 </span>';
	} else {
		pagerStrings[pagerStrings.length] = '<span style="cursor:pointer;text-decoration: underline;" onclick="pager.gotoPage(1);">首页</span> <span style="cursor:pointer;text-decoration: underline;" onclick="pager.gotoPage(' + previousPage + ');">上一页</span> ';
	}

	
	if (this.currentPage == pageCount || pageCount <= 1) {
		pagerStrings[pagerStrings.length] = '<span style="color:#CCCCCC;"> 下一页 最后一页</span>';
	} else {
		pagerStrings[pagerStrings.length] = '<span style="cursor:pointer;text-decoration: underline;" onclick="pager.gotoPage(' + nextPage + ');">下一页</span> <span style="cursor:pointer;text-decoration: underline;" onclick="pager.gotoPage(' + lastPage + ');">最后一页</span> ';
	}
	

	
	if(pageCount>1){
		pagerStrings[pagerStrings.length] = ' 第 <input style="text-align:center;border: 1px solid #CCCCCC;" name="pager.requestPage" type="text" id="reqPage" size="4" maxlength="6" value="' + this.currentPage + '" onchange="this.value = isInteger(this.value) ? this.value : 1" /> 页 ';
		pagerStrings[pagerStrings.length] = '<span style="cursor:pointer;text-decoration: underline;" onclick="pager.gotoPage($(\'#reqPage\').attr(\'value\'));" >转到</span>';
	}
	
			
	return pagerStrings.join("").toString();
};

function isInteger(value) {
	return /^[-\+]?\d+$/.test(value);
};

function showPaginIcon(form,recordCount,pageSize,currentPage){
	var pager = new Pager(form,recordCount,pageSize,currentPage);
	document.write(pager.toString());
}
