package com.tbszxj.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tbszxj.common.ApiResult;
import com.tbszxj.config.RsaKeyProperties;
import com.tbszxj.entity.SysRole;
import com.tbszxj.entity.SysUser;
import com.tbszxj.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.tbszxj.utils.PayLoad;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 验证过滤器
 * @author zxj
 * @date  2020/1/4
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    private RsaKeyProperties properties;

    public JwtVerifyFilter(AuthenticationManager authenticationManager,RsaKeyProperties properties) {
        super(authenticationManager);
        this.properties = properties;
    }

    
    /**
     * 用户已经登录验证token是否合法
     * 合法的时候将验证成功相关信息放入到过滤器链中
     * 用户没有登录，提示相关信息
     * @author zxj
     * @date  2020/1/12
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("token");
        if(header != null){
            //token格式正确，进行验证
            PayLoad<SysUser> payload = JwtUtils.getInfoFromToken(header,properties.getPublicKey(), SysUser.class);
            SysUser sysUser = payload.getUserInfo();
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<String> sysRoles = sysUser.getAuthorities();
            for(String role : sysRoles){
                authorities.add(new SimpleGrantedAuthority(role));
            }
            if(null != sysUser){
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                        sysUser.getUsername(),null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authRequest);
                chain.doFilter(request,response);
            }
        }else{
            chain.doFilter(request,response);
            try{
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                PrintWriter out = response.getWriter();
                ApiResult<Void> apiResult = new ApiResult<>();
                apiResult.setMessage("请登录！");
                out.write(new ObjectMapper().writeValueAsString(apiResult));
                out.flush();
                out.close();
            }catch (Exception e1){
                logger.info(e1.getMessage());
            }
        }
    }
}
