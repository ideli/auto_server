package cn.mwee.auto.auth.model;

import java.util.Date;

public class AuthRole {
    /**
     * 主键
     * auth_role.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 角色编码
     * auth_role.rolecode
     *
     * @mbggenerated
     */
    private String rolecode;

    /**
     * 角色名称
     * auth_role.rolename
     *
     * @mbggenerated
     */
    private String rolename;

    /**
     * 描述
     * auth_role.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * 状态，0：不可用，1：可用
     * auth_role.status
     *
     * @mbggenerated
     */
    private Boolean status;

    /**
     * 创建者
     * auth_role.creator
     *
     * @mbggenerated
     */
    private String creator;

    /**
     * 创建时间
     * auth_role.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     * auth_role.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 主键
     * This method returns the value of the database column auth_role.id
     *
     * @return the value of auth_role.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * This method sets the value of the database column auth_role.id
     *
     * @param id the value for auth_role.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 角色编码
     * This method returns the value of the database column auth_role.rolecode
     *
     * @return the value of auth_role.rolecode
     *
     * @mbggenerated
     */
    public String getRolecode() {
        return rolecode;
    }

    /**
     * 角色编码
     * This method sets the value of the database column auth_role.rolecode
     *
     * @param rolecode the value for auth_role.rolecode
     *
     * @mbggenerated
     */
    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    /**
     * 角色名称
     * This method returns the value of the database column auth_role.rolename
     *
     * @return the value of auth_role.rolename
     *
     * @mbggenerated
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * 角色名称
     * This method sets the value of the database column auth_role.rolename
     *
     * @param rolename the value for auth_role.rolename
     *
     * @mbggenerated
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    /**
     * 描述
     * This method returns the value of the database column auth_role.description
     *
     * @return the value of auth_role.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     * This method sets the value of the database column auth_role.description
     *
     * @param description the value for auth_role.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 状态，0：不可用，1：可用
     * This method returns the value of the database column auth_role.status
     *
     * @return the value of auth_role.status
     *
     * @mbggenerated
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 状态，0：不可用，1：可用
     * This method sets the value of the database column auth_role.status
     *
     * @param status the value for auth_role.status
     *
     * @mbggenerated
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 创建者
     * This method returns the value of the database column auth_role.creator
     *
     * @return the value of auth_role.creator
     *
     * @mbggenerated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * This method sets the value of the database column auth_role.creator
     *
     * @param creator the value for auth_role.creator
     *
     * @mbggenerated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 创建时间
     * This method returns the value of the database column auth_role.create_time
     *
     * @return the value of auth_role.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * This method sets the value of the database column auth_role.create_time
     *
     * @param createTime the value for auth_role.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     * This method returns the value of the database column auth_role.update_time
     *
     * @return the value of auth_role.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * This method sets the value of the database column auth_role.update_time
     *
     * @param updateTime the value for auth_role.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}