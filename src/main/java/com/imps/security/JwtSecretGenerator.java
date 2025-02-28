package com.imps.security;

import io.jsonwebtoken.io.Encoders;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class JwtSecretGenerator {
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        String base64UrlSecret = Encoders.BASE64URL.encode(secretKey.getEncoded());
        System.out.println("JWT Secret: ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + base64UrlSecret);
    }
}