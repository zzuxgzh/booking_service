package com.zzu.booking_service.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//解决session 不同步问题
public class AuthorityInterceptor implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {
        System.out.println("进入过滤器");
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    response.setContentType("text/html;charset=UTF-8");
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));    //
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers","Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("XDomainRequestAllowed", "1");
    chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}