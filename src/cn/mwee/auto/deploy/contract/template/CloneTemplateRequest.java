package cn.mwee.auto.deploy.contract.template;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by huming on 16/8/12.
 */
@Data
public class CloneTemplateRequest {

    @NotNull(message = "未指定模板")
    private Integer templateId;

}
