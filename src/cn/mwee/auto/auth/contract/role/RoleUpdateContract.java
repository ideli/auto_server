package cn.mwee.auto.auth.contract.role;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/7/19.
 */
@Data
public class RoleUpdateContract extends BaseContract {

    @NotNull(message = "未指定角色")
    private Integer roleId;

    @NotBlank(message = "未指定角色名称")
    private String roleName;

    private String desc;


}
