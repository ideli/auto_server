package cn.mwee.auto.deploy.contract.template;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * Created by huming on 16/8/2.
 */
@Data
public class ModifyTemplateTaskRequest {

    @Min(value = 1, message = "invalid id value")
    private Integer id;

    private Byte group;

    private Short priority;

    private Integer taskId;

    private String taskType;

}
