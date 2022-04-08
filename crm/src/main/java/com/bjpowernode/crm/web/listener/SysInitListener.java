package com.bjpowernode.crm.web.listener;

/**
 * @author fangy
 * @date 2022-02-18 21:40
 */

import com.bjpowernode.crm.settings.entity.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;


public class SysInitListener implements ServletContextListener{

    public void contextInitialized(ServletContextEvent sce) {

        DicService dicService = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(DicService.class);

        ServletContext application = sce.getServletContext();

        Map<String, List<DicValue>> map = dicService.getAll();

        Set<String> set  = map.keySet();
        for (String key : set){

            application.setAttribute(key,map.get(key));

        }
        System.out.println("上下文作用域创建成功");

        HashMap<String,String> kmap = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> enumeration = rb.getKeys();

        while(enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            String value = rb.getString(key);
            kmap.put(key,value);
        }
        application.setAttribute("kmap",kmap);
    }

}
