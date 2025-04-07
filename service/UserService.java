package com.example.smarthome.service;


import com.example.smarthome.entity.User;

/*
 *@Description:
 *@Date:2024/11/27 19:32
 *@Author:YPH
 *@vision:1.0
 */
public interface UserService {

    //查用户
    User findByUserName(String UserEmail);

    //注册用户
    void register(String UserEmail, String UserPassword);

    //更新用户
    void update(User user);

    //更新密码
    void updatePwd(String newPwd);
}
