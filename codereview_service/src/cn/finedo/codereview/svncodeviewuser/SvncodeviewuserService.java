package cn.finedo.codereview.svncodeviewuser;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewuser;
import cn.finedo.codereview.svncodeviewuser.domain.SvncodeviewuserQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.fsdp.service.log.LogService;

/**      
* @Description: 代码评审高工用户表
* @author zhusf@finedo.com   
* @date 2018年5月15日 上午8:48:54   
* @version v1.0
*   
*/ 
@Service
@Transactional
@Scope("singleton")
public class SvncodeviewuserService {
	private static Logger logger = LogManager.getLogger();

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private LogService logservice;
	@Autowired
	private IDUtil idutil;

	/**
	 * svn代码评审用户表查询
	 * 
	 * @param SvncodeviewuserQueryDomain
	 * @return ReturnValueDomain<PageDomain<DopsSvncodeviewuser>>
	 */
	public ReturnValueDomain<PageDomain<DopsSvncodeviewuser>> query(
			SvncodeviewuserQueryDomain svncodeviewuserquerydomain) {
		ReturnValueDomain<PageDomain<DopsSvncodeviewuser>> ret = new ReturnValueDomain<PageDomain<DopsSvncodeviewuser>>();

		StringBuffer sql = new StringBuffer("select cuid,usercode,email from tb_dops_svncodeviewuser");

		DopsSvncodeviewuser dopssvncodeviewuser = null;
		PageParamDomain pageparam = null;
		if (NonUtil.isNotNon(svncodeviewuserquerydomain)) {
			pageparam = svncodeviewuserquerydomain.getPageparam();
			dopssvncodeviewuser = svncodeviewuserquerydomain.getDopssvncodeviewuser();

			if (NonUtil.isNotNon(dopssvncodeviewuser)) {
				StringBuffer condsql = new StringBuffer();
				if (NonUtil.isNotNon(dopssvncodeviewuser.getCuid())) {
					condsql.append(" AND cuid=:cuid");
				}
				if (NonUtil.isNotNon(dopssvncodeviewuser.getUsercode())) {
					condsql.append(" AND usercode=:usercode");
				}
				if (NonUtil.isNotNon(dopssvncodeviewuser.getEmail())) {
					condsql.append(" AND email=:email");
				}
				if (NonUtil.isNotNon(condsql.toString())){
				    sql.append(" WHERE 1=1 ").append(condsql);  
				}
					
			}
		}

		PageDomain<DopsSvncodeviewuser> retpage = new PageDomain<DopsSvncodeviewuser>();
		List<DopsSvncodeviewuser> dopsSvncodeviewusers = null;
		try {
			dopsSvncodeviewusers = jdbcTemplate.query(sql.toString(), dopssvncodeviewuser, DopsSvncodeviewuser.class);
			retpage.setDatalist(dopsSvncodeviewusers);
		} catch (Exception e) {
			logger.error("查询svn代码评审用户信息失败",e);
			return ret.setFail("查询svn代码评审用户信息失败");
		}
		return ret.setSuccess("查询svn代码评审用户信息成功", retpage);

	}
	

	
}
