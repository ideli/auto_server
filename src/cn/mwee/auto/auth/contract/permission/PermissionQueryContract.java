package cn.mwee.auto.auth.contract.permission;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/19.
 */
@Data
public class PermissionQueryContract extends BaseContract {

    /**
     * 父级菜单（未指定表示一级）
     */
    private Integer parentId;

    /**
     * 菜单url
     */
    private String code;

    /**
     * 菜单标题
     */
    private String name;

    /**
     * 菜单类型
     */
    private Byte type;

    /**
     *
     */
    private Byte level;

    /**
     * 创建日期-开始
     */
    private Date createTimeS;

    /**
     * 创建日期-结束
     */
    private Date createTimeE;

}
