package com.tbszxj.service.impl;

import com.tbszxj.entity.SysRole;
import com.tbszxj.entity.SysUser;
import com.tbszxj.mapper.SysRoleMapper;
import com.tbszxj.mapper.SysUserMapper;
import com.tbszxj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 自定义用户认证实现
 * @author zxj
 * @date 2019/11/17
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try{
            //查询出自己的用户对象转换成spring security的对象
            SysUser sysUser = new SysUser();
            sysUser.setUsername(s);
            SysUser user = sysUserMapper.selectOne(sysUser);
            if(sysUser == null){
                return null;
            }
            //获取角色集合
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<SysRole> sysRoles = sysRoleMapper.getRolesByUserName(s);
            for(SysRole role : sysRoles){
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
            return new User(user.getUsername(),
                    user.getPassword(),
                    user.getStatus()==1,
                    true,
                    true,
                    true,
                    authorities);
        }catch (Exception e){
            //认证失败
            return null;
        }
    }
}
