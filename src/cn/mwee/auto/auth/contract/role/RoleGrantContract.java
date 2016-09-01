package cn.mwee.auto.auth.contract.role;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 */
@Data
public class RoleGrantContract extends BaseContract {

    /**
     * 角色Id
     */
    @NotNull(message = "未指定角色")
    private Integer roleId;

    /**
     * 权限集
     */
    @NotNull(message = "未指定权限集")
    private List<Integer> permissionIds;

}
