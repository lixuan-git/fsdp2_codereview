package cn.finedo.codereview.util;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Description: 字符串辅助类
 * @company Finedo.cn
 * @author zhusf@finedo.com
 * @date 2018年7月7日 下午3:15:06
 * @version v1.0
 */
public class CodeviewUtil {
    
    /**
    * 获取本周一周日
    * @author zhusf
    * @param @return
    * @return Date[]
    */  
    public static Date[] getWeekDay() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        Date[] dates = new Date[2];
        for (int i = 0; i < 2; i++) {
            dates[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 6);
        }
        return dates;
    }

    
    /**
    * 检查域名
    * @author zhusf
    * @param @param str
    * @param @return
    * @return List<String>
    */  
    public static List<String> getDomainName(String str){
        List<String> list = new ArrayList<String>();
        String pattern = "((http://)|(https://)){1}([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);

        while(m.find()){
            //匹配结果
            list.add(m.group());
        }
        return list;
    }
    
    /**
    * 检查IP
    * @author zhusf
    * @param @param str
    * @param @return
    * @return List<String>
    */  
    public static List<String> getIp(String str){
        List<String> list = new ArrayList<String>();
        String pattern = "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);

        while(m.find()){
            //匹配结果
            list.add(m.group());
        }
        return list;
    }
    
    /**
    * 检查日志
    * @author zhusf
    * @param @param str
    * @param @return
    * @return List<String>
    */  
    public static List<String> getLoggerInfo(String str){
        List<String> list = new ArrayList<String>();
        String pattern = "l{1}o{1}g{1}g{1}e{1}r{1}.{1}i{1}n{1}f{1}o{1}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);

        while(m.find()){
            //匹配结果
            list.add(m.group());
        }
        return list;
    }
    
    /**
    * 检查控制台打印
    * @author zhusf
    * @param @param str
    * @param @return
    * @return List<String>
    */ 	
    public static List<String> getSystemout(String str){
        List<String> list = new ArrayList<String>();
        String pattern = "S{1}y{1}s{1}t{1}e{1}m{1}.{1}o{1}u{1}t{1}.{1}p{1}r{1}i{1}n{1}t{1}l{1}n{1}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);

        while(m.find()){
            //匹配结果
            list.add(m.group());
        }
        return list;
    }
    
    
}
