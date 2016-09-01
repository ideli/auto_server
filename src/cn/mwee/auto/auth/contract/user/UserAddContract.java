package cn.mwee.auto.auth.contract.user;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import cn.mwee.auto.misc.common.util.StringUtil;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Administrator on 2016/7/18.
 */
@Data
public class UserAddContract extends BaseContract {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    private String password;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 部门
     */
    private String department;
}
