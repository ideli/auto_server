package cn.mwee.auto.auth.contract.user;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 */
@Data
public class UserGrantContract extends BaseContract {

    @NotBlank(message = "未指定用户")
    private String userName;

    @NotNull(message = "未指定角色")
    private List<Integer> roleIds;
}
