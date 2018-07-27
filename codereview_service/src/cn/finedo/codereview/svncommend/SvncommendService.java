package cn.finedo.codereview.svncommend;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.finedo.codereview.common.pojo.DopsSvncodeviewgroup;
import cn.finedo.codereview.common.pojo.DopsSvncodeviewproject;
import cn.finedo.codereview.common.pojo.DopsSvncommend;
import cn.finedo.codereview.svncodeviewgroup.domain.SvncodeviewgroupListDomain;
import cn.finedo.codereview.svncommend.domain.SvncommendListDomain;
import cn.finedo.codereview.svncommend.domain.SvncommendQueryDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysCount;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;

/**
 * @author 刘青成
 *TODO 代码推荐
 */
@Service
@Transactional
@Scope("singleton")
public class SvncommendService {
	private static Logger logger = LogManager.getLogger(); 
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IDUtil idutil;
	/**
	 * @param svncommendDomain
	 * @return
	*TODO 查询推荐列表
	 */
	public ReturnValueDomain<PageDomain<DopsSvncommend>> querylist(SvncommendQueryDomain svncommendDomain) {
		ReturnValueDomain<PageDomain<DopsSvncommend>> ret = new ReturnValueDomain<PageDomain<DopsSvncommend>>();
		if(NonUtil.isNon(svncommendDomain)) {
			return ret.setFail("参数校验失败");
		}
		PageParamDomain pageparam=svncommendDomain.getPageparam();
		DopsSvncommend dopssvncommend=svncommendDomain.getDopssvncommend();
		PageDomain<DopsSvncommend> retpage=null;
		if(NonUtil.isNon(dopssvncommend)){
		    return ret.setFail("参数校验失败");
		}
        StringBuffer sb = new StringBuffer();
        //查询项目
        sb.append("SELECT  a.commendid,(select username from tb_dops_svnoauser where usercode=a.commend) commend,a.groupname,a.orgcoed,a.pollnumber,(select username from tb_dops_svnoauser where usercode=a.creatuser) creatuser,creattime FROM tb_dops_svncommend a  where a.creattime>=:begintime and a.creattime<=:endtime");
            sb.append("  AND a.groupname like :groupname ");
            sb.append(" ORDER BY a.pollnumber desc");
            try {
			retpage =  jdbcTemplate.queryForPage(sb.toString(), dopssvncommend, DopsSvncommend.class, pageparam);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("查询项目信息失败");
		}
		
		return ret.setSuccess("查询项目信息表成功", retpage);
	}
	
	
	/**
	 * @param svncommendDomain
	 * @return
	*TODO 查询详情
	 */
	public ReturnValueDomain<DopsSvncommend> querydetail(SvncommendQueryDomain svncommendDomain) {
		ReturnValueDomain<DopsSvncommend> ret = new ReturnValueDomain<DopsSvncommend>();
		if(NonUtil.isNon(svncommendDomain)) {
			return ret.setFail("参数校验失败");
		}
		DopsSvncommend dopssvncommend=svncommendDomain.getDopssvncommend();
		if(NonUtil.isNon(dopssvncommend)){
		    return ret.setFail("参数校验失败");
		}
        StringBuffer sb = new StringBuffer();
        List<DopsSvncommend> list =null;
        SvncommendQueryDomain resultcommend=new SvncommendQueryDomain();
        //查询项目
        sb.append("SELECT a.commendid,(select username from tb_dops_svnoauser where usercode=a.commend)commend,a.groupname,a.orgcoed,a.groupuser,a.commendreason,a.`code`,a.creattime FROM tb_dops_svncommend a where commendid=:commendid");
            try {
			list =  jdbcTemplate.query(sb.toString(), dopssvncommend, DopsSvncommend.class);
		}catch (Exception e) {
			logger.error("sql语句异常",e);
			return ret.setFail("查询项目信息失败");
		}
            if(NonUtil.isNotNon(list)){
            	resultcommend.setDopssvncommend(list.get(0));
	        }
		return ret.setSuccess("查询项目信息表成功",resultcommend.getDopssvncommend());
	}
	
	/**
	 * @param svncodeviewprojectlistdomain
	 * @return
	*TODO 添加推荐代码
	 */
	public ReturnValueDomain<String> create(SvncommendListDomain svncommendlistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer sql=new StringBuffer();
		List<DopsSvncommend> svncommendlist=new ArrayList<DopsSvncommend>();
		if (NonUtil.isNon(svncommendlistdomain)) {
			return ret.setFail("无评审小组表");
		}
		List<DopsSvncommend> dopssvncommendlist=svncommendlistdomain.getDopssvncommendlist();
		if (NonUtil.isNon(dopssvncommendlist)) {
			return ret.setFail("无评审小组表");
		}
		DopsSvncommend commend=dopssvncommendlist.get(0);
		try{
			String commendid=idutil.getID("coderproject");
			for(DopsSvncommend dopssvncommend : dopssvncommendlist) {
				dopssvncommend.setCommendid(commendid);
				dopssvncommend.setCommend(commend.getCommend());
				dopssvncommend.setGroupname(commend.getGroupname());
				dopssvncommend.setOrgcoed(commend.getOrgcoed());
				dopssvncommend.setGroupuser(commend.getGroupuser());
				dopssvncommend.setCommendreason(commend.getCommendreason());
				dopssvncommend.setCode(commend.getCode());
				dopssvncommend.setCreatuser(commend.getCreatuser());
				svncommendlist.add(dopssvncommend);
			}
		}catch(Exception ex) {
			logger.error("添加项目失败",ex);
			return ret.setFail("添加项目失败");
		}
		//project表插入数据
  		    sql.append("INSERT INTO tb_dops_svncommend (commendid, commend,groupname, orgcoed, groupuser, commendreason, code,pollnumber,creatuser,creattime) ");
            sql.append("  VALUES (:commendid,:commend,:groupname,:orgcoed,:groupuser,:commendreason,:code,'0',:creatuser,NOW())");
  		    try {
			jdbcTemplate.batchUpdate(sql.toString(), svncommendlist);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("项目新增成功");
	}
	

	/**
	 * @param svncommendQueryDomain
	 * @return
	*TODO 查询是否是评审人员以及剩余投票次数
	 */
	public ReturnValueDomain<DopsSvncommend> querynumber(SvncommendQueryDomain svncommendQueryDomain) {
		ReturnValueDomain<DopsSvncommend> ret = new ReturnValueDomain<DopsSvncommend>();
		DopsSvncommend dopssvncommend=svncommendQueryDomain.getDopssvncommend();
		DopsSvncommend resultcommend= new DopsSvncommend();
		String sql="select count(1) as count from tb_dops_svncodeviewgroupuserrole where usercode=:creatuser";
		SysCount count = jdbcTemplate.queryForObject(sql, dopssvncommend, SysCount.class);
		String flag="";
		  List<DopsSvncommend> list =null;
		  Date day=new Date();    
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM"); 
		  List<DopsSvncommend> dopssvncommendlist = new ArrayList<DopsSvncommend>();
		  dopssvncommendlist.add(dopssvncommend);
		  //判断是否能投票后查询上次投票时间
		if(count.getCount() > 0){
	        String sql1 = "SELECT DISTINCT  lasttime endtime FROM tb_dops_svncodeviewgroupuserrole WHERE usercode=:creatuser";
	        try {
	             list= jdbcTemplate.query(sql1,dopssvncommend,DopsSvncommend.class);
	        }
	        catch (DataAccessException e1) {
	            logger.error("查询项目经理权限表失败", e1);
	            return ret.setFail("查询项目经理权限表失败");
	        }
	        //上次投票时间是本月或者为不为空查询剩余投票次数
	        if(NonUtil.isNotNon(list.get(0).getEndtime()) && list.get(0).getEndtime().substring(0,7).toString().equals(df.format(day).toString())){
	        String sql2 = "SELECT DISTINCT pollnumber  FROM tb_dops_svncodeviewgroupuserrole WHERE usercode=:creatuser";
	        try {
	             list=jdbcTemplate.query(sql2,dopssvncommend,DopsSvncommend.class);
	             resultcommend.setPollnumber(list.get(0).getPollnumber());
	        }
	        catch (DataAccessException e1) {
	            logger.error("查询项目经理权限表失败", e1);
	            return ret.setFail("查询项目经理权限表失败");
	        }
	        }else{
	        	 String sql3 = "update tb_dops_svncodeviewgroupuserrole set pollnumber=3 WHERE usercode=:creatuser";
	 	        try {
		             jdbcTemplate.batchUpdate(sql3, dopssvncommendlist);
		        }
		        catch (DataAccessException e1) {
		            logger.error("查询项目经理权限表失败", e1);
		            return ret.setFail("查询项目经理权限表失败");
		        }
	 	       resultcommend.setPollnumber("3");
	        }
	        flag="有投票权限";
		}else{
			flag="没有投票权限";
		}
	   
		return  ret.setSuccess(flag, resultcommend);
		
	}
	
	public ReturnValueDomain<String> poll(SvncommendListDomain svncommendlistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		StringBuffer sql=new StringBuffer();
		StringBuffer sql1=new StringBuffer();
		if (NonUtil.isNon(svncommendlistdomain)) {
			return ret.setFail("无评审小组表");
		}
		
		List<DopsSvncommend> dopssvncommendlist=svncommendlistdomain.getDopssvncommendlist();
		
		 sql.append("UPDATE tb_dops_svncommend SET pollnumber = pollnumber + 1 WHERE commendid=:commendid");
		 sql1.append(" UPDATE tb_dops_svncodeviewgroupuserrole SET pollnumber = pollnumber - 1,lasttime=NOW() WHERE usercode=:creatuser");
		try {
			jdbcTemplate.batchUpdate(sql.toString(), dopssvncommendlist);
			jdbcTemplate.batchUpdate(sql1.toString(), dopssvncommendlist);
		} catch (Exception e) {
			logger.error("sql语句异常",e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("评审小组表修改成功");
	}
}
