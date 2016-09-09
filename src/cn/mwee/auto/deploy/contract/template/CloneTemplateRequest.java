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

    /**
     * 克隆后后缀
     */
    @NotNull(message = "未指定模板名称")
    private String templateName;

    /**
     * 克隆后后缀
     */
    @NotNull(message = "未指定模板克隆模式")
    private Integer cloneType;

}
