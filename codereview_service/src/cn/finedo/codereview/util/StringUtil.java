package cn.finedo.codereview.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.finedo.common.non.NonUtil;


/**
 * @Description: 字符串辅助类
 * @company Finedo.cn
 * @author zhusf@finedo.com
 * @date 2018年7月7日 下午3:15:06
 * @version v1.0
 */
public class StringUtil {
    
    /**
    * 字符串截取
    * @author zhusf
    * @param @param str 原始字符串
    * @param @param modelStr 规则字符串
    * @param @param count 第几次出现
    * @param @return
    * @return int 返回下标
    */ 	
    public static int getIndexBymodelstr(String str, String modelStr, Integer count) {
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
        return index;
    }
    
    /**
    * 拼接路径
    * @author zhusf
    * @param @param repopath
    * @param @param projectpath
    * @param @return
    * @return String
    */ 	
    public static String concatTwoPath(String repopath,String projectpath){
        if (NonUtil.isNotNon(repopath) && "/".equals(repopath.substring(repopath.length() - 1))) {
            repopath = repopath.substring(0, repopath.length() - 1);
        }
        return repopath + projectpath;
    }
}
