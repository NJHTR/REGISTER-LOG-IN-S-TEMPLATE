package com.example.smarthome.mapper;

import com.example.smarthome.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/*
 *@Description:
 *@Date:2024/11/27 19:34
 *@Author:YPH
 *@vision:1.0
 */

@Mapper
public interface UserMapper {

    //查用户
    @Select("select * from tb_user where UserEmail = #{UserEmail}")
    User findByUserName(String UserEmail);

    //注册
    @Insert("insert into tb_user(UserEmail, UserPassword, UserCreateTime, UserUpdateTime) " +
            "values(#{UserEmail}, #{UserPassword},now(),now())")
    void add(String username, String password);

    //更新昵称，邮箱，更新时间，
    @Update("update tb_user set UserEmail = #{UserEmail} UserUpdateTime = #{UserUpdateTime} where Id = #{Id}")
    void update(User user);

    //更新密码
    @Update("update tb_user set UserPassword = #{newPwd}, UserUpdateTime = now() where Id = #{Id}")
    void updatePwd(String newPwd, Integer Id);
}
