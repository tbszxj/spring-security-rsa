package com.tbszxj.utils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA工具类
 *
 * @author zxj
 * @date 2020/1/4
 */
public class RsaUtils {



    /**
     * RSA最小为2048
     */
    public static final Integer DEFAULT_KEY_SIZE = 2048;

    /**
     * 从指定文件中获取公钥
     * @author zxj
     * @date  2020/1/4
     */
    public static PublicKey getPublicKey(String filename) throws Exception {
        byte[] bytes = readFile(filename);
        return getPublicKey(bytes);
    }

    /**
     * 从字节流中获取公钥
     * @author zxj
     * @date  2020/1/4
     */
    public static PublicKey getPublicKey(byte[] bytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    /**
     * 从指定文件中获取私钥
     * @author zxj
     * @date  2020/1/4
     */
    public static PrivateKey getPrivateKey(String filename) throws Exception {
        byte[] bytes = readFile(filename);
        return getPrivateKey(bytes);
    }

    /**
     * 从字节流中获取私钥
     * @author zxj
     * @date  2020/1/4
     */
    public static PrivateKey getPrivateKey(byte[] bytes) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }
    
    /**
     * 根据密文，生存rsa公钥和私钥,并写入指定文件
     * @param publicKeyFilename 公钥存放路径
     * @param privateKeyFilename 私钥存放路径
     * @param secret 生成密钥的密文
     * @author zxj
     * @date  2020/1/4
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret,int keySize) throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(Math.max(keySize,DEFAULT_KEY_SIZE), secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        //获取公钥并写到指定文件中
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        //publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyFilename,publicKeyBytes);
        //获取私钥并写到指定文件中
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        //privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyFilename,privateKeyBytes);
    }

    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret) throws Exception {
        generateKey(publicKeyFilename,privateKeyFilename,secret,DEFAULT_KEY_SIZE);
    }
    
    /**
     * 读取文件信息
     * @author zxj
     * @date  2020/1/4
     */
    private static byte[] readFile(String fileName) throws Exception {
        return Files.readAllBytes(new File(fileName).toPath());
    }
    
    /**
     * 将字节流写入到目标文件中
     * @author zxj
     * @date  2020/1/4
     */
    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }
}
