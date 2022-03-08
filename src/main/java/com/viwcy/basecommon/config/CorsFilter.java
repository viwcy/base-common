package com.viwcy.basecommon.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@WebFilter(urlPatterns = {"/*"})
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        servletResponse.setHeader("Access-Control-Allow-Origin", servletRequest.getHeader("Origin"));
        servletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH,OPTIONS");
        servletResponse.setHeader("Access-Control-Max-Age", "3600");
        servletResponse.setHeader("Access-Control-Allow-Headers", "TOKEN, Origin, X-Requested-With, Content-Type, Accept,Content-Disposition");
        servletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(request, response);
    }
}
