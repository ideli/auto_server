package cn.mwee.auto.deploy.contract.template;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by huming on 16/8/2.
 */
@Data
public class TemplateZoneIpQuery {

    @NotNull(message = "未指定主机地址")
    private String host;

}
