package com.tbszxj.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbszxj.common.ApiResult;
import com.tbszxj.config.RsaKeyProperties;
import com.tbszxj.entity.SysRole;
import com.tbszxj.entity.SysUser;
import com.tbszxj.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 重写认证的过滤器
 * @author zxj
 * @date  2020/1/4
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtLoginFilter.class);

    private AuthenticationManager authenticationManager;

    private RsaKeyProperties properties;


    /**
     * 创建对象时传入所需的内容
     */
    public JwtLoginFilter(AuthenticationManager authenticationManager, RsaKeyProperties properties) {
        this.authenticationManager = authenticationManager;
        this.properties = properties;
    }


    /**
     * 重写认证逻辑，主要是修改获取用户名密码的方式
     * 原先从表单中获取，现在从请求体中获取
     * 将获取的用户名密码放到过滤器链中，后续会在自定义的UserServiceImpl中使用
     * 使用自定义UserService还需要进行相关配置
     * @author zxj
     * @date  2020/1/4
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            SysUser sysUser = new ObjectMapper().readValue(request.getInputStream(), SysUser.class);
            //原先的username和password是从form表单中解析出来的，现在从请求体获取
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(sysUser.getUsername(),sysUser.getPassword());
            return authenticationManager.authenticate(authRequest);
        }catch (Exception e){
            try{
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                ApiResult<Void> apiResult = new ApiResult<>();
                apiResult.setMessage("用户名或密码错误");
                out.write(new ObjectMapper().writeValueAsString(apiResult));
                out.flush();
                out.close();
            }catch (Exception e1){
                logger.info(e1.getMessage());
            }
            throw new RuntimeException(e);
        }
    }



    /**
     * 认证成功生成token返回给用户
     * 作用是认证成功回写token
     * @author zxj
     * @date  2020/1/4
     */
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SysUser user = new SysUser();
        user.setUsername(authResult.getName());
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority authority : authResult.getAuthorities()){
            authorities.add(authority.getAuthority());
        }
        user.setAuthorities(authorities);
        String token = JwtUtils.generateToken(user,properties.getPrivateKey(),24*60);
        response.addHeader("token",token);
        try{
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            ApiResult<Void> apiResult = new ApiResult<>();
            apiResult.setMessage("认证成功");
            out.write(new ObjectMapper().writeValueAsString(apiResult));
            out.flush();
            out.close();
        }catch (Exception e1){
            logger.info(e1.getMessage());
        }
    }
}
