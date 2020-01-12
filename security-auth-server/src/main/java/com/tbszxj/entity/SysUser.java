package com.tbszxj.entity;

import javax.persistence.*;
import java.util.List;

@Table(name = "sys_user")
public class SysUser {
    /**
     * 用户id
     */
    @Id
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态0禁用1正常
     */
    private Integer status;


    /**
     * 用户角色集合
     */
    List<String> authorities;

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取状态0禁用1正常
     *
     * @return status - 状态0禁用1正常
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态0禁用1正常
     *
     * @param status 状态0禁用1正常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}