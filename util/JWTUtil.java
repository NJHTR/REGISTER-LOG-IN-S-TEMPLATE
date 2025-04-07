package com.example.smarthome.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

/*
 *@Description:
 *@Date:2024/11/30 2:47
 *@Author:YPH
 *@vision:1.0
 */
public class JWTUtil {

    private static final String KEY = "your_key";
    public static String genToken(Map<String,Object> claims) {
        return JWT.create()
                .withClaim("claims",claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256(KEY));
    }

    public static Map<String,Object> ParseToken(String token){
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}
