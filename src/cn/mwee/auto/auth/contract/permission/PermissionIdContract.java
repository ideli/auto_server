package cn.mwee.auto.auth.contract.permission;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/7/19.
 */
@Data
public class PermissionIdContract extends BaseContract {

    /**
     * id
     */
    @NotNull(message = "未指定记录")
    private Integer id;

}
