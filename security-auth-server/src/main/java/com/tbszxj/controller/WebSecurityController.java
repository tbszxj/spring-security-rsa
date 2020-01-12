package com.tbszxj.controller;

import com.tbszxj.entity.SysUser;
import com.tbszxj.mapper.SysUserMapper;
import com.tbszxj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 权限控制器类
 * @author zxj
 * @date 2019/11/16
 */
@RestController
public class WebSecurityController {

    @Autowired
    UserService userService;

    @Autowired
    SysUserMapper sysUserMapper;



    @RequestMapping("/")
    public String index(){
        SysUser sysUser = sysUserMapper.selectByPrimaryKey("1");
        return "welcome to index "+sysUser.getUsername();
    }

    @RequestMapping("/level1/{path}")
    public String level1(@PathVariable("path") String path){
        return "welcome to vip "+path;
    }

    @RequestMapping("/level2/{path}")
    public String level2(@PathVariable("path") String path){
        return "welcome to cut "+path;
    }

    @RequestMapping("/level3/{path}")
    public String level3(@PathVariable("path") String path){
        return "welcome to yk "+path;
    }
}
