package com.tbszxj;

import com.tbszxj.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 认证服务启动类
 * @author zxj
 * @date  2020/1/4
 */
@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@MapperScan("com.tbszxj.mapper")
public class AuthServerApplication {
    public static void main(String[] args){
        SpringApplication.run(AuthServerApplication.class,args);
    }
}
