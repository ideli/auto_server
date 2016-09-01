package cn.mwee.auto.deploy.model;

import java.util.Date;

public class Flow {
    /**
     * flows.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 流程名
     * flows.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 模板id
     * flows.template_id
     *
     * @mbggenerated
     */
    private Integer templateId;

    /**
     * 项目Id
     * flows.project_id
     *
     * @mbggenerated
     */
    private Integer projectId;

    /**
     * 区域Ip或host信息
     * flows.zones
     *
     * @mbggenerated
     */
    private String zones;

    /**
     * 任务执行参数json格式
     * flows.params
     *
     * @mbggenerated
     */
    private String params;

    /**
     * 是否需要构建：0，不需要；1，需要
     * flows.needbuild
     *
     * @mbggenerated
     */
    private Byte needbuild;

    /**
     * 版本控制系统分支
     * flows.vcs_branch
     *
     * @mbggenerated
     */
    private String vcsBranch;

    /**
     * 状态 [新建,运行中,手动,定时,失败,成功]
     * flows.state
     *
     * @mbggenerated
     */
    private String state;

    /**
     * 审核状态：0，无需审核；1，未审核；2，审核通过；3审核不通过
     * flows.isreview
     *
     * @mbggenerated
     */
    private Byte isreview;

    /**
     * flows.reviewer
     *
     * @mbggenerated
     */
    private String reviewer;

    /**
     * flows.reviewdate
     *
     * @mbggenerated
     */
    private Date reviewdate;

    /**
     * 创建者
     * flows.creator
     *
     * @mbggenerated
     */
    private String creator;

    /**
     * 操作者
     * flows.operater
     *
     * @mbggenerated
     */
    private String operater;

    /**
     * flows.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * flows.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method returns the value of the database column flows.id
     *
     * @return the value of flows.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column flows.id
     *
     * @param id the value for flows.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 流程名
     * This method returns the value of the database column flows.name
     *
     * @return the value of flows.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * 流程名
     * This method sets the value of the database column flows.name
     *
     * @param name the value for flows.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 模板id
     * This method returns the value of the database column flows.template_id
     *
     * @return the value of flows.template_id
     *
     * @mbggenerated
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * 模板id
     * This method sets the value of the database column flows.template_id
     *
     * @param templateId the value for flows.template_id
     *
     * @mbggenerated
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * 项目Id
     * This method returns the value of the database column flows.project_id
     *
     * @return the value of flows.project_id
     *
     * @mbggenerated
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 项目Id
     * This method sets the value of the database column flows.project_id
     *
     * @param projectId the value for flows.project_id
     *
     * @mbggenerated
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 区域Ip或host信息
     * This method returns the value of the database column flows.zones
     *
     * @return the value of flows.zones
     *
     * @mbggenerated
     */
    public String getZones() {
        return zones;
    }

    /**
     * 区域Ip或host信息
     * This method sets the value of the database column flows.zones
     *
     * @param zones the value for flows.zones
     *
     * @mbggenerated
     */
    public void setZones(String zones) {
        this.zones = zones;
    }

    /**
     * 任务执行参数json格式
     * This method returns the value of the database column flows.params
     *
     * @return the value of flows.params
     *
     * @mbggenerated
     */
    public String getParams() {
        return params;
    }

    /**
     * 任务执行参数json格式
     * This method sets the value of the database column flows.params
     *
     * @param params the value for flows.params
     *
     * @mbggenerated
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 是否需要构建：0，不需要；1，需要
     * This method returns the value of the database column flows.needbuild
     *
     * @return the value of flows.needbuild
     *
     * @mbggenerated
     */
    public Byte getNeedbuild() {
        return needbuild;
    }

    /**
     * 是否需要构建：0，不需要；1，需要
     * This method sets the value of the database column flows.needbuild
     *
     * @param needbuild the value for flows.needbuild
     *
     * @mbggenerated
     */
    public void setNeedbuild(Byte needbuild) {
        this.needbuild = needbuild;
    }

    /**
     * 版本控制系统分支
     * This method returns the value of the database column flows.vcs_branch
     *
     * @return the value of flows.vcs_branch
     *
     * @mbggenerated
     */
    public String getVcsBranch() {
        return vcsBranch;
    }

    /**
     * 版本控制系统分支
     * This method sets the value of the database column flows.vcs_branch
     *
     * @param vcsBranch the value for flows.vcs_branch
     *
     * @mbggenerated
     */
    public void setVcsBranch(String vcsBranch) {
        this.vcsBranch = vcsBranch;
    }

    /**
     * 状态 [新建,运行中,手动,定时,失败,成功]
     * This method returns the value of the database column flows.state
     *
     * @return the value of flows.state
     *
     * @mbggenerated
     */
    public String getState() {
        return state;
    }

    /**
     * 状态 [新建,运行中,手动,定时,失败,成功]
     * This method sets the value of the database column flows.state
     *
     * @param state the value for flows.state
     *
     * @mbggenerated
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 审核状态：0，无需审核；1，未审核；2，审核通过；3审核不通过
     * This method returns the value of the database column flows.isreview
     *
     * @return the value of flows.isreview
     *
     * @mbggenerated
     */
    public Byte getIsreview() {
        return isreview;
    }

    /**
     * 审核状态：0，无需审核；1，未审核；2，审核通过；3审核不通过
     * This method sets the value of the database column flows.isreview
     *
     * @param isreview the value for flows.isreview
     *
     * @mbggenerated
     */
    public void setIsreview(Byte isreview) {
        this.isreview = isreview;
    }

    /**
     * This method returns the value of the database column flows.reviewer
     *
     * @return the value of flows.reviewer
     *
     * @mbggenerated
     */
    public String getReviewer() {
        return reviewer;
    }

    /**
     * This method sets the value of the database column flows.reviewer
     *
     * @param reviewer the value for flows.reviewer
     *
     * @mbggenerated
     */
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * This method returns the value of the database column flows.reviewdate
     *
     * @return the value of flows.reviewdate
     *
     * @mbggenerated
     */
    public Date getReviewdate() {
        return reviewdate;
    }

    /**
     * This method sets the value of the database column flows.reviewdate
     *
     * @param reviewdate the value for flows.reviewdate
     *
     * @mbggenerated
     */
    public void setReviewdate(Date reviewdate) {
        this.reviewdate = reviewdate;
    }

    /**
     * 创建者
     * This method returns the value of the database column flows.creator
     *
     * @return the value of flows.creator
     *
     * @mbggenerated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * This method sets the value of the database column flows.creator
     *
     * @param creator the value for flows.creator
     *
     * @mbggenerated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 操作者
     * This method returns the value of the database column flows.operater
     *
     * @return the value of flows.operater
     *
     * @mbggenerated
     */
    public String getOperater() {
        return operater;
    }

    /**
     * 操作者
     * This method sets the value of the database column flows.operater
     *
     * @param operater the value for flows.operater
     *
     * @mbggenerated
     */
    public void setOperater(String operater) {
        this.operater = operater;
    }

    /**
     * This method returns the value of the database column flows.create_time
     *
     * @return the value of flows.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method sets the value of the database column flows.create_time
     *
     * @param createTime the value for flows.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method returns the value of the database column flows.update_time
     *
     * @return the value of flows.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method sets the value of the database column flows.update_time
     *
     * @param updateTime the value for flows.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}