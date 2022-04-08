package com.bjpowernode.crm.handle;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fangy
 * @date 2022-02-08 11:29
 */
public class myInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("进入到登录验证");
//
//        String path = request.getServletPath();
//        System.out.println(path);
//        if("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
//            System.out.println("000");
//            return true;
//        }else{
//
//            User user = (User) request.getSession().getAttribute("user");
//            if (user != null){
//                System.out.println("111");
//                return true;
//            }else{
//                request.getRequestDispatcher(request.getContextPath()+"/login.jsp").forward(request,response);
//            }
//        }

        return true;
    }
}
