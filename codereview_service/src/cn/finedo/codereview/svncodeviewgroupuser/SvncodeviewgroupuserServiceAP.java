/**
 * 评审小组表管理服务接口
 *
 * @version 1.0
 * @since 2018-06-07
 */
package cn.finedo.codereview.svncodeviewgroupuser;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuser;
import cn.finedo.codereview.svncodeviewgroupuser.domain.SvncodeviewgroupuserQueryDomain;


@Controller
@Scope("singleton")
@RequestMapping("service/finedo/svncodeviewgroupuser")
public class SvncodeviewgroupuserServiceAP {
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private SvncodeviewgroupuserService codeviewgroupnumservice;
	
	/**
	 * 评审小组表查询
	 * @param SvncodeviewgroupQueryDomain
	 * @return ReturnValueDomain<DopsSvncodeviewgroup>对象
	 */
	@Proxy(method="querynum", inarg="SvncodeviewgroupuserQueryDomain", outarg="ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>>")
	@ResponseBody
	@RequestMapping("/querynum")
	public ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> querynum(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>>();
		SvncodeviewgroupuserQueryDomain codeviewgroupnumquerydomain = null;
		 
		try {
			codeviewgroupnumquerydomain = JsonUtil.request2Domain(request, SvncodeviewgroupuserQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据转换异常",e);
			return ret.setFail("查询评审小组成员失败");
		}
		
		return codeviewgroupnumservice.querynum(codeviewgroupnumquerydomain);
	}
	 
	
	/**
	 * 评审小组成员删除
	 * @param request
	 * @return
	 * @authro pt
	 * @date 2018年6月13日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	@Proxy(method="delete", inarg="SvncodeviewgroupuserQueryDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnValueDomain<String> delete(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncodeviewgroupuserQueryDomain codeviewgroupnumquerydomain = null;
		 
		try {
			codeviewgroupnumquerydomain = JsonUtil.request2Domain(request, SvncodeviewgroupuserQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据转换异常",e);
			return ret.setFail("删除评审小组失败");
		}
		
		return codeviewgroupnumservice.delete(codeviewgroupnumquerydomain);
	}
	
	/**
	 * 重新加入评审小组
	 * @param request
	 * @return
	 * @authro pt
	 * @date 2018年7月1日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	@Proxy(method="returnback", inarg="SvncodeviewgroupuserQueryDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/returnback")
	public ReturnValueDomain<String> returnback(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncodeviewgroupuserQueryDomain codeviewgroupnumquerydomain = null;
		 
		try {
			codeviewgroupnumquerydomain = JsonUtil.request2Domain(request, SvncodeviewgroupuserQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据转换异常",e);
			return ret.setFail("重新加入评审小组失败");
		}
		
		return codeviewgroupnumservice.returnback(codeviewgroupnumquerydomain);
	}
	
	
	
	
	
	
	/**
	 * 删除codeviewperson表中的数据根据cgid来删除
	 * @param request
	 * @return
	 * @authro pt
	 * @date 2018年6月19日
	 * @return ReturnValueDomain<String>
	 * @version V1.0
	 */
	@Proxy(method="deletecodeviewperson", inarg="SvncodeviewgroupuserQueryDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/deletecodeviewperson")
	public ReturnValueDomain<String> deletecodeviewperson(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		SvncodeviewgroupuserQueryDomain codeviewgroupnumquerydomain = null;
		 
		try {
			codeviewgroupnumquerydomain = JsonUtil.request2Domain(request, SvncodeviewgroupuserQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据转换异常",e);
			return ret.setFail("删除评审小组成员失败");
		}
		
		return codeviewgroupnumservice.deletecodeviewperson(codeviewgroupnumquerydomain);
	}
	
	
	
	
	/**
	 * 根据人员的姓名进行模糊查询
	 * @param request
	 * @return
	 * @authro pt
	 * @date 2018年6月15日
	 * @return ReturnValueDomain<PageDomain<DopsSvncodeviewgroupnum>>
	 * @version V1.0
	 */
	@Proxy(method="queryuserbyname", inarg="SvncodeviewgroupuserQueryDomain", outarg="ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>>")
	@ResponseBody
	@RequestMapping("/queryuserbyname")
	public ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> queryuserbyname(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewgroupuser>>();
		SvncodeviewgroupuserQueryDomain codeviewgroupnumquerydomain = null;
		 
		try {
			codeviewgroupnumquerydomain = JsonUtil.request2Domain(request, SvncodeviewgroupuserQueryDomain.class);
		}catch(Exception e) {
			logger.error("json数据转换异常",e);
			return ret.setFail("查询成员信息失败");
		}
		
		return codeviewgroupnumservice.queryuserbyname(codeviewgroupnumquerydomain);
	}
}
