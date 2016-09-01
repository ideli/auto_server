package cn.mwee.auto.deploy.model;

import java.util.Date;

public class TemplateZone {
    /**
     * template_zones.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 模板id
     * template_zones.template_id
     *
     * @mbggenerated
     */
    private Integer templateId;

    /**
     * 区id
     * template_zones.zone_id
     *
     * @mbggenerated
     */
    private Integer zoneId;

    /**
     * 状态
     * template_zones.state
     *
     * @mbggenerated
     */
    private String state;

    /**
     * template_zones.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * template_zones.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 更新原因
     * template_zones.update_reason
     *
     * @mbggenerated
     */
    private String updateReason;

    /**
     * This method returns the value of the database column template_zones.id
     *
     * @return the value of template_zones.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column template_zones.id
     *
     * @param id the value for template_zones.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 模板id
     * This method returns the value of the database column template_zones.template_id
     *
     * @return the value of template_zones.template_id
     *
     * @mbggenerated
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * 模板id
     * This method sets the value of the database column template_zones.template_id
     *
     * @param templateId the value for template_zones.template_id
     *
     * @mbggenerated
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * 区id
     * This method returns the value of the database column template_zones.zone_id
     *
     * @return the value of template_zones.zone_id
     *
     * @mbggenerated
     */
    public Integer getZoneId() {
        return zoneId;
    }

    /**
     * 区id
     * This method sets the value of the database column template_zones.zone_id
     *
     * @param zoneId the value for template_zones.zone_id
     *
     * @mbggenerated
     */
    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * 状态
     * This method returns the value of the database column template_zones.state
     *
     * @return the value of template_zones.state
     *
     * @mbggenerated
     */
    public String getState() {
        return state;
    }

    /**
     * 状态
     * This method sets the value of the database column template_zones.state
     *
     * @param state the value for template_zones.state
     *
     * @mbggenerated
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * This method returns the value of the database column template_zones.create_time
     *
     * @return the value of template_zones.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method sets the value of the database column template_zones.create_time
     *
     * @param createTime the value for template_zones.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method returns the value of the database column template_zones.update_time
     *
     * @return the value of template_zones.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method sets the value of the database column template_zones.update_time
     *
     * @param updateTime the value for template_zones.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 更新原因
     * This method returns the value of the database column template_zones.update_reason
     *
     * @return the value of template_zones.update_reason
     *
     * @mbggenerated
     */
    public String getUpdateReason() {
        return updateReason;
    }

    /**
     * 更新原因
     * This method sets the value of the database column template_zones.update_reason
     *
     * @param updateReason the value for template_zones.update_reason
     *
     * @mbggenerated
     */
    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason;
    }
}