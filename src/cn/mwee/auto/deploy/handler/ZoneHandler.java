package cn.mwee.auto.deploy.handler;

import cn.mwee.auto.deploy.controller.ITemplateController;
import cn.mwee.auto.deploy.controller.IZoneController;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.handler.BaseHandler;
import cn.mwee.auto.misc.server.MyApplicationContext;

/**
 * Created by huming on 16/8/2.
 */
public class ZoneHandler extends BaseHandler {
    public ZoneHandler() {
        this.adaptorController = (IController) MyApplicationContext.getInstance()
                .getBean(IZoneController.class);
    }
}
