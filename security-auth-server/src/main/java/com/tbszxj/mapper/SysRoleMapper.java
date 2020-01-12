package com.tbszxj.mapper;

import com.tbszxj.entity.SysRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @description 用户角色mapper接口
 * @author zxj
 * @date 2019/11/17
 */
public interface SysRoleMapper extends Mapper<SysRole> {

    /**
     * 根据用户账户获取用户角色信息
     * @param userName 用户账户
     * @return 角色列表
     */
    public List<SysRole> getRolesByUserName(String userName);

}