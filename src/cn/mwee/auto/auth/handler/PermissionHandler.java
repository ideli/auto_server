package cn.mwee.auto.auth.handler;

import cn.mwee.auto.auth.controller.IPermissionController;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.handler.BaseHandler;
import cn.mwee.auto.misc.server.MyApplicationContext;

/**
 * Created by huming on 16/6/7.
 */
public class PermissionHandler extends BaseHandler {

    public PermissionHandler() {
        this.adaptorController = (IController) MyApplicationContext.getInstance()
                .getBean(IPermissionController.class);
    }

}