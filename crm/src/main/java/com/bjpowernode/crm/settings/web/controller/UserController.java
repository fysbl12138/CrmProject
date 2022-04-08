package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.entity.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fangy
 * @date 2022-02-05 17:20
 */
@Controller
@RequestMapping("/setting/user")
public class UserController {

   @Resource
    private UserService userService;

    @RequestMapping("/login.do")
    public void login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入到controller验证登录操作");

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //明文转密文
        loginPwd = MD5Util.getMD5(loginPwd);
        //获取当前浏览器端IP地址
        String IP = request.getRemoteAddr();
        System.out.println("本地IP"+IP);

        try {

            User user = userService.login(loginAct,loginPwd,IP);
            request.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(response,true);

        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);

            PrintJson.printJsonObj(response,map);
        }


    }

    /*

    @RequestMapping("/login.do")
    @ResponseBody
    public User login1(HttpServletRequest request, HttpServletResponse response){

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");



        return null;
    }


     */

}
