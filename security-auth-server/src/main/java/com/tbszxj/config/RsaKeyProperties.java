package com.tbszxj.config;

import com.tbszxj.utils.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 公私钥配置信息
 * @author zxj
 * @date  2020/1/4
 */

@ConfigurationProperties("rsa.key")
public class RsaKeyProperties {

    private String publicKeyFile;
    private String privateKeyFile;

    private PublicKey publicKey;
    private PrivateKey privateKey;


    /**
     * 加上PostConstruct注解确保文件路径值注入后执行下面的初始化
     * @date 2020/1/4
     */
    @PostConstruct
    public void createRsaKey() throws Exception{
        publicKey = RsaUtils.getPublicKey(publicKeyFile);
        privateKey = RsaUtils.getPrivateKey(privateKeyFile);
    }

    public String getPublicKeyFile() {
        return publicKeyFile;
    }

    public void setPublicKeyFile(String publicKeyFile) {
        this.publicKeyFile = publicKeyFile;
    }

    public String getPrivateKeyFile() {
        return privateKeyFile;
    }

    public void setPrivateKeyFile(String privateKeyFile) {
        this.privateKeyFile = privateKeyFile;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
