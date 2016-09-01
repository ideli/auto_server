package cn.mwee.auto.deploy.model;

import java.util.Date;

public class FlowTaskLog {
    /**
     * flow_task_log.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 流程Id
     * flow_task_log.flow_id
     *
     * @mbggenerated
     */
    private Integer flowId;

    /**
     * 区域
     * flow_task_log.zone
     *
     * @mbggenerated
     */
    private String zone;

    /**
     * 流程任务id
     * flow_task_log.flow_task_id
     *
     * @mbggenerated
     */
    private Integer flowTaskId;

    /**
     * 任务信息
     * flow_task_log.task_info
     *
     * @mbggenerated
     */
    private String taskInfo;

    /**
     * flow_task_log.group
     *
     * @mbggenerated
     */
    private Byte group;

    /**
     * 优先级
     * flow_task_log.priority
     *
     * @mbggenerated
     */
    private Short priority;

    /**
     * flow_task_log.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 日志
     * flow_task_log.log
     *
     * @mbggenerated
     */
    private String log;

    /**
     * This method returns the value of the database column flow_task_log.id
     *
     * @return the value of flow_task_log.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column flow_task_log.id
     *
     * @param id the value for flow_task_log.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 流程Id
     * This method returns the value of the database column flow_task_log.flow_id
     *
     * @return the value of flow_task_log.flow_id
     *
     * @mbggenerated
     */
    public Integer getFlowId() {
        return flowId;
    }

    /**
     * 流程Id
     * This method sets the value of the database column flow_task_log.flow_id
     *
     * @param flowId the value for flow_task_log.flow_id
     *
     * @mbggenerated
     */
    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    /**
     * 区域
     * This method returns the value of the database column flow_task_log.zone
     *
     * @return the value of flow_task_log.zone
     *
     * @mbggenerated
     */
    public String getZone() {
        return zone;
    }

    /**
     * 区域
     * This method sets the value of the database column flow_task_log.zone
     *
     * @param zone the value for flow_task_log.zone
     *
     * @mbggenerated
     */
    public void setZone(String zone) {
        this.zone = zone;
    }

    /**
     * 流程任务id
     * This method returns the value of the database column flow_task_log.flow_task_id
     *
     * @return the value of flow_task_log.flow_task_id
     *
     * @mbggenerated
     */
    public Integer getFlowTaskId() {
        return flowTaskId;
    }

    /**
     * 流程任务id
     * This method sets the value of the database column flow_task_log.flow_task_id
     *
     * @param flowTaskId the value for flow_task_log.flow_task_id
     *
     * @mbggenerated
     */
    public void setFlowTaskId(Integer flowTaskId) {
        this.flowTaskId = flowTaskId;
    }

    /**
     * 任务信息
     * This method returns the value of the database column flow_task_log.task_info
     *
     * @return the value of flow_task_log.task_info
     *
     * @mbggenerated
     */
    public String getTaskInfo() {
        return taskInfo;
    }

    /**
     * 任务信息
     * This method sets the value of the database column flow_task_log.task_info
     *
     * @param taskInfo the value for flow_task_log.task_info
     *
     * @mbggenerated
     */
    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    /**
     * This method returns the value of the database column flow_task_log.group
     *
     * @return the value of flow_task_log.group
     *
     * @mbggenerated
     */
    public Byte getGroup() {
        return group;
    }

    /**
     * This method sets the value of the database column flow_task_log.group
     *
     * @param group the value for flow_task_log.group
     *
     * @mbggenerated
     */
    public void setGroup(Byte group) {
        this.group = group;
    }

    /**
     * 优先级
     * This method returns the value of the database column flow_task_log.priority
     *
     * @return the value of flow_task_log.priority
     *
     * @mbggenerated
     */
    public Short getPriority() {
        return priority;
    }

    /**
     * 优先级
     * This method sets the value of the database column flow_task_log.priority
     *
     * @param priority the value for flow_task_log.priority
     *
     * @mbggenerated
     */
    public void setPriority(Short priority) {
        this.priority = priority;
    }

    /**
     * This method returns the value of the database column flow_task_log.create_time
     *
     * @return the value of flow_task_log.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method sets the value of the database column flow_task_log.create_time
     *
     * @param createTime the value for flow_task_log.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 日志
     * This method returns the value of the database column flow_task_log.log
     *
     * @return the value of flow_task_log.log
     *
     * @mbggenerated
     */
    public String getLog() {
        return log;
    }

    /**
     * 日志
     * This method sets the value of the database column flow_task_log.log
     *
     * @param log the value for flow_task_log.log
     *
     * @mbggenerated
     */
    public void setLog(String log) {
        this.log = log;
    }
}