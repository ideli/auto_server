package cn.mwee.auto.deploy.contract.project;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/8/10.
 */
@Data
public class ProjectAddContract {

    /**
     * 项目Id
     */
    @NotNull(message = "未指定项目Id")
    @Min(value = 1,message = "项目Id不合法")
    @Max(value = 10000,message = "项目Id不合法")
    private Integer projectId;

    /**
     * 项目名称
     */
    @NotBlank(message = "未指定项目名称")
    private String projectName;

    /**
     * 项目描述
     */
    private String description;

}
