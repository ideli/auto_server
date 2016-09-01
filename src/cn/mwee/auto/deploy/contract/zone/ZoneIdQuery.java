package cn.mwee.auto.deploy.contract.zone;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * Created by huming on 16/8/2.
 */
@Data
public class ZoneIdQuery {

    @Range(min = 1,message = "invalid zoneId value")
    private Integer zoneId;

}
