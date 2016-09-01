package cn.mwee.auto.deploy.service;

import cn.mwee.auto.deploy.contract.task.QueryTasksRequest;
import cn.mwee.auto.deploy.contract.task.QueryTasksResult;
import cn.mwee.auto.deploy.model.AutoTask;
import cn.mwee.auto.deploy.model.Model4Sel;

import java.util.List;
import java.util.Set;

/**
 * Created by huming on 16/6/24.
 */
public interface ITaskManagerService {

    boolean addTask(AutoTask task);

    AutoTask getTask(Integer taskId);

    boolean deleteTask(Integer taskId);

    boolean checkTaskCanDel(Integer taskId);

    boolean modifyTask(AutoTask task);

    QueryTasksResult getAutoTasks(QueryTasksRequest request);

    List<AutoTask> getAutoTasksByIds (Set<Integer> ids);

    List<Model4Sel> getAutoTask4Sel2(String param);
}
