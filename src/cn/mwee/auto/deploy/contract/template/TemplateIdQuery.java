package cn.mwee.auto.deploy.contract.template;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * Created by huming on 16/7/26.
 */
@Data
public class TemplateIdQuery {

    @Range(min = 1,message = "invalid templateId value")
    private Integer templateId;

}
