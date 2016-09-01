/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.handler;

import cn.mwee.auto.deploy.controller.IDeployController;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.handler.BaseHandler;
import cn.mwee.auto.misc.server.MyApplicationContext;

/**
 * @author mengfanyuan
 * 2016年7月6日下午5:13:10
 */
public class DeployHandler extends BaseHandler {
	 public DeployHandler() {
	        this.adaptorController = (IController) MyApplicationContext.getInstance()
	                .getBean(IDeployController.class);
	    }
}
