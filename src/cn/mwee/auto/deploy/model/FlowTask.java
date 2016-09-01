package cn.mwee.auto.deploy.model;

import java.util.Date;

public class FlowTask {
    /**
     * flow_tasks.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 流程id
     * flow_tasks.flow_id
     *
     * @mbggenerated
     */
    private Integer flowId;

    /**
     * 区信息
     * flow_tasks.zone
     *
     * @mbggenerated
     */
    private String zone;

    /**
     * 任务id
     * flow_tasks.task_id
     *
     * @mbggenerated
     */
    private Integer taskId;

    /**
     * 任务参数
     * flow_tasks.task_param
     *
     * @mbggenerated
     */
    private String taskParam;

    /**
     * 任务类型 [手动，自动，定时]
     * flow_tasks.task_type
     *
     * @mbggenerated
     */
    private String taskType;

    /**
     * flow_tasks.group
     *
     * @mbggenerated
     */
    private Byte group;

    /**
     * 优先级
     * flow_tasks.priority
     *
     * @mbggenerated
     */
    private Short priority;

    /**
     * 延迟
     * flow_tasks.delay
     *
     * @mbggenerated
     */
    private Integer delay;

    /**
     * 状态[新建,运行中,手动,定时,失败,成功]
     * flow_tasks.state
     *
     * @mbggenerated
     */
    private String state;

    /**
     * flow_tasks.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * flow_tasks.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method returns the value of the database column flow_tasks.id
     *
     * @return the value of flow_tasks.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column flow_tasks.id
     *
     * @param id the value for flow_tasks.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 流程id
     * This method returns the value of the database column flow_tasks.flow_id
     *
     * @return the value of flow_tasks.flow_id
     *
     * @mbggenerated
     */
    public Integer getFlowId() {
        return flowId;
    }

    /**
     * 流程id
     * This method sets the value of the database column flow_tasks.flow_id
     *
     * @param flowId the value for flow_tasks.flow_id
     *
     * @mbggenerated
     */
    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    /**
     * 区信息
     * This method returns the value of the database column flow_tasks.zone
     *
     * @return the value of flow_tasks.zone
     *
     * @mbggenerated
     */
    public String getZone() {
        return zone;
    }

    /**
     * 区信息
     * This method sets the value of the database column flow_tasks.zone
     *
     * @param zone the value for flow_tasks.zone
     *
     * @mbggenerated
     */
    public void setZone(String zone) {
        this.zone = zone;
    }

    /**
     * 任务id
     * This method returns the value of the database column flow_tasks.task_id
     *
     * @return the value of flow_tasks.task_id
     *
     * @mbggenerated
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * 任务id
     * This method sets the value of the database column flow_tasks.task_id
     *
     * @param taskId the value for flow_tasks.task_id
     *
     * @mbggenerated
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * 任务参数
     * This method returns the value of the database column flow_tasks.task_param
     *
     * @return the value of flow_tasks.task_param
     *
     * @mbggenerated
     */
    public String getTaskParam() {
        return taskParam;
    }

    /**
     * 任务参数
     * This method sets the value of the database column flow_tasks.task_param
     *
     * @param taskParam the value for flow_tasks.task_param
     *
     * @mbggenerated
     */
    public void setTaskParam(String taskParam) {
        this.taskParam = taskParam;
    }

    /**
     * 任务类型 [手动，自动，定时]
     * This method returns the value of the database column flow_tasks.task_type
     *
     * @return the value of flow_tasks.task_type
     *
     * @mbggenerated
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * 任务类型 [手动，自动，定时]
     * This method sets the value of the database column flow_tasks.task_type
     *
     * @param taskType the value for flow_tasks.task_type
     *
     * @mbggenerated
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * This method returns the value of the database column flow_tasks.group
     *
     * @return the value of flow_tasks.group
     *
     * @mbggenerated
     */
    public Byte getGroup() {
        return group;
    }

    /**
     * This method sets the value of the database column flow_tasks.group
     *
     * @param group the value for flow_tasks.group
     *
     * @mbggenerated
     */
    public void setGroup(Byte group) {
        this.group = group;
    }

    /**
     * 优先级
     * This method returns the value of the database column flow_tasks.priority
     *
     * @return the value of flow_tasks.priority
     *
     * @mbggenerated
     */
    public Short getPriority() {
        return priority;
    }

    /**
     * 优先级
     * This method sets the value of the database column flow_tasks.priority
     *
     * @param priority the value for flow_tasks.priority
     *
     * @mbggenerated
     */
    public void setPriority(Short priority) {
        this.priority = priority;
    }

    /**
     * 延迟
     * This method returns the value of the database column flow_tasks.delay
     *
     * @return the value of flow_tasks.delay
     *
     * @mbggenerated
     */
    public Integer getDelay() {
        return delay;
    }

    /**
     * 延迟
     * This method sets the value of the database column flow_tasks.delay
     *
     * @param delay the value for flow_tasks.delay
     *
     * @mbggenerated
     */
    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    /**
     * 状态[新建,运行中,手动,定时,失败,成功]
     * This method returns the value of the database column flow_tasks.state
     *
     * @return the value of flow_tasks.state
     *
     * @mbggenerated
     */
    public String getState() {
        return state;
    }

    /**
     * 状态[新建,运行中,手动,定时,失败,成功]
     * This method sets the value of the database column flow_tasks.state
     *
     * @param state the value for flow_tasks.state
     *
     * @mbggenerated
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * This method returns the value of the database column flow_tasks.create_time
     *
     * @return the value of flow_tasks.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method sets the value of the database column flow_tasks.create_time
     *
     * @param createTime the value for flow_tasks.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method returns the value of the database column flow_tasks.update_time
     *
     * @return the value of flow_tasks.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method sets the value of the database column flow_tasks.update_time
     *
     * @param updateTime the value for flow_tasks.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}