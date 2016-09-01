package cn.mwee.auto.deploy.dao;

import java.util.List;
import java.util.Map;

import cn.mwee.auto.deploy.model.FlowTask;
import cn.mwee.auto.deploy.model.FlowTaskExample;
import cn.mwee.auto.deploy.model.FlowTaskExtModel;

public interface FlowTaskExtMapper {

    /**
     * 获取当前组的下个任务
     *
     * @param example
     * @return
     */
    FlowTask getCurrentGroupNextTask(FlowTaskExample example);

    /**
     * 统计为完成的任务
     *
     * @param example
     * @return
     */
    int countUnFinishedTasks(FlowTaskExample example);

    /**
     * 统计任务状态
     *
     * @param example
     * @return
     */
    List<Map<String, Object>> countState(FlowTaskExample example);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int insertBatch(List<FlowTask> list);

    /**
     * 获取准备组，or 前置组 or 后置组的开始任务
     *
     * @param example
     * @return
     */
    List<FlowTask> getPreOrPostGroupStartTasks(FlowTaskExample example);

    /**
     * 获取并发组的开始任务
     *
     * @param example
     * @return
     */
    List<FlowTask> getConcurrentGroupStartTasks(FlowTaskExample example);

    /**
     * 获取扩展
     * @param example
     * @return
     */
    List<FlowTaskExtModel> getFlowTaskExtByExample(FlowTaskExample example);

}