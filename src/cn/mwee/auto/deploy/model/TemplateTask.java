package cn.mwee.auto.deploy.model;

import java.util.Date;

public class TemplateTask {
    /**
     * template_tasks.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 模板id
     * template_tasks.template_id
     *
     * @mbggenerated
     */
    private Integer templateId;

    /**
     * template_tasks.group
     *
     * @mbggenerated
     */
    private Byte group;

    /**
     * 优先级
     * template_tasks.priority
     *
     * @mbggenerated
     */
    private Short priority;

    /**
     * 任务id
     * template_tasks.task_id
     *
     * @mbggenerated
     */
    private Integer taskId;

    /**
     * 任务类型 [AUTO,MANUAL,TIMER]
     * template_tasks.task_type
     *
     * @mbggenerated
     */
    private String taskType;

    /**
     * 使用中(0:否,1:是)
     * template_tasks.inuse
     *
     * @mbggenerated
     */
    private Byte inuse;

    /**
     * 创建者
     * template_tasks.creator
     *
     * @mbggenerated
     */
    private String creator;

    /**
     * 操作者
     * template_tasks.operater
     *
     * @mbggenerated
     */
    private String operater;

    /**
     * template_tasks.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * template_tasks.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method returns the value of the database column template_tasks.id
     *
     * @return the value of template_tasks.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column template_tasks.id
     *
     * @param id the value for template_tasks.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 模板id
     * This method returns the value of the database column template_tasks.template_id
     *
     * @return the value of template_tasks.template_id
     *
     * @mbggenerated
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * 模板id
     * This method sets the value of the database column template_tasks.template_id
     *
     * @param templateId the value for template_tasks.template_id
     *
     * @mbggenerated
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * This method returns the value of the database column template_tasks.group
     *
     * @return the value of template_tasks.group
     *
     * @mbggenerated
     */
    public Byte getGroup() {
        return group;
    }

    /**
     * This method sets the value of the database column template_tasks.group
     *
     * @param group the value for template_tasks.group
     *
     * @mbggenerated
     */
    public void setGroup(Byte group) {
        this.group = group;
    }

    /**
     * 优先级
     * This method returns the value of the database column template_tasks.priority
     *
     * @return the value of template_tasks.priority
     *
     * @mbggenerated
     */
    public Short getPriority() {
        return priority;
    }

    /**
     * 优先级
     * This method sets the value of the database column template_tasks.priority
     *
     * @param priority the value for template_tasks.priority
     *
     * @mbggenerated
     */
    public void setPriority(Short priority) {
        this.priority = priority;
    }

    /**
     * 任务id
     * This method returns the value of the database column template_tasks.task_id
     *
     * @return the value of template_tasks.task_id
     *
     * @mbggenerated
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * 任务id
     * This method sets the value of the database column template_tasks.task_id
     *
     * @param taskId the value for template_tasks.task_id
     *
     * @mbggenerated
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * 任务类型 [AUTO,MANUAL,TIMER]
     * This method returns the value of the database column template_tasks.task_type
     *
     * @return the value of template_tasks.task_type
     *
     * @mbggenerated
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * 任务类型 [AUTO,MANUAL,TIMER]
     * This method sets the value of the database column template_tasks.task_type
     *
     * @param taskType the value for template_tasks.task_type
     *
     * @mbggenerated
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * 使用中(0:否,1:是)
     * This method returns the value of the database column template_tasks.inuse
     *
     * @return the value of template_tasks.inuse
     *
     * @mbggenerated
     */
    public Byte getInuse() {
        return inuse;
    }

    /**
     * 使用中(0:否,1:是)
     * This method sets the value of the database column template_tasks.inuse
     *
     * @param inuse the value for template_tasks.inuse
     *
     * @mbggenerated
     */
    public void setInuse(Byte inuse) {
        this.inuse = inuse;
    }

    /**
     * 创建者
     * This method returns the value of the database column template_tasks.creator
     *
     * @return the value of template_tasks.creator
     *
     * @mbggenerated
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * This method sets the value of the database column template_tasks.creator
     *
     * @param creator the value for template_tasks.creator
     *
     * @mbggenerated
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 操作者
     * This method returns the value of the database column template_tasks.operater
     *
     * @return the value of template_tasks.operater
     *
     * @mbggenerated
     */
    public String getOperater() {
        return operater;
    }

    /**
     * 操作者
     * This method sets the value of the database column template_tasks.operater
     *
     * @param operater the value for template_tasks.operater
     *
     * @mbggenerated
     */
    public void setOperater(String operater) {
        this.operater = operater;
    }

    /**
     * This method returns the value of the database column template_tasks.create_time
     *
     * @return the value of template_tasks.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method sets the value of the database column template_tasks.create_time
     *
     * @param createTime the value for template_tasks.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method returns the value of the database column template_tasks.update_time
     *
     * @return the value of template_tasks.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method sets the value of the database column template_tasks.update_time
     *
     * @param updateTime the value for template_tasks.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}