package cn.finedo.codereview.filter;


import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.finedo.common.non.NonUtil;


/**
 * @Description: 跨站脚本过滤器
 * @company Finedo.cn
 * @author zhusf@finedo.com
 * @date 2018年7月16日 上午11:31:07
 * @version v1.0
 */
public class NoHostFilter implements Filter {

    private static Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig)
        throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse response = (HttpServletResponse)res;
        // 获取请求所有参数，校验防止脚本注入，防止XSS漏洞
        String url = req.getRequestURI();
        Enumeration<?> params = req.getParameterNames();
        String paramN = null;
        while (params.hasMoreElements()) {
            paramN = (String)params.nextElement();
            String paramVale = req.getParameter(paramN);
            logger.debug("传参为：{}，参数值为：{}", paramN, paramVale);
            // 校验是否存在SQL注入信息
            if (checkSQLInject(paramVale, url)) {
                response.sendRedirect(req.getContextPath() + "/common/error-pages/404.jsp");
            }
        }
        chain.doFilter(req, response);
    }

    /**
     * 检查是否存在非法字符，防止js脚本注入
     * 
     * @param str
     *            被检查的字符串
     * @return ture-字符串中存在非法字符，false-不存在非法字符
     */

    public boolean checkSQLInject(String str, String url) {
        if (NonUtil.isNon(str)) {
            return false;// 如果传入空串则认为不存在非法字符
        }
        // 判断黑名单
        String[] inj_stra = {"script", "mid", "master", "truncate", "insert", "select", "delete", "update", "declare", "iframe", "'", "onreadystatechange", "alert", "atestu", "xss", ";", "'", "\"", "<", ">", "(", ")", ",", "\\", "svg", "confirm", "prompt", "onload", "onmouseover", "onfocus", "onerror"};
        str = str.toLowerCase(); // sql不区分大小写
        for (int i = 0; i < inj_stra.length; i++ ) {
            if (str.indexOf(inj_stra[i]) >= 0) {
                return true;
            }
        }
        return false;
    }

}