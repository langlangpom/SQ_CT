package com.evian.sqct.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ClassName:APITimeFilter
 * Package:com.evian.sqct.filter
 * Description:接口API时间记录
 *
 * @Date:2020/3/3 9:40
 * @Author:XHX
 */

@WebFilter(filterName = "apiTimeFilter", urlPatterns = "/evian/sqct/*")
public class APITimeFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        long initTime = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        filterChain.doFilter(request,response);
        long destroyTime = System.currentTimeMillis();
        long useTime = destroyTime - initTime;
        if(useTime>1000){
            logger.error("[url:{}] [ms:{}]",new Object[]{requestURI, useTime});
        }else{
            logger.info("[url:{}] [ms:{}]",new Object[]{requestURI, useTime});
        }
    }

    @Override
    public void destroy() {}
}
