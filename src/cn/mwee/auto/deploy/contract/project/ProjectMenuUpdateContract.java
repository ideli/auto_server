package cn.mwee.auto.deploy.contract.project;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/8/10.
 */
@Data
public class ProjectMenuUpdateContract {
    @NotNull(message = "未指定项目")
    private Integer menuId;

    @NotBlank(message = "未指定菜单标题")
    private String menuName;

    @NotBlank(message = "未指定菜单url")
    private String menuUrl;

    private String desc;
}
