package cn.mwee.auto.deploy.contract.task;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by huming on 16/7/19.
 */
@Data
public class TaskIdRequest {

    @Range(min = 1,message = "invalid taskId value")
    private Integer taskId;
}
