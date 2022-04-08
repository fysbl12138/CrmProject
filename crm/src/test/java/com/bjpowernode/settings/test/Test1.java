package com.bjpowernode.settings.test;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.MD5Util;
import org.junit.Test;

/**
 * @author fangy
 * @date 2022-02-05 17:44
 */
public class Test1 {

    @Test
    public void test1(){

        String expireTime = "2022-02-05 22:22:22";
        String currentTime = DateTimeUtil.getSysTime();

        int count = expireTime.compareTo(currentTime);

        String pwd = "Sbl1314520..";

        String mima = MD5Util.getMD5(pwd);
        System.out.println(mima);

    }
}
