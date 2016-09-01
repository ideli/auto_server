package cn.mwee.auto.deploy.model;

import lombok.Data;

/**
 * Created by Administrator on 2016/8/10.
 */
@Data
public class ZoneStateModel {
    /**
     * 模板区域Id
     */
    private Integer templateZoneId;
    /**
     * 名称
     */
    private String name;
    /**
     * ip
     */
    private String ip;
    /**
     * 主机名
     */
    private String host;
    /**
     * 状态
     */
    private String state;
}
