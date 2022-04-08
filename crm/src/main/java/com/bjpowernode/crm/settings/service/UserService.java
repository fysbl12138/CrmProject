package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.exception.loginException;
import com.bjpowernode.crm.settings.entity.User;
import com.bjpowernode.crm.workbench.entity.Clue;

import java.util.List;

/**
 * @author fangy
 * @date 2022-02-05 17:12
 */
public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws loginException;

    List<User> selectAllUser();

}
