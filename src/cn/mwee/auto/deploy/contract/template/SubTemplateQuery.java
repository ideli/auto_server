package cn.mwee.auto.deploy.contract.template;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by huming on 16/7/26.
 */
@Data
public class SubTemplateQuery extends BaseContract {

    @NotNull(message = "流程Id不能为空")
    private Integer flowId;

    @NotNull(message = "未指定模板类型")
    private Byte type;


}
