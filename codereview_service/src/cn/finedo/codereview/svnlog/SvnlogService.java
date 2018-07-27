/**
 * svn日志信息表管理服务
 * 
 * @version 1.0
 * @since 2018-05-02
 */
package cn.finedo.codereview.svnlog;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewuser;
import cn.finedo.codereview.common.pojo.DopsSvnlog;
import cn.finedo.codereview.common.pojo.DopsSvnmng;
import cn.finedo.codereview.entity.SvnInfoEntity;
import cn.finedo.codereview.entity.SvnParaEntity;
import cn.finedo.codereview.entity.mail.MailEntity;
import cn.finedo.codereview.entity.selfcheck.IssueDetailEntity;
import cn.finedo.codereview.entity.selfcheck.SelfcheckEntity;
import cn.finedo.codereview.svnlog.domain.SvnlogProjectDomain;
import cn.finedo.codereview.svnmng.SvnmngService;
import cn.finedo.codereview.util.CodeviewUtil;
import cn.finedo.codereview.util.StringUtil;
import cn.finedo.common.date.DateUtil;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.fsdp.service.common.configure.ConfigureUtil;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.svnagent.entity.SvnFile;
import cn.finedo.svnagent.entity.SvnLog;
import cn.finedo.svnagent.util.SvnUtil;


/**
 * @Description: svn日志信息管理服务
 * @company Finedo.cn
 * @author zhusf@finedo.com
 * @date 2018年6月5日 下午4:09:09
 * @version v1.0
 */
@Service
@Transactional
@Scope("singleton")
public class SvnlogService {
    private static Logger logger = LogManager.getLogger();

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IDUtil idutil;

    @Autowired
    private SvnmngService svnmngService;
    
    
    /**
     * 定时发送代码评审邮件
     * 
     * @author zhusf
     * @param @return
     * @return ReturnValueDomain<String>
     */
    public ReturnValueDomain<String> sendEmail() {
        logger.debug("触发了发送代码评审的定时器。。。。");
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        // 所有的邮件
        List<MailEntity> mailEntityList = new ArrayList<MailEntity>();
        // 查询昨天0点到今天0点
        List<Date> datelist = getPrevDate(0, 0);
        SvnParaEntity svnParaEntity = new SvnParaEntity();
        svnParaEntity.setStart(DateUtil.format(datelist.get(0), "yyyy-MM-dd HH:mm:ss"));
        svnParaEntity.setEnd(DateUtil.format(datelist.get(1), "yyyy-MM-dd HH:mm:ss"));
        // svnParaEntity.setStart("2018-05-20");
        // svnParaEntity.setEnd("2018-05-29");
        StringBuilder sql1 = new StringBuilder();
        if (jdbcTemplate.isMysql()) {
            sql1.append("select DISTINCT g.svnpath,g.svnaddr,e.projecttype from tb_dops_svnlog g left join tb_dops_svnpathtype e on g.svnpath = e.svnpath where g.lastchangeddate between :start and :end");
        } else {
            sql1.append("select DISTINCT g.svnpath,g.svnaddr,e.projecttype from tb_dops_svnlog g left join tb_dops_svnpathtype e on g.svnpath = e.svnpath where to_char(g.lastchangeddate,'yyyy-mm-dd hh24:mi:ss') between :start and :end");
        }
        List<DopsSvnlog> dopsSvnlogList = null;
        try {
            dopsSvnlogList = jdbcTemplate.query(sql1.toString(), svnParaEntity, DopsSvnlog.class);
        }
        catch (Exception e) {
            logger.error("查询日志文件失败！", e);
            return ret.setFail("查询日志文件失败！");
        }
        logger.debug("查询到最近更新的项目个数：{}", dopsSvnlogList.size());
        // 遍历svn地址，根据项目类型，查询需要发送的人的邮件地址集合
        for (DopsSvnlog dopsSvnlog : dopsSvnlogList) {
            logger.debug("开始查询邮件信息。。。");
            String svnpath = dopsSvnlog.getSvnpath();
            String svnaddr = dopsSvnlog.getSvnaddr();
            // 查询这个项目有哪些人
            List<DopsSvncodeviewuser> userList = queryUserByType(dopsSvnlog);
            // 查询邮件正文
            SvnInfoEntity svnInfoEntity = new SvnInfoEntity();
            svnInfoEntity.setStart(DateUtil.format(datelist.get(0), "yyyy-MM-dd HH:mm:ss"));
            svnInfoEntity.setEnd(DateUtil.format(datelist.get(1), "yyyy-MM-dd HH:mm:ss"));
            // svnInfoEntity.setStart("2018-05-20");
            // svnInfoEntity.setEnd("2018-05-29");
            svnInfoEntity.setSvnpath(svnpath);
            svnInfoEntity.setSvnaddr(svnaddr);
            SvnlogProjectDomain svnlogProjectDomain = svnmngService.querySvnlogForEmail(svnInfoEntity);
            if (!NonUtil.isNon(svnlogProjectDomain)) {
                for (DopsSvncodeviewuser user : userList) {
                    MailEntity mailEntity = new MailEntity();
                    mailEntity.setSvnlogProjectDomain(svnlogProjectDomain);
                    mailEntity.setEmailaddress(user.getEmail());
                    mailEntityList.add(mailEntity);
                }
            }
        }

        logger.debug("未处理邮件个数：{}", mailEntityList.size());
        try {
            doPost(mailEntityList, "notice");
        }
        catch (Exception e) {
            logger.error("调用邮件接口失败，请检查邮件接口服务是否正常！", e);
            return ret.setFail("通知邮件发送失败！");
        }
        return ret.setSuccess("邮件发送结束！");
    }

    
    /**
    * 远程调用邮件接口服务
    * @author zhusf
    * @param @param entity
    * @param @param interfaceName
    * @param @throws Exception
    * @return void
    */ 	
    public void doPost(Object entity, String interfaceName) throws Exception {
        JSONObject obj = new JSONObject();
        // 需要发送的邮件集合
        obj.put("mailEntityList", entity);
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        String emailcallpath = ConfigureUtil.getParamByName("邮件配置", "邮件服务基础路径");
        String url = emailcallpath + interfaceName;
        HttpPost post = new HttpPost(url);
        StringEntity s = new StringEntity(obj.toString(), "utf-8");
        s.setContentEncoding("UTF-8");
        s.setContentType("application/json");// 发送json数据需要设置contentType
        post.setEntity(s);
        HttpResponse res = httpclient.execute(post);
        logger.debug("接口调用返回码：{}", res.getStatusLine().getStatusCode());
    }

    /**
     * svn日志信息定时同步
     * 
     * @author zhusf
     * @param @return
     * @return ReturnValueDomain<String>
     */
    public ReturnValueDomain<String> syncsvnlogForDate() {
        logger.debug("触发了同步svn日志的定时器");
        long start = System.currentTimeMillis();

        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        //获取前一个小时
        List<Date> datelist = getPrevOnehour();
        Date startDate = datelist.get(0);
        Date endDate = datelist.get(1);
        logger.debug("同步开始时间：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDate));
        logger.debug("同步结束时间：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endDate));
        StringBuilder sql = new StringBuilder();
        sql.append("select DISTINCT r.svnpath as svnname,m.svnaddr,m.mnguser,m.mngpwd from tb_dops_svnuserright r left JOIN tb_dops_svnmng m on r.svnid = m.svnid");
        List<DopsSvnmng> dopsSvnmngList = null;
        try {
            dopsSvnmngList = jdbcTemplate.query(sql.toString(), DopsSvnmng.class);
        }
        catch (Exception e) {
            logger.error("查询svn信息失败！", e);
            return ret.setFail("查询svn信息失败！");
        }
        //过滤无效记录
        dopsSvnmngList = fliterDopsSvnmngList(dopsSvnmngList);
        List<DopsSvnlog> dopsSvnlogList = new ArrayList<DopsSvnlog>();
        for (DopsSvnmng dopsSvnmng : dopsSvnmngList) {
            String usercode = dopsSvnmng.getMnguser();
            SvnParaEntity svnParaEntity = new SvnParaEntity();
            svnParaEntity.setUsercode(usercode);
            String pwd = svnmngService.getSvnpwdByUsercode(svnParaEntity);
            String svnaddr = dopsSvnmng.getSvnaddr();
            String svnpath = dopsSvnmng.getSvnname();
            logger.debug("usercode:{}, svnaddr:{}, svnpath:{}",usercode,svnaddr,svnpath);
            List<SvnLog> svnLogList = SvnUtil.viewSvnLogs(usercode, pwd, svnaddr, svnpath, startDate, endDate);
            logger.debug("-----------------------------------------viewSvnLogs完成");
            for (SvnLog svnLog : svnLogList) {
                List<SvnFile> svnFileList = svnLog.getFilelist();
                for (SvnFile svnFile : svnFileList) {
                    String filename = svnFile.getName();
                    // 过滤删除操作和过滤文件格式
                    if (!"D".equals(String.valueOf(svnFile.getAction())) && svnmngService.isJsJavaFile(filename)) {
                        long reversion = svnLog.getReversion();
                        String revision = String.valueOf(reversion);
                        DopsSvnlog dopsSvnlog = new DopsSvnlog();
                        String svnlogid = idutil.getID("svnlogid");
                        dopsSvnlog.setSvnlogid(svnlogid);
                        dopsSvnlog.setSvnaddr(svnaddr);
                        dopsSvnlog.setSvnpath(svnpath);
                        dopsSvnlog.setFilename(filename);
                        dopsSvnlog.setUsercode(usercode);
                        dopsSvnlog.setRevision(revision);
                        dopsSvnlog.setSubmitperson(svnLog.getAuthor());
                        dopsSvnlog.setLastchangeddate(svnLog.getDateStr());
                        dopsSvnlog.setMessage(svnLog.getMessage());
                        dopsSvnlog.setAction(String.valueOf(svnFile.getAction()));
                        dopsSvnlogList.add(dopsSvnlog);
                    }

                }
            }
        }
        logger.debug("插入的大小：{}", dopsSvnlogList.size());

        try {
            //插入日志信息
            queryAndInsertSvnlogs(dopsSvnlogList, "sync");
        }
        catch (Exception e) {
            logger.error("同步svn日志数据失败！", e);
            throw new TransactionException(e);
        }
        logger.debug("用户svn日志数据同步成功！");
        logger.debug("开始同步项目类型表.....");
        String sql3 = "insert into tb_dops_svnpathtype (svnpath,projecttype) values (:svnpath,'company')";
        try {
            jdbcTemplate.batchUpdateNohis(sql3.toString(), dopsSvnlogList);
        }
        catch (Exception e) {}
        logger.debug("同步项目类型表完成！");
        logger.debug("总耗时：{}", (System.currentTimeMillis() - start) / 1000 / 60 + "分钟");
        return ret.setSuccess("日志信息同步完成！");
    }

    /**
     * 从昨天的某小时到今天的某小时
     * 
     * @author zhusf
     * @param @param
     *            starthour 开始时间的小时
     * @param @param
     *            endhour 结束时间的小时
     * @param @return
     * @return List<Date>
     */
    public List<Date> getPrevDate(int endhour, int starthour) {
        List<Date> datelist = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, endhour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date end = calendar.getTime();

        Calendar calendar1 = new GregorianCalendar();
        calendar1.setTime(new Date());
        calendar1.set(Calendar.DATE, calendar1.get(Calendar.DATE) - 1);
        calendar1.set(Calendar.HOUR_OF_DAY, starthour);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date start = calendar1.getTime();

        datelist.add(start);
        datelist.add(end);
        return datelist;
    }

    /**
     * 获取前一小时
     * 
     * @author zhusf
     * @param @return
     * @return List<Date>
     */
    public List<Date> getPrevOnehour() {
        List<Date> datelist = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
        // calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date end = calendar.getTime();

        Calendar calendar1 = new GregorianCalendar();
        calendar1.setTime(new Date());
        calendar1.set(Calendar.DATE, calendar1.get(Calendar.DATE));
        calendar1.set(Calendar.HOUR_OF_DAY, calendar1.get(Calendar.HOUR_OF_DAY) - 1);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date start = calendar1.getTime();

        datelist.add(start);
        datelist.add(end);
        return datelist;
    }
    


    /**
     * 通过项目地址和项目类型，查看邮件发给那些人
     * 
     * @author zhusf
     * @param @param
     *            dopsSvnlog
     * @param @return
     * @return List<DopsSvncodeviewuser>
     */
    public List<DopsSvncodeviewuser> queryUserByType(DopsSvnlog dopsSvnlog) {
        List<DopsSvncodeviewuser> userList = new ArrayList<DopsSvncodeviewuser>();
        if ("company".equals(dopsSvnlog.getProjecttype())) {
            return queryAllHighUser();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select email from tb_sys_person where personid in (select r.personid from tb_sys_user r where r.usercode in (select DISTINCT t.usercode from tb_dops_svnuserright t where svnpath = :svnpath))");
        try {
            userList = jdbcTemplate.query(sql.toString(), dopsSvnlog, DopsSvncodeviewuser.class);
        }
        catch (Exception e) {
            logger.error("根据项目类型查询失败！", e);
            return new ArrayList<DopsSvncodeviewuser>();
        }
        return userList;
    }

    /**
     * 查询所有的代码走查高工人员
     * 
     * @author zhusf
     * @param @return
     * @return List<DopsSvncodeviewuser>
     */
    public List<DopsSvncodeviewuser> queryAllHighUser() {
        List<DopsSvncodeviewuser> userList = new ArrayList<DopsSvncodeviewuser>();
        String sql = "select * from tb_dops_svncodeviewuser";
        try {
            userList = jdbcTemplate.query(sql.toString(), DopsSvncodeviewuser.class);
        }
        catch (Exception e) {
            logger.error("查询所有的高工人员信息失败！", e);
            return new ArrayList<DopsSvncodeviewuser>();
        }

        return userList;
    }

    /**
     * 过滤项目的父级目录
     * 
     * @author zhusf
     * @param @param
     *            dopsSvnmngList
     * @param @return
     * @return List<DopsSvnmng>
     */
    public List<DopsSvnmng> fliterDopsSvnmngList(List<DopsSvnmng> dopsSvnmngList) {
        Iterator<DopsSvnmng> it = dopsSvnmngList.iterator();
        while (it.hasNext()) {
            String svnname = it.next().getSvnname();
            String[] arr = svnname.split("/");
            //目前开发者平台数据库的svn项目，项目URI有的是无效的，暂通过规则/xx/xx认定是有效的项目。分割后的数组长度是3。
            if (arr.length != 3) {
                logger.debug("过滤了{}",svnname);
                it.remove();
            }
        }
        return dopsSvnmngList;
    }

    /**
     * 定时发送代码走查结果邮件
     * 
     * @author zhusf
     * @param @return
     * @return ReturnValueDomain<String>
     */
    public ReturnValueDomain<String> sendResultEmail() {
        logger.debug("触发了发送代码走查结果的定时器。。。。");
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        // 所有的邮件
        List<MailEntity> mailEntityList = new ArrayList<MailEntity>();
        String sql1 = "select DISTINCT g.svnpath,g.svnaddr,e.projecttype from tb_dops_svnlog g left join tb_dops_svnpathtype e on g.svnpath = e.svnpath";
        List<DopsSvnlog> dopsSvnlogList = null;
        try {
            dopsSvnlogList = jdbcTemplate.query(sql1.toString(), DopsSvnlog.class);
        }
        catch (Exception e) {
            logger.error("查询svn日志信息失败！", e);
            return ret.setFail("查询svn日志信息失败！");
        }
        logger.debug("代码走查结果，项目个数：{}", dopsSvnlogList.size());
        // 遍历svn地址，根据项目类型，查询需要发送的人的邮件地址集合
        logger.debug("代码走查结果，开始查询邮件信息。。。");
        for (DopsSvnlog dopsSvnlog : dopsSvnlogList) {
            // 查询这个项目有哪些人
            List<DopsSvncodeviewuser> userList = queryUserByType(dopsSvnlog);
            // 查询邮件正文
            List<Date> datelist = getPrevDate(0, 0);
            SvnInfoEntity svnInfoEntity = new SvnInfoEntity();
            svnInfoEntity.setStart(DateUtil.format(datelist.get(0), "yyyy-MM-dd HH:mm:ss"));
            svnInfoEntity.setEnd(DateUtil.format(datelist.get(1), "yyyy-MM-dd HH:mm:ss"));
            svnInfoEntity.setSvnpath(dopsSvnlog.getSvnpath());
            svnInfoEntity.setSvnaddr(dopsSvnlog.getSvnaddr());
            SvnlogProjectDomain svnlogProjectDomain = svnmngService.querySvnlogForResultEmail(svnInfoEntity);
            if (!NonUtil.isNon(svnlogProjectDomain)) {
                for (DopsSvncodeviewuser user : userList) {
                    MailEntity mailEntity = new MailEntity();
                    mailEntity.setSvnlogProjectDomain(svnlogProjectDomain);
                    mailEntity.setEmailaddress(user.getEmail());
                    mailEntityList.add(mailEntity);
                }
            }
        }

        logger.debug("代码走查结果，未处理邮件个数：{}", mailEntityList.size());
        try {
            doPost(mailEntityList, "noticeResult");
        }
        catch (Exception e) {
            logger.error("调用邮件接口失败，请检查邮件接口服务是否正常！", e);
            return ret.setFail("走查结果邮件发送失败！");
        }
        return ret.setSuccess("代码走查结果邮件发送结束！");
    }
    
    
    
   	
    /**
    * 不存在则插入
    * @author zhusf
    * @param @param dopsSvnlogList
    * @param @param type "tree"标识通过打开目录树的插入。"sync"标识定时同步的插入。
    * @param @return
    * @param @throws Exception
    * @return List<DopsSvnlog>
    */ 	
    public synchronized List<DopsSvnlog> queryAndInsertSvnlogs(List<DopsSvnlog> dopsSvnlogList, String type) throws Exception {
        // 打开目录树时的插入
        if ("tree".endsWith(type)) {
            if (NonUtil.isNon(dopsSvnlogList)) {
                throw new RuntimeException("通过API获取svn日志异常！");
            }
            // 检查这个日志是否已存在
            DopsSvnlog checkdopsSvnlog = dopsSvnlogList.get(0);
            String sql = "select * from tb_dops_svnlog where filename = :filename and revision = :revision";
            List<DopsSvnlog> dopsSvnlogListTmp = null;
            try {
                dopsSvnlogListTmp = jdbcTemplate.query(sql, checkdopsSvnlog, DopsSvnlog.class);
            }
            catch (DataAccessException e) {
                logger.error("查询日志表异常", e);
                throw new RuntimeException("查询日志表异常");
            }
            if (NonUtil.isNon(dopsSvnlogListTmp)) {
                // 当前日志表不存在
                String sql1 = "insert into tb_dops_svnlog (svnlogid,svnaddr,svnpath,filename,usercode,submitperson,revision,optdate,lastchangeddate,message,action) values (:svnlogid,:svnaddr,:svnpath,:filename,:usercode,:submitperson,:revision,now(),STR_TO_DATE(:lastchangeddate,'%Y-%m-%d %H:%i:%s'),:message,:action)";
                try {
                    jdbcTemplate.batchUpdateNohis(sql1.toString(), dopsSvnlogList);
                }
                catch (DataAccessException e) {
                    logger.error("树状插入日志表异常", e);
                    throw new RuntimeException("树状插入日志表异常");
                }
                logger.debug("通过目录树方式，生成了svn日志，{}",dopsSvnlogList.get(0).toString());
                // 不存在插入，返回插入的结果集
                return dopsSvnlogList;
            }
            // 存在，返回查询的结果集
            return dopsSvnlogListTmp;
        }
        if ("sync".equals(type)) {
            // 定时同步，不存在即插入。
            String sql = "INSERT INTO tb_dops_svnlog (svnlogid,svnaddr,svnpath,filename,usercode,submitperson,revision,optdate,lastchangeddate,message,action) SELECT :svnlogid,:svnaddr,:svnpath,:filename,:usercode,:submitperson,:revision,now(),STR_TO_DATE(:lastchangeddate,'%Y-%m-%d %H:%i:%s'),:message,:action FROM dual WHERE not exists (select * from tb_dops_svnlog where filename=:filename and revision = :revision)";
            try {
                jdbcTemplate.batchUpdate(sql, dopsSvnlogList);
            }
            catch (Exception e) {
                logger.error("同步方法插入日志表异常", e);
                throw new RuntimeException("同步方法插入日志表异常");
            }
        }
        logger.debug("返回参数结果集");
        return dopsSvnlogList;
    }
    
    /**
    * 定时对代码进行检查，并邮件通知
    * @author zhusf
    * @param @return
    * @return ReturnValueDomain<String>
    */ 	
    public ReturnValueDomain<String> selfcheck() {
        logger.debug("触发了文件自检的定时器。。。。");
        List<SelfcheckEntity> selfcheckEntityList = new ArrayList<SelfcheckEntity>();
        //读取仓库信息
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        String sql = "select r.passwd,r.usercode,g.svnaddr from tb_dops_svnuser r right join tb_dops_svnmng g on r.usercode = g.mnguser";
        List<SvnParaEntity> svnParaEntityList =jdbcTemplate.query(sql, SvnParaEntity.class);
        for (SvnParaEntity svnParaEntity : svnParaEntityList) {
            String realpwd = svnmngService.getSvnpwdByUsercode(svnParaEntity);
            svnParaEntity.setPasswd(realpwd);
        }
        //获取要检查的svn日志
        Date[] dates = CodeviewUtil.getWeekDay();
        SvnInfoEntity svnInfoEntity = new SvnInfoEntity();
        svnInfoEntity.setStart(DateUtil.format(dates[0], "yyyy-MM-dd"));
        svnInfoEntity.setEnd(DateUtil.format(dates[1], "yyyy-MM-dd"));
        
//        svnInfoEntity.setStart("2018-07-16");
//        svnInfoEntity.setEnd("2018-07-22");
        String sql1 = "select filename,MAX(revision) as revision,svnaddr from tb_dops_svnlog where lastchangeddate between :start and :end GROUP BY filename";
        List<DopsSvnlog> svnlogList = null;
        try {
            svnlogList = jdbcTemplate.query(sql1, svnInfoEntity,DopsSvnlog.class);
        }
        catch (DataAccessException e) {
            logger.error("查询日志表失败!",e);
            throw new RuntimeException(e);
        }
        for (DopsSvnlog dopsSvnlog : svnlogList) {
            String filename = dopsSvnlog.getFilename();
            //对java文件进行检查
            if(!filename.endsWith(".java")){
                continue;
            }
            String svnaddr = dopsSvnlog.getSvnaddr();
            long revision = Long.parseLong(dopsSvnlog.getRevision());
            SvnParaEntity svnParaEntityForsql = new SvnParaEntity();
            //匹配文件的仓库信息
            for (SvnParaEntity svnParaEntity : svnParaEntityList) {
                if(svnaddr.equals(svnParaEntity.getSvnaddr())){
                    svnParaEntityForsql = svnParaEntity;
                    break;
                }
            }
            String usercode = svnParaEntityForsql.getUsercode();
            String passwd = svnParaEntityForsql.getPasswd();
            String checkstr = svnmngService.readSvnFile(usercode, passwd, svnaddr, filename, revision);
            if(NonUtil.isNon(checkstr)){
                continue;
            }
            //按行处理，记录行号
            String[] items = checkstr.split("\n");
            SelfcheckEntity selfcheckEntity = new SelfcheckEntity();
            selfcheckEntity.setFilename(StringUtil.concatTwoPath(svnaddr, filename));
            selfcheckEntity.setRevision(dopsSvnlog.getRevision());
            List<IssueDetailEntity> results = new ArrayList<IssueDetailEntity>();
            for(int i=0;i<items.length;i++){
                String checkitem = items[i];
                //注释代码过滤
                if(checkitem.trim().indexOf("//") == 0){
                    continue;
                }
                List<String> ipstrs = CodeviewUtil.getIp(checkitem);
                List<String> domainstrs = CodeviewUtil.getDomainName(checkitem);
                List<String> logstrs = CodeviewUtil.getLoggerInfo(checkitem);
                List<String> outstrs = CodeviewUtil.getSystemout(checkitem);
                
                IssueDetailEntity detail = new IssueDetailEntity();
                List<String> contents = detail.getContents();
                
                //检查IP
                if(NonUtil.isNotNon(ipstrs)){
                    contents.addAll(ipstrs);
                }
                //检查域名
                if(NonUtil.isNotNon(domainstrs)){
                    contents.addAll(domainstrs);
                }
                //检查日志
                if(NonUtil.isNotNon(logstrs)){
                    contents.addAll(logstrs);
                }
                //检查控制台打印
                if(NonUtil.isNotNon(outstrs)){
                    contents.addAll(outstrs);
                }
                if(NonUtil.isNotNon(contents)){
                    detail.setRownumber(String.valueOf(i+1));
                    results.add(detail);
                }
            }
            if(NonUtil.isNotNon(results)){
                selfcheckEntity.setResults(results);
                selfcheckEntityList.add(selfcheckEntity);
            }
        }
        logger.debug("检查到的文件个数：{}",selfcheckEntityList.size());
        try {
            doPost(selfcheckEntityList, "noticeSelfcheck");
        }
        catch (Exception e) {
            logger.error("调用检查坏代码邮件接口失败，请检查邮件接口服务是否正常！", e);
            return ret.setFail("检查坏代码邮件发送失败！");
        }
        return ret.setSuccess("检查坏代码邮件发送结束！");
    }
    
    
    //测试 评审统计表的默认数据
//    public ReturnValueDomain<String> syncforcount(){
//        String sql = "select DISTINCT filename,revision,personid,projectname from tb_dops_svncomment";
//       List<DopsSvncomment> dopsSvncommentList = jdbcTemplate.query(sql, DopsSvncomment.class);
//        for (DopsSvncomment dopsSvncomment : dopsSvncommentList) {
//            dopsSvncomment.setCommentid(idutil.getID("commentid"));
//        }
//        String sql1 = "insert into tb_dops_svncommentforcount (commentid,filename,revision,personid,projectname,optdate) values (:commentid,:filename,:revision,:personid,:projectname,now())";
//        try {
//            jdbcTemplate.batchUpdateNohis(sql1, dopsSvncommentList);
//        }
//        catch (DataAccessException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        String sql = "insert into tb_dops_svnlog_tmp (svnlogid) values ('1')";
//        String sql1 = "insert into tb_dops_svnlog_tmp (svnlogid) values ('2222222222222222222222222222222222222222222222')";
//        String sql2 = "insert into tb_dops_svnlog_tmp (svnlogid) values ('2')";
//        try {
//            jdbcTemplate.update(sql);
//            jdbcTemplate.update(sql2);
//        }
//        catch (Exception e) {
//            logger.error(e.toString());
//            throw new TransactionException(e);
//        }
//        return null;
//    }


    
    //测试 添加评审小组的默认数据
    public ReturnValueDomain<String> syncgroup() {
//        String sql = "select DISTINCT t.svnpath,g.svnaddr from tb_dops_svnuserright t LEFT JOIN tb_dops_svnmng g on t.svnid = g.svnid";
//        // 查询所有的小组
//        List<DopsSvncodeviewgroup> groupList = null;
//        groupList = jdbcTemplate.query(sql, DopsSvncodeviewgroup.class);
//        Iterator<DopsSvncodeviewgroup> it = groupList.iterator();
//        while (it.hasNext()) {
//            String svnpath = it.next().getSvnpath();
//            String[] arr = svnpath.split("/");
//            if (arr.length != 3) {
//                it.remove();
//            }
//        }
//        // 创建所有的小组
//        for (DopsSvncodeviewgroup dopsSvncodeviewgroup : groupList) {
//            dopsSvncodeviewgroup.setCgid(idutil.getID("dopssvncodeviewgroup"));
//        }
//        String sql1 = "insert into tb_dops_svncodeviewgroup (cgid,svnaddr,svnpath,repotype,createperson,optdate,grouptype) VALUE (:cgid,:svnaddr,:svnpath,'svn','superuser',NOW(),'common')";
//
//        try {
//            jdbcTemplate.batchUpdate(sql1, groupList);
//        }
//        catch (DataAccessException e) {
//            e.printStackTrace();
//        }
//        // 创建小组成员表 对小组表遍历
//        for (DopsSvncodeviewgroup dopsSvncodeviewgroup : groupList) {
//            List<DopsProjectmember> dopsProjectMemberList = new ArrayList<DopsProjectmember>();
//            // 查询这个项目有哪些人
//            String sql2 = "select DISTINCT usercode from tb_dops_svnuserright where svnpath = :svnpath";
//            List<DopsSvnuserright> dopsSvnuserrightList = jdbcTemplate.query(sql2, dopsSvncodeviewgroup, DopsSvnuserright.class);
//            for (DopsSvnuserright dopsSvnuserright : dopsSvnuserrightList) {
//                DopsProjectmember dopsProjectMember = new DopsProjectmember();
//                dopsProjectMember.setCpid(idutil.getID("dopssvncodeviewperson"));
//                dopsProjectMember.setCgid(dopsSvncodeviewgroup.getCgid());
//                dopsProjectMember.setUsercode(dopsSvnuserright.getUsercode());
//                dopsProjectMemberList.add(dopsProjectMember);
//            }
//            // 插入该项目的项目成员表
//            String sql3 = "insert into tb_dops_projectmember (cpid,usercode,cgid,addtime,addoptuser,userstate)values (:cpid,:usercode,:cgid,now(),'2014046','1')";
//            try {
//                jdbcTemplate.batchUpdate(sql3, dopsProjectMemberList);
//            }
//            catch (DataAccessException e) {
//                e.printStackTrace();
//            }
//        }
        
        String sql = "select n.projectid,g.svnaddr,n.svnpath from tb_dops_projectsvn n left join tb_dops_svnmng g on n.svnid = g.svnid";
        List<DopsSvncodeviewproject> list = null;
        list = jdbcTemplate.query(sql, DopsSvncodeviewproject.class);
        for (DopsSvncodeviewproject dopsSvncodeviewproject : list) {
            String sql1 = "update tb_dops_project set svnaddr = :svnaddr,svnpath = :svnpath where projectid = :projectid";
            jdbcTemplate.update(sql1,dopsSvncodeviewproject);
        }
        
        return null;

    }
//    
//    //测试 生成评审小组权限表默认数据
//    public ReturnValueDomain<String> syncgroupauth() {
//        
//        
////        String sql = "select t.projectmng as usercode,n.svnpath,n.svnid from tb_dops_project t RIGHT JOIN tb_dops_projectsvn n on t.projectid = n.projectid";
////        List<DopsSvncodeviewgroupuserrole> groupuserroleList = null;
////        try {
////            groupuserroleList = jdbcTemplate.query(sql, DopsSvncodeviewgroupuserrole.class);
////        }
////        catch (DataAccessException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////        String sql1 = "insert into tb_dops_svncodeviewgroupuserrole set usercode = :usercode,grouprole = 'pm',svnpath = :svnpath,svnid = :svnid";
////        try {
////            jdbcTemplate.batchUpdate(sql1, groupuserroleList);
////        }
////        catch (DataAccessException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
//        
//        
////        String sql = "select * from tb_dops_svncodeviewgroup";
////        List<DopsSvncodeviewgroup> dopsSvncodeviewgroupList = jdbcTemplate.query(sql, DopsSvncodeviewgroup.class);
////        for (DopsSvncodeviewgroup dopsSvncodeviewgroup : dopsSvncodeviewgroupList) {
////            String svnpath = dopsSvncodeviewgroup.getSvnpath();
////            String str = getFromIndex(svnpath, "/", 2);
////            String s = str.concat("评审小组");
////            dopsSvncodeviewgroup.setGroupname(s);
////        }
////        String sql1 = "update tb_dops_svncodeviewgroup set groupname = :groupname where cgid = :cgid";
////        try {
////            jdbcTemplate.batchUpdate(sql1, dopsSvncodeviewgroupList);
////        }
////        catch (DataAccessException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
//        
//        
//        String sql = "select * from tb_dops_svncodeviewuser";
//        List<DopsSvncodeviewuser> userList = jdbcTemplate.query(sql, DopsSvncodeviewuser.class);
//        for (DopsSvncodeviewuser dopsSvncodeviewuser : userList) {
//            String cuid = idutil.getID("projectmemberid");
//            dopsSvncodeviewuser.setCuid(cuid);
//            String sql1 = "insert into tb_dops_projectmember (cpid,usercode,cgid,addtime,addoptuser,userstate) values (:cuid,:usercode,'FD426201806000388888',now(),'2014046','1')";
//            jdbcTemplate.update(sql1,dopsSvncodeviewuser);
//        }
////
//        return null;
//
//    }
//    
//    private String getFromIndex(String str, String modelStr, Integer count) {
//        // 对子字符串进行匹配
//        Matcher slashMatcher = Pattern.compile(modelStr).matcher(str);
//        int index = 0;
//        // matcher.find();尝试查找与该模式匹配的输入序列的下一个子序列
//        while (slashMatcher.find()) {
//            index++ ;
//            // 当modelStr字符第count次出现的位置
//            if (index == count) {
//                break;
//            }
//        }
//        // matcher.start();返回以前匹配的初始索引。
//        index = slashMatcher.start();
//        return str.substring(index+1);
//    }

}
