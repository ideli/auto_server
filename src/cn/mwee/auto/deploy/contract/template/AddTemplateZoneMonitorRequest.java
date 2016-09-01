package cn.mwee.auto.deploy.contract.template;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by huming on 16/8/12.
 */
@Data
public class AddTemplateZoneMonitorRequest {

    @NotNull(message = "未指定模板")
    private Integer templateId;

    @NotNull(message = "未指定监控类型")
    private Byte monitorType;

    @NotBlank(message = "未指定参数")
    private String monitorParam;

    @NotNull(message = "未指定启用状态")
    private Byte inUse;
}
