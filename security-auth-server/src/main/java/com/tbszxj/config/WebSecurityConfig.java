package com.tbszxj.config;

import com.tbszxj.filter.JwtLoginFilter;
import com.tbszxj.filter.JwtVerifyFilter;
import com.tbszxj.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description 自定义安全过滤规则
 * @author zxj
 * @date 2019/11/16
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RsaKeyProperties properties;

    @Bean
    UserDetailsService userService(){
        return new UserServiceImpl();
    }
    //认证用户的来源【内存或数据库】
    /**
     * @deprecated 定义认证规则
     * @author zxj
     * @date 2019/11/16
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //数据库验证,使用自定义用户认证服务
        //数据库中角色取名时一定要加上ROLE_
        //匹配角色时框架默认会加上ROLE_
        auth.userDetailsService(userService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    //配置springsecurity相关信息
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制2请求授权规则
        http.csrf()
                .disable()
                .authorizeRequests()
                //VIP可以访问level1
                .antMatchers("/level1/**").hasRole("VIP")
                //CUT可以访问level2
                .antMatchers("/level2/**").hasRole("CUT")
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JwtLoginFilter(super.authenticationManager(),properties))
                .addFilter(new JwtVerifyFilter(super.authenticationManager(),properties))
                //禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
