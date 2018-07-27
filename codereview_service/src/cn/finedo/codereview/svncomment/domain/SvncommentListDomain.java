package cn.finedo.codereview.svncomment.domain;

import java.util.ArrayList;
import java.util.List;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.codereview.common.pojo.DopsSvncomment;

/**      
* @Description: 评论信息实体类
* @author zhusf@finedo.com   
* @date 2018年5月2日 上午10:51:49   
* @version v1.0
*   
*/ 
public class SvncommentListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private List<DopsSvncomment> dopssvncommentlist=new ArrayList<DopsSvncomment>();

	public List<DopsSvncomment> getDopssvncommentlist() {
		return dopssvncommentlist;
	}

	public void setDopssvncommentlist(List<DopsSvncomment> dopssvncommentlist) {
		this.dopssvncommentlist = dopssvncommentlist;
	}
}
