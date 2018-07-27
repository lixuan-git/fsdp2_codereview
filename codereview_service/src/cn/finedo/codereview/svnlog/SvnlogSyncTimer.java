package cn.finedo.codereview.svnlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.codereview.svnlog.SvnlogService;

/**
 * @Description: 定时同步svn日志信息
 * @author zhusf@finedo.com
 * @date 2018年5月2日 下午3:04:08
 * @version v1.0
 * 
 */
@Component
public class SvnlogSyncTimer {

	@Autowired
	private SvnlogService svnlogService;

	/**   
	* @Title: sync   
	* @Description: 定时同步svn日志  
	* @param @return    设定文件   
	* @return ReturnValueDomain<String>    返回类型   
	* @throws     
	*/ 
	public ReturnValueDomain<String> sync() {
		return svnlogService.syncsvnlogForDate();
	}

	/**   
	* @Title: sendEmail   
	* @Description: 定时发送评审邮件  
	* @param @return    设定文件   
	* @return ReturnValueDomain<String>    返回类型   
	* @throws     
	*/ 
	public ReturnValueDomain<String> sendEmail() {
		return svnlogService.sendEmail();
	}
	
	/**   
	* @Title: sendResultEmail   
	* @Description: 定时发送走查结果 邮件
	* @param @return    设定文件   
	* @return ReturnValueDomain<String>    返回类型   
	* @throws     
	*/ 
	public ReturnValueDomain<String> sendResultEmail() {
		return svnlogService.sendResultEmail();
	}
	
	/**
	* 定时检测文件中的IP 域名 日志等规范
	* @author zhusf
	* @param @return
	* @return ReturnValueDomain<String>
	*/ 	
	public ReturnValueDomain<String> selfcheck() {
        return svnlogService.selfcheck();
    } 
}
