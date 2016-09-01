package cn.mwee.auto.deploy.contract.template;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by huming on 16/7/21.
 */
@Data
public class AddTemplateTaskRequest {

    @Min(value = 1, message = "invalid templateId value")
    private Integer templateId;

    @NotNull(message = "group is null")
    @Range(min = -128, max = 127, message = "invalid group value")
    private Byte group;

    @Min(value = 0, message = "invalid priority value")
    private Short priority;

    @Min(value = 1, message = "invalid taskId value")
    private Integer taskId;

}
