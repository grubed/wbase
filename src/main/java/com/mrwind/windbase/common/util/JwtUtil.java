package com.mrwind.windbase.common.util;


import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    // JWT 生成 token 加密时需要的秘钥
    private static final String ENCODE_KEY = "windbase2018";

    private static final long EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L;

    public static String createJWT(String subject) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        Map<String, Object> claims = new HashMap<>();
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)           //iat: jwt的签发时间
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        //if (ttlMillis >= 0) {
        long expMillis = nowMillis + EXPIRE_TIME;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);     //设置过期时间
        // }
        return builder.compact();
    }

    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(ENCODE_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
