package cn.mwee.auto.deploy.contract.project;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/8/10.
 */
@Data
public class ProjectUpdateContract {

    @NotNull(message = "未指定项目")
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
