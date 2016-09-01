package cn.mwee.auto.deploy.handler;

import cn.mwee.auto.deploy.controller.IAutoTaskController;
import cn.mwee.auto.deploy.controller.IDeployController;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.handler.BaseHandler;
import cn.mwee.auto.misc.server.MyApplicationContext;

/**
 * Created by huming on 16/7/19.
 */
public class AutoTaskHandler extends BaseHandler
{
    public AutoTaskHandler() {
        this.adaptorController = (IController) MyApplicationContext.getInstance()
                .getBean(IAutoTaskController.class);
    }
}
