package com.tbszxj.utils;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

/**
 * JWT工具类
 * @author zxj
 * @date  2020/1/1
 */
public class JwtUtils {

    private static final String JWT_PAYLOAD_USER_KEY = "user";



    /**
     * 私钥加密token
     * @param userInfo 载荷中的数据
     * @param privateKey 私钥
     * @param expire 过期时间，单位分钟
     * @author zxj
     * @date  2020/1/1
     */
    public static String generateToken(Object userInfo, PrivateKey privateKey,int expire) {
        return Jwts.builder()
                .claim(JWT_PAYLOAD_USER_KEY,JSONObject.toJSONString(userInfo))
                .setId(createJTI())
                .setExpiration(DateTime.now().plusMinutes(expire).toDate())
                .signWith(privateKey,SignatureAlgorithm.RS256)
                .compact();
    }


    /**
     * 公钥解析token
     * @param token 用户请求中的token
     * @param publicKey 公钥
     * @author zxj
     * @date  2020/1/1
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey){
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    private static String createJTI(){
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }


    /**
     * 获取token中的用户信息
     * @param token 用户请求中的令牌
     * @param publicKey 公钥
     * @param userType 用户类型
     * @author zxj
     * @date  2020/1/1
     */
    public static <T> PayLoad<T> getInfoFromToken(String token,PublicKey publicKey,Class<T> userType){
        Jws<Claims> claimsJws = parserToken(token,publicKey);
        Claims body = claimsJws.getBody();
        PayLoad<T> claims = new PayLoad<>();
        claims.setId(body.getId());
        claims.setUserInfo(JSONObject.parseObject(body.get(JWT_PAYLOAD_USER_KEY).toString(),userType));
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 获取token中的用户信息
     * @param token 用户请求中的令牌
     * @param publicKey 公钥
     * @author zxj
     * @date  2020/1/1
     */
    public static <T> PayLoad<T> getInfoFromToken(String token,PublicKey publicKey){
        Jws<Claims> claimsJws = parserToken(token,publicKey);
        Claims body = claimsJws.getBody();
        PayLoad<T> claims = new PayLoad<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }

}
