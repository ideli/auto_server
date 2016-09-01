package cn.mwee.auto.auth.handler;

import cn.mwee.auto.auth.controller.IAuthController;
import cn.mwee.auto.auth.controller.IRoleController;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.handler.BaseHandler;
import cn.mwee.auto.misc.server.MyApplicationContext;

/**
 * Created by huming on 16/6/7.
 */
public class RoleHandler extends BaseHandler {

    public RoleHandler() {
        this.adaptorController = (IController) MyApplicationContext.getInstance()
                .getBean(IRoleController.class);
    }

}