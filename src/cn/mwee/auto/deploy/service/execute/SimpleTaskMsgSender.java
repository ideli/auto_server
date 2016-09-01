/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.service.execute;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.mwee.auto.common.util.MessageUtil;
import cn.mwee.auto.deploy.service.IFlowManagerService;
import cn.mwee.auto.deploy.util.AutoConsts;
import org.springframework.stereotype.Component;

import cn.mwee.auto.deploy.model.FlowTask;
import cn.mwee.auto.misc.mq.AppEvent;
import cn.mwee.auto.misc.mq.MQSender;

/**
 * @author mengfanyuan
 * 2016年7月6日上午11:29:14
 */
@Component
public class SimpleTaskMsgSender implements TaskMsgSender {

	@Resource
	private MQSender appEventMQSender;

    @Resource
	private MessageUtil activeMQSender;

	@Resource
	IFlowManagerService flowManagerService;
	
	/* (non-Javadoc)
	 * @see cn.mwee.auto.deploy.service.execute.TaskMsgSender#sendTasks(java.util.List)
	 */
	@Override
	public void sendTasks(List<FlowTask> flowTasks) {
		for (FlowTask flowTask : flowTasks) {
			sendTask(flowTask);
		}
	}

	@Override
	public void sendTask(FlowTask flowTask) {
		//只初始化的手动任务修改状态为手动不发送
        if (AutoConsts.TaskState.INIT.name().equals(flowTask.getState())
				&& AutoConsts.TaskState.MANUAL.name().equals(flowTask.getTaskType())) {
			flowManagerService.updateTaskStatus(flowTask.getId(),AutoConsts.TaskState.MANUAL.name());
			return;
		}
		AppEvent event = new AppEvent(0, "soa.notify.flow.create", flowTask);
//		appEventMQSender.sendAsync(event.getEventType(), event);
        if (flowTask.getDelay() > 0) {
            activeMQSender.convertAndSendDelay(event,flowTask.getDelay()*1000);
        } else {
            activeMQSender.convertAndSend(event);
        }
	}
}
