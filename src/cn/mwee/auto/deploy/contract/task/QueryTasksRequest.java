package cn.mwee.auto.deploy.contract.task;

import cn.mwee.auto.common.db.PageInfo;
import lombok.Data;

import java.util.Date;

/**
 * Created by huming on 16/7/19.
 */
@Data
public class QueryTasksRequest {

    private PageInfo page;

    private Integer id;

    private String name;

    private String exec;

    private String params;

    private String desc;

    private String createTimeS;

    private String createTimeE;
}
