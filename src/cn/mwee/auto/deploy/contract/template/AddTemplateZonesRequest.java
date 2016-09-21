package cn.mwee.auto.deploy.contract.template;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by huming on 16/8/11.
 */
@Data
public class AddTemplateZonesRequest extends BaseContract {

    @Min(value = 1, message = "invalid templateId value")
    private Integer templateId;

    @NotBlank(message="区信息不能为空")
    private String zones;

    @NotNull(message = "区域信息数据不能为空")
    private List<Map<String,String>> zoneDataList;
}
