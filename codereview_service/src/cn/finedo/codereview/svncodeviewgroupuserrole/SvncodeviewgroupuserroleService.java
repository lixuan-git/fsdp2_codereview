/**
 * 成员小组权限管理
 * @version 1.0
 * @since 2018-06-05
 */
package cn.finedo.codereview.svncodeviewgroupuserrole;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuserrole;
import cn.finedo.common.non.NonUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;

@Service
@Transactional
@Scope("singleton")
public class SvncodeviewgroupuserroleService {
    private static Logger logger = LogManager.getLogger();

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    
    /**
    * 查询成员小组权限。该方法为其他service复用，不为前台调用，不使用ret返回。
    * @author zhusf
    * @param @param groupuserrole
    * @param @return
    * @param @throws Exception
    * @return List<DopsSvncodeviewgroupuserrole>
    */ 	
    public List<DopsSvncodeviewgroupuserrole> querygroupuserrole(DopsSvncodeviewgroupuserrole groupuserrole) throws Exception{
        StringBuffer sql = new StringBuffer();
        sql.append("select * from tb_dops_svncodeviewgroupuserrole");
        if (NonUtil.isNon(groupuserrole)) {
            throw new RuntimeException("调用者传参异常！");
        }
        StringBuffer condsql = new StringBuffer();
        if (NonUtil.isNotNon(groupuserrole.getUsercode())) {
            condsql.append(" AND usercode=:usercode");
        }
        if (NonUtil.isNotNon(groupuserrole.getGrouprole())) {
            condsql.append(" AND grouprole=:grouprole");
        }
        if (NonUtil.isNotNon(groupuserrole.getSvnpath())) {
            condsql.append(" AND svnpath=:svnpath");
        }
        if (NonUtil.isNotNon(condsql.toString())) {
            sql.append(" WHERE 1=1 ").append(condsql);
        }
        List<DopsSvncodeviewgroupuserrole> groupuserroleList = jdbcTemplate.query(sql.toString(),groupuserrole, DopsSvncodeviewgroupuserrole.class);
        return groupuserroleList;
    }
    
    
}
