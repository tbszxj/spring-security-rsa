package com.tbszxj.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_role")
public class SysRole{
    /**
     * 角色id
     */
    @Id
    private String id;

    /**
     * 角色名
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色描述
     */
    @Column(name = "role_desc")
    private String roleDesc;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 获取角色id
     *
     * @return id - 角色id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置角色id
     *
     * @param id 角色id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取角色名
     *
     * @return role_name - 角色名
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名
     *
     * @param roleName 角色名
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 获取角色描述
     *
     * @return role_desc - 角色描述
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * 设置角色描述
     *
     * @param roleDesc 角色描述
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

}