# REGISTER-LOG-IN-S-TEMPLATE
REGISTER &amp; LOG IN`S TEMPLATE

## Features  
## 功能  

- User registration with email validation  
  带邮箱验证的用户注册  
- JWT-based authentication  
  基于 JWT 的认证  
- Redis token storage with 1-hour expiration  
  Redis 令牌存储（1小时有效期）  
- Password update functionality  
  密码更新功能  
- User information management  
  用户信息管理  
- Global exception handling  
  全局异常处理  
- Request interception for authentication  
  请求拦截认证  

## API Endpoints  
## API 接口  

### Authentication  
### 认证  

| Method 方法 | Endpoint 接口       | Description 描述                     | Parameters 参数                          |
|--------|----------------|---------------------------------|-------------------------------------|
| POST   | /user/register | Register new user 注册新用户               | UserEmail (email), UserPassword (5-16 chars) 邮箱, 密码(5-16位非空字符) |
| POST   | /user/login    | Login and get JWT token 登录并获取 JWT 令牌         | UserEmail, UserPassword 邮箱, 密码             |

### User Operations (Require Authorization Header)  
### 用户操作 (需 Authorization 请求头)  

| Method 方法 | Endpoint 接口       | Description 描述                     | Parameters 参数                          |
|--------|----------------|---------------------------------|-------------------------------------|
| GET    | /user/userInfo | Get current user information 获取当前用户信息    | None 无参数                                |
| PUT    | /user/update   | Update user information 更新用户信息         | User object (JSON) 用户对象 (JSON)                  |
| PATCH  | /user/updatePwd| Update password 更新密码                 | old_pwd, new_pwd, re_pwd (JSON) 旧密码, 新密码, 确认密码     |

## System Architecture  
## 系统架构  

### Components  
### 组件  

1. **WebConfig** - Configures interceptors to protect endpoints (excludes /register and /login)  
   **WebConfig** - 配置拦截器保护端点（排除 /register 和 /login）  
2. **UserController** - Handles all user-related HTTP requests  
   **UserController** - 处理所有用户相关的 HTTP 请求  
3. **UserService** - Business logic layer for user operations  
   **UserService** - 用户操作的业务逻辑层  
4. **UserMapper** - Data access layer (MyBatis)  
   **UserMapper** - 数据访问层 (MyBatis)  
5. **LoginInterceptor** - Validates JWT tokens and checks Redis for active sessions  
   **LoginInterceptor** - 验证 JWT 令牌并检查 Redis 中的活跃会话  
6. **GlobalExceptionHandler** - Centralized exception handling  
   **GlobalExceptionHandler** - 集中式异常处理  

### Security Flow  
### 安全流程  

1. Client registers or logs in to get JWT token  
   客户端注册或登录以获取 JWT 令牌  
2. Token is stored in Redis with 1-hour TTL  
   令牌存储在 Redis 中，有效期1小时  
3. Subsequent requests include token in Authorization header  
   后续请求在 Authorization 请求头中包含令牌  
4. LoginInterceptor validates token against Redis  
   LoginInterceptor 根据 Redis 验证令牌  
5. Valid requests proceed; invalid get 401 response  
   有效请求继续；无效请求返回 401 响应  

## Data Model  
## 数据模型  

### User Entity  
### 用户实体  

```java
public class User {
    private Integer Id;
    private String UserEmail;       // @Email validated
    private String UserPassword;    // @NotEmpty, @JsonIgnore
    private String UserRole;
    private LocalDateTime UserCreateTime;
    private LocalDateTime UserUpdateTime;
    private LocalDateTime UserLastLoginTime;
}
```

## Response Format
## 响应格式
All responses follow the Result<T> format:
所有响应遵循 Result<T> 格式：

```java
public class Result<T> {
    private Integer code;    // 0=success, 1=error
    private String message;  // Descriptive message
    private T date;         // Response payload
}
```
## Setup
## 设置

1. Configure Redis connection in application.properties
   在 application.properties 中配置 Redis 连接
2. Set JWT secret key in JWTUtil class
   在 JWTUtil 类中设置 JWT 密钥
3. Database schema should include tb_user table matching User entity
   数据库应包含与 User 实体匹配的 tb_user 表

## Validation Rules
## 验证规则
Email: Standard email format
邮箱：标准邮箱格式
Password: 5-16 non-whitespace characters
密码：5-16位非空字符
All user fields marked @NotEmpty cannot be null/empty
所有标记为 @NotEmpty 的用户字段不能为空

## Error Handling
## 错误处理

```java
{
  "code": 1,
  "message": "Error description",
  "date": null
}
```
## Dependencies
## 依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.2</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>SmartHome</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>SmartHome</name>
    <description>SmartHome</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <!--启动依赖坐标-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <!--web依赖坐标-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--mysql驱动依赖坐标-->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>
        <!--mybatis依赖坐标-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>3.0.3</version>
        </dependency>
        <!--java-jwt坐标-->
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>4.3.0</version>
        </dependency>
        <!--redis依赖坐标-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!--validation-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <!--测试依赖坐标-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```
