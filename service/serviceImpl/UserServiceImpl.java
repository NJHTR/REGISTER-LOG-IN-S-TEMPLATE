package com.example.smarthome.service.serviceImpl;

import com.example.smarthome.entity.User;
import com.example.smarthome.mapper.UserMapper;
import com.example.smarthome.service.UserService;
import com.example.smarthome.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Map;

/*
 *@Description:
 *@Date:2024/11/27 19:32
 *@Author:YPH
 *@vision:1.0
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //查找用户
    @Override
    public User findByUserName(String UserEmail) {
        return userMapper.findByUserName(UserEmail);
    }

    //注册
    @Override
    public void register(String UserEmail, String UserPassword) {
        //加密
        //。。。。。。。。。。。。

        //调用mapper层添加
        userMapper.add(UserEmail,UserPassword);
    }

    //更新信息
    @Override
    public void update(@RequestBody User user) {
        user.setUserUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    //更新密码
    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("Id");
        userMapper.updatePwd(newPwd ,id);
    }


}
