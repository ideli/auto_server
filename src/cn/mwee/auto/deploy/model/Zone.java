package cn.mwee.auto.deploy.model;

import java.util.Date;

public class Zone {
    /**
     * zones.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 区名
     * zones.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * ip地址
     * zones.ip
     *
     * @mbggenerated
     */
    private String ip;

    /**
     * hostname
     * zones.host
     *
     * @mbggenerated
     */
    private String host;

    /**
     * zones.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * zones.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method returns the value of the database column zones.id
     *
     * @return the value of zones.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column zones.id
     *
     * @param id the value for zones.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 区名
     * This method returns the value of the database column zones.name
     *
     * @return the value of zones.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * 区名
     * This method sets the value of the database column zones.name
     *
     * @param name the value for zones.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ip地址
     * This method returns the value of the database column zones.ip
     *
     * @return the value of zones.ip
     *
     * @mbggenerated
     */
    public String getIp() {
        return ip;
    }

    /**
     * ip地址
     * This method sets the value of the database column zones.ip
     *
     * @param ip the value for zones.ip
     *
     * @mbggenerated
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * hostname
     * This method returns the value of the database column zones.host
     *
     * @return the value of zones.host
     *
     * @mbggenerated
     */
    public String getHost() {
        return host;
    }

    /**
     * hostname
     * This method sets the value of the database column zones.host
     *
     * @param host the value for zones.host
     *
     * @mbggenerated
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * This method returns the value of the database column zones.create_time
     *
     * @return the value of zones.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method sets the value of the database column zones.create_time
     *
     * @param createTime the value for zones.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method returns the value of the database column zones.update_time
     *
     * @return the value of zones.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method sets the value of the database column zones.update_time
     *
     * @param updateTime the value for zones.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}