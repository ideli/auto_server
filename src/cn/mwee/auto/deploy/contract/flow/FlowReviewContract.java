package cn.mwee.auto.deploy.contract.flow;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/7/28.
 */
@Data
public class FlowReviewContract extends BaseContract {

    /**
     * 流程Id
     */
    @NotNull(message = "未指定流程")
    private Integer flowId;

    /**
     * 审核结果
     */
    @NotNull(message = "未输入审核结果")
    @Range(min = 2,max = 3,message = "请输入正确的值")
    private Byte isReview;
}
