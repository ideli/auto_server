package cn.mwee.auto.auth.handler;

import cn.mwee.auto.auth.controller.IAuthController;
import cn.mwee.auto.auth.controller.IUserController;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.handler.BaseHandler;
import cn.mwee.auto.misc.server.MyApplicationContext;

/**
 * Created by huming on 16/6/7.
 */
public class UserHandler extends BaseHandler {

    public UserHandler() {
        this.adaptorController = (IController) MyApplicationContext.getInstance()
                .getBean(IUserController.class);
    }

}