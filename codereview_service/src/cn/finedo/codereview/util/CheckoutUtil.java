package cn.finedo.codereview.util;


import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**      
* @Description: svn文件检出
* @company Finedo.cn
* @author zhusf@finedo.com   
* @date 2018年7月27日 上午9:14:06   
* @version v1.0 
*/ 
public class CheckoutUtil {
    private static Logger logger = LogManager.getLogger(); 
    // 声明SVN客户端管理类
    private static SVNClientManager ourClientManager;

    public static void checkout(String dirpath,String basepath,String filenamedir,String name, String password,String filename,String revision) throws SVNException{
        // 初始化支持svn://协议的库。 必须先执行此操作。
        SVNRepositoryFactoryImpl.setup();
        // 相关变量赋值
        SVNURL repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded(dirpath);
        }
        catch (SVNException e) {
            logger.debug("无法连接到{}",dirpath);
        }
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        // 实例化客户端管理类
        ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions)options, name, password);
        // 要把版本库的内容check out到的目录
        SVNRevision sVNRevision = SVNRevision.create(Long.parseLong(revision));
        
        String revisiondir = "/".concat(revision);
        String workPath = StringUtil.concatTwoPath(basepath, filenamedir).concat(revisiondir);
        File wcDir = new File(workPath);
        // 通过客户端管理类获得updateClient类的实例。
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        // sets externals not to be ignored during the checkout
        updateClient.setIgnoreExternals(false);
        
        long workingVersion = updateClient.doCheckout(repositoryURL, wcDir, sVNRevision, sVNRevision, SVNDepth.INFINITY, false);
        logger.debug("把版本：{}check out到目录：{}中。",workingVersion,wcDir );
        
    }
    
    
}
