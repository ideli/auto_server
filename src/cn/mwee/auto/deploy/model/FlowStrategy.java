package cn.mwee.auto.deploy.model;

import java.util.Date;

public class FlowStrategy {
    /**
     * 主键id
     * flow_strategy.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 流程Id
     * flow_strategy.flow_id
     *
     * @mbggenerated
     */
    private Integer flowId;

    /**
     * 区域数量
     * flow_strategy.zonesize
     *
     * @mbggenerated
     */
    private Integer zonesize;

    /**
     * 时间间隔
     * flow_strategy.interval
     *
     * @mbggenerated
     */
    private Integer interval;

    /**
     * flow_strategy.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * 主键id
     * This method returns the value of the database column flow_strategy.id
     *
     * @return the value of flow_strategy.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键id
     * This method sets the value of the database column flow_strategy.id
     *
     * @param id the value for flow_strategy.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 流程Id
     * This method returns the value of the database column flow_strategy.flow_id
     *
     * @return the value of flow_strategy.flow_id
     *
     * @mbggenerated
     */
    public Integer getFlowId() {
        return flowId;
    }

    /**
     * 流程Id
     * This method sets the value of the database column flow_strategy.flow_id
     *
     * @param flowId the value for flow_strategy.flow_id
     *
     * @mbggenerated
     */
    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    /**
     * 区域数量
     * This method returns the value of the database column flow_strategy.zonesize
     *
     * @return the value of flow_strategy.zonesize
     *
     * @mbggenerated
     */
    public Integer getZonesize() {
        return zonesize;
    }

    /**
     * 区域数量
     * This method sets the value of the database column flow_strategy.zonesize
     *
     * @param zonesize the value for flow_strategy.zonesize
     *
     * @mbggenerated
     */
    public void setZonesize(Integer zonesize) {
        this.zonesize = zonesize;
    }

    /**
     * 时间间隔
     * This method returns the value of the database column flow_strategy.interval
     *
     * @return the value of flow_strategy.interval
     *
     * @mbggenerated
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * 时间间隔
     * This method sets the value of the database column flow_strategy.interval
     *
     * @param interval the value for flow_strategy.interval
     *
     * @mbggenerated
     */
    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    /**
     * This method returns the value of the database column flow_strategy.createtime
     *
     * @return the value of flow_strategy.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method sets the value of the database column flow_strategy.createtime
     *
     * @param createtime the value for flow_strategy.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}