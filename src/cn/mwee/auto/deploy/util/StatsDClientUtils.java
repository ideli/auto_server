package cn.mwee.auto.deploy.util;

import cn.mwee.auto.common.util.DateUtil;
import cn.mwee.auto.deploy.dao.FlowMapper;
import cn.mwee.auto.deploy.dao.FlowTaskMapper;
import cn.mwee.auto.deploy.model.Flow;
import cn.mwee.auto.deploy.model.FlowTask;
import cn.mwee.auto.deploy.service.impl.FlowManagerService;
import com.timgroup.statsd.NonBlockingStatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/14.
 */
@Component
public class StatsDClientUtils {

    @Resource(name = "simpleExecutor")
    private ThreadPoolTaskExecutor simpleExecutor;

    @Resource(name = "statsDClient")
    private NonBlockingStatsDClient statsDClient;

    @Resource
    private FlowManagerService flowManagerService;


    public void sendFlowExecutionTime (Integer flowId) {
        simpleExecutor.execute(() -> {
            Flow flow = flowManagerService.getFlow(flowId);
            long timeInMs = calcExecuteTime(flow);
            statsDClient.recordExecutionTime(flow.getTemplateId()+"",timeInMs,1);
        });
    }
    private long calcExecuteTime(Flow flow){
        if (null == flow) return -1;
        FlowTask flowTask = flowManagerService.getOneFlowTask(flow.getId());
        if (flowTask == null) return -1;
        Date endDate = flow.getUpdateTime();
        Date startDate = flowTask.getCreateTime();
        return endDate.getTime() - startDate.getTime();
    }

}
