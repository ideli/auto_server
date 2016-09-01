package cn.mwee.auto.auth.model;

import java.io.Serializable;
import java.util.Date;

public class AuthUser implements Serializable {
    /**
     * 主键id
     * auth_user.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户名
     * auth_user.username
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 姓名
     * auth_user.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 邮箱
     * auth_user.email
     *
     * @mbggenerated
     */
    private String email;

    /**
     * 手机号
     * auth_user.phoneNo
     *
     * @mbggenerated
     */
    private String phoneno;

    /**
     * 部门
     * auth_user.department
     *
     * @mbggenerated
     */
    private String department;

    /**
     * 密码
     * auth_user.password
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 盐值：用于密码加密
     * auth_user.salt
     *
     * @mbggenerated
     */
    private String salt;

    /**
     * 状态，1：可用，0：不可用
     * auth_user.status
     *
     * @mbggenerated
     */
    private Boolean status;

    /**
     * 创建者
     * auth_user.creator
     *
     * @mbggenerated
     */
    private String creator;

    /**
     * 创建时间
     * auth_user.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     * auth_user.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 主键id
     * This method returns the value of the database column auth_user.id
     *
     * @return the value of auth_user.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键id
     * This method sets the value of the database column auth_user.id
     *
     * @param id the value for auth_user.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户名
     * This method returns the value of the database column auth_user.username
     *
     * @return the value of auth_user.username
     *
     * @mbggenerated
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     * This method sets the value of the database column auth_user.username
     *
     * @param username the value for auth_user.username
     *
     * @mbggenerated
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 姓名
     * This method returns the value of the database column auth_user.name
     *
     * @return the value of auth_user.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * This method sets the value of the database column auth_user.name
     *
     * @param name the value for auth_user.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 邮箱
     * This method returns the value of the database column auth_user.email
     *
     * @return the value of auth_user.email
     *
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     * This method sets the value of the database column auth_user.email
     *
     * @param email the value for auth_user.email
     *
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 手机号
     * This method returns the value of the database column auth_user.phoneNo
     *
     * @return the value of auth_user.phoneNo
     *
     * @mbggenerated
     */
    public String getPhoneno() {
        return phoneno;
    }

    /**
     * 手机号
     * This method sets the value of the database column auth_user.phoneNo
     *
     * @param phoneno the value for auth_user.phoneNo
     *
     * @mbggenerated
     */
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    /**
     * 部门
     * This method returns the value of the database column auth_user.department
     *
     * @return the value of auth_user.department
     *
     * @mbggenerated
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 部门
     * This method sets the value of the database column auth_user.department
     *
     * @param department the value for auth_user.department
     *
     * @mbggenerated
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 密码
     * This method returns the value of the database column auth_user.password
     *
     * @return the value of auth_user.password
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * This method sets the value of the database column auth_user.password
     *
     * @param password the value for auth_user.password
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 盐值：用于密码加密
     * This method returns the value of the database column auth_user.salt
     *
     * @return the value of auth_user.salt
     *
     * @mbggenerated
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 盐值：用于密码加密
     * This method sets the value of the database column auth_user.salt
     *
     * @param salt the value for auth_user.salt
     *
     * @mbggenerated
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 状态，1：可用，0：不可用
     * This method returns the value of the database column auth_user.status
     *
     * @return the value of auth_user.status
     *
     * @mbggenerated
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 状态，1：可用，0：不可用
     * This method sets the value of the database column auth_user.status
     *
     * @param status the value for auth_user.status
     *
     * @mbggenerated
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 创建者
     * This method returns the value of the database column auth_user.creator
     *
     * @return the value of auth_user.creator
     *
     * @mbggenerated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * This method sets the value of the database column auth_user.creator
     *
     * @param creator the value for auth_user.creator
     *
     * @mbggenerated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 创建时间
     * This method returns the value of the database column auth_user.create_time
     *
     * @return the value of auth_user.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * This method sets the value of the database column auth_user.create_time
     *
     * @param createTime the value for auth_user.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     * This method returns the value of the database column auth_user.update_time
     *
     * @return the value of auth_user.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * This method sets the value of the database column auth_user.update_time
     *
     * @param updateTime the value for auth_user.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}