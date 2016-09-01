package cn.mwee.auto.auth.contract.user;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/7/19.
 */
@Data
public class UserDelContract extends BaseContract {

    /**
     * 用户Id
     */
    @NotNull(message = "未指定用户")
    private String userName;
}
