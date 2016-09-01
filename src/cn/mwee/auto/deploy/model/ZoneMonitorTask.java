package cn.mwee.auto.deploy.model;

import lombok.Data;

/**
 * Created by Administrator on 2016/8/12.
 */
@Data
public class ZoneMonitorTask {

    /**
     * 模板区域Id
     */
    private Integer templateZoneId;

    /**
     * 脚本执行主机
     */
    private String exeTargetHost;

    /**
     * 监控脚本
     */
    private String monitorShell;

    /**
     * 脚本执行用户
     */
    private String monitorUser;
}
