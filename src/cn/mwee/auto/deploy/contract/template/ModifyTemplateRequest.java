package cn.mwee.auto.deploy.contract.template;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by huming on 16/7/26.
 */
@Data
public class ModifyTemplateRequest {

    @Min(value = 1, message = "invalid templateId value")
    private Integer id;

    @NotBlank(message="模板名不能为空")
    private String name;

    private Byte review;

    /**
     * 版本控制系统类型
     * templates.vcs_type
     *
     * @mbggenerated
     */
    private String vcsType;

    /**
     * 版本仓库地址
     * templates.vcs_rep
     *
     * @mbggenerated
     */
    private String vcsRep;

    private Integer projectId;
}
