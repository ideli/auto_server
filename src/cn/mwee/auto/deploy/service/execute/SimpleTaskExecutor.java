/**
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.service.execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import cn.mwee.auto.deploy.util.AutoConsts;
import cn.mwee.auto.misc.common.util.SSHManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import cn.mwee.auto.deploy.model.AutoTask;
import cn.mwee.auto.deploy.model.FlowTask;
import cn.mwee.auto.deploy.service.IFlowManagerService;
import cn.mwee.auto.deploy.service.IFlowTaskLogService;
import cn.mwee.auto.deploy.service.ITaskManagerService;
import cn.mwee.auto.deploy.util.AutoConsts.TaskState;

/**
 * @author mengfanyuan
 *         2016年7月5日下午8:13:34
 */
@Component
public class SimpleTaskExecutor implements TaskExecutor {
    private Logger logger = LoggerFactory.getLogger(SimpleTaskExecutor.class);

    @Autowired
    private IFlowManagerService flowManagerService;

    @Autowired
    private ITaskManagerService taskManagerService;

    @Autowired
    private IFlowTaskLogService flowTaskLogService;

    @Resource(name = "taskLogExecutor")
    private ThreadPoolTaskExecutor taskLogExecutor;

    @Resource
    private TaskMsgSender taskMsgSender;



    @Value("${auto.ssh.auths}")
    private String sshAuthStrs;

    private Map<String, String> sshAuthMap = new HashMap<>();

    @PostConstruct
    public void init() {
        String[] sshAuths = sshAuthStrs.split(";");
        for (String sshAuth : sshAuths) {
            if (StringUtils.isEmpty(sshAuth)) continue;
            String[] sshAuthInfo = sshAuth.split("@");
            sshAuthMap.put(sshAuthInfo[0], sshAuthInfo[1]);
        }
    }

    @Override
    public void executeChain(FlowTask flowTask) {
        // TODO Auto-generated method stub
        try {
            if (!flowManagerService.updateTaskStatusWithouTime(flowTask.getId(), TaskState.ING.name())) {
                return;
            }
            if (!execute(flowTask)) {
                throw new Exception(String.format("execute task error id[%s]", flowTask.getId()));
            }
            flowManagerService.updateTaskStatus(flowTask.getId(), TaskState.SUCCESS.name());
            FlowTask nextFlowTask = flowManagerService.getCurrentGroupNextTask(flowTask.getId());
            if (nextFlowTask != null) {
                //执行下一任务
                taskMsgSender.sendTask(nextFlowTask);
            } else {
                //当前组任务执行结束
                if (flowManagerService.checkConcurrentGroupsFinished(flowTask.getId())) {
                    List<FlowTask> nextGroupTasks = flowManagerService.getNextGroupStartFlowTasks(flowTask.getId());
                    flowManagerService.updateFlowStatus(flowTask.getFlowId());
                    //还有未执行的组任务
                    if (CollectionUtils.isNotEmpty(nextGroupTasks)) {
                        taskMsgSender.sendTasks(nextGroupTasks);
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Execute task error: FlowTask[flowTaskId={},flowId={},taskId={},params={}]", flowTask.getId(), flowTask.getFlowId(), flowTask.getTaskId(), flowTask.getTaskParam());
            logger.error("Execute task error", e);
            flowManagerService.updateTaskStatus(flowTask.getId(), TaskState.ERROR.name());
            flowManagerService.updateFlowStatus(flowTask.getFlowId());
        }

    }

    @Override
    public boolean execute(FlowTask flowTask) {
        /*AutoTask task = null;
        // 为prepare组任务生成匿名任务
        if (flowTask.getGroup().equals(AutoConsts.GroupType.PrepareGroup)) {
            task = new AutoTask();
            task.setId(0);
            task.setExec("");
            task.setName("prepare_group_task");
            task.setExecUser("root");
        } else {
            task = taskManagerService.getTask(flowTask.getTaskId());
        }*/
        AutoTask task = taskManagerService.getTask(flowTask.getTaskId());
        SSHManager instance = null;

        Integer logId = flowTaskLogService.addLog(flowTask, task);
        try {
            String shell = StringUtils.isNotBlank(task.getExec()) ? task.getExec() : "";
            String taskParam = StringUtils.isNotBlank(flowTask.getTaskParam()) ? flowTask.getTaskParam() : "";
            String command = shell + " " + taskParam;

            //获取脚步执行用户
            String sshShellUser = task.getExecUser();
            String sshPriAddr = sshAuthMap.get(sshShellUser);
            if (StringUtils.isEmpty(sshPriAddr)) {
                throw new Exception("找不到用户[" + sshShellUser + "]的秘钥地址，请检查是否配置");
            }
            String exeTargetHost = AutoConsts.DEFAULT_EXEC_ZONE.equals(task.getExecZone()) ?
                    flowTask.getZone() : task.getExecZone();
            instance = new SSHManager(sshShellUser, sshPriAddr, exeTargetHost,
                    flowTaskLogService, taskLogExecutor, logId);

            String errorMessage = instance.connect();

            if (errorMessage != null) {
                logger.error(errorMessage);
                flowTaskLogService.addLineLog(logId, errorMessage);
                return false;
            }

            String result = instance.sendCommand(command, flowTask.getId());
            logger.info("----result----");
            logger.info(result);

            if (result.contains("MW_SUCCESS")) {
                logger.info("task[id={},name={}] execute success!!!!", task.getId(), task.getName());
                return true;
            }
        } catch (Exception e) {
            logger.error("",e);
            flowTaskLogService.addLineLog(logId, e.getMessage());
        } finally {
            try {
                if (instance != null)
                instance.close();
            } catch (Exception e) {
                logger.error("",e);
            }
        }
        return false;
    }
}
