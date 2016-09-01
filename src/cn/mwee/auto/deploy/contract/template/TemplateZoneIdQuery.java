package cn.mwee.auto.deploy.contract.template;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Created by huming on 16/8/2.
 */
@Data
public class TemplateZoneIdQuery {

    @Min(value = 1, message = "invalid templateId value")
    private Integer templateZoneId;

}
