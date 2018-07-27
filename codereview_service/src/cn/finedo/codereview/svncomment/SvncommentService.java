/**
 * svn评审信息表管理服务
 * 
 * @version 1.0
 * @since 2017-07-29
 */
package cn.finedo.codereview.svncomment;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.finedo.codereview.common.pojo.DopsSvncomment;
import cn.finedo.codereview.svncomment.domain.SvncommentListDomain;
import cn.finedo.codereview.svncomment.domain.SvncommentQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.fsdp.service.login.domain.LoginDomain;


@Service
@Transactional
@Scope("singleton")
public class SvncommentService {
    private static Logger logger = LogManager.getLogger();

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IDUtil idutil;

    /**
     * svn评审信息表查询
     * 
     * @param SvncommentQueryDomain
     * @return ReturnValueDomain<PageDomain<DopsSvncomment>>
     */
    public ReturnValueDomain<PageDomain<DopsSvncomment>> query(SvncommentQueryDomain svncommentquerydomain) {
        ReturnValueDomain<PageDomain<DopsSvncomment>> ret = new ReturnValueDomain<PageDomain<DopsSvncomment>>();
        StringBuffer sql = new StringBuffer();
        sql.append("select s.commentid,s.projectid,s.projectname,s.repurl,s.filename,s.revision,s.rownumber,s.startrownumber,s.codecomment,s.personid,s.finishstate,s.optdate,s.remark,s.commenttype,s.submitperson,s.ismustmodify,s.iscommon,s.acceptstate,s.mousedownx,s.mousedowny,s.mouseupx,s.mouseupy,p.personname from tb_dops_svncomment s left join tb_sys_person p on s.personid = p.personid");

        DopsSvncomment dopssvncomment = null;
        PageParamDomain pageparam = null;
        if (NonUtil.isNotNon(svncommentquerydomain)) {
            pageparam = svncommentquerydomain.getPageparam();
            dopssvncomment = svncommentquerydomain.getDopssvncomment();

            if (NonUtil.isNotNon(dopssvncomment)) {
                StringBuffer condsql = new StringBuffer();

                if (NonUtil.isNotNon(dopssvncomment.getCommentid())) {
                    condsql.append(" AND s.commentid=:commentid");
                }
                if (NonUtil.isNotNon(dopssvncomment.getProjectname())) {
                    condsql.append(" AND s.projectname=:projectname");
                }
                if (NonUtil.isNotNon(dopssvncomment.getRepurl())) {
                    condsql.append(" AND s.repurl=:repurl");
                }
                if (NonUtil.isNotNon(dopssvncomment.getFilename())) {
                    condsql.append(" AND s.filename=:filename");
                }
                if (NonUtil.isNotNon(dopssvncomment.getRevision())) {
                    condsql.append(" AND s.revision=:revision");
                }
                if (NonUtil.isNotNon(dopssvncomment.getRownumber())) {
                    condsql.append(" AND s.rownumber=:rownumber");
                }
                if (NonUtil.isNotNon(dopssvncomment.getStartrownumber())) {
                    condsql.append(" AND s.startrownumber=:startrownumber");
                }
                if (NonUtil.isNotNon(dopssvncomment.getCodecomment())) {
                    condsql.append(" AND s.codecomment=:codecomment");
                }
                if (NonUtil.isNotNon(dopssvncomment.getPersonid())) {
                    condsql.append(" AND s.personid=:personid");
                }
                if (NonUtil.isNotNon(dopssvncomment.getRemark())) {
                    condsql.append(" AND s.remark=:remark");
                }
                if (NonUtil.isNotNon(dopssvncomment.getFinishstate())) {
                    condsql.append(" AND s.finishstate=:finishstate");
                }
                if (NonUtil.isNotNon(dopssvncomment.getProjectid())) {
                    condsql.append(" AND s.projectid=:projectid");
                }
                if (NonUtil.isNotNon(dopssvncomment.getCommenttype())) {
                    condsql.append(" AND s.commenttype=:commenttype");
                }
                if (NonUtil.isNotNon(dopssvncomment.getSubmitperson())) {
                    condsql.append(" AND s.submitperson=:submitperson");
                }
                if (NonUtil.isNotNon(dopssvncomment.getIsmustmodify())) {
                    condsql.append(" AND s.ismustmodify=:ismustmodify");
                }
                if (NonUtil.isNotNon(dopssvncomment.getIscommon())) {
                    condsql.append(" AND s.iscommon=:iscommon");
                }
                if (NonUtil.isNotNon(dopssvncomment.getAcceptstate())) {
                    condsql.append(" AND s.acceptstate=:acceptstate");
                }
                if (NonUtil.isNotNon(dopssvncomment.getMousedownx())) {
                    condsql.append(" AND s.mousedownx=:mousedownx");
                }
                if (NonUtil.isNotNon(dopssvncomment.getMousedowny())) {
                    condsql.append(" AND s.mousedowny=:mousedowny");
                }
                if (NonUtil.isNotNon(dopssvncomment.getMouseupx())) {
                    condsql.append(" AND s.mouseupx=:mouseupx");
                }
                if (NonUtil.isNotNon(dopssvncomment.getMouseupy())) {
                    condsql.append(" AND s.mouseupy=:mouseupy");
                }
                if (NonUtil.isNotNon(condsql.toString())) {
                    sql.append(" WHERE 1=1 ").append(condsql);
                }

            }
        }

        PageDomain<DopsSvncomment> retpage = new PageDomain<DopsSvncomment>();
        List<DopsSvncomment> dopsSvncomments = null;
        try {
            dopsSvncomments = jdbcTemplate.query(sql.toString(), dopssvncomment, DopsSvncomment.class);
            retpage.setDatalist(dopsSvncomments);
        }
        catch (Exception e) {
            logger.error("查询评审表失败！", e);
            throw new TransactionException(e);
        }
        return ret.setSuccess("查询svn评审信息成功", retpage);
    }

    /**
     * svn评审信息统计表查询
     * 
     * @param SvncommentQueryDomain
     * @return ReturnValueDomain<PageDomain<DopsSvncomment>>
     */
    public ReturnValueDomain<PageDomain<DopsSvncomment>> queryforcount(SvncommentQueryDomain svncommentquerydomain) {
        ReturnValueDomain<PageDomain<DopsSvncomment>> ret = new ReturnValueDomain<PageDomain<DopsSvncomment>>();
        StringBuffer sql = new StringBuffer();
        sql.append("select s.commentid,s.projectid,s.projectname,s.repurl,s.filename,s.revision,s.rownumber,s.startrownumber,s.codecomment,s.personid,s.finishstate,s.optdate,s.remark,s.commenttype,s.submitperson,s.ismustmodify,s.iscommon,s.acceptstate,p.personname from tb_dops_svncommentforcount s left join tb_sys_person p on s.personid = p.personid");

        DopsSvncomment dopssvncomment = null;
        PageParamDomain pageparam = null;
        if (NonUtil.isNotNon(svncommentquerydomain)) {
            pageparam = svncommentquerydomain.getPageparam();
            dopssvncomment = svncommentquerydomain.getDopssvncomment();

            if (NonUtil.isNotNon(dopssvncomment)) {
                StringBuffer condsql = new StringBuffer();

                if (NonUtil.isNotNon(dopssvncomment.getCommentid())) {
                    condsql.append(" AND s.commentid=:commentid");
                }
                if (NonUtil.isNotNon(dopssvncomment.getFilename())) {
                    condsql.append(" AND s.filename=:filename");
                }
                if (NonUtil.isNotNon(dopssvncomment.getRevision())) {
                    condsql.append(" AND s.revision=:revision");
                }
                if (NonUtil.isNotNon(dopssvncomment.getPersonid())) {
                    condsql.append(" AND s.personid=:personid");
                }
                if (NonUtil.isNotNon(condsql.toString())) {
                    sql.append(" WHERE 1=1 ").append(condsql);
                }
            }
        }
        PageDomain<DopsSvncomment> retpage = new PageDomain<DopsSvncomment>();
        List<DopsSvncomment> dopsSvncomments = null;
        try {
            dopsSvncomments = jdbcTemplate.query(sql.toString(), dopssvncomment, DopsSvncomment.class);
            retpage.setDatalist(dopsSvncomments);
        }
        catch (Exception e) {
            logger.error("查询评审统计表失败！", e);
            throw new TransactionException(e);
        }
        return ret.setSuccess("查询svn评审信息统计成功", retpage);
    }

    /**
     * svn评审信息表新增
     * 
     * @param SvnlogListDomain
     * @return ReturnValueDomain<DopsSvncomment>
     */
    public ReturnValueDomain<String> add(SvncommentListDomain svncommentlistdomain) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();

        if (NonUtil.isNon(svncommentlistdomain)) {
            return ret.setFail("无svn评审信息表");
        }
        List<DopsSvncomment> dopssvncommentlist = svncommentlistdomain.getDopssvncommentlist();
        if (NonUtil.isNon(dopssvncommentlist)) {
            return ret.setFail("无svn评审信息表");
        }
        DopsSvncomment dopssvncomment = dopssvncommentlist.get(0);
        dopssvncomment.setCommentid(idutil.getID("commentid"));
        StringBuffer sql = new StringBuffer();
        if (jdbcTemplate.isMysql()) {
            sql.append("INSERT INTO tb_dops_svncomment (commentid, projectid,projectname,repurl, filename, revision, rownumber,startrownumber, codecomment, personid, optdate,finishstate,commenttype,submitperson,ismustmodify,iscommon,remark,acceptstate,mousedownx,mousedowny,mouseupx,mouseupy) " + "VALUES (:commentid,:projectid,:projectname,:repurl, :filename, :revision, :rownumber,:startrownumber, :codecomment, :personid,now(),:finishstate,:commenttype, :submitperson,:ismustmodify,:iscommon,:remark,:acceptstate,:mousedownx,:mousedowny,:mouseupx,:mouseupy)");
        } else {
            sql.append("INSERT INTO tb_dops_svncomment (commentid, projectid,projectname,repurl, filename, revision, rownumber,startrownumber, codecomment, personid, optdate,finishstate,commenttype,submitperson,ismustmodify,iscommon,remark,acceptstate,mousedownx,mousedowny,mouseupx,mouseupy) " + "VALUES (:commentid,:projectid,:projectname,:repurl, :filename, :revision, :rownumber,:startrownumber, :codecomment, :personid,sysdate,:finishstate,:commenttype, :submitperson,:ismustmodify,:iscommon,:remark,:acceptstate,:mousedownx,:mousedowny,:mouseupx,:mouseupy)");
        }
        try {
            jdbcTemplate.batchUpdateNohis(sql.toString(), dopssvncommentlist);

        }
        catch (Exception e) {
            logger.error("更新评审表失败！", e);
            throw new TransactionException(e);
        }
        try {
            syncCommentforcountTable(dopssvncomment);
        }
        catch (Exception e) {
            logger.error("更新评审统计表失败！", e);
            return ret.setFail("更新评审统计表失败！");
        }
        return ret.setSuccess("svn评审信息新增成功");
    }

    /**
     * svn评审信息统计新增 用于覆盖率统计
     * 
     * @param SvnlogListDomain
     * @return ReturnValueDomain<DopsSvncomment>
     */
    public ReturnValueDomain<String> addcommentforcount(SvncommentListDomain svncommentlistdomain) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();

        if (NonUtil.isNon(svncommentlistdomain)) {
            return ret.setFail("无svn评审信息统计");
        }
        List<DopsSvncomment> dopssvncommentlist = svncommentlistdomain.getDopssvncommentlist();
        if (NonUtil.isNon(dopssvncommentlist)) {
            return ret.setFail("无svn评审信息统计");
        }
        DopsSvncomment dopssvncomment = dopssvncommentlist.get(0);
        dopssvncomment.setCommentid(idutil.getID("commentid"));
        StringBuffer sql = new StringBuffer();
        if (jdbcTemplate.isMysql()) {
            sql.append("INSERT INTO tb_dops_svncommentforcount (commentid, projectid,projectname,repurl, filename, revision, rownumber,startrownumber, codecomment, personid, optdate,finishstate,commenttype,submitperson,ismustmodify,iscommon,remark,acceptstate) " + "VALUES (:commentid,:projectid,:projectname,:repurl, :filename, :revision, :rownumber,:startrownumber, :codecomment, :personid,now(),:finishstate,:commenttype, :submitperson,:ismustmodify,:iscommon,:remark,:acceptstate)");
        } else {
            sql.append("INSERT INTO tb_dops_svncommentforcount (commentid, projectid,projectname,repurl, filename, revision, rownumber,startrownumber, codecomment, personid, optdate,finishstate,commenttype,submitperson,ismustmodify,iscommon,remark,acceptstate) " + "VALUES (:commentid,:projectid,:projectname,:repurl, :filename, :revision, :rownumber,:startrownumber, :codecomment, :personid,sysdate,:finishstate,:commenttype, :submitperson,:ismustmodify,:iscommon,:remark,:acceptstate)");
        }
        try {
            jdbcTemplate.batchUpdate(sql.toString(), dopssvncommentlist);

        }
        catch (Exception e) {
            logger.error("更新评审统计失败！", e);
            throw new TransactionException(e);
        }

        return ret.setSuccess("svn评审信息统计新增成功");
    }

    /**
     * svn评审信息表修改
     * 
     * @param SvnlogListDomain
     * @return ReturnValueDomain<String>
     */
    public ReturnValueDomain<String> update(SvncommentListDomain svncommentlistdomain, LoginDomain login) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        String personid = login.getSysuser().getPersonid();
        String usercode = login.getSysuser().getUsercode();
        if (NonUtil.isNon(svncommentlistdomain)) {
            return ret.setFail("无svn评审信息");
        }

        List<DopsSvncomment> dopssvncommentlist = svncommentlistdomain.getDopssvncommentlist();

        DopsSvncomment dopsSvncomment = dopssvncommentlist.get(0);
        // 查找这条评审的personid
        try {
            dopsSvncomment = queryDopsSvncommentById(dopsSvncomment);
        }
        catch (Exception e1) {
            logger.error("查询评审表失败！", e1);
            throw new TransactionException(e1);
        }
        StringBuffer sql = new StringBuffer("update tb_dops_svncomment set");
        StringBuffer condsql = new StringBuffer();

        if (NonUtil.isNotNon(dopsSvncomment.getCodecomment())) {
            if (!dopsSvncomment.getPersonid().equals(personid)) {
                return ret.setFail("只允许修改自己的评审内容！");
            }
            condsql.append(" codecomment=:codecomment,ismustmodify=:ismustmodify,iscommon=:iscommon");
        }
        if (NonUtil.isNotNon(dopsSvncomment.getFinishstate())) {
            condsql.append(" finishstate=:finishstate");
        }
        if (NonUtil.isNotNon(dopsSvncomment.getCommenttype())) {
            condsql.append(" commenttype=:commenttype");
        }
        if (NonUtil.isNotNon(dopsSvncomment.getAcceptstate())) {
            if (!dopsSvncomment.getSubmitperson().equals(usercode)) {
                return ret.setFail("只有文件的提交者可以修改此状态!");
            }
            condsql.append(" acceptstate=:acceptstate,remark=:remark");
        }
        if (NonUtil.isNotNon(condsql.toString())) {
            sql.append(condsql.toString()).append(" WHERE commentid = :commentid");
        }
        try {
            jdbcTemplate.update(sql.toString(), dopsSvncomment);

        }
        catch (Exception e) {
            logger.error("更新评审表失败！", e);
            throw new TransactionException(e);
        }

        return ret.setSuccess("svn评审修改成功");
    }

    /**
     * 删除评审
     * 
     * @param
     * @return ReturnValueDomain<String>
     */
    public ReturnValueDomain<String> delete(SvncommentListDomain svncommentListDomain, LoginDomain login) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        List<DopsSvncomment> dopsSvncommentList = svncommentListDomain.getDopssvncommentlist();
        String personid = login.getSysuser().getPersonid();
        DopsSvncomment dopsSvncomment = dopsSvncommentList.get(0);
        if (NonUtil.isNon(dopsSvncomment)) {
            return ret.setFail("评审对象为空！");
        }
        // 查找这条评审的personid
        try {
            dopsSvncomment = queryDopsSvncommentById(dopsSvncomment);
        }
        catch (Exception e1) {
            logger.error("查询评审表失败！", e1);
            throw new TransactionException(e1);
        }
        if (!dopsSvncomment.getPersonid().equals(personid)) {
            return ret.setFail("只允许删除自己的评审内容！");
        }
        String sql = "DELETE FROM tb_dops_svncomment WHERE commentid=:commentid";
        try {
            jdbcTemplate.batchUpdate(sql, dopsSvncommentList);
        }
        catch (Exception e) {
            logger.error("删除评审表失败！", e);
            throw new TransactionException(e);
        }
        return ret.setSuccess("评审删除成功");
    }

    /**
     * svn评审信息表修改
     * 
     * @param SvnlogListDomain
     * @return ReturnValueDomain<String>
     */
    public ReturnValueDomain<String> updateNoAuth(SvncommentListDomain svncommentlistdomain, LoginDomain login) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();

        if (NonUtil.isNon(svncommentlistdomain)) {
            return ret.setFail("无svn评审信息");
        }

        DopsSvncomment dopsSvncomment = svncommentlistdomain.getDopssvncommentlist().get(0);
        StringBuffer sql = new StringBuffer("update tb_dops_svncomment set");
        StringBuffer condsql = new StringBuffer();

        if (NonUtil.isNotNon(dopsSvncomment.getCodecomment())) {
            condsql.append(" codecomment=:codecomment,ismustmodify=:ismustmodify,iscommon=:iscommon");
        }
        if (NonUtil.isNotNon(dopsSvncomment.getFinishstate())) {
            condsql.append(" finishstate=:finishstate");
        }
        if (NonUtil.isNotNon(dopsSvncomment.getCommenttype())) {
            condsql.append(" commenttype=:commenttype");
        }
        if (NonUtil.isNotNon(dopsSvncomment.getAcceptstate())) {
            condsql.append(" acceptstate=:acceptstate");
        }
        if (NonUtil.isNotNon(condsql.toString())) {
            sql.append(condsql.toString()).append(" WHERE commentid = :commentid");
        }
        try {
            jdbcTemplate.update(sql.toString(), dopsSvncomment);
        }
        catch (Exception e) {
            logger.error("更新评审表失败！", e);
            throw new TransactionException(e);
        }

        return ret.setSuccess("svn评审修改成功");
    }

    /**
     * 通过评审表主键查询评审信息
     * 
     * @author zhusf
     * @param @param
     *            DopsSvncomment
     * @param @return
     * @param @throws
     *            Exception
     * @return DopsSvncomment
     */
    public DopsSvncomment queryDopsSvncommentById(DopsSvncomment dopsSvncomment)
        throws Exception {
        String sql = "select commentid,personid,submitperson,ismustmodify,iscommon from tb_dops_svncomment where commentid = :commentid";
        DopsSvncomment ds = jdbcTemplate.queryForObject(sql, dopsSvncomment, DopsSvncomment.class);
        if (NonUtil.isNotNon(dopsSvncomment.getCodecomment())) {
            ds.setCodecomment(dopsSvncomment.getCodecomment());
        }
        if (NonUtil.isNotNon(dopsSvncomment.getIsmustmodify())) {
            ds.setIsmustmodify(dopsSvncomment.getIsmustmodify());
        }
        if (NonUtil.isNotNon(dopsSvncomment.getIscommon())) {
            ds.setIscommon(dopsSvncomment.getIscommon());
        }
        if (NonUtil.isNotNon(dopsSvncomment.getFinishstate())) {
            ds.setFinishstate(dopsSvncomment.getFinishstate());
        }
        if (NonUtil.isNotNon(dopsSvncomment.getCommenttype())) {
            ds.setCommenttype(dopsSvncomment.getCommenttype());
        }
        if (NonUtil.isNotNon(dopsSvncomment.getAcceptstate())) {
            ds.setAcceptstate(dopsSvncomment.getAcceptstate());
        }
        if (NonUtil.isNotNon(dopsSvncomment.getRemark())) {
            ds.setRemark(dopsSvncomment.getRemark());
        }
        return ds;
    }

    public void syncCommentforcountTable(DopsSvncomment dopsSvncomment) throws Exception {
        String sql = "INSERT INTO tb_dops_svncommentforcount (commentid,repurl,filename,revision,personid,rownumber,startrownumber,codecomment,submitperson,commenttype,ismustmodify,iscommon,finishstate,projectname,optdate) SELECT :commentid,'0',:filename,:revision,:personid,'0','0','##$$%%',:submitperson,'0','0','0','0',:projectname,now() FROM dual WHERE not exists (select * from tb_dops_svncommentforcount where filename=:filename and revision = :revision and personid = :personid)";
        jdbcTemplate.update(sql, dopsSvncomment);
    }

}
