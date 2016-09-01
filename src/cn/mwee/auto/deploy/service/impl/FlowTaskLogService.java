/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.service.impl;

import java.util.Date;
import java.util.List;

import cn.mwee.auto.deploy.model.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mwee.auto.deploy.dao.FlowTaskLogExtMapper;
import cn.mwee.auto.deploy.dao.FlowTaskLogMapper;
import cn.mwee.auto.deploy.service.IFlowTaskLogService;

/**
 * @author mengfanyuan
 * 2016年7月7日下午7:26:47
 */
@Service
public class FlowTaskLogService implements IFlowTaskLogService {

	@Autowired
	private FlowTaskLogMapper flowTaskLogMapper; 
	
	@Autowired
	private FlowTaskLogExtMapper flowTaskLogExtMapper; 


	@Override
	public Integer addLog(FlowTask flowTask,AutoTask task) {
		FlowTaskLog flowTaskLog = new FlowTaskLog(); 
		flowTaskLog.setFlowId(flowTask.getFlowId());
		flowTaskLog.setZone(flowTask.getZone());
		flowTaskLog.setFlowTaskId(flowTask.getId());
		flowTaskLog.setGroup(flowTask.getGroup());
		flowTaskLog.setPriority(flowTask.getPriority());
		String exec = task.getExec() == null? "" : task.getExec() ;
		String param = flowTask.getTaskParam() == null? "" : flowTask.getTaskParam();
		flowTaskLog.setTaskInfo(exec + " " + param);
		flowTaskLog.setLog("");
		flowTaskLog.setCreateTime(new Date());
		flowTaskLogMapper.insertSelective(flowTaskLog);
		return flowTaskLog.getId();
	}

	@Override
	public void updateLog(Integer flowTaskId, String log) {
		FlowTaskLog flowTaskLog = new FlowTaskLog();
		flowTaskLog.setFlowTaskId(flowTaskId);
		flowTaskLog.setLog(log);
		FlowTaskLogExample example = new FlowTaskLogExample();
		example.createCriteria().andFlowTaskIdEqualTo(flowTaskId);
		flowTaskLogMapper.updateByExampleSelective(flowTaskLog, example);
		
	}
	@Override
	public FlowTaskLog getLog4FlowTask(Integer flowTaskId) {
		FlowTaskLogExample example = new FlowTaskLogExample();
		example.createCriteria().andFlowTaskIdEqualTo(flowTaskId);
		List<FlowTaskLog> flowTaskLogs = flowTaskLogMapper.selectByExampleWithBLOBs(example);
		return CollectionUtils.isEmpty(flowTaskLogs) ? null : flowTaskLogs.get(0);
	}

	@Override
	public void updateLog2End(Integer flowTaskId) {
		// TODO Auto-generated method stub
		addLineLog(flowTaskId, "");
	}

	@Override
	public void addLineLog(Integer logId, String log) {
		// TODO Auto-generated method stub
		flowTaskLogExtMapper.addLineLog(logId, log+"<br>");
	}

	@Override
	public List<FlowTaskLogExtModle> getZoneLogs(Integer flowId, String zone) {
		// TODO Auto-generated method stub
		FlowTaskLogExample example = new FlowTaskLogExample();
		example.createCriteria()
			.andFlowIdEqualTo(flowId)
			.andZoneEqualTo(zone);
		example.setOrderByClause("`group` ASC,priority ASC,create_time ASC");
		return flowTaskLogExtMapper.selectZoneLogsByExample(example);
	}
}
