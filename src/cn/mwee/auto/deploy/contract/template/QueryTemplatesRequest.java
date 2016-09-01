package cn.mwee.auto.deploy.contract.template;

import cn.mwee.auto.common.db.PageInfo;
import lombok.Data;

import java.util.Date;

/**
 * Created by huming on 16/7/27.
 */
@Data
public class QueryTemplatesRequest {

    private PageInfo page;

    private Integer id;

    private String name;

    private Byte review;

    private String createTimeS;

    private String createTimeE;

    private Integer projectId;
}
