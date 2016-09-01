package cn.mwee.auto.deploy.model;

import java.util.Date;

public class AutoTemplate {
    /**
     * templates.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 模板名
     * templates.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 项目Id；为空时表示通用模板
     * templates.project_id
     *
     * @mbggenerated
     */
    private Integer projectId;

    /**
     * 版本控制系统类型
     * templates.vcs_type
     *
     * @mbggenerated
     */
    private String vcsType;

    /**
     * 版本仓库地址
     * templates.vcs_rep
     *
     * @mbggenerated
     */
    private String vcsRep;

    /**
     * 审核(0:否,1:是)
     * templates.review
     *
     * @mbggenerated
     */
    private Byte review;

    /**
     * 使用中(0:否,1:是)
     * templates.inuse
     *
     * @mbggenerated
     */
    private Byte inuse;

    /**
     * 创建者
     * templates.creator
     *
     * @mbggenerated
     */
    private String creator;

    /**
     * 操作者
     * templates.operater
     *
     * @mbggenerated
     */
    private String operater;

    /**
     * 创建时间
     * templates.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     * templates.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 父模板
     * templates.pid
     *
     * @mbggenerated
     */
    private Integer pid;

    /**
     * This method returns the value of the database column templates.id
     *
     * @return the value of templates.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column templates.id
     *
     * @param id the value for templates.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 模板名
     * This method returns the value of the database column templates.name
     *
     * @return the value of templates.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * 模板名
     * This method sets the value of the database column templates.name
     *
     * @param name the value for templates.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 项目Id；为空时表示通用模板
     * This method returns the value of the database column templates.project_id
     *
     * @return the value of templates.project_id
     *
     * @mbggenerated
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 项目Id；为空时表示通用模板
     * This method sets the value of the database column templates.project_id
     *
     * @param projectId the value for templates.project_id
     *
     * @mbggenerated
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 版本控制系统类型
     * This method returns the value of the database column templates.vcs_type
     *
     * @return the value of templates.vcs_type
     *
     * @mbggenerated
     */
    public String getVcsType() {
        return vcsType;
    }

    /**
     * 版本控制系统类型
     * This method sets the value of the database column templates.vcs_type
     *
     * @param vcsType the value for templates.vcs_type
     *
     * @mbggenerated
     */
    public void setVcsType(String vcsType) {
        this.vcsType = vcsType;
    }

    /**
     * 版本仓库地址
     * This method returns the value of the database column templates.vcs_rep
     *
     * @return the value of templates.vcs_rep
     *
     * @mbggenerated
     */
    public String getVcsRep() {
        return vcsRep;
    }

    /**
     * 版本仓库地址
     * This method sets the value of the database column templates.vcs_rep
     *
     * @param vcsRep the value for templates.vcs_rep
     *
     * @mbggenerated
     */
    public void setVcsRep(String vcsRep) {
        this.vcsRep = vcsRep;
    }

    /**
     * 审核(0:否,1:是)
     * This method returns the value of the database column templates.review
     *
     * @return the value of templates.review
     *
     * @mbggenerated
     */
    public Byte getReview() {
        return review;
    }

    /**
     * 审核(0:否,1:是)
     * This method sets the value of the database column templates.review
     *
     * @param review the value for templates.review
     *
     * @mbggenerated
     */
    public void setReview(Byte review) {
        this.review = review;
    }

    /**
     * 使用中(0:否,1:是)
     * This method returns the value of the database column templates.inuse
     *
     * @return the value of templates.inuse
     *
     * @mbggenerated
     */
    public Byte getInuse() {
        return inuse;
    }

    /**
     * 使用中(0:否,1:是)
     * This method sets the value of the database column templates.inuse
     *
     * @param inuse the value for templates.inuse
     *
     * @mbggenerated
     */
    public void setInuse(Byte inuse) {
        this.inuse = inuse;
    }

    /**
     * 创建者
     * This method returns the value of the database column templates.creator
     *
     * @return the value of templates.creator
     *
     * @mbggenerated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * This method sets the value of the database column templates.creator
     *
     * @param creator the value for templates.creator
     *
     * @mbggenerated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 操作者
     * This method returns the value of the database column templates.operater
     *
     * @return the value of templates.operater
     *
     * @mbggenerated
     */
    public String getOperater() {
        return operater;
    }

    /**
     * 操作者
     * This method sets the value of the database column templates.operater
     *
     * @param operater the value for templates.operater
     *
     * @mbggenerated
     */
    public void setOperater(String operater) {
        this.operater = operater;
    }

    /**
     * 创建时间
     * This method returns the value of the database column templates.create_time
     *
     * @return the value of templates.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * This method sets the value of the database column templates.create_time
     *
     * @param createTime the value for templates.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     * This method returns the value of the database column templates.update_time
     *
     * @return the value of templates.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * This method sets the value of the database column templates.update_time
     *
     * @param updateTime the value for templates.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 父模板
     * This method returns the value of the database column templates.pid
     *
     * @return the value of templates.pid
     *
     * @mbggenerated
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 父模板
     * This method sets the value of the database column templates.pid
     *
     * @param pid the value for templates.pid
     *
     * @mbggenerated
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }
}