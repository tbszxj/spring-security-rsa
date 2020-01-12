package com.tbszxj.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RsaUtilsTest {

    String pubKeyPath = "D:\\auth-key\\publicKey";
    String priKeyPath = "D:\\auth-key\\privateKey";
    String secret = "sc@Login(Auth}*^31)&czxy%";

    @Test
    void getPublicKey() throws Exception{
        System.out.println(RsaUtils.getPublicKey(pubKeyPath));
    }

    @Test
    void getPrivateKey() throws Exception{
        System.out.println(RsaUtils.getPrivateKey(priKeyPath));
    }

    @Test
    void generateKey() throws Exception{
        RsaUtils.generateKey(pubKeyPath,priKeyPath,secret);

    }
}