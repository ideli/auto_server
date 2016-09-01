package cn.mwee.auto.listener.auto;

import cn.mwee.auto.deploy.model.FlowTask;
import cn.mwee.auto.deploy.service.execute.TaskExecutor;
import cn.mwee.auto.misc.mq.AppEvent;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huming on 16/6/21.
 */
public class FlowTaskListener {

    private static final Logger logger = LoggerFactory.getLogger(FlowTaskListener.class);

    private TaskExecutor taskExecutor;
    /**
     * 创建站内信 用户/范围 的处理
     */
    public void replyMessage(String json)
    {
        FlowTask flowTask = null;
        try
        {
            AppEvent evt = JSON.parseObject(json, AppEvent.class);
            logger.info("receive message {}",evt);
            flowTask = evt.getContentObject(FlowTask.class);
        }
        catch (Exception e)
        {
            String msg = String.format("receive message fail -> %s", json);
            logger.error(msg,e);
        }
        if (flowTask != null) {
            try {
                taskExecutor.executeChain(flowTask);
                logger.info("Execute flowTask{}",JSON.toJSONString(flowTask));
            } catch (Exception e) {
                String msg = String.format("Execute Task Error -> %s", JSON.toJSONString(flowTask));
                logger.error(msg,e);
            }
        }
    }
	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}


}
