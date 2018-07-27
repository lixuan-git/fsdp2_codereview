/**
 * 服务调用
 * 1、服务发布在那些server上由servicecaller.xml配置
 * 2、任何容器在使用本类时，需要在容器初始化时调用init方法，否则会在第一次调用call方法时进行初始化
 * 3、通过调用refresh方法实现重新加载配置文件
 * 
 * @company finedo.cn
 * @since 2014.04
 */

package cn.finedo.codereview.login.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.protocol.HttpClientUtil;
import cn.finedo.fsdp.service.common.configure.ConfigureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class OAServiceCaller extends BaseDomain{
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LogManager.getLogger();
	
	private OAServiceCaller() {
	}
	
	public static <T> ReturnValue<T> call(String url, TypeReference<ReturnValue<T>> type) {
		ReturnValue<T> ret=null;
		
		try {
			String jsonstr=HttpClientUtil.httpPostJson(url, null);
			
			if(jsonstr == null)
				return null;
			if(jsonstr.length() == 0)
				return null;
			
			ret = JSON.parseObject(jsonstr, type);
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    	logger.error(ex.getMessage());
	    }
		
		return ret;
	}
		
	// http/get下载字符串
	public static String httpGetString(String url) {
		return HttpClientUtil.httpGetString(url, null);
	}
	
	// http/post下载字符串
	public static String httpPostString(String url) {
		return HttpClientUtil.httpPostString(url, null);
	}
	
	public static boolean checkOAAccount(String userid, String passwd) {
		OAAccountDomain account=null;
		
		String url=ConfigureUtil.getParamByName("FSDP单点登录", 	"用户名与密码单点登录URL");
    	
		//调用OA服务验证密码
		url=url + "&userid=" + userid + "&password=" + java.net.URLEncoder.encode(passwd);
		ReturnValue<OAAccountDomain> oaret = OAServiceCaller.call(url, new TypeReference<ReturnValue<OAAccountDomain>>(){});
		logger.debug(url);
		if (oaret.getRetCode().equals("0000")) {
			account = oaret.getRetVal();
			/*if(NonUtil.isNon(oaret.getList())) {
				return false;
			}else {
				if(oaret.getList().size() > 0)
					account = oaret.getList().get(0);
			}*/
			
		}
		
		if(account == null)
			return false;
		
		return true;
	}
	
	public static String getOAUserID(String tokenid) {
		OAAccountDomain account=null;
		
		String url=ConfigureUtil.getParamByName("FSDP单点登录", 	"TOKEN单点登录URL");
		
		//调用OA服务验证密码
		url= url + "&tokenid=" + tokenid;
		ReturnValue<OAAccountDomain> oaret = OAServiceCaller.call(url, new TypeReference<ReturnValue<OAAccountDomain>>(){});
		
		if (oaret.getRetCode().equals("0000")) {
			if(oaret.getList().size() > 0)
				account = oaret.getList().get(0);
		}
		
		if(account == null)
			return null;
		
		return account.getUserID();
	}
}
