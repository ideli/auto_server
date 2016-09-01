package cn.mwee.auto.deploy.contract.template;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * Created by huming on 16/7/21.
 */
@Data
public class AddTemplateRequest {

    @NotBlank(message="未指定模板名")
    private String name;

    @Range(min = 0, max = 1, message = "invalid review value")
    private Byte review;

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
