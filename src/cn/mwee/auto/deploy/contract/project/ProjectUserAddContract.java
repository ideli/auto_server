package cn.mwee.auto.deploy.contract.project;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/8/10.
 */
@Data
public class ProjectUserAddContract {
    @NotNull(message = "未指定项目")
    private Integer projectId;

    @NotNull(message = "未指定用户")
    private Integer[] userIds;
}
