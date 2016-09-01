package cn.mwee.auto.deploy.contract.task;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Created by huming on 16/7/27.
 */
@Data
public class ModifyTaskRequest {

    @Min(value = 1, message = "invalid id value")
    private Integer id;

    private String name;

    private String exec;

    private String execUser;

    private String execZone;

    private String params;

    private String desc;

}
