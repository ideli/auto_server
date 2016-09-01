/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.service;

import java.util.List;

import cn.mwee.auto.deploy.model.AutoTask;
import cn.mwee.auto.deploy.model.FlowTask;
import cn.mwee.auto.deploy.model.FlowTaskLog;
import cn.mwee.auto.deploy.model.FlowTaskLogExtModle;

/**
 * @author mengfanyuan
 * 2016年7月7日下午7:23:51
 */
public interface IFlowTaskLogService {

	/**
	 * 增加日志
	 * @param flowTask
	 * @return log_id
	 */
	Integer addLog(FlowTask flowTask,AutoTask task);
	/**
	 * 更新日志
	 * @param flowTaskId
	 * @param log
	 */
	void updateLog(Integer flowTaskId, String log);
	
	/**
	 * 更新日志
	 * @param flowTaskId
	 * @param log
	 */
	void updateLog2End(Integer flowTaskId);
	
	/**
	 * 获取日志记录
	 * @param flowTaskId
	 * @return
	 */
	FlowTaskLog getLog4FlowTask(Integer flowTaskId);
	
	/**
	 * 增加一行日志
	 * 
	 * @param flowTaskId
	 * @param log
	 */
	void addLineLog(Integer logId, String log);
	
	/**
	 * 获取流程区域日志
	 * @param flowId
	 * @param zone
	 * @return
	 */
	List<FlowTaskLogExtModle> getZoneLogs(Integer flowId, String zone);
	
	
}
