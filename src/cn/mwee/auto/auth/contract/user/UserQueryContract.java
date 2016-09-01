package cn.mwee.auto.auth.contract.user;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2016/7/20.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryContract extends BaseContract {
    /**
     * 用户名
     */
    private String userName;
}
