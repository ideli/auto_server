package cn.mwee.auto.deploy.contract.project;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/8/10.
 */
@Data
public class ProjectUserIdContract {
    @NotNull(message = "未指定项目成员")
    private Integer projectUserId;
}
