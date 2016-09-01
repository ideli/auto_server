package cn.mwee.auto.deploy.contract.zone;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by huming on 16/8/2.
 */
@Data
public class AddZoneRequest {

    @NotBlank(message="name不能为空")
    private String name;

    private String ip;

    private String host;
}
