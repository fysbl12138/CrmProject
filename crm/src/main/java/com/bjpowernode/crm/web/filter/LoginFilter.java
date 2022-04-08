package com.bjpowernode.crm.web.filter;

import com.bjpowernode.crm.settings.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fangy
 * @date 2022-02-08 16:22
 */

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("进入到过滤器验证登录操作");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String path = request.getServletPath();
        System.out.println("当前项目路径"+path);

        if("/login.jsp".equals(path) || "/setting/user/login.do".equals(path)){
            chain.doFilter(req,resp);
            System.out.println("111");
        }else {
            User user = (User) request.getSession().getAttribute("user");

            if(user != null){
                chain.doFilter(req,resp);
                System.out.println("222");
            }else{
                response.sendRedirect(request.getContextPath()+"/login.jsp");
                System.out.println("333");
            }
        }
    }
}
