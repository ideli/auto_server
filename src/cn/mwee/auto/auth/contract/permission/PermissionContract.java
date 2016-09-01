package cn.mwee.auto.auth.contract.permission;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/7/19.
 */
@Data
public class PermissionContract extends BaseContract {

    /**
     * id
     */
    @NotNull(message = "未指定记录")
    private Integer id;

    /**
     * 父级菜单（未指定表示一级）
     */
    private Integer parentId;

    /**
     * 菜单url
     */
    @NotBlank(message = "code不能为空")
    private String code;

    /**
     * 级别
     */
    @NotNull(message = "未指定级别")
    private Byte level;

    /**
     * 菜单标题
     */
    @NotBlank(message = "标题不能为空")
    private String name;

    /**
     * 菜单类型
     */
    @NotNull(message = "未指定菜单类型")
    private Byte type;

    /**
     * 描述
     */
    private String description;
}
