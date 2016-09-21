package cn.mwee.auto.deploy.contract.template;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * Created by huming on 16/8/12.
 */
@Data
public class DeleteTemplateZoneRequest {

    @Range(min = 1,message = "invalid templateZoneId value")
    private Integer templateZoneId;

}
