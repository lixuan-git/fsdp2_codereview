/**
 * SVN项目类型服务
 * @version 1.0
 * @since 2018-06-05
 */
package cn.finedo.codereview.svnpathtype;

import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.finedo.codereview.common.pojo.DopsSvnpathtype;
import cn.finedo.codereview.svnpathtype.domain.SvnpathtypeListDomain;
import cn.finedo.codereview.svnpathtype.domain.SvnpathtypeQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.fsdp.service.log.LogService;
import cn.finedo.fsdp.service.login.domain.LoginDomain;

@Service
@Transactional
@Scope("singleton")
public class SvnpathtypeService {
	private static Logger logger = LogManager.getLogger();

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private LogService logservice;
	@Autowired
	private IDUtil idutil;

	/**
	 * svn项目类型表查询
	 * 
	 * @param SvnpathtypeQueryDomain
	 * @return ReturnValueDomain<PageDomain<DopsSvnpathtype>>
	 */
	public ReturnValueDomain<PageDomain<DopsSvnpathtype>> query(SvnpathtypeQueryDomain svnpathtypequerydomain) {
		ReturnValueDomain<PageDomain<DopsSvnpathtype>> ret = new ReturnValueDomain<PageDomain<DopsSvnpathtype>>();
		StringBuffer sql = new StringBuffer("select svnpath,projecttype from tb_dops_svnpathtype");

		DopsSvnpathtype dopssvnpathtype = null;
		PageParamDomain pageparam = null;
		if (NonUtil.isNotNon(svnpathtypequerydomain)) {
			pageparam = svnpathtypequerydomain.getPageparam();
			dopssvnpathtype = svnpathtypequerydomain.getDopssvnpathtype();

			if (NonUtil.isNotNon(dopssvnpathtype)) {
				StringBuffer condsql = new StringBuffer();

				if (NonUtil.isNotNon(dopssvnpathtype.getSvnpath())) {
					condsql.append(" AND svnpath=:svnpath");
				}
				if (NonUtil.isNotNon(dopssvnpathtype.getProjecttype())) {
					condsql.append(" AND projecttype=:projecttype");
				}
				if (NonUtil.isNotNon(condsql.toString()))
					sql.append(" WHERE 1=1 ").append(condsql);
			}
		}

		PageDomain<DopsSvnpathtype> retpage = new PageDomain<DopsSvnpathtype>();
		List<DopsSvnpathtype> dopsSvnpathtypes = null;
		try {
			dopsSvnpathtypes = jdbcTemplate.query(sql.toString(), dopssvnpathtype, DopsSvnpathtype.class);
			retpage.setDatalist(dopsSvnpathtypes);
		} catch (Exception e) {
			logger.error("查询svn项目类型表失败",e);
			return ret.setFail("查询svn项目类型表失败");
		}
		return ret.setSuccess("查询svn项目类型表成功", retpage);
	}

	/**
	 * svn项目类型表新增
	 * @param SvnlogListDomain
	 * @return ReturnValueDomain<DopsSvnpathtype>
	 */
	public ReturnValueDomain<String> add(SvnpathtypeListDomain svnpathtypelistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();

		List<DopsSvnpathtype> dopssvnpathtypelist = svnpathtypelistdomain.getDopssvnpathtypelist();

		String sql = "INSERT INTO tb_dops_svnpathtype (svnpath,projecttype) values (:svnpath,:projecttype)";
		try {
			jdbcTemplate.batchUpdateNohis(sql, dopssvnpathtypelist);

		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}

		return ret.setSuccess("svn项目类型新增成功");
	}

	/**
	 * svn项目类型表修改
	 * @param SvnlogListDomain
	 * @return ReturnValueDomain<String>
	 */
	public ReturnValueDomain<String> update(SvnpathtypeListDomain svnpathtypelistdomain, LoginDomain login) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		List<DopsSvnpathtype> dopsSvnpathtypeList = svnpathtypelistdomain.getDopssvnpathtypelist();
		StringBuffer sql = new StringBuffer("update tb_dops_svnpathtype set projecttype=:projecttype where svnpath = :svnpath");
		try {
			jdbcTemplate.batchUpdateNohis(sql.toString(), dopsSvnpathtypeList);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		return ret.setSuccess("svn项目类型修改成功");
	}
}
