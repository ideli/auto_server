package cn.mwee.auto.deploy.controller.impl;

import cn.mwee.auto.auth.model.AuthPermission;
import cn.mwee.auto.deploy.AutoAbstractController;
import cn.mwee.auto.deploy.contract.template.*;
import cn.mwee.auto.deploy.controller.ITemplateController;
import cn.mwee.auto.deploy.model.*;
import cn.mwee.auto.deploy.service.*;
import cn.mwee.auto.deploy.util.AutoConsts;
import cn.mwee.auto.misc.aspect.contract.Contract;
import cn.mwee.auto.misc.aspect.contract.Model;
import cn.mwee.auto.misc.common.util.Utilities;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by huming on 16/7/21.
 */
@Controller
public class TemplateController extends AutoAbstractController implements ITemplateController
{
    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private ITemplateManagerService templateManagerService;

    @Autowired
    private IZoneService zoneService;

    @Resource
    private IProjectService projectService;

    @Resource
    private IFlowManagerService flowManagerService;

    @Resource
    private ITaskManagerService taskManagerService;

    @Override
    @Contract(QueryTemplatesRequest.class)
    public NormalReturn queryTemplates(ServiceRequest request)
    {
        QueryTemplatesRequest req = request.getContract();
        QueryTemplatesResult result = templateManagerService.getTemplates(req);
        return new NormalReturn(result);
    }

    @Override
    @Model(contract = AddTemplateRequest.class, model = AutoTemplate.class)
    public NormalReturn addTemplate(ServiceRequest request)
    {
        AutoTemplate contract = request.getModel();
        templateManagerService.addTemplate(contract);
        return new NormalReturn("success");
    }

    @Override
    @Contract(TemplateIdQuery.class)
    public NormalReturn getTemplate(ServiceRequest request)
    {
        TemplateIdQuery contract = request.getContract();
        Map<String,Object> resultMap = new HashMap<>();
        AutoTemplate template = templateManagerService.getTemplate(contract.getTemplateId());
        if (template != null) {
            AuthPermission project = projectService.getProject(template.getProjectId());
            resultMap.put("project",project);
        }
        resultMap.put("template",template);
        return new NormalReturn(resultMap);
    }

    @Override
    @Contract(TemplateIdQuery.class)
    public NormalReturn deleteTemplate(ServiceRequest request)
    {
        TemplateIdQuery contract = request.getContract();
        if (CollectionUtils.isNotEmpty(templateManagerService.getTemplateTasks(contract.getTemplateId()))
                || CollectionUtils.isNotEmpty(templateManagerService.getTemplateZones(contract.getTemplateId()))) {
            return new NormalReturn("500","template["+contract.getTemplateId()+"] is in use, can not delete");
        }
        templateManagerService.deleteTemplate(contract.getTemplateId());
        return new NormalReturn("success");
    }

    @Override
    @Model(contract = ModifyTemplateRequest.class, model = AutoTemplate.class)
    public NormalReturn modifyTemplate(ServiceRequest request)
    {
        AutoTemplate template = request.getModel();
        templateManagerService.modifyTemplate(template);
        return new NormalReturn("success");
    }

    @Override
    @Model(contract = AddTemplateTaskRequest.class, model = TemplateTask.class)
    public NormalReturn addTask2Template(ServiceRequest request)
    {
        TemplateTask task = request.getModel();
        AddTemplateTaskRequest req = request.getContract();
        Integer templateId = req.getTemplateId();
        Byte templateType = req.getTemplateType();
        Integer subTemplateId = req.getSubTemplateId();

        if (subTemplateId == null || subTemplateId == 0){
            AutoTemplate subTemplate = templateManagerService.getSubTemplate(templateId,templateType,true);
            subTemplateId = subTemplate.getId();
        }
        if (templateManagerService.addTask2Template(subTemplateId,task)) {
            return new NormalReturn("success");
        } else {
            return new NormalReturn("500","error");
        }
        /*

        if(task.getGroup().byteValue() == AutoConsts.GroupType.RollbackGroup)
        {
            // 回滚组, init ? 初始化回滚组模板 : 添加回滚模板任务
            templateManagerService.addTask2RollbackTemplate(task.getTemplateId(),task);
        }
        else
        {
            templateManagerService.addTask2Template(task.getTemplateId(),task);
        }

        return new NormalReturn("success");
        */
    }

    @Override
    @Contract(TemplateTaskIdQuery.class)
    public NormalReturn removeTemplateTask(ServiceRequest request)
    {
        TemplateTaskIdQuery contract = request.getContract();
        boolean rmSuccess = templateManagerService.removeTemplateTask(contract.getTemplateTaskId());
        return new NormalReturn(rmSuccess);
    }

    @Override
    @Model(contract = ModifyTemplateTaskRequest.class, model = TemplateTask.class)
    public NormalReturn modifyTemplateTask(ServiceRequest request)
    {
        TemplateTask templateTask = request.getModel();
        boolean modifySuccess = templateManagerService.modifyTemplateTask(templateTask);
        return new NormalReturn(modifySuccess);
    }

    @Override
    @Contract(TemplateIdQuery.class)
    public NormalReturn getTemplateTasks(ServiceRequest request)
    {
        TemplateIdQuery contract = request.getContract();
        List<TemplateTask> tasks = templateManagerService.getTemplateTasks(contract.getTemplateId());
        return new NormalReturn(tasks);
    }

    @Override
    @Contract(TemplateIdQuery.class)
    public NormalReturn getTemplateInfo(ServiceRequest request) {
        TemplateIdQuery req = request.getContract();
        try {
            AutoTemplate template = templateManagerService.getTemplate(req.getTemplateId());
            if (template == null) {
                return new NormalReturn("500","error","模板不存在");
            }
            Map<String,Object> result = new HashMap<>();
            //基础信息
            result.put("baseInfo",templateManagerService.getTemplate(req.getTemplateId()));
            //区信息
            result.put("zones", templateManagerService.getTemplateZones(req.getTemplateId(),req.getEnv()));
            //任务信息
//            result.put("tasks", templateManagerService.getTemplateSimpleTasks(req.getTemplateId()));
            //任务参数key
            result.put("taskParamKeys", templateManagerService.getTemplateTaskParamKeys(req.getTemplateId()));
            result.put("vcsInfo", templateManagerService.getGitRepInfo(template));
            Flow lastFlow = flowManagerService.getLastFlow(req.getTemplateId(),null);
            if (lastFlow != null) {
                result.put("defaultVcsBranch", lastFlow.getVcsBranch());
            }
            return new NormalReturn("200","success",result);
        } catch (Exception e) {
            logger.error("",e);
            return new NormalReturn("500","error",e.getMessage());
        }
    }

    @Override
    @Contract(TemplateIdQuery.class)
    public NormalReturn getTemplateDetail(ServiceRequest request)
    {
        TemplateIdQuery req = request.getContract();
        try {
            AutoTemplate template = templateManagerService.getTemplate(req.getTemplateId());
            if (template == null) {
                return new NormalReturn("500","error","模板不存在");
            }

            Map<String,Object> result = new HashMap<>();

            Integer templateId = req.getTemplateId();

            //基础信息
            result.put("baseInfo",templateManagerService.getTemplate(templateId));
            //区信息
            result.put("zones", templateManagerService.getTemplateZones(templateId));
            //任务信息
            List<TemplateTask> templateTasks = templateManagerService.getTemplateTasks(templateId);
            result.put("templateTasks", templateTasks);

            Map<String,List<TemplateTask>> templateTaskMap = templateManagerService.getAllTemplateTasks(templateId);
            result.put("allTemplateTasks", templateTaskMap);


            //监控配置
            result.put("monitorInfo", templateManagerService.getTemplateZoneMonitor(templateId));
            //回滚模板数据
            List<TemplateTask> rollTemplateTasks = templateManagerService.getRollbackTemplateTasks(templateId);
            result.put("rollbackTemplate", rollTemplateTasks);

            Set<Integer> taskIds = new HashSet<>();
            templateTasks.forEach(templateTask -> taskIds.add(templateTask.getTaskId()));
            rollTemplateTasks.forEach(templateTask -> taskIds.add(templateTask.getTaskId()));

            templateTaskMap.forEach((key, value) -> value.forEach(templateTask -> taskIds.add(templateTask.getTaskId())));

            result.put("autoTasks",taskManagerService.getAutoTasksByIds(taskIds));
            return new NormalReturn(result);
        }
        catch (Exception e)
        {
            logger.error("",e);
            return new NormalReturn("500","error",e.getMessage());
        }
    }

    @Override
    @Model(contract = AddTemplateZoneRequest.class, model = TemplateZone.class)
    public NormalReturn addTemplateZone(ServiceRequest request)
    {
        TemplateZone templateZone = request.getModel();

        boolean addSuccess = templateManagerService.addTemplateZone(templateZone);

        return new NormalReturn(addSuccess);
    }

    @Override
    @Contract(AddTemplateZonesRequest.class)
    public NormalReturn addBatchTemplateZone(ServiceRequest request)
    {
        List<String> successList = Lists.newArrayList();
        List<String> failList = Lists.newArrayList();
        AddTemplateZonesRequest contract = request.getContract();

        if (contract.getAddType() == 1) {
            List<Map<String,String>> zoneDataList = contract.getZoneDataList();
            zoneDataList.forEach(zoneData -> {
                String name = zoneData.get("name");
                String ip = zoneData.get("ip");
                Byte env = new Byte(zoneData.get("env"));
                if (Utilities.isIpAddress(ip)) {
                    Zone exitsZone = zoneService.getZone4Ip(ip);
                    int zoneId;
                    if (exitsZone != null) {
                        zoneId = exitsZone.getId();
                    } else {
                        zoneId = zoneService.addZone(ip);
                    }
                    TemplateZone templateZone = new TemplateZone();
                    templateZone.setTemplateId(contract.getTemplateId());
                    templateZone.setName(name);
                    templateZone.setZoneId(zoneId);
                    templateZone.setEnv(env);
                    boolean addSuccess = templateManagerService.addTemplateZone(templateZone);
                    if(addSuccess)
                    {
                        successList.add(ip);
                    }
                    else
                    {
                        failList.add(ip);
                    }
                } else {
                    failList.add(ip);
                }
            });
        } else {
            String[] zones = StringUtils.split(contract.getZones(), ",|;| ");
            Byte env = contract.getEnv();
            if (env == null) return new NormalReturn("500","模板区环境不能为空");
            for (String zone : zones)
            {
                if(Utilities.isIpAddress(zone))
                {
                    Zone exitsZone = zoneService.getZone4Ip(zone);
                    int zoneId;
                    if (exitsZone != null) {
                        zoneId = exitsZone.getId();
                    } else {
                        zoneId = zoneService.addZone(zone);
                    }
                    TemplateZone templateZone = new TemplateZone();

                    templateZone.setName(zone);

                    templateZone.setTemplateId(contract.getTemplateId());

                    templateZone.setZoneId(zoneId);

                    templateZone.setEnv(env);
                    boolean addSuccess = templateManagerService.addTemplateZone(templateZone);

                    if(addSuccess)
                    {
                        successList.add(zone);
                    }
                    else
                    {
                        failList.add(zone);
                    }
                }
                else
                {
                    failList.add(zone);
                }
            }
        }
        HashMap<String,List<String>> maps = Maps.newHashMap();
        maps.put("successList",successList);
        maps.put("failList",failList);
        return new NormalReturn(maps);
    }

    @Override
    @Contract(DeleteTemplateZoneRequest.class)
    public NormalReturn removeTemplateZone(ServiceRequest request)
    {
        DeleteTemplateZoneRequest contract = request.getContract();
        boolean rmSuccess = templateManagerService.removeTemplateZone(contract.getTemplateZoneId());
        return new NormalReturn(rmSuccess);
    }

    @Override
    @Contract(CloneTemplateRequest.class)
    public NormalReturn cloneTemplate(ServiceRequest request)
    {
        CloneTemplateRequest req = request.getContract();
        templateManagerService.cloneTemplate(req.getTemplateId(),req.getTemplateName(),req.getCloneType());
        return new NormalReturn("success");
    }

    @Override
    @Contract(AddTemplateZoneMonitorRequest.class)
    public NormalReturn addTempZoneMonitor(ServiceRequest request) {
        AddTemplateZoneMonitorRequest req = request.getContract();
        if (templateManagerService.getTemplateZoneMonitor(req.getTemplateId()) != null) {
            return new NormalReturn("500","template["+req.getTemplateId()+"] monitor exist already");
        }
        templateManagerService.addTemplateZoneMonitor(req.getTemplateId(),req.getMonitorType(),req.getMonitorParam(),req.getInUse());
        return new NormalReturn("success");
    }

    @Override
    @Contract(AddTemplateZoneMonitorRequest.class)
    public NormalReturn updateTempZoneMonitor(ServiceRequest request) {
        AddTemplateZoneMonitorRequest req = request.getContract();
        templateManagerService.updateTemplateZoneMonitor(req.getTemplateId(),req.getMonitorType(),req.getMonitorParam(),req.getInUse());
        return new NormalReturn("success");
    }

    @Override
    @Contract(TemplateZoneIpQuery.class)
    public NormalReturn getZoneTemplates(ServiceRequest request) {
        TemplateZoneIpQuery req = request.getContract();
        return new NormalReturn(templateManagerService.getTemplate4Host(req.getHost()));
    }
}
