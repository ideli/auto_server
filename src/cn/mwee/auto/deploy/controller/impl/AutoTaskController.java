package cn.mwee.auto.deploy.controller.impl;

import cn.mwee.auto.deploy.contract.commom.SelQueryParamContract;
import cn.mwee.auto.deploy.contract.task.*;
import cn.mwee.auto.deploy.controller.IAutoTaskController;
import cn.mwee.auto.deploy.model.AutoTask;
import cn.mwee.auto.deploy.service.ITaskManagerService;
import cn.mwee.auto.misc.aspect.contract.Contract;
import cn.mwee.auto.misc.aspect.contract.Model;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by huming on 16/7/19.
 */
@Controller
public class AutoTaskController implements IAutoTaskController
{
    @Autowired
    private ITaskManagerService taskManagerService;

    @Override
    @Contract(QueryTasksRequest.class)
    public NormalReturn queryTasks(ServiceRequest request)
    {
        QueryTasksRequest req = request.getContract();
        QueryTasksResult result = taskManagerService.getAutoTasks(req);
        return new NormalReturn(result);
    }

    @Override
    @Model(contract = AddAutoTaskRequest.class, model = AutoTask.class)
    public NormalReturn addTask(ServiceRequest request)
    {
        AutoTask model = request.getModel();
        model.setCreateTime(new Date());
        boolean addSuccess = taskManagerService.addTask(model);
        if(addSuccess)
        {
            return new NormalReturn(model.getId());
        }
        else
        {
            return new NormalReturn("500","add task error");
        }
    }

    @Override
    @Contract(TaskIdRequest.class)
    public NormalReturn getTask(ServiceRequest request)
    {
        TaskIdRequest contract = request.getContract();
        AutoTask autoTask = taskManagerService.getTask(contract.getTaskId());
        return new NormalReturn(autoTask);
    }

    @Override
    @Contract(TaskIdRequest.class)
    public NormalReturn deleteTask(ServiceRequest request)
    {
        TaskIdRequest contract = request.getContract();
        if (!taskManagerService.checkTaskCanDel(contract.getTaskId())) {
            return new NormalReturn("500","task["+contract.getTaskId()+"] is in use,can not delete ");
        }
        boolean delSuccess = taskManagerService.deleteTask(contract.getTaskId());
        if(delSuccess)
        {
            return new NormalReturn(contract.getTaskId());
        }
        else
        {
            return new NormalReturn("500","delete task error");
        }
    }

    @Override
    @Model(contract = ModifyTaskRequest.class, model = AutoTask.class)
    public NormalReturn modifyTask(ServiceRequest request)
    {
        AutoTask contract = request.getModel();
        boolean modifySuccess = taskManagerService.modifyTask(contract);
        if(modifySuccess)
        {
            return new NormalReturn(contract.getId());
        }
        else
        {
            return new NormalReturn("500","modify task error");
        }
    }


    @Override
    @Contract(SelQueryParamContract.class)
    public NormalReturn queryTasks4Sel2(ServiceRequest request) {
        SelQueryParamContract req = request.getContract();
        return new NormalReturn(taskManagerService.getAutoTask4Sel2(req.getQueryParam()));
    }
}
