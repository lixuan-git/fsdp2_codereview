package cn.finedo.codereview.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.finedo.common.date.DateUtil;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysLoginlog;
import cn.finedo.common.pojo.SysOrganization;
import cn.finedo.common.pojo.SysOrganizationtree;
import cn.finedo.common.pojo.SysPerson;
import cn.finedo.common.pojo.SysRole;
import cn.finedo.common.pojo.SysUser;
import cn.finedo.common.pojo.SysUserrole;
import cn.finedo.common.protocol.HttpClientUtil;
import cn.finedo.common.sec.DES;
import cn.finedo.codereview.login.domain.OAAccountDomain;
import cn.finedo.codereview.login.domain.OAOrganization;
import cn.finedo.codereview.login.domain.OAUser;
import cn.finedo.codereview.login.domain.ReturnValue;
import cn.finedo.codereview.svnmng.SvnmngService;
import cn.finedo.fsdp.service.common.configure.ConfigureUtil;
import cn.finedo.fsdp.service.common.err.ErrorUtil;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.fsdp.service.login.domain.AccountDomain;
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.fsdp.service.login.domain.UserRightViewDomain;
import cn.finedo.fsdp.service.user.UserService;
import cn.finedo.fsdp.service.user.domain.UserInfoDomain;
import cn.finedo.fsdp.service.user.domain.UserInfoListDomain;
@Service
@Scope("singleton")
public class DevLoginService {
    
	private static Logger logger = LogManager.getLogger();  

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbctemplate;

    @Autowired
    private IDUtil idutil;

    @Autowired
    private UserService userService;
    
    @Autowired
    private SvnmngService svnService;
    
	public ReturnValueDomain<LoginDomain> localauth(AccountDomain accountdomain) {
		ReturnValueDomain<LoginDomain> ret=new ReturnValueDomain<LoginDomain>();
		
		String userid=accountdomain.getAccount();
		String passwd=accountdomain.getPasswd();
		
		String url=ConfigureUtil.getParamByName("FSDP单点登录", 	"用户名与密码单点登录URL");
	    	
		//调用OA服务验证密码
		url=url + "&userid=" + userid + "&password=" + java.net.URLEncoder.encode(passwd);
		String jsonstr =HttpClientUtil.httpGetString (url);
			
		ReturnValue<OAAccountDomain> oaret=JSON.parseObject(jsonstr, new TypeReference<ReturnValue<OAAccountDomain>>() {} );
		
		logger.debug(url);
		OAAccountDomain account=null;
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
			return ret.setFail("用户名或密码错误!");
			
		// 生成token
		String token=UUID.randomUUID().toString();
		
		//查询用户信息
        SysUser sysuser = null;
        List<SysUser> userlists = queryAccount(account.getUserID());
        if(NonUtil.isNon(userlists)){
            addSysUser(account);
        }
        
        userlists = queryAccount(account.getUserID());
        sysuser = userlists.get(0);
        
        // 缓存用户信息
        LoginDomain logindomain = new LoginDomain();
        logindomain.setToken(token);
        logindomain.setLogintime(new Date());
                
        logindomain.setSysuser(sysuser);            

        AccountDomain openaccount = new AccountDomain();
        openaccount.setIpaddr(accountdomain.getIpaddr());
        openaccount.setSessionid(token);
        openaccount.setLoginsource("WEB应用");
        ReturnValueDomain<LoginDomain> rettoken=generateToken(sysuser, openaccount);
        if(rettoken.hasFail())
            return rettoken;
        logindomain.setUserrightlist(rettoken.getObject().getUserrightlist());
        logindomain.setOtherrolelist(rettoken.getObject().getOtherrolelist());
        
        return ret.setSuccess("OA登录成功", logindomain);
		
	}
		
	public ReturnValueDomain<LoginDomain> getoauser(String tokenid) {
		ReturnValueDomain<LoginDomain> ret=new ReturnValueDomain<LoginDomain>();
		
		String url=ConfigureUtil.getParamByName("FSDP单点登录", 	"TOKEN单点登录URL");
		
		//调用OA服务验证密码
		url= url + "&tokenid=" + tokenid;
		String jsonstr =HttpClientUtil.httpGetString (url);
		
		ReturnValue<OAAccountDomain> oaret = JSON.parseObject(jsonstr, new TypeReference<ReturnValue<OAAccountDomain>>() {} );
		
		OAAccountDomain account=null;
		if (oaret.getRetCode().equals("0000")) {
			if(oaret.getList().size() > 0)
				account = oaret.getList().get(0);
		}
				
		if(account == null)
			return ret.setFail("根据tokenid获取用户信息失败!");
		
		//查询用户信息
        SysUser sysuser = null;
        List<SysUser> userlists = queryAccount(account.getUserID());
        if(NonUtil.isNon(userlists)){
            addSysUser(account);
        }
        userlists = queryAccount(account.getUserID());
        sysuser = userlists.get(0);
        
        // 缓存用户信息
        LoginDomain logindomain = new LoginDomain();
        logindomain.setToken(tokenid);
        logindomain.setLogintime(new Date());
                
        logindomain.setSysuser(sysuser);            

        AccountDomain openaccount = new AccountDomain();
        openaccount.setIpaddr("127.0.0.1");
        openaccount.setSessionid(tokenid);
        openaccount.setLoginsource("WEB应用");
        ReturnValueDomain<LoginDomain> rettoken=generateToken(sysuser, openaccount);
        if(rettoken.hasFail())
            return rettoken;
        logindomain.setUserrightlist(rettoken.getObject().getUserrightlist());
        logindomain.setOtherrolelist(rettoken.getObject().getOtherrolelist());
        
        return ret.setSuccess("OA登录成功", logindomain);
	}

    private void addSysUser(OAAccountDomain account){
        
        String ssorole = ConfigureUtil.getParamByName("系统基本参数",     "单点登录用户默认岗位角色");
        
        UserInfoListDomain userlist = new UserInfoListDomain();
        UserInfoDomain userinfo = new UserInfoDomain();
        SysUser user = new SysUser();
        user.setUsercode(account.getUserID());
        user.setPersonname(account.getUserName());
        user.setEmail(account.getEmail());
        user.setLoginpasswd(account.getUserID().toLowerCase());
        user.setGender("男");
        user.setState("有效");
        user.setEffdate(DateUtil.format(new Date(), "yyyy-MM-dd"));
        user.setExpdate("2099-12-31");
        userinfo.setUser(user);

        SysUserrole stdrole = new SysUserrole();
        stdrole.setRoleid(ssorole);
        userinfo.setStdrole(stdrole);
        
        List<UserInfoDomain> userlistdomain = new ArrayList<UserInfoDomain>();
        userlistdomain.add(userinfo);
        userlist.setUserlist(userlistdomain);
        userService.add(userlist);
        
        //增加svn用户
        logger.debug("svnuseradd======={}", svnService.addsvnuser(account));
    }

    private ReturnValueDomain<LoginDomain> generateToken(SysUser user, AccountDomain account) {
        ReturnValueDomain<LoginDomain> ret=new ReturnValueDomain<LoginDomain>();
        
        // 生成token
        String token=UUID.randomUUID().toString();
        
        // 登录日志
        SysLoginlog sysloginlog = new SysLoginlog();
        sysloginlog.setLoginid(idutil.getID("sysloginlog"));
        sysloginlog.setToken(token);
        sysloginlog.setState("登入");
        sysloginlog.setUserid(user.getUserid());
        sysloginlog.setIpaddr(account.getIpaddr());
        sysloginlog.setLoginsource(account.getLoginsource());
        addLoginlog(sysloginlog);
        
        // 查询用户岗位角色信息
        List<SysRole> rolelist=queryUserRole(user.getUserid());
        List<SysRole> userstdrolelist = new ArrayList<SysRole>();
        List<SysRole> otherrolelist = new ArrayList<SysRole>();
        for(SysRole sysrole : rolelist) {
            if(sysrole.getRemark().equals("基本岗位")) {
                // 用户基本岗位
                userstdrolelist.add(sysrole);
            }else {
                // 用户兼任岗位
                otherrolelist.add(sysrole);
            }
        }
        
        if(userstdrolelist.size() != 1) {
            return ErrorUtil.getError("ER_LOGIN009");
        }
        
        SysRole stdrole=userstdrolelist.get(0);
        // 查询用户 基本岗位组织节点信息
        SysOrganization sysorg=queryOrg(stdrole);
        if(NonUtil.isNon(sysorg)) {
            return ErrorUtil.getError("ER_LOGIN010");
        }
        //logger.debug("rolelist={}", rolelist.toString());
        // 查询用户权限视图
        List<UserRightViewDomain> userrightlist=queryUserRightView(account.getLoginsource(), rolelist);
        //logger.debug("userrightlist={}", userrightlist.toString());
        // 剔除权限视图树中无叶子节点的子树
        userrightlist=refreshTree(userrightlist);
        //logger.debug("userrightlist={}", userrightlist.toString());
        
        // 缓存用户信息
        LoginDomain logindomain = new LoginDomain();
        logindomain.setSessionid(account.getSessionid());
        logindomain.setToken(token);
        logindomain.setSysuser(user);
        logindomain.setStdrole(stdrole);
        logindomain.setOtherrolelist(otherrolelist);
        logindomain.setUserrightlist(userrightlist);
        logindomain.setSysorg(sysorg);
        logindomain.setLoginsource(account.getLoginsource());
        logindomain.setLogintime(new Date());
        
        // 用户信息的基本岗位必须有组织节点, 用于数据权限控制
        logindomain.setOrgnode(stdrole.getOrgid());
        //logger.debug("logindomain={}", logindomain);
        return ret.setSuccess("创建Token成功", logindomain);
    }

    // 增加登录日志
    private void addLoginlog(SysLoginlog sysloginlog){
        String table = "tb_sys_loginlog_"+DateUtil.getNowTime("yyMM");
        
        StringBuffer sql = new StringBuffer("INSERT INTO " + table + "(loginid, token, state, userid, ipaddr, loginsource, logindate) VALUES ");
        if(jdbctemplate.isMysql()) {
            sql.append("(:loginid, :token, :state, :userid, :ipaddr, :loginsource, now())");
        }else if(jdbctemplate.isSqlserv()){
            sql.append("(:loginid, :token, :state, :userid, :ipaddr, :loginsource, getdate())");
        }else{
            sql.append("(:loginid, :token, :state, :userid, :ipaddr, :loginsource, sysdate)");
        }
        
        try{
            jdbctemplate.update(sql.toString(), sysloginlog);
        }catch(Exception e){
            logger.error(e);
            throw new TransactionException(e);
        }
    }
    
    // 查询用户岗位角色信息
    private List<SysRole> queryUserRole(String userid){
        List<SysRole> rolelist=null;
        
        StringBuffer sql=new StringBuffer("SELECT b.orgid, b.roleid, b.rolename, b.rolelvl, b.roletype, b.dutydesc, b.usercount, b.state, a.type as remark ");
        sql.append("FROM tb_sys_userrole a, tb_sys_role b ");
        sql.append("WHERE a.userid=:userid AND a.roleid=b.roleid");
        
        SysUserrole sysuserrole=new SysUserrole();
        sysuserrole.setUserid(userid);
        try{
            rolelist = jdbctemplate.query(sql.toString(), sysuserrole, SysRole.class);
        }catch(Exception e){
            logger.error(e);
            return null;
        }
        
        return rolelist;
    }
    
    // 查询用户权限视图信息
    private List<UserRightViewDomain> queryUserRightView(String loginsource, List<SysRole> rolelist){
        List<UserRightViewDomain> userrightviewlist=null;
        
        String roleid="";
        for(SysRole sysrole : rolelist) {
            roleid = roleid + "'" + sysrole.getRoleid() + "',";
        }
        roleid=roleid.substring(0, roleid.length() - 1);
        
        StringBuffer sql=new StringBuffer("SELECT b.nodeid, b.nodename, b.parentnodeid, b.orderseq, c.moduleid, c.rightid, c.rightname, b.rightentry, b.isnavigation, b.remark ");
        sql.append("FROM tb_sys_view a, tb_sys_viewtree b ");
        sql.append("LEFT JOIN tb_sys_moduleright c ON b.rightid=c.rightid AND EXISTS(SELECT 1 FROM tb_sys_roleright d WHERE c.rightid=d.rightid AND d.roleid IN (" + roleid + ")) ");
        sql.append("WHERE a.viewid='" + loginsource + "' AND a.viewid=b.viewid order by b.orderseq");
        logger.debug(sql);
        try{
            userrightviewlist = jdbctemplate.query(sql.toString(), UserRightViewDomain.class);
        }catch(Exception e){
            logger.error(e);
            return null;
        }
        
        return userrightviewlist;
    }
    
    // 查询用户基本岗位组织节点信息
    private SysOrganization queryOrg(SysRole stdrole) {
        StringBuffer sql=new StringBuffer("SELECT a.orgid, a.orgname, a.orgtype, a.orgdesc, a.parentorgid, a.linkman, a.phoneno, a.address, a.remark ");
        sql.append("FROM tb_sys_organization a, tb_sys_role b WHERE a.orgid=b.orgid AND b.roleid=:roleid AND b.roletype='组织岗位角色'");
        
        List<SysOrganization> orglist=new ArrayList<SysOrganization>();
        try{
            orglist = jdbctemplate.query(sql.toString(), stdrole, SysOrganization.class);
        }catch(Exception e){
            logger.error(e);
            return null;
        }
                
        if(NonUtil.isNon(orglist)) {
            return null;
        }
        SysOrganization sysorg=orglist.get(0);
        
        sql=new StringBuffer("SELECT orgid, orgname, orgtype, uporgid, uporgname FROM tb_sys_organizationtree WHERE orgid=:orgid ORDER BY uplevel DESC");
        List<SysOrganizationtree> treelist=new ArrayList<SysOrganizationtree>();
        try{
            treelist = jdbctemplate.query(sql.toString(), sysorg, SysOrganizationtree.class);
        }catch(Exception e){
            logger.error(e);
            return null;
        }
                
        String orgname="";
        for(SysOrganizationtree orgtree : treelist) {
            orgname=orgname + orgtree.getUporgname() + "/";
        }
        if(orgname.endsWith("/"))
            orgname=orgname + sysorg.getOrgname();
        
        if(orgname.length() >0 )
            sysorg.setOrgname(orgname);
        
        return sysorg;
    }
    
    // 剔除权限视图树中无叶子节点的子树
    private List<UserRightViewDomain> refreshTree(List<UserRightViewDomain> rightviewlist) {
        HashMap<String, String> nodemap=new HashMap<String, String>();

        // 1. 先找出所有叶子节点
        List<UserRightViewDomain> leafnodelist=new ArrayList<UserRightViewDomain>();
        for(UserRightViewDomain userrightview : rightviewlist){
            String rightid=userrightview.getRightid();
            if(NonUtil.isNotNon(rightid)) {
                leafnodelist.add(userrightview);
                nodemap.put(userrightview.getNodeid(), userrightview.getNodeid());
            }
        }
                
        // 2. 根据每个叶子节点找父节点，并且DISTINCT
        HashMap<String, String> parentnodehash=new HashMap<String, String>();
        for(UserRightViewDomain userrightview : leafnodelist){
            String parentid=userrightview.getParentnodeid();
            //parentnodehash.put(parentid, parentid);
            recursionParentnode(rightviewlist, parentnodehash, parentid);
        }
        
        // 3. 将父节点与叶子节点合并返回
        for(UserRightViewDomain userrightview : rightviewlist){
            String nodeid=userrightview.getNodeid();
            if(NonUtil.isNotNon(parentnodehash.get(nodeid))) {
                nodemap.put(userrightview.getNodeid(), userrightview.getNodeid());
            }
        }
        
        List<UserRightViewDomain> retlist=new ArrayList<UserRightViewDomain>();
        for(UserRightViewDomain userrightview : rightviewlist){
            String nodeid=userrightview.getNodeid();
            if(NonUtil.isNotNon(nodemap.get(nodeid))) {
                retlist.add(userrightview);
            }
        }
        
        return retlist;
    }
    
    /**
     * 多级菜单，递归获取叶子节点的所有上级nodeid
     * @author	feng
     * @date 2017年12月26日 下午2:48:58
     * @param rightviewlist
     * @param parentnodehash
     * @param nodeid
     */
    private void recursionParentnode(List<UserRightViewDomain> rightviewlist, HashMap<String, String> parentnodehash, String parentid) {
    	if(NonUtil.isNon(parentid))
    		return;
    	if(!parentnodehash.containsKey(parentid))
    		parentnodehash.put(parentid, parentid);
    	for(UserRightViewDomain userright : rightviewlist) {
    		if(!parentid.equals(userright.getNodeid())) {
    			continue;
    		}
    		recursionParentnode(rightviewlist, parentnodehash, userright.getParentnodeid());
    	}
    }
    

    // 查询用户信息
    private List<SysUser> queryAccount(String account){
        List<SysUser> userlist = null;
        
        StringBuffer sql =new StringBuffer("SELECT a.userid, a.usercode, a.loginpasswd, a.effdate, a.expdate, a.state, a.createtime, a.lastbptime, a.lastlptime, ");
        sql.append("b.personid, b.personname, b.gender, b.idnumber, b.phoneno, b.email, b.postcode, b.address, b.photofile,b.remark ");
        sql.append("FROM tb_sys_user a, tb_sys_person b ");
        sql.append("WHERE a.personid=b.personid AND (a.userid=:userid OR usercode=:usercode OR a.authcode=:authcode OR b.idnumber=:idnumber OR b.phoneno=:phoneno OR b.email=:email)");
        //System.out.println("=="+sql.toString());
        SysUser sysuser = new SysUser();
        sysuser.setUserid(account);
        sysuser.setUsercode(account);
        sysuser.setAuthcode(account);
        sysuser.setIdnumber(account);
        sysuser.setPhoneno(account);
        sysuser.setEmail(account);
        try{
            userlist = jdbctemplate.query(sql.toString(), sysuser, SysUser.class);
        }catch(Exception e){
            logger.error(e);
            return null;
        }
        
        return userlist;
    }

    /**
     * 同步OA用户信息
     * @author  Finedo
     * @date 2017年8月11日 下午7:18:24
     * @return
     */
    public ReturnValueDomain<String> syncOAUser(){
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        try{
            String url = ConfigureUtil.getParamByName("FSDP单点登录",   "OA用户同步服务");
            String jsonstr =HttpClientUtil.httpGetString (url);
            List<OAUser> oaret=JSON.parseObject(jsonstr, new TypeReference<List<OAUser>>(){});
            if(NonUtil.isNon(oaret)){
                return ret.setFail("同步OA用户信息为空");
            }
            List<SysUser> userlist = new ArrayList<SysUser>();
            List<SysPerson> personlist = new ArrayList<SysPerson>();
            List<SysUserrole> userrolelist = new ArrayList<SysUserrole>();
            for(OAUser oauser : oaret){
                SysUser user = new SysUser();
                user.setOptsn("FSDP");
                user.setUserid(oauser.getUserid());
                user.setUsercode(oauser.getUserid());
                user.setPersonid(oauser.getPersonid());
                user.setEffdate(oauser.getEffdate());
                user.setExpdate(oauser.getExpdate());
                user.setState("1".equals(oauser.getValidate()) ? "有效" : "失效");
                user.setLoginpasswd(DES.encryptString(user.getUsercode()));
                user.setCreatetime(oauser.getEffdate());
                user.setRemark(oauser.getOrgcode());
                userlist.add(user);
                
                SysPerson person = new SysPerson();
                person.setOptsn("FSDP");
                person.setPersonid(oauser.getPersonid());
                person.setPersonname(oauser.getUsername());
                person.setGender("M".equals(oauser.getGender()) ? "男" : "女");
                person.setPhoneno(oauser.getSvcnum());
                person.setEmail(oauser.getEmail());
                personlist.add(person);
                
                SysUserrole role = new SysUserrole();
                role.setOptsn("FSDP");
                role.setUserid(oauser.getUserid());
                role.setRoleid(oauser.getOrgcode()+"_stdrole");
                role.setType("基本岗位");
                userrolelist.add(role);
            }
            
            String sql = "delete from tb_sys_user where userid != 'superuser'";
            jdbctemplate.updateNohis(sql);
            sql = "delete from tb_sys_person where personid != 'superuser'";
            jdbctemplate.updateNohis(sql);
            
            sql = "insert into tb_sys_user (optsn, userid, usercode, personid, effdate, expdate, state, loginpasswd, createtime, lastlptime, remark) values (:optsn, :userid, :usercode, :personid, :effdate, :expdate, :state, :loginpasswd, :createtime, now(), :remark)";
            jdbctemplate.batchUpdateNohis(sql, userlist);
            sql = "insert into tb_sys_person(optsn, personid, personname, gender, phoneno, email) values(:optsn, :personid, :personname, :gender, :phoneno, :email)";
            jdbctemplate.batchUpdateNohis(sql, personlist);
            sql = "delete from tb_sys_userrole where userid = :userid and type = :type";
            jdbctemplate.batchUpdateNohis(sql, userrolelist);
            sql = "insert into tb_sys_userrole(optsn, userid, roleid, type) values(:optsn, :userid, :roleid, :type)";
            jdbctemplate.batchUpdateNohis(sql, userrolelist);
            
            svnService.syncOAUser(oaret);
        }catch(Exception e){
            logger.error("同步OA用户信息异常", e);
            return ret.setFail("同步OA用户信息异常");
        }
        return ret.setSuccess("OA用户同步成功");
    }

    /**
     * 同步OA组织机构信息
     * @author  Finedo
     * @date 2017年8月11日 下午7:18:24
     * @return
     */
    public ReturnValueDomain<String> syncOAOrg(){
        ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
        try{
            String url = ConfigureUtil.getParamByName("FSDP单点登录",   "OA组织机构同步服务");
            String jsonstr =HttpClientUtil.httpGetString (url);
            ReturnValue<OAOrganization> oaret=JSON.parseObject(jsonstr, new TypeReference<ReturnValue<OAOrganization>>(){});
            if(!"0000".equals(oaret.getRetCode())){
                logger.error(oaret.getRetDesc());
                return ret.setFail(oaret.getRetDesc());
            }
            List<OAOrganization> list = oaret.getList();
            List<SysOrganization> orglist = new ArrayList<SysOrganization>();
            for(OAOrganization oao : list){
                SysOrganization sysorg = new SysOrganization();
                sysorg.setOptsn("FSDP");
                sysorg.setOrgid(oao.getOrgcode().trim());
                sysorg.setOrgname(oao.getOrgname());
                sysorg.setOrgtype("部门");
                sysorg.setParentorgid(NonUtil.isNon(oao.getParentcode()) ? "0" : oao.getParentcode());
                orglist.add(sysorg);
            }
            String sql = "delete from tb_sys_organization";
            jdbctemplate.updateNohis(sql);
            sql = "insert into tb_sys_organization (optsn, orgid, orgname, orgtype, parentorgid) values (:optsn, :orgid, :orgname, :orgtype, :parentorgid)";
            jdbctemplate.batchUpdateNohis(sql, orglist);
            
            sql = "insert into tb_sys_role (optsn, orgid, roleid, rolename, rolelvl, roletype, usercount, state) select 'FSDP',a.orgid, concat(a.orgid, '_stdrole'), '基本角色','10岗','组织岗位角色','100','有效' from tb_sys_organization a where not exists(select 1 from tb_sys_role where roleid = concat(a.orgid, '_stdrole'))";
            jdbctemplate.updateNohis(sql);
            
            List<SysRole> rolelist = jdbctemplate.query("select roleid from tb_sys_role a where not exists(select 1 from tb_sys_roleright where roleid = a.roleid)", SysRole.class);
            if(NonUtil.isNotNon(rolelist)){
                for(SysRole role : rolelist){
                    jdbctemplate.updateNohis("insert into tb_sys_roleright(optsn, roleid, rightid) select 'FSDP',:roleid,rightid from tb_sys_roleright where roleid = 'sso_role'", role);
                }
            }
        }catch(Exception e){
            logger.error("OA组织机构信息同步异常", e);
            return ret.setFail("OA组织机构信息同步异常");
        }
        return ret.setSuccess("OA组织机构信息同步成功");
    }
}
