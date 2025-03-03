package com.imps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.imps.service", "com.imps.controller"})
public class InsurenceApplication {

    public static void main(String[] args) {
        /*KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        String base64UrlSecret = Encoders.BASE64URL.encode(secretKey.getEncoded());*/
        //System.out.println("JWT Secret: ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + base64UrlSecret);

        SpringApplication.run(InsurenceApplication.class, args);
    }

}
