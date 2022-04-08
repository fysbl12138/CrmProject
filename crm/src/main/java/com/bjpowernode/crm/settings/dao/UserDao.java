package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fangy
 * @date 2022-02-05 17:01
 */
public interface UserDao {


    User login(@Param("loginAct") String loginAct, @Param("loginPwd") String loginPwd);

    List<User> selectAllUser();
}
