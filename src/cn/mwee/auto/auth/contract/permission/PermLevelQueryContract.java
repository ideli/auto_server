package cn.mwee.auto.auth.contract.permission;

import lombok.Data;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/7/25.
 */
@Data
public class PermLevelQueryContract extends BasicDesktopIconUI {


    /**
     * 类型
     */
    @NotNull(message = "未指定类型")
    private Byte type;
    /**
     * 菜单级别
     */
    @NotNull(message = "未指定菜单级别")
    private Byte level;
}
