package com.example.smarthome.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/*
 *@Description:
 *@Date:2024/11/28 0:12
 *@Author:YPH
 *@vision:1.0
 */

//Lombok - @Data注解：自动在实体类自动配置GET SET 方法
public class User {
    @NotNull
    private Integer Id;
    @NotEmpty
    @Email
    private String UserEmail;
    @NotEmpty
    @JsonIgnore
    private String UserPassword;
    private String UserRole;
    @NotEmpty
    private LocalDateTime UserCreateTime;
    @NotEmpty
    private LocalDateTime UserUpdateTime;
    @NotEmpty
    private LocalDateTime UserLastLoginTime;
    public User() {
    }
    public User(Integer Id, String UserEmail, String UserPassword, String UserRole, LocalDateTime UserCreateTime, LocalDateTime UserUpdateTime, LocalDateTime UserLastLoginTime) {
        this.Id = Id;
        this.UserEmail = UserEmail;
        this.UserPassword = UserPassword;
        this.UserRole = UserRole;
        this.UserCreateTime = UserCreateTime;
        this.UserUpdateTime = UserUpdateTime;
        this.UserLastLoginTime = UserLastLoginTime;
    }
    public Integer getId() {
        return Id;
    }
    public void setId(Integer Id) {
        this.Id = Id;
    }
    public String getUserEmail() {
        return UserEmail;
    }
    public void setUserEmail(String UserEmail) {
        this.UserEmail = UserEmail;
    }
    public String getUserPassword() {
        return UserPassword;
    }
    public void setUserPassword(String UserPassword) {
        this.UserPassword = UserPassword;
    }
    public String getUserRole() {
        return UserRole;
    }
    public void setUserRole(String UserRole) {
        this.UserRole = UserRole;
    }
    public LocalDateTime getUserCreateTime() {
        return UserCreateTime;
    }
    public void setUserCreateTime(LocalDateTime UserCreateTime) {
        this.UserCreateTime = UserCreateTime;
    }
    public LocalDateTime getUserUpdateTime() {
        return UserUpdateTime;
    }
    public void setUserUpdateTime(LocalDateTime UserUpdateTime) {
        this.UserUpdateTime = UserUpdateTime;
    }
    public LocalDateTime getUserLastLoginTime() {
        return UserLastLoginTime;
    }
    public void setUserLastLoginTime(LocalDateTime UserLastLoginTime) {
        this.UserLastLoginTime = UserLastLoginTime;
    }
}
