package cn.mwee.auto.deploy.model;

import java.util.Date;

public class AutoTask {
    /**
     * tasks.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 任务名
     * tasks.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 执行路径
     * tasks.exec
     *
     * @mbggenerated
     */
    private String exec;

    /**
     * 执行用户
     * tasks.exec_user
     *
     * @mbggenerated
     */
    private String execUser;

    /**
     * 任务脚本执行目标主机
     * tasks.exec_zone
     *
     * @mbggenerated
     */
    private String execZone;

    /**
     * 参数
     * tasks.params
     *
     * @mbggenerated
     */
    private String params;

    /**
     * 使用中(0:否,1:是)
     * tasks.inuse
     *
     * @mbggenerated
     */
    private Byte inuse;

    /**
     * 任务描述
     * tasks.desc
     *
     * @mbggenerated
     */
    private String desc;

    /**
     * tasks.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * tasks.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method returns the value of the database column tasks.id
     *
     * @return the value of tasks.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method sets the value of the database column tasks.id
     *
     * @param id the value for tasks.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 任务名
     * This method returns the value of the database column tasks.name
     *
     * @return the value of tasks.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * 任务名
     * This method sets the value of the database column tasks.name
     *
     * @param name the value for tasks.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 执行路径
     * This method returns the value of the database column tasks.exec
     *
     * @return the value of tasks.exec
     *
     * @mbggenerated
     */
    public String getExec() {
        return exec;
    }

    /**
     * 执行路径
     * This method sets the value of the database column tasks.exec
     *
     * @param exec the value for tasks.exec
     *
     * @mbggenerated
     */
    public void setExec(String exec) {
        this.exec = exec;
    }

    /**
     * 执行用户
     * This method returns the value of the database column tasks.exec_user
     *
     * @return the value of tasks.exec_user
     *
     * @mbggenerated
     */
    public String getExecUser() {
        return execUser;
    }

    /**
     * 执行用户
     * This method sets the value of the database column tasks.exec_user
     *
     * @param execUser the value for tasks.exec_user
     *
     * @mbggenerated
     */
    public void setExecUser(String execUser) {
        this.execUser = execUser;
    }

    /**
     * 任务脚本执行目标主机
     * This method returns the value of the database column tasks.exec_zone
     *
     * @return the value of tasks.exec_zone
     *
     * @mbggenerated
     */
    public String getExecZone() {
        return execZone;
    }

    /**
     * 任务脚本执行目标主机
     * This method sets the value of the database column tasks.exec_zone
     *
     * @param execZone the value for tasks.exec_zone
     *
     * @mbggenerated
     */
    public void setExecZone(String execZone) {
        this.execZone = execZone;
    }

    /**
     * 参数
     * This method returns the value of the database column tasks.params
     *
     * @return the value of tasks.params
     *
     * @mbggenerated
     */
    public String getParams() {
        return params;
    }

    /**
     * 参数
     * This method sets the value of the database column tasks.params
     *
     * @param params the value for tasks.params
     *
     * @mbggenerated
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 使用中(0:否,1:是)
     * This method returns the value of the database column tasks.inuse
     *
     * @return the value of tasks.inuse
     *
     * @mbggenerated
     */
    public Byte getInuse() {
        return inuse;
    }

    /**
     * 使用中(0:否,1:是)
     * This method sets the value of the database column tasks.inuse
     *
     * @param inuse the value for tasks.inuse
     *
     * @mbggenerated
     */
    public void setInuse(Byte inuse) {
        this.inuse = inuse;
    }

    /**
     * 任务描述
     * This method returns the value of the database column tasks.desc
     *
     * @return the value of tasks.desc
     *
     * @mbggenerated
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 任务描述
     * This method sets the value of the database column tasks.desc
     *
     * @param desc the value for tasks.desc
     *
     * @mbggenerated
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * This method returns the value of the database column tasks.create_time
     *
     * @return the value of tasks.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method sets the value of the database column tasks.create_time
     *
     * @param createTime the value for tasks.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method returns the value of the database column tasks.update_time
     *
     * @return the value of tasks.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method sets the value of the database column tasks.update_time
     *
     * @param updateTime the value for tasks.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}