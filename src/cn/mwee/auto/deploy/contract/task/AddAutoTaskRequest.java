package cn.mwee.auto.deploy.contract.task;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by huming on 16/7/19.
 */
@Data
public class AddAutoTaskRequest
{
    @NotBlank(message="任务名称不能为空")
    private String name;

    @NotBlank(message="exec不能为空")
    private String exec;

    private String desc;

    @NotBlank(message="execZone不能为空")
    private String execZone;

    @NotBlank(message="execUser不能为空")
    private String execUser;

    private String params;
}
