package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.exception.loginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.entity.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fangy
 * @date 2022-02-05 17:12
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao dao;


    @Override
    public User login(String loginAct, String loginPwd, String ip) throws loginException {

        User user = dao.login(loginAct,loginPwd);


        if (user == null){
            throw new loginException("账号或密码错误");
        }

        String expireTimeime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();

        if (expireTimeime.compareTo(currentTime) < 0){
            throw new loginException("账号已失效");
        }

        if("0".equals(user.getLockState())){
            throw new loginException("账号已被锁定");
        }

        String ips = user.getAllowIps();
        if(!ips.contains(ip)){
            throw new loginException("该ip不属于内部网络");
        }

        return user;
    }

    @Override
    public List<User> selectAllUser() {
        List<User> users = dao.selectAllUser();
        return users;
    }
}
