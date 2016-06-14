package com.tomasky.doms.support.filter;

import com.fanqie.util.JacksonUtil;
import com.tomasky.doms.dto.qunar.QunarStatusCode;
import com.tomasky.doms.dto.qunar.response.QunarDataResult;
import com.tomasky.doms.support.util.TomaskyMD5;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by jame
 * Date: 2016/6/14
 * Version: 1.0
 * Description: pms接口权限过滤
 */
public class TomaskyPmsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletResponse, ServletResponse servletRequest, FilterChain chain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        String token = servletResponse.getParameter("token");
        if (checkToken(token)) {
            chain.doFilter(servletResponse, servletRequest);
            return;
        } else {
            servletRequest.setContentType("application/json;charset=UTF-8");
            QunarDataResult result = new QunarDataResult(QunarStatusCode.ERROR_6000, "授权参数不正确", null);
            servletRequest.getWriter().print(JacksonUtil.obj2json(result));
        }
    }

    private boolean checkToken(String token) {
        boolean pass = false;
        String regToken = TomaskyMD5.getTomaskyPmsToken();
        if (regToken.equals(token)) {
            pass = true;
        }
        return pass;
    }

    @Override
    public void destroy() {

    }
}
