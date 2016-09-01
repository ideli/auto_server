package cn.mwee.auto.auth.model;

import java.util.Date;

public class AuthRolePermission {
    /**
     * 主键
     * auth_role_permission.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 角色Id
     * auth_role_permission.role_id
     *
     * @mbggenerated
     */
    private Integer roleId;

    /**
     * 权限Id
     * auth_role_permission.permission_id
     *
     * @mbggenerated
     */
    private Integer permissionId;

    /**
     * auth_role_permission.creator
     *
     * @mbggenerated
     */
    private String creator;

    /**
     * 创建时间
     * auth_role_permission.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 主键
     * This method returns the value of the database column auth_role_permission.id
     *
     * @return the value of auth_role_permission.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * This method sets the value of the database column auth_role_permission.id
     *
     * @param id the value for auth_role_permission.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 角色Id
     * This method returns the value of the database column auth_role_permission.role_id
     *
     * @return the value of auth_role_permission.role_id
     *
     * @mbggenerated
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 角色Id
     * This method sets the value of the database column auth_role_permission.role_id
     *
     * @param roleId the value for auth_role_permission.role_id
     *
     * @mbggenerated
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 权限Id
     * This method returns the value of the database column auth_role_permission.permission_id
     *
     * @return the value of auth_role_permission.permission_id
     *
     * @mbggenerated
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * 权限Id
     * This method sets the value of the database column auth_role_permission.permission_id
     *
     * @param permissionId the value for auth_role_permission.permission_id
     *
     * @mbggenerated
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * This method returns the value of the database column auth_role_permission.creator
     *
     * @return the value of auth_role_permission.creator
     *
     * @mbggenerated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method sets the value of the database column auth_role_permission.creator
     *
     * @param creator the value for auth_role_permission.creator
     *
     * @mbggenerated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 创建时间
     * This method returns the value of the database column auth_role_permission.create_time
     *
     * @return the value of auth_role_permission.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * This method sets the value of the database column auth_role_permission.create_time
     *
     * @param createTime the value for auth_role_permission.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}