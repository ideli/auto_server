package cn.mwee.auto.deploy.contract.commom;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by huming on 16/7/19.
 */
@Data
public class SelQueryParamContract {

    @NotBlank(message = "查询参数不能为空")
    private String queryParam;
}
