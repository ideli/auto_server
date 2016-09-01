package cn.mwee.auto.auth.model;

import java.util.Date;

public class AuthUserRole {
    /**
     * 主键
     * auth_user_role.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户Id
     * auth_user_role.user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 角色Id
     * auth_user_role.role_id
     *
     * @mbggenerated
     */
    private Integer roleId;

    /**
     * 创建者
     * auth_user_role.creator
     *
     * @mbggenerated
     */
    private String creator;

    /**
     * 创建时间
     * auth_user_role.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 主键
     * This method returns the value of the database column auth_user_role.id
     *
     * @return the value of auth_user_role.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * This method sets the value of the database column auth_user_role.id
     *
     * @param id the value for auth_user_role.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户Id
     * This method returns the value of the database column auth_user_role.user_id
     *
     * @return the value of auth_user_role.user_id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户Id
     * This method sets the value of the database column auth_user_role.user_id
     *
     * @param userId the value for auth_user_role.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 角色Id
     * This method returns the value of the database column auth_user_role.role_id
     *
     * @return the value of auth_user_role.role_id
     *
     * @mbggenerated
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 角色Id
     * This method sets the value of the database column auth_user_role.role_id
     *
     * @param roleId the value for auth_user_role.role_id
     *
     * @mbggenerated
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 创建者
     * This method returns the value of the database column auth_user_role.creator
     *
     * @return the value of auth_user_role.creator
     *
     * @mbggenerated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * This method sets the value of the database column auth_user_role.creator
     *
     * @param creator the value for auth_user_role.creator
     *
     * @mbggenerated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 创建时间
     * This method returns the value of the database column auth_user_role.create_time
     *
     * @return the value of auth_user_role.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * This method sets the value of the database column auth_user_role.create_time
     *
     * @param createTime the value for auth_user_role.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}