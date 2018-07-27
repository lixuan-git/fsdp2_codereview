/**
 * svn管理信息表管理服务
 * 
 * @version 1.0
 * @since 2017-07-29
 */
package cn.finedo.codereview.svnmng;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuser;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroupuserrole;
import cn.finedo.codereview.common.pojo.DopsProjectmember;
import cn.finedo.codereview.common.pojo.DopsSvncomment;
import cn.finedo.codereview.common.pojo.DopsSvnlog;
import cn.finedo.codereview.common.pojo.DopsSvnmng;
import cn.finedo.codereview.common.pojo.DopsSvnuser;
import cn.finedo.codereview.common.pojo.Treepojo;
import cn.finedo.codereview.entity.ReportParaEntity;
import cn.finedo.codereview.entity.SvnInfoEntity;
import cn.finedo.codereview.entity.SvnParaEntity;
import cn.finedo.codereview.login.domain.OAAccountDomain;
import cn.finedo.codereview.login.domain.OAUser;
import cn.finedo.codereview.svncodeviewgroup.SvncodeviewgroupService;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupQueryDomain;
import cn.finedo.codereview.svncodeviewgroupuserrole.SvncodeviewgroupuserroleService;
import cn.finedo.codereview.svncomment.domain.SvnProjectComment;
import cn.finedo.codereview.svnlog.SvnlogService;
import cn.finedo.codereview.svnlog.domain.SvnlogProjectDomain;
import cn.finedo.codereview.svnmng.domain.SvnmngQueryDomain;
import cn.finedo.codereview.util.CheckoutUtil;
import cn.finedo.codereview.util.RepoFileUtils;
import cn.finedo.codereview.util.StringUtil;
import cn.finedo.codereview.util.ListSortUtil;
import cn.finedo.common.convert.ConvertUtil;
import cn.finedo.common.date.DateUtil;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysCount;
import cn.finedo.common.pojo.SysUser;
import cn.finedo.common.sec.DES;
import cn.finedo.fsdp.service.common.configure.ConfigureUtil;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;


@Service
@Transactional
@Scope("singleton")
public class SvnmngService {
    private static Logger logger = LogManager.getLogger();

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IDUtil idutil;
    
    @Autowired
    private SvnlogService svnlogService;
    
    @Autowired
    private SvncodeviewgroupuserroleService groupuserroleService;
    
    @Autowired
    private SvncodeviewgroupService svncodeviewgroupService;

    // 每次前台页面查看报表，都会生成随机串保存到服务端缓存，比对校验。校验完成移除。
    private ConcurrentHashMap<String, String> checkmap = new ConcurrentHashMap<String, String>();
    
    private String basepath = ConfigureUtil.getParamByName("登录配置", "basepath");
    //private String emailcallpath = ConfigureUtil.getParamByName("登录配置", "emailcallpath");
    
    /**
     * svn管理信息表查询
     * @param SvnmngQueryDomain
     * @return ReturnValueDomain<PageDomain<DopsSvnmng>>
     */
    public ReturnValueDomain<PageDomain<DopsSvnmng>> query(SvnmngQueryDomain svnmngquerydomain) {
        ReturnValueDomain<PageDomain<DopsSvnmng>> ret = new ReturnValueDomain<PageDomain<DopsSvnmng>>();
        
        StringBuffer sql=new StringBuffer("SELECT a.svnid,a.svnname,a.svnaddr,a.mnguser,b.passwd as mngpwd,a.opuserid,a.optime,a.remark,a.optsn,a.lastsynctime,b.username as opusername FROM tb_dops_svnmng a left join tb_dops_svnuser b on a.mnguser = b.usercode ");
            
        DopsSvnmng dopssvnmng=null;
        PageParamDomain pageparam=null;
        if(NonUtil.isNotNon(svnmngquerydomain)) {
            pageparam=svnmngquerydomain.getPageparam();
            dopssvnmng=svnmngquerydomain.getDopssvnmng();
            
            if(NonUtil.isNotNon(dopssvnmng)) {
                StringBuffer condsql=new StringBuffer();
                
                if(NonUtil.isNotNon(dopssvnmng.getSvnid())) {
                    condsql.append(" AND a.svnid=:svnid");
                }
                if(NonUtil.isNotNon(dopssvnmng.getSvnname())) {
                    condsql.append(" AND a.svnname=:svnname");
                }
                if(NonUtil.isNotNon(dopssvnmng.getSvnaddr())) {
                    condsql.append(" AND a.svnaddr=:svnaddr");
                }
                if(NonUtil.isNotNon(dopssvnmng.getMnguser())) {
                    condsql.append(" AND a.mnguser=:mnguser");
                }
                if(NonUtil.isNotNon(dopssvnmng.getOpuserid())) {
                    condsql.append(" AND a.opuserid=:opuserid");
                }
                if(NonUtil.isNotNon(dopssvnmng.getOptimebegin())) {
                    condsql.append(" AND a.optime >= " + ConvertUtil.stringToDateByMysql(":optimebegin"));
                }
                if(NonUtil.isNotNon(dopssvnmng.getOptimeend())) {
                    condsql.append(" AND a.optime <= " + ConvertUtil.stringToDateByMysql(":optimeend"));
                }
                if(NonUtil.isNotNon(dopssvnmng.getRemark())) {
                    condsql.append(" AND a.remark=:remark");
                }
                if(NonUtil.isNotNon(dopssvnmng.getOptsn())) {
                    condsql.append(" AND a.optsn=:optsn");
                }
                if(NonUtil.isNotNon(dopssvnmng.getLastsynctimebegin())) {
                    condsql.append(" AND a.lastsynctime >= " + ConvertUtil.stringToDateByMysql(":lastsynctimebegin"));
                }
                if(NonUtil.isNotNon(dopssvnmng.getLastsynctimeend())) {
                    condsql.append(" AND a.lastsynctime <= " + ConvertUtil.stringToDateByMysql(":lastsynctimeend"));
                }
                
                if(NonUtil.isNotNon(condsql.toString())){
                    sql.append(" WHERE 1=1 ").append(condsql); 
                }
                    
            }
        }
                
        PageDomain<DopsSvnmng> retpage=null;
        try {
            retpage =  jdbcTemplate.queryForPage(sql.toString(), dopssvnmng, DopsSvnmng.class, pageparam);
        }catch (Exception e) {
            logger.error("查询svn配置信息失败！",e);
            return ret.setFail("查询svn配置信息失败！");
        }
        return ret.setSuccess("查询svn配置信息成功", retpage);
    }
    
    /**
    * 查询树状svn配置信息表
    * @author zhusf
    * @param @param svnmngquerydomain
    * @param @return
    * @return ReturnValueDomain<PageDomain<DopsSvnmng>>
    */ 	
    public ReturnValueDomain<PageDomain<DopsSvnmng>> queryForTree(SvnmngQueryDomain svnmngquerydomain) {
        ReturnValueDomain<PageDomain<DopsSvnmng>> ret = new ReturnValueDomain<PageDomain<DopsSvnmng>>();
        StringBuffer sql = new StringBuffer("select distinct(t.svnpath),g.* from tb_dops_svnmng g left join tb_dops_svnuserright t on g.svnid = t.svnid");
        DopsSvnmng dopssvnmng = null;
        PageParamDomain pageparam = null;
        if (NonUtil.isNotNon(svnmngquerydomain)) {
            pageparam = svnmngquerydomain.getPageparam();
            dopssvnmng = svnmngquerydomain.getDopssvnmng();
            if (NonUtil.isNotNon(dopssvnmng)) {
                StringBuffer condsql = new StringBuffer();
                if (NonUtil.isNotNon(dopssvnmng.getSvnpath())) {
                    condsql.append(" AND t.svnpath=:svnpath");
                }
                if (NonUtil.isNotNon(condsql.toString())){
                    sql.append(" WHERE 1=1 ").append(condsql);
                }
            }
        }
        PageDomain<DopsSvnmng> retpage = null;
        try {
            retpage = jdbcTemplate.queryForPage(sql.toString(), dopssvnmng, DopsSvnmng.class, pageparam);
        }
        catch (Exception e) {
            logger.error("查询svn配置信息失败！", e);
            return ret.setFail("查询svn配置信息失败！");
        }
        return ret.setSuccess("查询svn配置信息成功", retpage);
    }
    
    /**
     * 添加svn用户
     * 
     * @author zhusf
     * @param @param
     *            account
     * @param @return
     * @return ReturnValueDomain<String>
     */
    public ReturnValueDomain<String> addsvnuser(OAAccountDomain account) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();

        DopsSvnuser user = new DopsSvnuser();
        user.setUsercode(account.getUserID());

        SysCount count;
        try {
            count = jdbcTemplate.queryForObject("select count(1) as count from tb_dops_svnuser where usercode = :usercode", user, SysCount.class);
        }
        catch (DataAccessException e2) {
            logger.error("查询用户表异常！", e2);
            return ret.setFail("查询用户表异常！");
        }
        if (count.getCount() > 0) {
            return ret.setFail("用户已存在");
        }
        user.setUserid(account.getUserID());
        user.setUsername(account.getUserName());
        try {
            user.setPasswd(DES.encryptString(account.getUserID()));
        }
        catch (Exception e1) {
            logger.error("加密异常！SVN用户增异常！", e1);
            return ret.setFail("加密异常！SVN用户增异常");
        }
        user.setOpuserid("superuser");
        user.setOptsn(idutil.getOptsn());
        StringBuffer sql = new StringBuffer();
        try {
            if (jdbcTemplate.isMysql()) {
                sql.append("insert into tb_dops_svnuser(userid, usercode, passwd, lastmdtime, opuserid, optime, remark, username, optsn) values(:userid, :usercode, :passwd, now(), :opuserid, now(), :remark, :username, :optsn)");
            } else {
                sql.append("insert into tb_dops_svnuser(userid, usercode, passwd, lastmdtime, opuserid, optime, remark, username, optsn) values(:userid, :usercode, :passwd, sysdate, :opuserid, sysdate, :remark, :username, :optsn)");
            }
            jdbcTemplate.update(sql.toString(), user);
        }
        catch (Exception e) {
            logger.error("SVN用户增异常", e);
            return ret.setFail("SVN用户增异常");
        }
        return ret.setSuccess("SVN用户增加成功");
    }

    /**
     * 同步oa用户
     * 
     * @author zhusf
     * @param @param
     *            oauserlist
     * @param @return
     * @return ReturnValueDomain<String>
     */
    public ReturnValueDomain<String> syncOAUser(List<OAUser> oauserlist) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        try {
            String optsn = idutil.getOptsn();
            for (OAUser oauser : oauserlist) {
                DopsSvnuser user = new DopsSvnuser();
                user.setUsercode(oauser.getUserid());
                SysCount count = jdbcTemplate.queryForObject("select count(1) as count from tb_dops_svnuser where usercode = :usercode", user, SysCount.class);
                if (count.getCount() > 0) {
                    continue;
                }
                user.setUserid(oauser.getUserid());
                user.setUsername(oauser.getUsername());
                user.setPasswd(DES.encryptString(oauser.getUserid()));
                user.setOpuserid("superuser");
                user.setOptsn(optsn);
                StringBuffer sql = new StringBuffer();
                if (jdbcTemplate.isMysql()) {
                    sql.append("insert into tb_dops_svnuser(userid, usercode, passwd, lastmdtime, opuserid, optime, remark, username, optsn) values(:userid, :usercode, :passwd, now(), :opuserid, now(), :remark, :username, :optsn)");
                } else {
                    sql.append("insert into tb_dops_svnuser(userid, usercode, passwd, lastmdtime, opuserid, optime, remark, username, optsn) values(:userid, :usercode, :passwd, sysdate, :opuserid, sysdate, :remark, :username, :optsn)");
                }
                jdbcTemplate.update(sql.toString(), user);
            }
        }
        catch (Exception e) {
            logger.error("同步OA用户异常", e);
            return ret.setFail("同步OA用户异常");
        }
        return ret.setSuccess("同步OA用户成功");
    }
    
    public ReturnValueDomain<List<Treepojo>> gettreenoderights(List<Treepojo> treelist){
        ReturnValueDomain<List<Treepojo>> ret = new ReturnValueDomain<List<Treepojo>>();
        try{
            String sql = "select count(*) as count from tb_dops_svnuserright where svnid = :hisid and svnpath=:optsn";
            for(Treepojo treenode : treelist){
                SysCount count = jdbcTemplate.queryForObject(sql, treenode, SysCount.class);
                treenode.setHisid(count.getCount()+"");
            }
            ret.setObject(treelist);
        }catch(Exception e){
            logger.error("获取SVN权限数量异常", e);
            return ret.setFail("获取SVN权限数量异常");
        }
        return ret.setSuccess("获取SVN权限数量成功");
    }
    
    /**
     * 查询文件内容和相关信息
     * 
     * @author zhusf
     * @param @param
     *            svnParaEntity
     * @param @return
     * @return ReturnValueDomain<DopsSvncomment>
     */
    public ReturnValueDomain<DopsSvncomment> queryText(SvnParaEntity svnParaEntity) {
        ReturnValueDomain<DopsSvncomment> ret = new ReturnValueDomain<DopsSvncomment>();
        if (NonUtil.isNon(svnParaEntity)) {
            return ret.setFail("前台传参异常！");
        }
        //"default"标识当通过目录树打开评审页面，此时未选择文件。
        if("default".equals(svnParaEntity.getSvnlogid())){
            return ret.setFail("default");
        }
        String sql = "select * from tb_dops_svnlog where svnlogid = :svnlogid";
        DopsSvnlog dopsSvnlog = null;
        try {
            dopsSvnlog = jdbcTemplate.queryForObject(sql.toString(), svnParaEntity, DopsSvnlog.class);
        }
        catch (Exception e) {
            logger.error("查询日志失败！", e);
            return ret.setFail("查询日志失败！");
        }
        DopsSvncomment dopsSvncomment = new DopsSvncomment();
        String svnaddr = dopsSvnlog.getSvnaddr();
        String filename = dopsSvnlog.getFilename();
        // 查询仓库的拥有者usercode
        String sql1 = "SELECT b.usercode mnguser,b.passwd mngpwd from tb_dops_svnuser b where b.usercode in (select a.mnguser from tb_dops_svnmng a where a.svnaddr=:svnaddr)";
        DopsSvnmng dopsSvnmng = null;
        try {
            dopsSvnmng = jdbcTemplate.queryForObject(sql1.toString(), dopsSvnlog, DopsSvnmng.class);
        }
        catch (Exception e) {
            logger.error("查询svn管理表失败！", e);
            return ret.setFail("查询svn管理表失败！");
        }
        String usercode = dopsSvnmng.getMnguser();
        svnParaEntity.setUsercode(usercode);
        String realpwd = getSvnpwdByUsercode(svnParaEntity);
        long revision = Long.parseLong(dopsSvnlog.getRevision());
        
        String codecontent = "";
        if (isOfficeFile(filename)) {
            //如果是office文件，读取html内容
            try {
                //String targetPath = basepath.concat(filename.substring(0,filename.lastIndexOf(".")).concat(".html"));
                String dir = basepath.concat(filename.substring(0,filename.lastIndexOf("/")+1).concat(dopsSvnlog.getRevision()));
                String prefix = filename.substring(filename.lastIndexOf("/")+1, filename.lastIndexOf("."));
                String targetPath = dir.concat("/").concat(prefix).concat(".html");
                logger.debug("--------------targetPath:"+targetPath);
                codecontent = RepoFileUtils.readFile(targetPath);
            }
            catch (IOException e) {
                logger.error("读取文件失败！", e);
                return ret.setFail("读取文件失败！");
            }
        }else{
            codecontent = readSvnFile(usercode, realpwd, svnaddr, filename, revision);
        }
        
        dopsSvncomment.setCodecontent(codecontent);
        dopsSvncomment.setFilename(filename);
        dopsSvncomment.setRevision(dopsSvnlog.getRevision());
        dopsSvncomment.setSubmitperson(dopsSvnlog.getSubmitperson());
        dopsSvncomment.setRepurl(dopsSvnlog.getSvnaddr());
        dopsSvncomment.setProjectname(dopsSvnlog.getSvnpath());
        dopsSvncomment.setLastchangeddate(dopsSvnlog.getLastchangeddate());
        SysUser sysUser = new SysUser();
        sysUser.setUsercode(dopsSvnlog.getSubmitperson());
        String personname = queryPersonnameByUsercode(sysUser);
        dopsSvncomment.setPersonname(personname);
        return ret.setSuccess("查询成功", dopsSvncomment);
    }
    
    
    /**
    * 生成查询评审页的参数svnlogid。首先在api中读，如果本地库已同步，返回svnlogid，如果本地未同步，进行同步，返回svnlogid。
    * @author zhusf
    * @param @param svnParaEntity
    * @param @return
    * @return ReturnValueDomain<DopsSvncomment>
    */ 	
    public ReturnValueDomain<DopsSvncomment> queryTextForTree(SvnParaEntity svnParaEntity) {
        ReturnValueDomain<DopsSvncomment> ret = new ReturnValueDomain<DopsSvncomment>();
        String filename = svnParaEntity.getFilename();
        if (!isTreeFile(filename) && (!isOfficeFile(filename))) {
            return ret.setFail("文件格式不支持！");
        }

        // 通过文件名和项目地址获取最新svn日志，转化成表的日志格式(检出文件)
        DopsSvnlog dopsSvnlog;
        try {
            dopsSvnlog = querySvnlogByFilenameAndSvnpath(svnParaEntity);
        }
        catch (Exception e1) {
            logger.error("查询svn管理表失败！", e1);
            return ret.setFail("查询svn管理表失败！");
        }
        
        //如果是office格式，格式转换
        if (isOfficeFile(filename)) {
            String filenamedir = filename.substring(0, filename.lastIndexOf("/")+1);
            String workPath = StringUtil.concatTwoPath(basepath, filenamedir).concat(dopsSvnlog.getRevision());
          List<String> briefFilenameList = RepoFileUtils.getFiles(workPath);
          for (String string : briefFilenameList) {
              if(string.contains(filename.substring(filename.lastIndexOf("/")+1))){
                  String targetPath = basepath.concat(filename.substring(0,filename.lastIndexOf("/")+1)).concat(dopsSvnlog.getRevision());
                  String sourcePath = basepath.concat(filenamedir).concat(dopsSvnlog.getRevision()).concat(filename.substring(filename.lastIndexOf("/")));
//                  String sourcePath = filename;
//                  String targetPath = htmlfilepath;
                  logger.debug("----sourcePath:",sourcePath);
                  logger.debug("----targetPath:",targetPath);
                  RepoFileUtils.officeToHtml(sourcePath, targetPath);
                  break;
              }
          }
        }
        
        // 先查询，不存在插入到数据库。返回日志结果集。方法为同步方法
        List<DopsSvnlog> dopsSvnlogList = new ArrayList<DopsSvnlog>();
        dopsSvnlogList.add(dopsSvnlog);
        try {
            dopsSvnlogList = svnlogService.queryAndInsertSvnlogs(dopsSvnlogList,"tree");
        }
        catch (Exception e) {
            logger.error("生成日志表数据失败！", e);
            return ret.setFail("生成日志表数据失败！");
        }
        //
        DopsSvncomment dopsSvncomment = new DopsSvncomment();
        // dopsSvnlogList为空时，上述方法已抛异常，此处不做空判断
        dopsSvncomment.setSvnlogid(dopsSvnlogList.get(0).getSvnlogid());
        return ret.setSuccess("查询日志表文件成功", dopsSvncomment);
    }

    /**
    * 通过文件名和项目地址 生成svn日志结构的数据
    * @author zhusf
    * @param @param svnParaEntity
    * @param @return
    * @param @throws Exception
    * @return DopsSvnlog
    */ 	
    public DopsSvnlog querySvnlogByFilenameAndSvnpath(SvnParaEntity svnParaEntity) throws Exception {
        String filename = svnParaEntity.getFilename();
        String svnpath = svnParaEntity.getSvnpath();
        String sql = "SELECT b.usercode mnguser,b.passwd mngpwd,a.svnaddr from tb_dops_svnuser b left join tb_dops_svnmng a on b.usercode = a.mnguser where a.svnid= :svnid";
        DopsSvnmng dopsSvnmng = null;
        try {
            dopsSvnmng = jdbcTemplate.queryForObject(sql.toString(), svnParaEntity, DopsSvnmng.class);
        }
        catch (Exception e) {
            logger.error("查询svn管理表失败！", e);
            throw new RuntimeException("查询svn管理表失败！");
        }
        String usercode = dopsSvnmng.getMnguser();
        svnParaEntity.setUsercode(usercode);
        String realpwd = getSvnpwdByUsercode(svnParaEntity);
        String svnaddr = dopsSvnmng.getSvnaddr();
 
        //查询文件的svn日志信息
        List<SVNLogEntry> sVNLogEntryList = viewSvnLogsBySj(usercode, realpwd, svnaddr, filename);
        if(sVNLogEntryList.size() < 1){
            throw new RuntimeException("viewSvnLogsBySj方法异常！");
        }
        //文件的最新版本日志信息
        SVNLogEntry sVNLogEntryLatest = sVNLogEntryList.get(sVNLogEntryList.size()-1);
        //转换成本地结构的svn日志信息
        DopsSvnlog ds = new DopsSvnlog();
        String svnlogid = idutil.getID("svnlogid");
        ds.setSvnlogid(svnlogid);
        ds.setSvnaddr(svnaddr);
        ds.setSvnpath(svnpath);
        ds.setFilename(filename);
        ds.setRevision(String.valueOf(sVNLogEntryLatest.getRevision()));
        ds.setSubmitperson(sVNLogEntryLatest.getAuthor());
        ds.setLastchangeddate(DateUtil.format(sVNLogEntryLatest.getDate(), "yyyy-MM-dd HH:mm:ss"));
        ds.setMessage(sVNLogEntryLatest.getMessage());
        //如果是office文件 需要检出
        if (isOfficeFile(filename)) {
            try {
                String fullpath = StringUtil.concatTwoPath(svnaddr, filename);
                logger.debug("fullpath:",fullpath);
                String dirpath = fullpath.substring(0,fullpath.lastIndexOf("/"));
                logger.debug("dirpath:",dirpath);
                String filenamedir = filename.substring(0, filename.lastIndexOf("/"));
                logger.debug("filenamedir:",filenamedir);
                String workPath = StringUtil.concatTwoPath(basepath, filenamedir);
                logger.debug("workPath:",workPath);
                CheckoutUtil.checkout(dirpath, basepath,filenamedir, usercode, realpwd, filename,ds.getRevision());
            }
            catch (SVNException e) {
                logger.error("检出文件失败！",e);
                throw new RuntimeException(e);
            }
        }
        //
        return ds;
    }
    

    /**
     * 读取文件内容
     * 
     * @author zhusf
     * @param @param
     *            username
     * @param @param
     *            password
     * @param @param
     *            repositoryUrl
     * @param @param
     *            filename
     * @param @param
     *            reversion
     * @param @return
     * @return String
     */
    public String readSvnFile(String username, String password, String repositoryUrl, String filename, long reversion) {
        SVNRepository repository = createAndGetRepository(repositoryUrl, username, password);
        String filecontent = "";
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            SVNProperties svnProperties = new SVNProperties();
            repository.getFile(filename, reversion, svnProperties, byteArrayOutputStream);
            filecontent = byteArrayOutputStream.toString();
            byteArrayOutputStream.close();
        }
        catch (Exception e) {
            logger.error("读取文件异常:{}", filename, e);
        }
        finally {
            repository.closeSession();
        }
        return filecontent;
    }

    /**
     * 创建和获取svn仓库信息
     * 
     * @author zhusf
     * @param @param
     *            repositoryUrl
     * @param @param
     *            username
     * @param @param
     *            password
     * @param @return
     * @return SVNRepository
     * @throws SVNException 
     */
    private static SVNRepository createAndGetRepository(String repositoryUrl, String username, String password) {
        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(repositoryUrl));
        }
        catch (SVNException e) {
            logger.error("获取svn仓库失败！");
            throw new RuntimeException(e);
        }
        // 身份验证
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
        repository.setAuthenticationManager(authManager);
        return repository;
    }

    /**
     * 查询svn日志项目信息
     * 
     * @author zhusf
     * @param @param
     *            svnParaEntity
     * @param @return
     * @return ReturnValueDomain<List<SvnlogProjectDomain>>
     */
    public ReturnValueDomain<List<SvnlogProjectDomain>> querySvnlog(SvnParaEntity svnParaEntity) {
        ReturnValueDomain<List<SvnlogProjectDomain>> ret = new ReturnValueDomain<List<SvnlogProjectDomain>>();
        if (NonUtil.isNon(svnParaEntity)) {
            return ret.setFail("前台传参异常！");
        }
        if (NonUtil.isNon(svnParaEntity.getStart()) || NonUtil.isNon(svnParaEntity.getEnd())) {
            return ret.setFail("请输入日期！");
        }
        if (Integer.parseInt(svnParaEntity.getStart().replace("-", "")) > Integer.parseInt(svnParaEntity.getEnd().replace("-", ""))) {
            return ret.setFail("开始时间不能大于结束时间！");
        }
        List<DopsSvnlog> dopsSvnlogList = new ArrayList<DopsSvnlog>();
        // 校验查看svn项目的范围
        List<SvnInfoEntity> svnInfoEntityList = getSvninfoByUsercode(svnParaEntity);
        // 模糊查询项目
        String searchcontent = svnParaEntity.getSvnaddr().trim();
        if (NonUtil.isNotNon(searchcontent)) {
            // 查询参数设置
            SvnInfoEntity svnInfoEntity = new SvnInfoEntity();
            svnInfoEntity.setStart(svnParaEntity.getStart());
            String end = svnParaEntity.getEnd();
            svnInfoEntity.setEnd((end + " 23:59:59"));
            svnInfoEntity.setSvnpath(searchcontent);
            // 在权限范围内查找
            boolean flag = false;
            for (SvnInfoEntity item : svnInfoEntityList) {
                String svnpath = item.getSvnpath();
                // 匹配到项目名
                if (svnpath.indexOf(searchcontent) != -1) {
                    flag = true;
                    continue;
                }
            }
            if (flag) {
                // 查询项目
                dopsSvnlogList = querySvnlogByDateAndFuzzysvninfo(svnInfoEntity);
            }
        } else {
            // 搜索条件为所有
            for (SvnInfoEntity svnInfoEntity : svnInfoEntityList) {
                // 参数设置
                svnInfoEntity.setStart(svnParaEntity.getStart());
                String end = svnParaEntity.getEnd();
                svnInfoEntity.setEnd((end + " 23:59:59"));
                // 在权限范围内查询所有
                DopsSvnlog dopsSvnlog = querySvnlogByDateAndSvninfo(svnInfoEntity);
                if (NonUtil.isNotNon(dopsSvnlog)) {
                    dopsSvnlogList.add(dopsSvnlog);
                }
            }
        }

        logger.debug("查询数目：{}", dopsSvnlogList.size());
        //
        if (NonUtil.isNon(dopsSvnlogList)) {
            return ret.setFail("未查到代码更新信息！");
        }
        //编辑项目权限查询
        DopsProjectmember person = new DopsProjectmember();
        person.setUsercode(svnParaEntity.getUsercode());
        List<DopsSvncodeviewgroup> groups = new ArrayList<DopsSvncodeviewgroup>();
        try {
             groups = svncodeviewgroupService.querygroupbyusercode(person);
        }
        catch (Exception e) {
            logger.error("查询项目成员表失败！",e);
            return ret.setFail("查询项目成员表失败！");
        }
        List<SvnlogProjectDomain> svnlogProjectDomainList = new ArrayList<SvnlogProjectDomain>();
        for (DopsSvnlog dopsSvnlog : dopsSvnlogList) {
            String svnaddr = dopsSvnlog.getSvnaddr();
            // 仓库地址处理
            svnaddr = svnaddrHandle(svnaddr);
            SvnlogProjectDomain svnlogProjectDomain = new SvnlogProjectDomain();
            svnlogProjectDomain.setSvnpath(svnaddr.concat(dopsSvnlog.getSvnpath()));
            svnlogProjectDomain.setUnsolvedcount(dopsSvnlog.getUnsolvedcount());
            svnlogProjectDomain.setFilesize(dopsSvnlog.getFilesize());
            //添加是否有项目的编辑权限
            for (DopsSvncodeviewgroup dopsSvncodeviewgroup : groups) {
                if(dopsSvncodeviewgroup.getSvnpath().equals(dopsSvnlog.getSvnpath())){
                    svnlogProjectDomain.setIsmanager(true);
                    svnlogProjectDomain.setCgid(dopsSvncodeviewgroup.getCgid());
                    break;
                }
            }
            svnlogProjectDomainList.add(svnlogProjectDomain);
        }
        return ret.setSuccess("查询成功！", svnlogProjectDomainList);
    }

    /**
     * 根据日期和项目全路径，初步查询日志信息
     * 
     * @author zhusf
     * @param @param
     *            svnInfoEntity
     * @param @return
     * @return DopsSvnlog
     */
    public DopsSvnlog querySvnlogByDateAndSvninfo(SvnInfoEntity svnInfoEntity) {
        StringBuffer sql = new StringBuffer();
        if (jdbcTemplate.isMysql()) {
            sql.append("select distinct g.svnpath,g.svnaddr,e.projecttype from tb_dops_svnlog g left join tb_dops_svnpathtype e on g.svnpath = e.svnpath where g.svnaddr = :svnaddr and g.svnpath = :svnpath and g.lastchangeddate between :start and :end");
        } else {
            sql.append("select distinct g.svnpath,g.svnaddr,e.projecttype from tb_dops_svnlog g left join tb_dops_svnpathtype e on g.svnpath = e.svnpath where g.svnaddr = :svnaddr and g.svnpath = :svnpath and to_char(g.lastchangeddate,'yyyy-mm-dd hh24:mi:ss') between :start and :end");
        }

        List<DopsSvnlog> dopsSvnlogListTmp = null;
        try {
            dopsSvnlogListTmp = jdbcTemplate.query(sql.toString(), svnInfoEntity, DopsSvnlog.class);
        }
        catch (Exception e) {
            logger.error("通过日期和项目地址查询异常！",e);
            throw new RuntimeException("通过日期和项目地址查询异常！", e);
        }
        if (NonUtil.isNotNon(dopsSvnlogListTmp)) {
            DopsSvnlog svnlogLast = dopsSvnlogListTmp.get(0);
            DopsSvnlog svnlog = null;
            // 查询项目这段时间内的未解决数
            StringBuffer sql1 = new StringBuffer();
            if (jdbcTemplate.isMysql()) {
                sql1.append("select count(*) as unsolvedcount from tb_dops_svncomment t left join tb_dops_svnlog g on (t.filename = g.filename and t.revision = g.revision) where t.projectname = :svnpath and t.finishstate = '0' and g.lastchangeddate between :start and :end");
            } else {
                sql1.append("select count(*) as unsolvedcount from tb_dops_svncomment t left join tb_dops_svnlog g on (t.filename = g.filename and t.revision = g.revision) where t.projectname = :svnpath and t.finishstate = '0' and to_char(g.lastchangeddate,'yyyy-mm-dd hh24:mi:ss') between :start and :end");
            }
            try {
                svnlog = jdbcTemplate.queryForObject(sql1.toString(), svnInfoEntity, DopsSvnlog.class);
            }
            catch (Exception e) {
                logger.error("该项目的未解决数查询异常！", e);
                return svnlogLast;
            }
            svnlogLast.setUnsolvedcount(svnlog.getUnsolvedcount());
            svnlogLast.setFilesize(querySvnlogsizeByDate(svnInfoEntity));
            return svnlogLast;
        }
        return null;

    }
    
    /**
    * //查询项目这段时间内的更新文件数，并进行去重
    * @author zhusf
    * @param @param svnInfoEntity
    * @param @return
    * @return String
    */ 	
    public String querySvnlogsizeByDate(SvnInfoEntity svnInfoEntity){
        //查询项目这段时间内的更新文件数，并进行去重
        StringBuffer sql = new StringBuffer();
        if (jdbcTemplate.isMysql()) {
            sql.append("select * from tb_dops_svnlog where lastchangeddate between :start and :end and svnpath = :svnpath");
        } else {
            sql.append("select * from tb_dops_svnlog where to_char(lastchangeddate,'yyyy-mm-dd hh24:mi:ss') between :start and :end and svnpath = :svnpath");
        }
        List<DopsSvnlog> dopsSvnlogList = null;
        try {
            dopsSvnlogList = jdbcTemplate.query(sql.toString(), svnInfoEntity, DopsSvnlog.class);
        }
        catch (Exception e) {
            logger.error("查询项目这段时间内的更新文件数！", e);
        }
        if(NonUtil.isNon(dopsSvnlogList)){
            return "0";
        }
        dopsSvnlogList = removeDuplicateForSvnlog(dopsSvnlogList);
        return String.valueOf(dopsSvnlogList.size());
    }

    /**
     * 根据日期和模糊项目名，初步查询日志信息
     * 
     * @author zhusf
     * @param @param
     *            svnInfoEntity
     * @param @return
     * @return List<DopsSvnlog>
     */
    public List<DopsSvnlog> querySvnlogByDateAndFuzzysvninfo(SvnInfoEntity svnInfoEntity) {
        List<DopsSvnlog> dopsSvnlogList = new ArrayList<DopsSvnlog>();
        StringBuffer sql = new StringBuffer();
        svnInfoEntity.setSvnpath("%" + svnInfoEntity.getSvnpath().trim() + "%");
        if (jdbcTemplate.isMysql()) {
            sql.append("select distinct g.svnpath,g.svnaddr,e.projecttype from tb_dops_svnlog g left join tb_dops_svnpathtype e on g.svnpath = e.svnpath where g.svnpath like :svnpath and g.lastchangeddate between :start and :end");
        } else {
            sql.append("select distinct g.svnpath,g.svnaddr,e.projecttype from tb_dops_svnlog g left join tb_dops_svnpathtype e on g.svnpath = e.svnpath where g.svnpath like :svnpath and to_char(g.lastchangeddate,'yyyy-mm-dd hh24:mi:ss') between :start and :end");
        }

        List<DopsSvnlog> dopsSvnlogListTmp = null;
        try {
            dopsSvnlogListTmp = jdbcTemplate.query(sql.toString(), svnInfoEntity, DopsSvnlog.class);
        }
        catch (Exception e) {
            logger.error(e);
            throw new RuntimeException("通过日期和项目地址查询异常！", e);
        }
        for (DopsSvnlog item : dopsSvnlogListTmp) {
            svnInfoEntity.setSvnpath(item.getSvnpath());
            DopsSvnlog svnlog = null;
            // 查询项目这段时间内的未解决数
            StringBuffer sql1 = new StringBuffer();
            if (jdbcTemplate.isMysql()) {
                sql1.append("select count(*) as unsolvedcount from tb_dops_svncomment t left join tb_dops_svnlog g on (t.filename = g.filename and t.revision = g.revision) where t.projectname = :svnpath and t.finishstate = '0' and g.lastchangeddate between :start and :end");
            } else {
                sql1.append("select count(*) as unsolvedcount from tb_dops_svncomment t left join tb_dops_svnlog g on (t.filename = g.filename and t.revision = g.revision) where t.projectname = :svnpath and t.finishstate = '0' and to_char(g.lastchangeddate,'yyyy-mm-dd hh24:mi:ss') between :start and :end");
            }
            try {
                svnlog = jdbcTemplate.queryForObject(sql1.toString(), svnInfoEntity, DopsSvnlog.class);
            }
            catch (Exception e) {
                logger.error("该项目的未解决数查询异常！", e);
            }
            item.setUnsolvedcount(svnlog.getUnsolvedcount());
            item.setFilesize(querySvnlogsizeByDate(svnInfoEntity));
            dopsSvnlogList.add(item);

        }
        return dopsSvnlogList;

    }

    /**
     * 搜索项目
     * 
     * @author zhusf
     * @param @param
     *            svnParaEntity
     * @param @return
     * @return List<SvnlogProjectDomain>
     */
    public SvnInfoEntity querySvnlogForSearchProject(SvnParaEntity svnParaEntity) {
        String searchContent = svnParaEntity.getSvnaddr();
        List<SvnInfoEntity> svnInfoEntityList = getSvninfoByUsercode(svnParaEntity);
        for (SvnInfoEntity svnInfoEntity : svnInfoEntityList) {
            String svnpath = svnInfoEntity.getSvnpath();
            if (svnpath.indexOf(searchContent) != -1) {
                return svnInfoEntity;
            }
        }
        return null;
    }

    /**
     * 查询单个svn项目的日志信息
     * 
     * @author zhusf
     * @param @param
     *            svnInfoEntity
     * @param @return
     * @return ReturnValueDomain<List<SvnlogProjectDomain>>
     */
    public ReturnValueDomain<List<SvnlogProjectDomain>> querySvnlogDetail(SvnInfoEntity svnInfoEntity) {
        ReturnValueDomain<List<SvnlogProjectDomain>> ret = new ReturnValueDomain<List<SvnlogProjectDomain>>();
        if (NonUtil.isNon(svnInfoEntity)) {
            return ret.setFail("前台传参异常！");
        }
        List<SvnlogProjectDomain> svnlogProjectDomainList = new ArrayList<SvnlogProjectDomain>();
        if (NonUtil.isNon(svnInfoEntity.getStart()) || NonUtil.isNon(svnInfoEntity.getEnd())) {
            return ret.setFail("请输入日期！");
        }
        if (Integer.parseInt(svnInfoEntity.getStart().replace("-", "")) > Integer.parseInt(svnInfoEntity.getEnd().replace("-", ""))) {
            return ret.setFail("开始时间不能大于结束时间！");
        }
        String end = svnInfoEntity.getEnd();
        svnInfoEntity.setEnd((end + " 23:59:59"));
        StringBuffer sql = new StringBuffer();
        if (jdbcTemplate.isMysql()) {
            sql.append("select * from tb_dops_svnlog where svnaddr = :svnaddr and svnpath = :svnpath and lastchangeddate between :start and :end order by lastchangeddate desc");
        } else {
            sql.append("select * from tb_dops_svnlog where svnaddr = :svnaddr and svnpath = :svnpath and to_char(lastchangeddate,'yyyy-mm-dd hh24:mi:ss') between :start and :end order by lastchangeddate desc");
        }
        List<DopsSvnlog> dopsSvnlogList = null;
        try {
            dopsSvnlogList = jdbcTemplate.query(sql.toString(), svnInfoEntity, DopsSvnlog.class);
        }
        catch (Exception e) {
            logger.error("查询日志文件失败！",e);
            return ret.setFail("查询日志文件失败！");
        }
        String sql1 = "select cgid from tb_dops_svncodeviewgroup where svnaddr = :svnaddr and svnpath = :svnpath";
        List<DopsSvncodeviewgroup> groupList = null;
        try {
            groupList = jdbcTemplate.query(sql1,svnInfoEntity, DopsSvncodeviewgroup.class);
        }
        catch (DataAccessException e) {
            logger.error("查询小组表失败",e);
            return ret.setFail("查询小组表失败");
        }
        
        logger.debug("查询到该项目有{}条日志", dopsSvnlogList.size());
        dopsSvnlogList = removeDuplicateForSvnlog(dopsSvnlogList);
        logger.debug("对旧版本进行过滤，该项目有{}条日志", dopsSvnlogList.size());
        for (DopsSvnlog dopsSvnlog : dopsSvnlogList) {
            dopsSvnlog.setCount(getFileCommentCount(dopsSvnlog));
            dopsSvnlog.setUnsolvedcount(getUnsolvedCount(dopsSvnlog));
            dopsSvnlog.setSolvedcount(getSolvedCount(dopsSvnlog));
            // 处理中文括号
            String message = dopsSvnlog.getMessage();
            String messageHandle = message.replace("（", "(").replace("）", ")");
            dopsSvnlog.setMessage(messageHandle);
        }
        new ListSortUtil().sort(dopsSvnlogList, "unsolvedcount", "desc");
        if (NonUtil.isNotNon(dopsSvnlogList)) {
            SvnlogProjectDomain svnlogProjectDomain = new SvnlogProjectDomain();
            svnlogProjectDomain.setDopsSvnlogList(dopsSvnlogList);
            if(NonUtil.isNon(groupList)){
                svnlogProjectDomain.setCgid("none");
            }else{
                svnlogProjectDomain.setCgid(groupList.get(0).getCgid());
            }
            svnlogProjectDomainList.add(svnlogProjectDomain);
        }
        return ret.setSuccess("查询成功！", svnlogProjectDomainList);
    }

    /**
     * 查询我评论的和被评论的
     * 
     * @author zhusf
     * @param @param
     *            svnParaEntity
     * @param @return
     * @return ReturnValueDomain<List<SvnProjectComment>>
     */
    public ReturnValueDomain<List<SvnProjectComment>> queryJoin(SvnParaEntity svnParaEntity) {

        ReturnValueDomain<List<SvnProjectComment>> ret = new ReturnValueDomain<List<SvnProjectComment>>();
        if (NonUtil.isNon(svnParaEntity)) {
            return ret.setFail("前台传参异常！");
        }
        if (NonUtil.isNon(svnParaEntity.getStart()) || NonUtil.isNon(svnParaEntity.getEnd())) {
            return ret.setFail("请输入日期！");
        }
        if (Integer.parseInt(svnParaEntity.getStart().replace("-", "")) > Integer.parseInt(svnParaEntity.getEnd().replace("-", ""))) {
            return ret.setFail("开始时间不能大于结束时间！");
        }
        // 处理结束时间
        String end = svnParaEntity.getEnd();
        svnParaEntity.setEnd((end + " 23:59:59"));
        //
        StringBuffer sql = new StringBuffer();
        if (jdbcTemplate.isMysql()) {
            if ("icomment".equals(svnParaEntity.getType())) {
                sql.append("select * from tb_dops_svncomment where personid = (select personid from tb_sys_user where usercode=:usercode) and optdate BETWEEN :start and :end");
            } else {
                sql.append("select * from tb_dops_svncomment where submitperson = :usercode and optdate BETWEEN :start and :end");
            }
        } else {
            if ("icomment".equals(svnParaEntity.getType())) {
                sql.append("select * from tb_dops_svncomment where personid = (select personid from tb_sys_user where usercode=:usercode) and to_char(optdate,'yyyy-mm-dd hh24:mi:ss') BETWEEN :start and :end");
            } else {
                sql.append("select * from tb_dops_svncomment where submitperson = :usercode and to_char(optdate,'yyyy-mm-dd hh24:mi:ss') BETWEEN :start and :end");
            }
        }

        List<DopsSvncomment> dopsSvncommentlist = null;
        try {
            dopsSvncommentlist = jdbcTemplate.query(sql.toString(), svnParaEntity, DopsSvncomment.class);
        }
        catch (Exception e) {
            logger.error("查询svn评审表失败！", e);
            return ret.setFail("查询svn评审表失败！");
        }
        logger.debug("过滤前大小：{}", dopsSvncommentlist.size());
        if (NonUtil.isNon(dopsSvncommentlist)) {
            return ret.setFail("未查到评审信息！");
        }
        dopsSvncommentlist = removeDuplicate(dopsSvncommentlist);
        logger.debug("过滤后大小：{}", dopsSvncommentlist.size());
        for (DopsSvncomment dopsSvncomment : dopsSvncommentlist) {
            // 设置svnlogid
            String sql1 = "select svnlogid,lastchangeddate,svnpath from tb_dops_svnlog where filename = :filename and revision = :revision";
            DopsSvnlog dopsSvnlog = null;
            try {
                dopsSvnlog = jdbcTemplate.queryForObject(sql1, dopsSvncomment, DopsSvnlog.class);
            }
            catch (DataAccessException e) {
                logger.error("查询svn日志表失败！", e);
                return ret.setFail("查询svn日志表失败！");
            }
            // 设置计数的查询参数
            dopsSvnlog.setFilename(dopsSvncomment.getFilename());
            dopsSvnlog.setRevision(dopsSvncomment.getRevision());
            dopsSvncomment.setSvnlogid(dopsSvnlog.getSvnlogid());
            dopsSvncomment.setLastchangeddate(dopsSvnlog.getLastchangeddate());
            //
            dopsSvncomment.setCount(getFileCommentCount(dopsSvnlog));
            dopsSvncomment.setSolvedcount(getSolvedCount(dopsSvnlog));
            dopsSvncomment.setUnsolvedcount(getUnsolvedCount(dopsSvnlog));
            // dopsSvncomment.setSvnpath(getFromIndex(dopsSvncomment.getFilename(),
            // "/", 3));
            dopsSvncomment.setSvnpath(dopsSvnlog.getSvnpath());
        }
        new ListSortUtil().sort(dopsSvncommentlist, "count", "desc");
        List<SvnProjectComment> svnProjectCommentList = new ArrayList<SvnProjectComment>();

        Map<String, List<DopsSvncomment>> resultMap = new HashMap<String, List<DopsSvncomment>>();
        for (DopsSvncomment dopsSvncomment : dopsSvncommentlist) {
            String svnaddr = dopsSvncomment.getRepurl();
            // 仓库地址处理
            svnaddr = svnaddrHandle(svnaddr);
            dopsSvncomment.setSvnpath(svnaddr.concat(dopsSvncomment.getSvnpath()));
            if (resultMap.containsKey(dopsSvncomment.getSvnpath())) {
                resultMap.get(dopsSvncomment.getSvnpath()).add(dopsSvncomment);
            } else {
                List<DopsSvncomment> tmpList = new ArrayList<DopsSvncomment>();
                tmpList.add(dopsSvncomment);
                resultMap.put(dopsSvncomment.getSvnpath(), tmpList);
            }
        }
        for (Entry<String, List<DopsSvncomment>> entry : resultMap.entrySet()) {
            SvnProjectComment svnProjectComment = new SvnProjectComment();
            svnProjectComment.setSvnpath(entry.getKey());
            //添加文件计数和未解决数计数
            List<DopsSvncomment> projectCts = entry.getValue();
            int filesizecount = projectCts.size();
            int unsolvedcount = 0;
            for (DopsSvncomment dopsSvncomment : projectCts) {
                unsolvedcount += dopsSvncomment.getUnsolvedcount();
            }
            svnProjectComment.setDopsSvncommentList(projectCts);
            svnProjectComment.setFilesize(String.valueOf(filesizecount));
            svnProjectComment.setUnsolvedcount(unsolvedcount);
            svnProjectCommentList.add(svnProjectComment);
        }
        return ret.setSuccess("查询成功", svnProjectCommentList);
    }

    /**
     * 查询文件的所有版本
     * 
     * @author zhusf
     * @param @param
     *            svnParaEntity
     * @param @return
     * @return ReturnValueDomain<List<DopsSvncomment>>
     */
    public ReturnValueDomain<List<DopsSvncomment>> queryFileAllRevision(SvnParaEntity svnParaEntity) {
        ReturnValueDomain<List<DopsSvncomment>> ret = new ReturnValueDomain<List<DopsSvncomment>>();
        if (NonUtil.isNon(svnParaEntity)) {
            return ret.setFail("前台传参异常！");
        }
        List<DopsSvncomment> dopsSvncommentList = new ArrayList<DopsSvncomment>();
        String sql = "select * from tb_dops_svnlog where filename = :filename order by lastchangeddate desc";
        List<DopsSvnlog> dopsSvnlogList = null;
        try {
            dopsSvnlogList = jdbcTemplate.query(sql.toString(), svnParaEntity, DopsSvnlog.class);
        }
        catch (Exception e) {
            logger.error("查询svn日志表失败！", e);
            ret.setFail("查询svn日志表失败！");
        }
        for (DopsSvnlog dopsSvnlog : dopsSvnlogList) {
            DopsSvncomment dopsSvncomment = new DopsSvncomment();
            // 提交日志
            dopsSvncomment.setMessage(dopsSvnlog.getMessage());
            // 版本号
            dopsSvncomment.setRevision(dopsSvnlog.getRevision());
            // 提交人
            dopsSvncomment.setSubmitperson(dopsSvnlog.getSubmitperson());
            SysUser sysUser = new SysUser();
            sysUser.setUsercode(dopsSvnlog.getSubmitperson());
            String personname = queryPersonnameByUsercode(sysUser);
            dopsSvncomment.setPersonname(personname);
            // 提交时间
            dopsSvncomment.setLastchangeddate(dopsSvnlog.getLastchangeddate());
            // 评论数
            dopsSvncomment.setCount(getFileCommentCountWithRevision(dopsSvnlog));
            dopsSvncomment.setUnsolvedcount(getUnsolvedCountWithRevision(dopsSvnlog));
            dopsSvncomment.setSvnlogid(dopsSvnlog.getSvnlogid());
            dopsSvncommentList.add(dopsSvncomment);
        }
        return ret.setSuccess("查询成功", dopsSvncommentList);
    }

    /**
     * 通过用户编号查询svn真实密码
     * 
     * @author zhusf
     * @param @param
     *            svnParaEntity
     * @param @return
     * @return String
     */
    public String getSvnpwdByUsercode(SvnParaEntity svnParaEntity) {
        StringBuffer sql = new StringBuffer("SELECT passwd FROM tb_dops_svnuser WHERE usercode = :usercode");
        DopsSvnuser dopsSvnuser = null;
        try {
            dopsSvnuser = jdbcTemplate.queryForObject(sql.toString(), svnParaEntity, DopsSvnuser.class);
        }
        catch (Exception e) {
            logger.error("查询用户svn原始密码失败！", e);
        }

        String realpwd = "";
        try {
            realpwd = new String(DES.decryptString(dopsSvnuser.getPasswd()));
        }
        catch (Exception e1) {
            logger.error("查询用户svn明文密码失败！", e1);
        }
        return realpwd;
    }

    /**
     * 通过用户编号，查询svn信息
     * 
     * @author zhusf
     * @param @param
     *            svnParaEntity
     * @param @return
     * @return List<SvnInfoEntity>
     */
    public List<SvnInfoEntity> getSvninfoByUsercode(SvnParaEntity svnParaEntity) {
        // 查询所有的svnpath
        String sql2  = "select svnpath,svnaddr from tb_dops_svncodeviewgroup";
        List<SvnInfoEntity> svnInfoEntitylist2 = null;
        try {
            svnInfoEntitylist2 = jdbcTemplate.query(sql2.toString(), SvnInfoEntity.class);
        }
        catch (Exception e) {
            logger.error("查询高工用户拥有的svn信息失败！", e);
            return new ArrayList<SvnInfoEntity>();
        }
        Iterator<SvnInfoEntity> it2 = svnInfoEntitylist2.iterator();
        while (it2.hasNext()) {
            String x = it2.next().getSvnpath();
            if ("/".equals(x)) {
                it2.remove();
            }
        }

        boolean flag = isHighlevelUser(svnParaEntity.getUsercode());
        List<SvnInfoEntity> svnInfoEntitylist = null;
        if (flag) {
            return svnInfoEntitylist2;
        } else {
//            String sql = "select distinct m.svnaddr,u.svnpath from tb_dops_svnmng m right join tb_dops_svnuserright u on m.svnid = u.svnid where m.svnid in (select svnid from tb_dops_svnuserright where usercode = :usercode) and usercode = :usercode";
//
//            try {
//                svnInfoEntitylist = jdbcTemplate.query(sql.toString(), svnParaEntity, SvnInfoEntity.class);
//            }
//            catch (Exception e) {
//                logger.error("查询普通用户拥有的svn信息失败！", e);
//                return new ArrayList<SvnInfoEntity>();
//            }
//            Iterator<SvnInfoEntity> it = svnInfoEntitylist.iterator();
//            while (it.hasNext()) {
//                String x = it.next().getSvnpath();
//                if ("/".equals(x)) {
//                    it.remove();
//                }
//            }
            //需求变更 普通用户查看项目权限，从评审小组中查询
            try {
                svnInfoEntitylist = queryGroupByUsercode(svnParaEntity);
            }
            catch (Exception e) {
                logger.error("查询评审小组表失败",e);
                return new ArrayList<SvnInfoEntity>();
            }
        }
        logger.debug("该用户可见的项目数有{}个", svnInfoEntitylist.size());
        return svnInfoEntitylist;
    }

    /**
     * 通过文件名查询评论数目
     * 
     * @author zhusf
     * @param @param
     *            dopsSvnlog
     * @param @return
     * @return int
     */
    public int getFileCommentCount(DopsSvnlog dopsSvnlog) {
        String sql = "select count(*) commentid from tb_dops_svncomment where filename = :filename";
        DopsSvncomment dopsSvncomment = null;
        try {
            dopsSvncomment = jdbcTemplate.queryForObject(sql.toString(), dopsSvnlog, DopsSvncomment.class);
        }
        catch (Exception e) {
            logger.error("查询评论数目失败!", e);
            return 0;
        }
        return Integer.parseInt(dopsSvncomment.getCommentid());
    }

    /**
     * 通过文件名查询已解决的评论数目
     * 
     * @author zhusf
     * @param @param
     *            dopsSvnlog
     * @param @return
     * @return int
     */
    public int getSolvedCount(DopsSvnlog dopsSvnlog) {
        String sql = "select count(*) commentid from tb_dops_svncomment where filename = :filename and finishstate = '1'";
        DopsSvncomment dopsSvncomment = null;
        try {
            dopsSvncomment = jdbcTemplate.queryForObject(sql.toString(), dopsSvnlog, DopsSvncomment.class);
        }
        catch (Exception e) {
            logger.error("查询已解决数目失败!", e);
        }
        return Integer.parseInt(dopsSvncomment.getCommentid());
    }

    /**
     * 通过文件名查询未解决的评论数目
     * 
     * @author zhusf
     * @param @param
     *            dopsSvnlog
     * @param @return
     * @return int
     */
    public int getUnsolvedCount(DopsSvnlog dopsSvnlog) {
        String sql = "select count(*) commentid from tb_dops_svncomment where filename = :filename and finishstate = '0'";
        DopsSvncomment dopsSvncomment = null;
        try {
            dopsSvncomment = jdbcTemplate.queryForObject(sql.toString(), dopsSvnlog, DopsSvncomment.class);
        }
        catch (Exception e) {
            logger.error("查询未解决数目失败!", e);
            return 0;
        }
        return Integer.parseInt(dopsSvncomment.getCommentid());
    }

    /**
     * 通过文件名和版本号查询评论数目
     * 
     * @author zhusf
     * @param @param
     *            dopsSvnlog
     * @param @return
     * @return int
     */
    public int getFileCommentCountWithRevision(DopsSvnlog dopsSvnlog) {
        String sql = "select count(*) commentid from tb_dops_svncomment where filename = :filename and revision = :revision";
        DopsSvncomment dopsSvncomment = null;
        try {
            dopsSvncomment = jdbcTemplate.queryForObject(sql.toString(), dopsSvnlog, DopsSvncomment.class);
        }
        catch (Exception e) {
            logger.error("查询评审数目失败!", e);
        }
        return Integer.parseInt(dopsSvncomment.getCommentid());
    }

    /**
     * 通过文件名和版本号查询已解决的评论数目
     * 
     * @author zhusf
     * @param @param
     *            dopsSvnlog
     * @param @return
     * @return int
     */
    public int getSolvedCountWithRevision(DopsSvnlog dopsSvnlog) {
        String sql = "select count(*) commentid from tb_dops_svncomment where filename = :filename and finishstate = '1' and revision = :revision";
        DopsSvncomment dopsSvncomment = null;
        try {
            dopsSvncomment = jdbcTemplate.queryForObject(sql.toString(), dopsSvnlog, DopsSvncomment.class);
        }
        catch (Exception e) {
            logger.error("查询已解决数目失败!", e);
        }
        return Integer.parseInt(dopsSvncomment.getCommentid());
    }

    /**
     * 通过文件名和版本号查询未解决的评论数目
     * 
     * @author zhusf
     * @param @param
     *            dopsSvnlog
     * @param @return
     * @return int
     */
    public int getUnsolvedCountWithRevision(DopsSvnlog dopsSvnlog) {
        String sql = "select count(*) commentid from tb_dops_svncomment where filename = :filename and finishstate = '0' and revision = :revision";
        DopsSvncomment dopsSvncomment = null;
        try {
            dopsSvncomment = jdbcTemplate.queryForObject(sql.toString(), dopsSvnlog, DopsSvncomment.class);
        }
        catch (Exception e) {
            logger.error("查询未解决数目失败!", e);
        }
        return Integer.parseInt(dopsSvncomment.getCommentid());
    }

    /**
     * 通过后缀判断是否是jsp，java文件
     * 
     * @author zhusf
     * @param @param
     *            fileName
     * @param @return
     * @return Boolean
     */
    public Boolean isJsJavaFile(String fileName) {
        // String[] img_type = new String[] {".java",".xml",".jsp"};
        String[] img_type = new String[] {".jsp", ".java"};
        if (NonUtil.isNon(fileName)) {
            return false;
        }
        fileName = fileName.toLowerCase();
        for (String type : img_type) {
            if (fileName.endsWith(type) && fileName.indexOf("pojo") == -1 && fileName.indexOf("serviceap") == -1) {
                return true;
            }
        }
        return false;
    }
    
    /**
    * 目录树的文件过滤规则
    * @author zhusf
    * @param @param fileName
    * @param @return
    * @return Boolean
    */ 	
    public Boolean isTreeFile(String fileName) {
        String[] img_type = new String[] {".jsp", ".java",".js",".xml",".properties",".txt",".sql"};
        if (NonUtil.isNon(fileName)) {
            return false;
        }
        fileName = fileName.toLowerCase();
        for (String type : img_type) {
            if (fileName.endsWith(type) ) {
                return true;
            }
        }
        return false;
    }
    
    public Boolean isOfficeFile(String fileName) {
        String[] img_type = new String[] {".doc", ".docx",".xls",".xlsx",".pdf"};
       // String[] img_type = new String[] {".xxxxxxxx"};
        if (NonUtil.isNon(fileName)) {
            return false;
        }
        fileName = fileName.toLowerCase();
        for (String type : img_type) {
            if (fileName.endsWith(type) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过用户编号查询姓名
     * 
     * @author zhusf
     * @param @param
     *            sysUser
     * @param @return
     * @return String
     */
    public String queryPersonnameByUsercode(SysUser sysUser) {
        StringBuffer sql = new StringBuffer();
        sql.append("select personname from tb_sys_person where personid = (select personid from tb_sys_user where usercode = :usercode)");
        DopsSvncomment dopsSvncomment = null;
        try {
            dopsSvncomment = jdbcTemplate.queryForObject(sql.toString(), sysUser, DopsSvncomment.class);
        }
        catch (Exception e) {
            logger.error("通过用户查询姓名失败！", e);
            return "姓名不详";
        }
        return dopsSvncomment.getPersonname();
    }

    /**
     * 字符串截断方法
     * 
     * @author zhusf
     * @param @param
     *            str
     * @param @param
     *            modelStr
     * @param @param
     *            count
     * @param @return
     * @return String
     */
    private String getFromIndex(String str, String modelStr, Integer count) {
        // 对子字符串进行匹配
        Matcher slashMatcher = Pattern.compile(modelStr).matcher(str);
        int index = 0;
        // matcher.find();尝试查找与该模式匹配的输入序列的下一个子序列
        while (slashMatcher.find()) {
            index++ ;
            // 当modelStr字符第count次出现的位置
            if (index == count) {
                break;
            }
        }
        // matcher.start();返回以前匹配的初始索引。
        index = slashMatcher.start();
        return str.substring(0, index);
    }

    /**
    * 根据文件名和版本号 去重评论对象
    * @author zhusf
    * @param @param dopsSvncomments
    * @param @return
    * @return ArrayList<DopsSvncomment>
    */ 	
    private List<DopsSvncomment> removeDuplicate(List<DopsSvncomment> dopsSvncomments) {
        ArrayList<DopsSvncomment> dopsSvncommentList = new ArrayList<DopsSvncomment>();
        for (DopsSvncomment dopsSvncomment : dopsSvncomments) {
            if (!dopsSvncommentList.contains(dopsSvncomment)) {
                dopsSvncommentList.add(dopsSvncomment);
            }
        }
        return dopsSvncommentList;
    }
    
    /**
    * 根据文件名去重文件对象
    * @author zhusf
    * @param @param dopsSvncomments
    * @param @return
    * @return ArrayList<DopsSvncomment>
    */ 	
    private List<DopsSvnlog> removeDuplicateForSvnlog(List<DopsSvnlog> dopsSvnlogLists) {
        ArrayList<DopsSvnlog> dopsSvnlogList = new ArrayList<DopsSvnlog>();
        for (DopsSvnlog dopsSvnlog : dopsSvnlogLists) {
            if (!dopsSvnlogList.contains(dopsSvnlog)) {
                dopsSvnlogList.add(dopsSvnlog);
            }
        }
        return dopsSvnlogList;
    }


    /**
     * 发送邮件时，查询svn日志信息
     * 
     * @author zhusf
     * @param @param
     *            svnInfoEntity
     * @param @return
     * @return SvnlogProjectDomain
     */
    public SvnlogProjectDomain querySvnlogForEmail(SvnInfoEntity svnInfoEntity) {
        List<SvnlogProjectDomain> svnlogProjectDomainList = new ArrayList<SvnlogProjectDomain>();
        StringBuffer sql = new StringBuffer();
        if (jdbcTemplate.isMysql()) {
            sql.append("select g.*,e.projecttype from tb_dops_svnlog g left join tb_dops_svnpathtype e on g.svnpath = e.svnpath where g.svnaddr = :svnaddr and g.svnpath = :svnpath and g.lastchangeddate between :start and :end");
        } else {
            sql.append("select g.*,e.projecttype from tb_dops_svnlog g left join tb_dops_svnpathtype e on g.svnpath = e.svnpath where g.svnaddr = :svnaddr and g.svnpath = :svnpath and to_char(g.lastchangeddate,'yyyy-mm-dd hh24:mi:ss') between :start and :end");
        }
        List<DopsSvnlog> dopsSvnlogList = null;
        try {
            dopsSvnlogList = jdbcTemplate.query(sql.toString(), svnInfoEntity, DopsSvnlog.class);
        }
        catch (Exception e) {
            logger.error("查询日志表失败！", e);
            return null;
        }
        for (DopsSvnlog dopsSvnlog : dopsSvnlogList) {
            dopsSvnlog.setFilename(getFromIndexLast(dopsSvnlog.getFilename(), "/", 3));
            dopsSvnlog.setLastchangeddate(dopsSvnlog.getLastchangeddate().substring(5, 16));
        }
        //
        if (NonUtil.isNon(dopsSvnlogList)) {
            return null;
        }
        new ListSortUtil().sort(dopsSvnlogList, "count", "desc");

        Map<String, List<DopsSvnlog>> resultMap = new HashMap<String, List<DopsSvnlog>>();
        for (DopsSvnlog dopsSvnlog : dopsSvnlogList) {
            String svnaddr = dopsSvnlog.getSvnaddr();
            // 仓库地址处理
            svnaddr = svnaddrHandle(svnaddr);
            dopsSvnlog.setSvnpath(svnaddr.concat(dopsSvnlog.getSvnpath()));
            if (resultMap.containsKey(dopsSvnlog.getSvnpath())) {
                resultMap.get(dopsSvnlog.getSvnpath()).add(dopsSvnlog);
            } else {
                List<DopsSvnlog> tmpList = new ArrayList<DopsSvnlog>();
                tmpList.add(dopsSvnlog);
                resultMap.put(dopsSvnlog.getSvnpath(), tmpList);
            }
        }
        for (Entry<String, List<DopsSvnlog>> entry : resultMap.entrySet()) {
            SvnlogProjectDomain svnlogProjectDomain = new SvnlogProjectDomain();
            svnlogProjectDomain.setSvnpath(entry.getKey());
            svnlogProjectDomain.setDopsSvnlogList(entry.getValue());
            svnlogProjectDomainList.add(svnlogProjectDomain);
        }
        if (NonUtil.isNon(svnlogProjectDomainList)) {
            return null;
        }
        return svnlogProjectDomainList.get(0);
    }

    /**
     * 发送走查结果邮件
     * 
     * @author zhusf
     * @param @param
     *            svnInfoEntity
     * @param @return
     * @return SvnlogProjectDomain
     */
    public SvnlogProjectDomain querySvnlogForResultEmail(SvnInfoEntity svnInfoEntity) {
        List<SvnlogProjectDomain> svnlogProjectDomainList = new ArrayList<SvnlogProjectDomain>();
        String sql = "select g.*,e.projecttype from tb_dops_svnlog g left join tb_dops_svnpathtype e on g.svnpath = e.svnpath where g.svnaddr = :svnaddr and g.svnpath = :svnpath";
        List<DopsSvnlog> dopsSvnlogList = null;
        try {
            dopsSvnlogList = jdbcTemplate.query(sql.toString(), svnInfoEntity, DopsSvnlog.class);
        }
        catch (Exception e) {
            logger.error("查询日志表失败！", e);
            return null;
        }
        if (NonUtil.isNon(dopsSvnlogList)) {
            return null;
        }
        for (DopsSvnlog dopsSvnlog : dopsSvnlogList) {
            // 查询该条svn日志的评论信息
            SvnParaEntity svnParaEntity = new SvnParaEntity();
            svnParaEntity.setFilename(dopsSvnlog.getFilename());
            svnParaEntity.setRevision(dopsSvnlog.getRevision());
            svnParaEntity.setStart(svnInfoEntity.getStart());
            svnParaEntity.setEnd(svnInfoEntity.getEnd());
            // 添加这一天文件的评论信息
            StringBuffer sql2 = new StringBuffer();
            if (jdbcTemplate.isMysql()) {
                sql2.append("select t.*,p.personname from tb_dops_svncomment t left join tb_sys_person p on t.personid = p.personid where t.filename = :filename and t.revision = :revision and t.optdate between :start and :end");
            } else {
                sql2.append("select t.*,p.personname from tb_dops_svncomment t left join tb_sys_person p on t.personid = p.personid where t.filename = :filename and t.revision = :revision and to_char(t.optdate,'yyyy-mm-dd hh24:mi:ss') between :start and :end");
            }
            List<DopsSvncomment> dopsSvncommentList = new ArrayList<DopsSvncomment>();
            try {
                dopsSvncommentList = jdbcTemplate.query(sql2.toString(), svnParaEntity, DopsSvncomment.class);
            }
            catch (Exception e) {
                logger.error("查询这一天文件的评论信息失败！", e);
            }
            int count = 0;
            int unsolvedcount = 0;
            for (DopsSvncomment dopsSvncomment : dopsSvncommentList) {
                count = count + 1;
                if ("0".equals(dopsSvncomment.getFinishstate())) {
                    unsolvedcount = unsolvedcount + 1;
                }
                String state = "0".equals(dopsSvncomment.getFinishstate()) ? "未解决" : "已解决";
                dopsSvncomment.setFinishstate(state);
                String optdate = dopsSvncomment.getOptdate();
                // 2018-12-12 12:00:00截取成12-12 12:00
                dopsSvncomment.setOptdate(optdate.substring(5, 16));
            }
            dopsSvnlog.setCount(count);
            dopsSvnlog.setUnsolvedcount(unsolvedcount);
            dopsSvnlog.setFilename(getFromIndexLast(dopsSvnlog.getFilename(), "/", 3));
            dopsSvnlog.setDopsSvncommentList(dopsSvncommentList);
        }

        Iterator<DopsSvnlog> it2 = dopsSvnlogList.iterator();
        while (it2.hasNext()) {
            List<DopsSvncomment> dopsSvncommentListtt = it2.next().getDopsSvncommentList();
            if (dopsSvncommentListtt.size() == 0) {
                it2.remove();
            }
        }

        new ListSortUtil().sort(dopsSvnlogList, "unsolvedcount", "desc");

        Map<String, List<DopsSvnlog>> resultMap = new HashMap<String, List<DopsSvnlog>>();
        int projectcount = 0;
        int projectunsolvedcount = 0;
        for (DopsSvnlog dopsSvnlog : dopsSvnlogList) {
            projectcount = projectcount + dopsSvnlog.getCount();
            projectunsolvedcount = projectunsolvedcount + dopsSvnlog.getUnsolvedcount();
            String svnaddr = dopsSvnlog.getSvnaddr();
            // 仓库地址处理
            svnaddr = svnaddrHandle(svnaddr);
            dopsSvnlog.setSvnpath(svnaddr.concat(dopsSvnlog.getSvnpath()));
            if (resultMap.containsKey(dopsSvnlog.getSvnpath())) {
                resultMap.get(dopsSvnlog.getSvnpath()).add(dopsSvnlog);
            } else {
                List<DopsSvnlog> tmpList = new ArrayList<DopsSvnlog>();
                tmpList.add(dopsSvnlog);
                resultMap.put(dopsSvnlog.getSvnpath(), tmpList);
            }
        }
        for (Entry<String, List<DopsSvnlog>> entry : resultMap.entrySet()) {
            SvnlogProjectDomain svnlogProjectDomain = new SvnlogProjectDomain();
            svnlogProjectDomain.setSvnpath(entry.getKey());
            svnlogProjectDomain.setDopsSvnlogList(entry.getValue());
            svnlogProjectDomain.setCount(projectcount);
            svnlogProjectDomain.setUnsolvedcount(projectunsolvedcount);
            svnlogProjectDomainList.add(svnlogProjectDomain);
        }
        if (NonUtil.isNon(svnlogProjectDomainList)) {
            return null;
        }
        return svnlogProjectDomainList.get(0);
    }

    /**
     * 字符串截取辅助方法
     * 
     * @author zhusf
     * @param @param
     *            str
     * @param @param
     *            modelStr
     * @param @param
     *            count
     * @param @return
     * @return String
     */
    private String getFromIndexLast(String str, String modelStr, Integer count) {
        // 对子字符串进行匹配
        Matcher slashMatcher = Pattern.compile(modelStr).matcher(str);
        int index = 0;
        // matcher.find();尝试查找与该模式匹配的输入序列的下一个子序列
        while (slashMatcher.find()) {
            index++ ;
            // 当modelStr字符第count次出现的位置
            if (index == count) {
                break;
            }
        }
        // matcher.start();返回以前匹配的初始索引。
        index = slashMatcher.start();
        return str.substring(index);
    }

    /**
     * 通过用户编号判断是否是高工人员
     * 
     * @author zhusf
     * @param @param
     *            usercode
     * @param @return
     * @return boolean
     */
    public boolean isHighlevelUser(String usercode) {
        // 查询所有的高工人员
//        String sql1 = "select * from tb_dops_svncodeviewuser";
//        List<DopsSvncodeviewuser> dopsSvncodeviewuserList = null;
//        try {
//            dopsSvncodeviewuserList = jdbcTemplate.query(sql1.toString(), DopsSvncodeviewuser.class);
//        }
//        catch (Exception e) {
//            logger.error("查询是否是高工失败！", e);
//        }
//        boolean flag = false;
//        for (DopsSvncodeviewuser dopsSvncodeviewuser : dopsSvncodeviewuserList) {
//            String databaseUsercode = dopsSvncodeviewuser.getUsercode();
//            // 如果是高工人员，添加所有的svn权限
//            if (databaseUsercode.equals(usercode)) {
//                flag = true;
//                break;
//            }
//        }
//        logger.debug("{}是否为高工人员,{}", usercode, flag);
//        return flag;
        
        //全局小组表
        DopsProjectmember person = new DopsProjectmember();
        person.setUsercode(usercode);
        String sql = "select * from tb_dops_projectmember where cgid = (select cgid from tb_dops_svncodeviewgroup where grouptype = 'all') and usercode = :usercode";
        List<DopsProjectmember> personList = null;
        try {
            personList = jdbcTemplate.query(sql, person,DopsProjectmember.class);
        }
        catch (DataAccessException e) {
            logger.error("查询全局评审小组失败！",e);
            return false;
        }
        if(NonUtil.isNotNon(personList)){
            return true;
        }
        return false;
    }

    /**
     * 处理数据库中仓库地址结尾处，有的带/，有的不带/
     * 
     * @author zhusf
     * @param @param
     *            svnaddr
     * @param @return
     * @return String
     */
    public String svnaddrHandle(String svnaddr) {
        if (NonUtil.isNotNon(svnaddr) && "/".equals(svnaddr.substring(svnaddr.length() - 1))) {
            svnaddr = svnaddr.substring(0, svnaddr.length() - 1);
        }
        return svnaddr;
    }

    /**
     * 验证用户是否是高工人员，高工人员可以查看报表
     * 
     * @author zhusf
     * @param @param
     *            svnParaEntity
     * @param @return
     * @return ReturnValueDomain<String>
     */
    public ReturnValueDomain<String> validateUserForReport(ReportParaEntity reportParaEntity) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        if (NonUtil.isNon(reportParaEntity)) {
            return ret.setFail("报表验证用户参数异常！");
        }
        String userid = reportParaEntity.getUserid();
        String validatestr = reportParaEntity.getValidatestr();
        Iterator<Map.Entry<String, String>> it = checkmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if (userid.equals(entry.getKey())) {
                if (!validatestr.equals(entry.getValue())) {
                    return ret.setFail("请求来源错误！");
                }
                logger.debug("校验用户串成功，开始校验用户权限");
                boolean flag = isHighlevelUser(userid);
                if (flag) {
                    checkmap.remove(userid);
                    return ret.setSuccess("该用户拥有权限！");
                }
                return ret.setFail("当前用户不具有权限！");
            }
        }
        return ret.setFail("请求来源错误！");

    }

    /**
     * 设置报表校验id，缓存到内存中
     * 
     * @author zhusf
     * @param @param
     *            reportParaEntity
     * @param @return
     * @return ReturnValueDomain<String>
     */
    public ReturnValueDomain<String> setCheckidForReport(ReportParaEntity reportParaEntity) {
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        if (NonUtil.isNon(reportParaEntity)) {
            return ret.setFail("保存校验id失败！");
        }
        checkmap.put(reportParaEntity.getUserid(), reportParaEntity.getValidatestr());
        return ret.setSuccess("保存校验id成功！");
    }
    
    public ReturnValueDomain<DopsSvncodeviewgroupuserrole> querygroupuserrole(DopsSvncodeviewgroupuserrole groupuserrole) {
        ReturnValueDomain<DopsSvncodeviewgroupuserrole> ret = new ReturnValueDomain<DopsSvncodeviewgroupuserrole>();
        if(NonUtil.isNon(groupuserrole)){
            return ret.setFail("参数异常！");
        }
        List<DopsSvncodeviewgroupuserrole> groupuserroleList = null;
        try {
            groupuserroleList = groupuserroleService.querygroupuserrole(groupuserrole);
        }
        catch (Exception e) {
            logger.error("查询评审小组权限表失败！",e);
            return ret.setFail("查询评审小组权限表失败！");
        }
        if(NonUtil.isNon(groupuserroleList)){
            return ret.setFail("非评审小组的管理人员");
        }
        for (DopsSvncodeviewgroupuserrole userrole : groupuserroleList) {
            if("sm".equals(userrole.getGrouprole())){
                return ret.setSuccess("查询评审小组权限表成功",userrole);
            }
        }
       return ret.setSuccess("查询评审小组权限表成功",groupuserroleList.get(0));
    }
    
    
    /**
    * 查询文件的svn日志信息
    * @author zhusf
    * @param @param username
    * @param @param password
    * @param @param repositoryUrl
    * @param @param directoryUrl
    * @param @return
    * @param @throws Exception
    * @return List<SVNLogEntry>
    */ 	
    public static List<SVNLogEntry> viewSvnLogsBySj(String username, String password, final String repositoryUrl, String directoryUrl) throws Exception{
        
        DAVRepositoryFactory.setup();  
        SVNRepositoryFactoryImpl.setup();  
        FSRepositoryFactory.setup();
       
        SVNRepository repository =null;
        try {  
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(repositoryUrl));  
        }  
        catch (SVNException e) {  
            logger.error(e.getErrorMessage(), e);
            throw new RuntimeException(e);
        }  
        // 身份验证  
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username,password);  
        repository.setAuthenticationManager(authManager);  
        final List<String> history = new ArrayList<String>();
        final List<SVNLogEntry> sVNLogEntryList = new ArrayList<SVNLogEntry>();
        repository.log(new String[]{directoryUrl},  
                       0,  
                       -1,  
                       true,  
                       true,  
                       new ISVNLogEntryHandler() {  
                           
                           @Override  
                           public void handleLogEntry(SVNLogEntry svnlogentry)  
                                   throws SVNException { 
                               //
                               sVNLogEntryList.add(svnlogentry);
                           }  
  
                           public void fillResult(SVNLogEntry svnlogentry) {  
                          //getChangedPaths为提交的历史记录MAP key为文件名，value为文件详情  
                               history.addAll(svnlogentry.getChangedPaths().keySet());  
                           }  
                       });  
        
        return sVNLogEntryList;
    }
    
    
    /**
    * 通过用户编号查询小组的项目信息
    * @author zhusf
    * @param @param svnParaEntity
    * @param @return
    * @param @throws Exception
    * @return List<SvnInfoEntity>
    */ 	
    public List<SvnInfoEntity> queryGroupByUsercode(SvnParaEntity svnParaEntity) throws Exception{
        List<SvnInfoEntity> svnInfoEntityList = new ArrayList<SvnInfoEntity>();
        String sql = "select distinct(g.svnpath),g.svnaddr,p.usercode from tb_dops_projectmember p right join tb_dops_svncodeviewgroup g on p.cgid = g.cgid where p.usercode = :usercode";
        List<DopsSvncodeviewgroup> dopsSvncodeviewgroupList = null;
        dopsSvncodeviewgroupList = jdbcTemplate.query(sql, svnParaEntity,DopsSvncodeviewgroup.class);
        for (DopsSvncodeviewgroup dopsSvncodeviewgroup : dopsSvncodeviewgroupList) {
            SvnInfoEntity svnInfoEntity = new SvnInfoEntity();
            svnInfoEntity.setSvnaddr(dopsSvncodeviewgroup.getSvnaddr());
            svnInfoEntity.setSvnpath(dopsSvncodeviewgroup.getSvnpath());
            svnInfoEntityList.add(svnInfoEntity);
        }
        return svnInfoEntityList;
    }

    /**
    * 查询项目的最新的一次提交
    * @author zhusf
    * @param @param svncodeviewgroupQueryDomain
    * @param @return
    * @return ReturnValueDomain<PageDomain<DopsSvnlog>>
    */ 	
    public ReturnValueDomain<PageDomain<DopsSvnlog>> querylatestrevision(SvncodeviewgroupQueryDomain svncodeviewgroupQueryDomain) {

        ReturnValueDomain<PageDomain<DopsSvnlog>> ret = new ReturnValueDomain<PageDomain<DopsSvnlog>>();
        if (NonUtil.isNon(svncodeviewgroupQueryDomain)) {
            return ret.setFail("参数错误");
        }
        PageParamDomain pageparam = svncodeviewgroupQueryDomain.getPageparam();
        DopsSvncodeviewgroup group = svncodeviewgroupQueryDomain.getDopssvncodeviewgroup();
        StringBuffer sql1 = new StringBuffer();
        PageDomain<DopsSvnlog> retpage = null;

        String sql = "select * from tb_dops_svncodeviewgroup where cgid = :cgid";
        List<DopsSvncodeviewgroup> dopsSvncodeviewgroupList = null;
        try {
            dopsSvncodeviewgroupList = jdbcTemplate.query(sql, group, DopsSvncodeviewgroup.class);
        }
        catch (DataAccessException e) {
            logger.error("查询项目表失败", e);
            return ret.setFail("查询项目表失败！");
        }
        if (NonUtil.isNon(dopsSvncodeviewgroupList)) {
            return ret.setSuccess("该svn项目没有项目", retpage);
        }
        DopsSvncodeviewgroup dopsSvncodeviewgroup = dopsSvncodeviewgroupList.get(0);

         sql1.append("select * from tb_dops_svnlog where svnaddr = :svnaddr and svnpath = :svnpath  ");
        if("7".equals(group.getDaytype())){
        	//查询一周内提交的代码
        	sql1.append("and  TO_DAYS( NOW( ) ) - TO_DAYS( lastchangeddate) <= 7  AND  TO_DAYS( NOW( ) ) - TO_DAYS( lastchangeddate)>=0");
        }else if("1".equals(group.getDaytype())){
        	//查询一天前提交的代码
        	sql1.append("and  TO_DAYS( NOW( ) ) - TO_DAYS( lastchangeddate) <= 1 AND  TO_DAYS( NOW( ) ) - TO_DAYS( lastchangeddate)>=0");
        }else {
        	//查询最新版本提交的代码
        	sql1.append("and revision = (select MAX(revision) from tb_dops_svnlog where svnaddr = :svnaddr and svnpath = :svnpath)");
        }
        try {
            retpage = jdbcTemplate.queryForPage(sql1.toString(), dopsSvncodeviewgroup, DopsSvnlog.class, pageparam);
        }
        catch (DataAccessException e) {
            logger.error("查询日志表失败", e);
            return ret.setFail("查询日志表失败");
        }
        return ret.setSuccess("查询成功", retpage);
    }

    
}
