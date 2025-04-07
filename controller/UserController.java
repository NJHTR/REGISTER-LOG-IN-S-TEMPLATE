package com.example.smarthome.controller;

/*
 *@Description:
 *@Date:2024/11/27 19:33
 *@Author:YPH
 *@vision:1.0
*/

import com.example.smarthome.entity.Result;
import com.example.smarthome.entity.User;
import com.example.smarthome.service.UserService;
import com.example.smarthome.util.JWTUtil;
import com.example.smarthome.util.ThreadLocalUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //注册
    @PostMapping("/register")
    public Result register(@Email String UserEmail, @Pattern(regexp = "^\\S{5,16}$") String UserPassword){

        //查询用户
        User u = userService.findByUserName(UserEmail);
        if(u == null){
            //null说明没有被占用
            //注册
            userService.register(UserEmail,UserPassword);
            return Result.success();
        }else{
            //占用
            return Result.error("用户被占用");
        }
    }

    //登录
    @PostMapping("/login")
    public Result<String> login(@Email String UserEmail, @Pattern(regexp="^\\S{5,16}$") String UserPassword){

        //查用户
        User loginUser = userService.findByUserName(UserEmail);

        //判断用户是否存在
        if(loginUser == null){
            return Result.error("用户未注册");
        }

        //密码是否正确
        if(UserPassword.equals(loginUser.getUserPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("ID",loginUser.getId());
            claims.put("UserEmail",loginUser.getUserEmail());
            String token = JWTUtil.genToken(claims);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }


    //GET用户信息
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
        Map<String,Object> map = ThreadLocalUtil.get();
        String UserName = (String)map.get("UserName");
        User user = userService.findByUserName(UserName);
        return Result.success(user);
    }

    //更新用户
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    //更新密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token){

        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要参数");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String UserName = (String) map.get("UserName");
        User loginUser = userService.findByUserName(UserName);
        if (!loginUser.getUserPassword().equals(oldPwd)) {
            return Result.error("原密码错误");
        }

        if (!newPwd.equals(rePwd)) {
            return Result.error("；两次输入的密码不一致");
        }

        userService.updatePwd(newPwd);
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}