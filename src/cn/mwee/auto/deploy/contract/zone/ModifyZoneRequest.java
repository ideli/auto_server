package cn.mwee.auto.deploy.contract.zone;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * Created by huming on 16/8/2.
 */
@Data
public class ModifyZoneRequest {

    @Range(min = 1,message = "invalid id value")
    private Integer id;

    @NotBlank(message="name不能为空")
    private String name;

    private String ip;

    private String host;

}
