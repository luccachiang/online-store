package com.cy.store.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 定义处理器拦截器 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("uid") == null)
        {
            // 没有uid说明用户没有登录，重定向到登陆界面
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}
