package com.tbszxj.utils;

import lombok.Data;

import java.util.Date;

/**
 * 方便后期获取token中的用户信息，将token中的载荷部分封装为一个对象
 * @author zxj
 * @date  2020/1/4
 */
@Data
public class PayLoad<T> {

    private String id;
    private T userInfo;
    private Date expiration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(T userInfo) {
        this.userInfo = userInfo;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
