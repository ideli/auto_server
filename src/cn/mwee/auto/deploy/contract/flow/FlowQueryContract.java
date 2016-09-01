package cn.mwee.auto.deploy.contract.flow;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/29.
 */
@Data
public class FlowQueryContract extends BaseContract {

    /**
     * 项目Id
     */
    @NotNull(message = "未指定项目Id")
    private Integer projectId;

    private Integer flowId;

    private String zone;

    private List<String> state;

    /**
     * 创建时间-开始
     */
    private Date createDateS;

    /**
     * 创建时间-结束
     */
    private Date createDateE;
}
