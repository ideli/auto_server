package cn.mwee.auto.deploy.contract.zone;

import cn.mwee.auto.common.db.PageInfo;
import lombok.Data;

import java.util.Date;

/**
 * Created by huming on 16/8/2.
 */
@Data
public class QueryZoneRequest {

    private PageInfo page;

    private Integer id;

    private String name;

    private String ipOrHost;

    private String createTimeS;

    private String createTimeE;

}
