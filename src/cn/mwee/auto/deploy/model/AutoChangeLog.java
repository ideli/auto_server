package cn.mwee.auto.deploy.model;

import java.util.Date;

public class AutoChangeLog {
    /**
     * auto_change_log.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * log类型[0:任务,1:模板,2:zone,3:模板任务,4:模板区]
     * auto_change_log.log_type
     *
     * @mbggenerated
     */
    private Byte logType;

    /**
     * 操作类型：1，增加；2，删除；3，修改
     * auto_change_log.operate_type
     *
     * @mbggenerated
     */
    private Byte operateType;

    /**
     * 变动数据的Id
     * auto_change_log.data_id
     *
     * @mbggenerated
     */
    private Integer dataId;

    /**
     * 变动数据json表示
     * auto_change_log.change_json
     *
     * @mbggenerated
     */
    private String changeJson;

    /**
     * 操作时间
     * auto_change_log.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 操作人
     * auto_change_log.operator
     *
     * @mbggenerated
     */
    private String operator;

    /**
     * This method returns the value of the database column auto_change_log.id
     *
     * @return the value of auto_change_log.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column auto_change_log.id
     *
     * @param id the value for auto_change_log.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * log类型[0:任务,1:模板,2:zone,3:模板任务,4:模板区]
     * This method returns the value of the database column auto_change_log.log_type
     *
     * @return the value of auto_change_log.log_type
     *
     * @mbggenerated
     */
    public Byte getLogType() {
        return logType;
    }

    /**
     * log类型[0:任务,1:模板,2:zone,3:模板任务,4:模板区]
     * This method sets the value of the database column auto_change_log.log_type
     *
     * @param logType the value for auto_change_log.log_type
     *
     * @mbggenerated
     */
    public void setLogType(Byte logType) {
        this.logType = logType;
    }

    /**
     * 操作类型：1，增加；2，删除；3，修改
     * This method returns the value of the database column auto_change_log.operate_type
     *
     * @return the value of auto_change_log.operate_type
     *
     * @mbggenerated
     */
    public Byte getOperateType() {
        return operateType;
    }

    /**
     * 操作类型：1，增加；2，删除；3，修改
     * This method sets the value of the database column auto_change_log.operate_type
     *
     * @param operateType the value for auto_change_log.operate_type
     *
     * @mbggenerated
     */
    public void setOperateType(Byte operateType) {
        this.operateType = operateType;
    }

    /**
     * 变动数据的Id
     * This method returns the value of the database column auto_change_log.data_id
     *
     * @return the value of auto_change_log.data_id
     *
     * @mbggenerated
     */
    public Integer getDataId() {
        return dataId;
    }

    /**
     * 变动数据的Id
     * This method sets the value of the database column auto_change_log.data_id
     *
     * @param dataId the value for auto_change_log.data_id
     *
     * @mbggenerated
     */
    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    /**
     * 变动数据json表示
     * This method returns the value of the database column auto_change_log.change_json
     *
     * @return the value of auto_change_log.change_json
     *
     * @mbggenerated
     */
    public String getChangeJson() {
        return changeJson;
    }

    /**
     * 变动数据json表示
     * This method sets the value of the database column auto_change_log.change_json
     *
     * @param changeJson the value for auto_change_log.change_json
     *
     * @mbggenerated
     */
    public void setChangeJson(String changeJson) {
        this.changeJson = changeJson;
    }

    /**
     * 操作时间
     * This method returns the value of the database column auto_change_log.create_time
     *
     * @return the value of auto_change_log.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 操作时间
     * This method sets the value of the database column auto_change_log.create_time
     *
     * @param createTime the value for auto_change_log.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 操作人
     * This method returns the value of the database column auto_change_log.operator
     *
     * @return the value of auto_change_log.operator
     *
     * @mbggenerated
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 操作人
     * This method sets the value of the database column auto_change_log.operator
     *
     * @param operator the value for auto_change_log.operator
     *
     * @mbggenerated
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
}