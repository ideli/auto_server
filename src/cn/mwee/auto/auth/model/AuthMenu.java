package cn.mwee.auto.auth.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class AuthMenu extends AuthPermission {
    /**
     * 子菜单
     */
    private List<AuthMenu> childMenu;


    public List<AuthMenu> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<AuthMenu> childMenu) {
        this.childMenu = childMenu;
    }

}
