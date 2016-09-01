/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.service.execute;

import cn.mwee.auto.deploy.model.AutoTask;
import cn.mwee.auto.deploy.model.FlowTask;

/**
 * @author mengfanyuan
 * 2016年7月5日下午8:08:58
 */
public interface TaskExecutor {
	/**
	 * 持续执行任务
	 * @param flowTask
     */
	void executeChain(FlowTask flowTask);

	/**
	 * 执行任务
	 * @param flowTask
	 * @return
     */
	boolean execute(FlowTask flowTask);
}
