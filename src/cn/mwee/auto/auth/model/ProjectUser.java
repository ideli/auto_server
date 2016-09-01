package cn.mwee.auto.auth.model;

import java.util.Date;

public class ProjectUser {
    /**
     * Id，主键
     * project_user.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 项目Id
     * project_user.projectId
     *
     * @mbggenerated
     */
    private Integer projectid;

    /**
     * 用Id
     * project_user.userId
     *
     * @mbggenerated
     */
    private Integer userid;

    /**
     * 用户类型：DEV；TEST；ADMIN；PRODUCT
     * project_user.userType
     *
     * @mbggenerated
     */
    private String usertype;

    /**
     * 创建人
     * project_user.creator
     *
     * @mbggenerated
     */
    private String creator;

    /**
     * 创建时间
     * project_user.createTime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * Id，主键
     * This method returns the value of the database column project_user.id
     *
     * @return the value of project_user.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * Id，主键
     * This method sets the value of the database column project_user.id
     *
     * @param id the value for project_user.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 项目Id
     * This method returns the value of the database column project_user.projectId
     *
     * @return the value of project_user.projectId
     *
     * @mbggenerated
     */
    public Integer getProjectid() {
        return projectid;
    }

    /**
     * 项目Id
     * This method sets the value of the database column project_user.projectId
     *
     * @param projectid the value for project_user.projectId
     *
     * @mbggenerated
     */
    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    /**
     * 用Id
     * This method returns the value of the database column project_user.userId
     *
     * @return the value of project_user.userId
     *
     * @mbggenerated
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 用Id
     * This method sets the value of the database column project_user.userId
     *
     * @param userid the value for project_user.userId
     *
     * @mbggenerated
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 用户类型：DEV；TEST；ADMIN；PRODUCT
     * This method returns the value of the database column project_user.userType
     *
     * @return the value of project_user.userType
     *
     * @mbggenerated
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * 用户类型：DEV；TEST；ADMIN；PRODUCT
     * This method sets the value of the database column project_user.userType
     *
     * @param usertype the value for project_user.userType
     *
     * @mbggenerated
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    /**
     * 创建人
     * This method returns the value of the database column project_user.creator
     *
     * @return the value of project_user.creator
     *
     * @mbggenerated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建人
     * This method sets the value of the database column project_user.creator
     *
     * @param creator the value for project_user.creator
     *
     * @mbggenerated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 创建时间
     * This method returns the value of the database column project_user.createTime
     *
     * @return the value of project_user.createTime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 创建时间
     * This method sets the value of the database column project_user.createTime
     *
     * @param createtime the value for project_user.createTime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}