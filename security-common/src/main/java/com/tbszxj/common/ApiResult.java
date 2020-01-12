package com.tbszxj.common;

import java.io.Serializable;

/**
 * 通用接口返回信息类
 * @author zxj
 * @date  2020/1/4
 */
public class ApiResult<T> implements Serializable {

    /**
     * 成功标识
     */
    private boolean success;

    /**
     * 描述信息
     */
    private String message;

    /**
     * 返回编码
     */
    private Integer code;

    /**
     * 响应数据
     */
    private T data;


    /**
     * 默认创建一个成功的返回对象
     */
    public ApiResult() {
        this.code = 200;
        this.message = "success";
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
