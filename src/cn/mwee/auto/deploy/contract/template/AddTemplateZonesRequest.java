package cn.mwee.auto.deploy.contract.template;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * Created by huming on 16/8/11.
 */
@Data
public class AddTemplateZonesRequest {

    @Min(value = 1, message = "invalid templateId value")
    private Integer templateId;

    @NotBlank(message="未指定模板名")
    private String zones;
}
