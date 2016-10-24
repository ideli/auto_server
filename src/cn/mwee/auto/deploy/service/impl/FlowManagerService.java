package cn.mwee.auto.deploy.service.impl;

import cn.mwee.auto.auth.model.AuthPermission;
import cn.mwee.auto.auth.util.AuthUtils;
import cn.mwee.auto.auth.util.SqlUtils;
import cn.mwee.auto.common.db.BaseModel;
import cn.mwee.auto.common.db.BaseQueryResult;
import cn.mwee.auto.common.util.DateUtil;
import cn.mwee.auto.deploy.contract.flow.FlowAddContract;
import cn.mwee.auto.deploy.contract.flow.FlowQueryContract;
import cn.mwee.auto.deploy.contract.flow.SubFlowQueryContract;
import cn.mwee.auto.deploy.dao.*;
import cn.mwee.auto.deploy.model.*;
import cn.mwee.auto.deploy.service.IFlowManagerService;
import cn.mwee.auto.deploy.service.IProjectService;
import cn.mwee.auto.deploy.service.ITemplateManagerService;
import cn.mwee.auto.deploy.service.execute.SimpleMailSender;
import cn.mwee.auto.deploy.service.execute.SimpleTaskExecutor;
import cn.mwee.auto.deploy.service.execute.TaskMsgSender;

import static cn.mwee.auto.deploy.util.AutoConsts.*;

import cn.mwee.auto.deploy.util.AutoUtils;

import cn.mwee.auto.deploy.util.StatsDClientUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import javax.annotation.Resource;

import java.util.*;

/**
 * Created by huming on 16/6/24.
 */
@Service
public class FlowManagerService implements IFlowManagerService {
    private static final Logger logger = LoggerFactory.getLogger(SimpleTaskExecutor.class);
    @Autowired
    private FlowMapper flowMapper;

    @Autowired
    private FlowTaskExtMapper flowTaskExtMapper;

    @Resource
    private FlowTaskMapper flowTaskMapper;

    @Autowired
    private TemplateTaskExtMapper templateTaskExtMapper;

    @Autowired
    private AutoTaskMapper autoTaskMapper;

    @Autowired
    private IProjectService projectService;

    @Resource
    private ITemplateManagerService templateManagerService;

    @Resource
    private SimpleMailSender simpleMailSender;

    @Resource
    private TaskMsgSender taskMsgSender;

    @Resource
    private StatsDClientUtils statsDClientUtils;

    @Value("${localhost.name}")
    private String localHost = "127.0.0.1";

    @Value("${deploy.env}")
    private String deployEnv = "";

    @Value("${auto.bak.dir}")
    private String autoBakDir;

    @Value("${auto.workSpace.dir}")
    private String workSpace = "/opt/auto/workspace";

    private int flowExeDelay = 1;

    @Override
    public Integer createFlow(FlowAddContract req) throws Exception {
        Flow flow = createFlowSimple(req);
        if (req.getPid() != null && req.getPid() > 0) {
            if (StringUtils.isEmpty(flow.getZones())) throw new Exception("未配置对应环境区域主机");
            AutoTemplate subTemplate = templateManagerService.getSubTemplate(req.getTemplateId(), req.getType(), false);
            if (null == subTemplate) throw new Exception("未配置对应任务");
            List<TemplateTask> ttList = templateManagerService.getTemplateTasks(subTemplate.getId());
            if (CollectionUtils.isEmpty(ttList)) throw new Exception("未配置对应任务");
            flow.setTemplateId(subTemplate.getId());
            flow.setPid(req.getPid());
            flow.setFlowStep(req.getStep());
            flow.setStepState(req.getStepState());
            flow.setIsreview(FlowReviewType.Ignore);
        } else {
            flow.setIsreview(needReview(flow.getFlowStep()) ? FlowReviewType.Unreviewed : FlowReviewType.Ignore);
        }
        Map<String, Object> params = req.getParams() == null ? new HashMap<>() : req.getParams();
        flow.setParams(JSON.toJSONString(params));
        flow.setCreateTime(new Date());
        flow.setCreator(AuthUtils.getCurrUserName());
        flow.setOperater(flow.getCreator());
        flow.setState(TaskState.INIT.name());
        int result = flowMapper.insertSelective(flow);
        return result > 0 ? flow.getId() : 0;
    }

    private boolean needReview(Byte flowStep) {
        return flowStep != null && ((flowStep & (1 << 4)) > 0 || (flowStep & (1 << 5)) > 0);
    }

    private Flow createFlowSimple(FlowAddContract req) {
        Flow flow = new Flow();
        flow.setName(req.getName());
        flow.setEnv(req.getEnv());
        flow.setTemplateId(req.getTemplateId());
        flow.setProjectId(req.getProjectId());
        flow.setType(req.getType());
        flow.setFlowStep(req.getFlowStep());
        if (req.getPid() != null && req.getPid() != 0) {
            if ((req.getStep() & 1) == 1) {
                flow.setZones(localHost);
            } else {
                flow.setZones(getZoneStr(req.getTemplateId(), req.getEnv(),req.getStep(),req.getZones()));
            }
        }
        flow.setVcsBranch(req.getVcsBranch());
        flow.setNeedbuild(req.getNeedBuild());
        return flow;
    }

    private String getZoneStr(Integer templateId, Byte env, Byte flowStep,String zones) {
        if (StringUtils.isNotEmpty(zones)) {return zones;}
        StringBuilder zoneStr = new StringBuilder();
        List<TemplateZoneModel> zoneModelList = templateManagerService.getTemplateZones(templateId, env);
        /*
        if (Env.PROD == env) {
            zoneModelList.addAll(templateManagerService.getTemplateZones(templateId, Env.FORTRESS));
        }*/

        /**
         * 去重
         */
        Set<String> zoneIpSet = new HashSet<>();
        zoneModelList.forEach(zoneModel -> zoneIpSet.add(zoneModel.getIp()));
        zoneIpSet.forEach(zoneIp -> {
            if (zoneStr.length() > 0) {
                zoneStr.append(",").append(zoneIp);
            } else {
                zoneStr.append(zoneIp);
            }
        });
        return zoneStr.toString();
    }

    @Override
    public boolean executeFlow(int flowId) throws Exception {
        Flow flow = flowMapper.selectByPrimaryKey(flowId);
        //判断
        /*if (!canExecute(flow)) {
            throw new Exception("执行失败");
        }*/
        if (initFlowTasks(flowId) && startFlow(flowId)) {
            Flow tmpFlow = new Flow();
            tmpFlow.setState(TaskState.ING.name());
            tmpFlow.setOperater(AuthUtils.getCurrUserName());
            tmpFlow.setUpdateTime(new Date());

            FlowExample example = new FlowExample();
            example.createCriteria()
                    .andIdEqualTo(flowId)
                    .andStateEqualTo(TaskState.INIT.name());
            flowMapper.updateByExampleSelective(tmpFlow,example);
//            sendNoticeMail(flow,TaskState.ING.name());
            return true;
        } else {
            return false;
        }
    }

    private boolean canExecute(Flow flow) throws Exception {
        if (FlowReviewType.Unreviewed.equals(flow.getIsreview()) ||
                FlowReviewType.Unapproved.equals(flow.getIsreview())) {
            throw new Exception("未经批准的流程");
        }
        Flow lastFlow = getLastExeFlow(flow.getTemplateId(), flow.getProjectId());
        Date exeDate = lastFlow.getUpdateTime();
        if (exeDate != null && DateUtil.addMinutes(exeDate, flowExeDelay).after(new Date())) {
            throw new Exception("发布过于频繁，请稍后重试");
        }
        return true;
    }

    @Override
    public boolean initFlowTasks(int flowId) {
        /*
         * copy tasks from template_tasks to flow_tasks
         */

        //get flow
        Flow flow = flowMapper.selectByPrimaryKey(flowId);
        Flow pFlow = null;
        if (flow == null) {
            throw new NullPointerException("Cant find flow for id:" + flowId);
        }
        if (flow.getPid() != null && flow.getPid() != 0) {
            pFlow = flowMapper.selectByPrimaryKey(flow.getPid());
        }

        FlowStrategy flowStrategy = (pFlow != null ? templateManagerService.getFlowStrategy(pFlow.getTemplateId()) : null);
        //get template
        AutoTemplate template = templateManagerService.getTemplate(flow.getTemplateId());
        if (template == null) {
            throw new NullPointerException("Cant find template for id:" + flow.getTemplateId());
        }
        //回滚流程设置父模板信息
        if (flow.getNeedbuild() == 1) {
            AutoTemplate pTemplate = templateManagerService.getTemplate(template.getPid());
            if (pTemplate != null) {
                template.setVcsType(pTemplate.getVcsType());
                template.setVcsRep(pTemplate.getVcsRep());
            }
        }

        //获取模板任务
        List<TemplateTask> tts = templateManagerService.getTemplateTasks(flow.getTemplateId());

        //获取区域
        String zoneStr = StringUtils.isBlank(flow.getZones()) ? "" : flow.getZones();
        String[] zones = zoneStr.split(",");
        //用户定义变量
        Map<String, String> userParamsMap = new HashMap<>();
        String paramStr = pFlow == null ? flow.getParams() : pFlow.getParams();
        if (StringUtils.isNotBlank(paramStr)) {
            userParamsMap = JSON.parseObject(flow.getParams(), Map.class);
        }
        //流程变量
        Map<String, String> flowParamMap = initFlowParams(template, flow, userParamsMap);
        //复制任务
        List<FlowTask> fts = new ArrayList<>();
        Map<String, FlowTask> zoneStartTaskMap = new HashMap<>();
        for (TemplateTask tt : tts) {
            //prepareGroup
            if (tt.getGroup().equals(GroupType.PrepareGroup)) {
                FlowTask ft = buildFlowTask(tt, flowId, localHost, flowParamMap, userParamsMap);
                if (ft != null) {
                    fts.add(ft);
                }
                continue;
            }
            //区域组
            for (int i = 0; i < zones.length; i++) {
                if (StringUtils.isBlank(zones[i])) continue;
                flowParamMap.put("%zoneIndex%", (i + 1) + "");
                //构建模板的构建任务单独出来
                if (flow.getType() == TemplateType.BUILD && tt.getGroup().equals(GroupType.BuildGroup) && pFlow != null) {
                    Byte flowStep = pFlow.getFlowStep();
                    for (int l = 1; l < 6; l++) {
                        int step = flowStep & (1 << l);
                        if (step == 0) continue;

                        if (step == 2) {
                            flowParamMap.put("%env%", "dev");
                        } else if (step == 4)  {
                            flowParamMap.put("%env%", "test");
                        } else if (step == 8)  {
                            flowParamMap.put("%env%", "uat");
                        } else if (step == 16)  {
                            flowParamMap.put("%env%", "prod");
                        } else if (step == 32)  {
                            if ("prod".equals(flowParamMap.get("%env%"))) {
                                break;
                            }
                            flowParamMap.put("%env%", "prod");
                        }
                        /*
                        switch (step) {
                            case 2:
                                flowParamMap.put("%env%", "dev");
                                break;
                            case 4:
                                flowParamMap.put("%env%", "test");
                                break;
                            case 8:
                                flowParamMap.put("%env%", "uat");
                                break;
                            case 16:
                                flowParamMap.put("%env%", "prod");
                                break;
                        }
                        */
                        /*short priority = (short) (tt.getPriority() + 1);
                        tt.setPriority(priority);*/
                        byte group = (byte) (tt.getGroup()+(l-1));
                        tt.setGroup(group);
                        FlowTask flowTask = buildFlowTask(tt, flowId, zones[i], flowParamMap, userParamsMap);
                        fts.add(flowTask);
                    }
                } else {
                    FlowTask flowTask = buildFlowTask(tt, flowId, zones[i], flowParamMap, userParamsMap);
                    if (flowTask != null) {
                        if (flowStrategy != null) {
                            calStrategy(flowTask, i, zoneStartTaskMap, flowStrategy);
                        }
                        fts.add(flowTask);
                    }
                }
            }
            /*
            for (String zone : zones) {
                if (StringUtils.isBlank(zone)) continue;
                fts.add(buildFlowTask(tt, flowId, zone, flowParamMap, userParamsMap));
            }
            */
        }
        if (CollectionUtils.isEmpty(fts)) {
            return false;
        }
        int result = flowTaskExtMapper.insertBatch(fts);
        return result > 0;
    }

    private void calStrategy(FlowTask flowTask, int index, Map<String, FlowTask> zoneStartTaskMap, FlowStrategy flowStrategy) {
        String key = flowTask.getZone();
        if (zoneStartTaskMap.get(key) != null) return;
        if (flowTask.getGroup() > GroupType.PreGroup
                && flowTask.getGroup() < GroupType.PostGroup) {
            key += flowTask.getGroup();
            if (zoneStartTaskMap.get(key) != null) return;
        }
        zoneStartTaskMap.put(key, flowTask);
        flowTask.setDelay(((index) / flowStrategy.getZonesize()) * flowStrategy.getInterval());
    }

    /**
     * 构建flowTask
     *
     * @param tt
     * @param flowId
     * @param zone
     * @param flowParamMap
     * @param userParamsMap
     * @return
     */
    private FlowTask buildFlowTask(TemplateTask tt, Integer flowId, String zone,
                                   Map<String, String> flowParamMap, Map<String, String> userParamsMap) {
        AutoTask task = autoTaskMapper.selectByPrimaryKey(tt.getTaskId());
        if (task == null) return null;
        String paramStr = task.getExec() + " ";
        if (StringUtils.isNotBlank(task.getParams())) {
            paramStr += task.getParams();
        }
        paramStr = paramStr.replaceAll("[\\t\\n\\r]", " ");
        FlowTask ft = new FlowTask();
        ft.setGroup(tt.getGroup());
        ft.setPriority(tt.getPriority());
        ft.setTaskId(tt.getTaskId());
        ft.setTaskType(tt.getTaskType());
        ft.setFlowId(flowId);
        ft.setZone(zone);
        ft.setDelay(0);
        ft.setTaskParam(paramStr);
        flowParamMap.put("%zone%", zone);
        replaceFlowParams(ft, flowParamMap);
        replaceUserParams(ft, userParamsMap);
        ft.setState(TaskState.INIT.name());
        ft.setCreateTime(new Date());
        return ft;
    }

    /**
     * 参数替换
     *
     * @param ft       flowTask
     * @param paramMap paramMap
     */
    private void replaceUserParams(FlowTask ft, Map<String, String> paramMap) {
        if (StringUtils.isNotBlank(ft.getTaskParam())) {
            String paramStr = ft.getTaskParam();
            if (paramStr.indexOf('#') < 0) return;
            Set<String> keySet = paramMap.keySet();
            for (String key : keySet) {
                String paramKey = "#" + key + "#";
                paramStr = paramStr.replaceAll(paramKey, paramMap.get(key));
            }
            ft.setTaskParam(paramStr);
        }
    }

    private Map<String, String> initFlowParams(AutoTemplate template, Flow flow, Map<String, String> userParamsMap) {
        Flow pFlow;
        AutoTemplate pTemplate;
        pFlow = (flow.getPid() != null && flow.getPid() != 0) ? getFlow(flow.getPid()) : flow;
        pTemplate = (template.getPid() != null && template.getPid() != 0) ? templateManagerService.getTemplate(template.getPid()) : template;

        Map<String, String> flowParamMap = new HashMap<>();
        flowParamMap.put("%bakDir%", autoBakDir);
        flowParamMap.put("%flowId%", pFlow.getId() + "");
        flowParamMap.put("%vcsType%", pTemplate.getVcsType());
        flowParamMap.put("%vcsRep%", pTemplate.getVcsRep());
        flowParamMap.put("%vcsBranch%", pFlow.getVcsBranch());
        flowParamMap.put("%flowStep%", pFlow.getFlowStep() + "");
        flowParamMap.put("%envSet%", getEnvSetString(pFlow.getFlowStep()));
        flowParamMap.put("%autoWorkspace%", workSpace);
        String env = "";
        switch (flow.getEnv()) {
            case Env.DEV:
                env = "dev";
                break;
            case Env.TEST:
                env = "test";
                break;
            case Env.UAT:
                env = "uat";
                break;
            case Env.FORTRESS:
                env = "prod";
                break;
            case Env.PROD:
                env = "prod";
                break;
        }
        flowParamMap.put("%env%", env);
        String repUrl = pTemplate.getVcsRep();
        if (StringUtils.isNotBlank(repUrl)
                && StringUtils.isNotBlank(pFlow.getVcsBranch())) {
            flowParamMap.put("%projectName%", repUrl.substring(repUrl.lastIndexOf('/') + 1, repUrl.lastIndexOf('.')));
        } else {
            flowParamMap.put("%projectName%", "");
        }
        String version = userParamsMap.get("version") == null ? "" : userParamsMap.get("version");
        flowParamMap.put("%projectBackupPath%", autoBakDir + "/" + flowParamMap.get("%projectName%") + "_" + version);

        String projectDir = workSpace + "/" + pFlow.getId() + "/" + flowParamMap.get("%env%") + "/" +flowParamMap.get("%projectName%");
        flowParamMap.put("%workspace%", projectDir.replace("//", "/"));
        return flowParamMap;
    }

    private String getEnvSetString (Byte flowStep) {
        StringBuilder sb = new StringBuilder();
        Set<String> envSet = getEnvSet(flowStep);
        envSet.forEach(envStr -> {
            if (sb.length() == 0) {
                sb.append(envStr);
            } else {
                sb.append(",").append(envStr);
            }
        } );
        return sb.toString();
    }

    private Set<String> getEnvSet(Byte flowStep) {
        Set<String> envSet = new HashSet<>();
        if (flowStep == null || flowStep == 0)
            return envSet;
        for (int i = 1; i<6;i++) {
            int envCode = flowStep & (1<<i);
            switch (envCode) {
                case 2:
                    envSet.add("dev");
                    break;
                case 4:
                    envSet.add("test");
                    break;
                case 8:
                    envSet.add("uat");
                    break;
                case 16:
                    envSet.add("prod");
                    break;
                case 32:
                    envSet.add("prod");
                    break;
            }
        }
        return envSet;
    }

    private void replaceFlowParams(FlowTask ft, Map<String, String> flowParamMap) {
        if (StringUtils.isNotBlank(ft.getTaskParam())) {
            String paramStr = ft.getTaskParam();
            if (paramStr.indexOf('%') < 0) return;
            Set<String> keySet = flowParamMap.keySet();
            for (String key : keySet) {
                paramStr = paramStr.replaceAll(key, flowParamMap.get(key));
            }
            ft.setTaskParam(paramStr);
        }
    }

    @Override
    public boolean updateFlowStepState(int flowId, byte step, Integer stepState) {
        int i;
        for (i = 0; i < 5; i++) {
            if (step >> i == 1) break;
        }
        Flow oldFlow = getFlow(flowId);
        Flow flow = new Flow();
        flow.setId(flowId);
        flow.setStepState((oldFlow.getStepState() & (~(3 << (i * 2)))) | (stepState << (i * 2)));
        return flowMapper.updateByPrimaryKeySelective(flow) > 0;
    }


    @Override
    public boolean updateFlowStatus(int flowId) {
        Flow flow = flowMapper.selectByPrimaryKey(flowId);
        FlowTaskExample example = new FlowTaskExample();
        example.createCriteria().andFlowIdEqualTo(flowId);
        List<Map<String, Object>> list = flowTaskExtMapper.countState(example);
        String stateNew = calcFlowStatus(list);
        Flow flowTmp = new Flow();
        flowTmp.setId(flow.getId());
        flowTmp.setState(stateNew);
        if (TaskState.SUCCESS.name().equals(stateNew)) {
            flowTmp.setStepState(2);
        } else if (TaskState.ERROR.name().equals(stateNew)) {
            flowTmp.setStepState(3);
        }

        int result = flowMapper.updateByPrimaryKeySelective(flowTmp);
        if (result > 0) {
            flowTmp.setUpdateTime(new Date());
            flowMapper.updateByPrimaryKeySelective(flowTmp);
            if (TaskState.SUCCESS.name().equals(stateNew) ||
                    TaskState.ERROR.name().equals(stateNew)) {
                if (flow.getPid() != null && flow.getPid() != 0 && (flow.getType() == 1 || flow.getType() == 2)) {
                    updateFlowStepState(flow.getPid(), flow.getFlowStep(), flowTmp.getStepState());
                }
                sendNoticeMail(flow, stateNew);
                if (flow.getNeedbuild() == 0) {
                    statsDClientUtils.sendFlowExecutionTime(flowId);
                }
            }
        }
        return true;
    }

    private void sendNoticeMail(Flow flow, String stateNew) {
        //在成功或失败是发送邮件
        try {
            String contentTemp = "流程[%s],当前状态[%s]";
            String content = String.format(contentTemp, flow.getName(), stateNew);
            simpleMailSender.sendNotice4ProjectAsync(flow.getProjectId(), content);
        } catch (Exception e) {
            logger.error("sendNoticeMail error", e);
        }
    }

    @Override
    public FlowTask getCurrentGroupNextTask(int flowTaskId) {
        FlowTask flowTask = flowTaskMapper.selectByPrimaryKey(flowTaskId);
        FlowTaskExample example = new FlowTaskExample();
        example.createCriteria().andFlowIdEqualTo(flowTask.getFlowId())
                .andZoneEqualTo(flowTask.getZone())
                .andGroupEqualTo(flowTask.getGroup())
                .andPriorityGreaterThan(flowTask.getPriority());
        return flowTaskExtMapper.getCurrentGroupNextTask(example);
    }

    @Override
    public boolean checkConcurrentGroupsFinished(int flowTaskId) {
        FlowTask flowTask = flowTaskMapper.selectByPrimaryKey(flowTaskId);
        FlowTaskExample example = new FlowTaskExample();
        cn.mwee.auto.deploy.model.FlowTaskExample.Criteria criteria = example.createCriteria();
        criteria.andFlowIdEqualTo(flowTask.getFlowId())
                .andZoneEqualTo(flowTask.getZone())        //区域
                .andStateNotEqualTo(TaskState.SUCCESS.name());        //不成功

        if (flowTask.getGroup() > GroupType.PreGroup
                && flowTask.getGroup() < GroupType.PostGroup) {
            criteria.andGroupBetween((byte) (GroupType.PreGroup + 1), (byte) (GroupType.PostGroup - 1));
        } else {
            criteria.andGroupEqualTo(flowTask.getGroup()); //准备组
        }
        int count = flowTaskExtMapper.countUnFinishedTasks(example);
        return !(count > 0);
    }

    @Override
    public boolean updateTaskStatusWithouTime(int flowTaskId, String state) {
        FlowTask flowTask = new FlowTask();
        flowTask.setId(flowTaskId);
        flowTask.setState(state);
        int result = flowTaskMapper.updateByPrimaryKeySelective(flowTask);
        return result > 0;
    }

    @Override
    public boolean updateTaskStatus(int flowTaskId, String state) {
        FlowTask flowTask = new FlowTask();
        flowTask.setId(flowTaskId);
        flowTask.setState(state);
        flowTask.setUpdateTime(new Date());
        return flowTaskMapper.updateByPrimaryKeySelective(flowTask) > 0;
    }

    @Override
    public List<FlowTask> getNextGroupStartFlowTasks(Integer flowTasId) {
        List<FlowTask> list = null;
        //获取当前任务
        FlowTask currentFlowTask = flowTaskMapper.selectByPrimaryKey(flowTasId);
        //当前组
        byte currentGroup = currentFlowTask.getGroup();
        //当前组为后置组 返回null
        if (currentGroup == GroupType.PostGroup) {
            return null;
        }
        //准备组
        if (currentGroup == GroupType.PrepareGroup) {
            list = getZoneStartFlowTask(currentFlowTask.getFlowId());
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
        }

        FlowTaskExample example = new FlowTaskExample();
        //当前组为前置组
        if (currentGroup == GroupType.PreGroup) {
            //获取并发组开始任务
            example.createCriteria()
                    .andFlowIdEqualTo(currentFlowTask.getFlowId())
                    .andGroupBetween((byte) (GroupType.PreGroup + 1), (byte) (GroupType.PostGroup - 1))
                    .andZoneEqualTo(currentFlowTask.getZone());
            list = flowTaskExtMapper.getConcurrentGroupStartTasks(example);
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
            //获取后置组开始任务
            example.clear();
            example.createCriteria()
                    .andFlowIdEqualTo(currentFlowTask.getFlowId())
                    .andGroupEqualTo(GroupType.PostGroup)
                    .andZoneEqualTo(currentFlowTask.getZone());
            example.setOrderByClause("priority");
            example.setLimitStart(0);
            example.setLimitEnd(1);
            list = flowTaskMapper.selectByExample(example);
            return list;
        }
        //当前组为并发
        if (currentGroup > GroupType.PreGroup
                && currentGroup < GroupType.PostGroup) {
            example.clear();
            example.createCriteria()
                    .andFlowIdEqualTo(currentFlowTask.getFlowId())
                    .andGroupEqualTo(GroupType.PostGroup)
                    .andZoneEqualTo(currentFlowTask.getZone());
            example.setOrderByClause("priority");
            example.setLimitStart(0);
            example.setLimitEnd(1);
            list = flowTaskMapper.selectByExample(example);
            return list;
        }
        return list;
    }

    @Override
    public List<FlowTask> getStartFlowTask(int flowId) {
        List<FlowTask> list;
        FlowTaskExample example = new FlowTaskExample();
        //获取准备组初始任务
        example.clear();
        example.createCriteria()
                .andFlowIdEqualTo(flowId)
                .andGroupEqualTo(GroupType.PrepareGroup);
        list = flowTaskExtMapper.getPreOrPostGroupStartTasks(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        }
        return getZoneStartFlowTask(flowId);
    }

    public List<FlowTask> getZoneStartFlowTask(int flowId) {

        Flow flow = flowMapper.selectByPrimaryKey(flowId);
        String[] zones = flow.getZones().split(",");

        List<FlowTask> list = null;
        FlowTaskExample example = new FlowTaskExample();
        //获取前置组初始任务
        example.clear();
        example.createCriteria()
                .andFlowIdEqualTo(flowId)
                .andGroupEqualTo(GroupType.PreGroup);
        list = flowTaskExtMapper.getPreOrPostGroupStartTasks(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        }
        //获取并发组
        for (String zone : zones) {
            example.clear();
            example.createCriteria()
                    .andFlowIdEqualTo(flowId)
                    .andZoneEqualTo(zone)
                    .andGroupBetween((byte) (GroupType.PreGroup + 1), (byte) (GroupType.PostGroup - 1));
            List<FlowTask> tmpList = flowTaskExtMapper.getConcurrentGroupStartTasks(example);
            if (CollectionUtils.isNotEmpty(tmpList)) {
                list.addAll(tmpList);
            }
        }
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        }
        //获取后置组
        example.clear();
        example.createCriteria()
                .andFlowIdEqualTo(flowId)
                .andGroupEqualTo(GroupType.PostGroup);
        list = flowTaskExtMapper.getPreOrPostGroupStartTasks(example);
        return list;
    }

    private boolean startFlow(int flowId) {
        try {
            List<FlowTask> flowTasks = getStartFlowTask(flowId);
            if (CollectionUtils.isEmpty(flowTasks)) {
                logger.error("StartFlow Error: none tasks for flow[id={}]", flowId);
                return false;
            }
            taskMsgSender.sendTasks(flowTasks);
            return true;
        } catch (Exception e) {
            logger.error("StartFlow Error:", e);
            return false;
        }
    }


    private boolean testInt(Integer value) {
        return (value != null && value > 0);
    }

    private MwGroupData getMwGroupData(int templateId) {
        List<Byte> groups = templateTaskExtMapper.distinctGroups(templateId);
        return AutoUtils.mwGroupData(groups);
    }

    @Override
    public Map<String, Object> getZoneFlowTaskInfo(Integer flowId, String zone) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //区域流程状态
        FlowTaskExample example = new FlowTaskExample();
        example.createCriteria()
                .andFlowIdEqualTo(flowId)
                .andZoneEqualTo(zone);
        List<Map<String, Object>> list = flowTaskExtMapper.countState(example);
        String flowState = calcFlowStatus(list);
        returnMap.put("state", flowState);
        //流程任务信息
        example.clear();
        example.createCriteria()
                .andFlowIdEqualTo(flowId)
                .andZoneEqualTo(zone);
        example.setOrderByClause("`group` ASC,priority ASC");
        List<FlowTask> flowTasks = flowTaskMapper.selectByExample(example);
        List<FlowTask> preGroupFlowTasks = new ArrayList<>();
        List<FlowTask> concurrentGroupFlowTasks = new ArrayList<>();
        List<FlowTask> postGroupFlowTasks = new ArrayList<>();
        for (FlowTask flowTask : flowTasks) {
            if (GroupType.PreGroup.byteValue() == flowTask.getGroup()) {
                preGroupFlowTasks.add(flowTask);
            } else if (GroupType.PostGroup.byteValue() == flowTask.getGroup()) {
                postGroupFlowTasks.add(flowTask);
            } else {
                concurrentGroupFlowTasks.add(flowTask);
            }
        }
        returnMap.put("pre", preGroupFlowTasks);
        returnMap.put("concurrent", concurrentGroupFlowTasks);
        returnMap.put("post", postGroupFlowTasks);
        return returnMap;
    }

    @Override
    public List<FlowTaskExtModel> getZoneFlowTaskInfoSimple(Integer flowId, String zone) {
        FlowTaskExample example = new FlowTaskExample();
        example.createCriteria()
                .andFlowIdEqualTo(flowId)
                .andZoneEqualTo(zone);
        example.setOrderByClause("`group` ASC,priority ASC");
        return flowTaskExtMapper.getFlowTaskExtByExample(example);
    }

    @Override
    public Map<String, String> getZonesState(Integer flowId) {
        Map<String, String> returnMap = new HashMap<String, String>();
        Flow flow = flowMapper.selectByPrimaryKey(flowId);
        String[] zones = flow.getZones().split(",");
        FlowTaskExample example = new FlowTaskExample();
        for (String zone : zones) {
            if (StringUtils.isBlank(zone)) continue;
            example.clear();
            example.createCriteria()
                    .andFlowIdEqualTo(flowId)
                    .andZoneEqualTo(zone);
            List<Map<String, Object>> list = flowTaskExtMapper.countState(example);
            if (CollectionUtils.isEmpty(list)) continue;
            String flowState = calcFlowStatus(list);
            returnMap.put(zone, flowState);
        }
        String localZoneState = getZoneState(flowId, localHost);
        if (StringUtils.isNotBlank(localZoneState)) returnMap.put(localHost, localZoneState);
        return returnMap;
    }

    @Override
    public String getZoneState(Integer flowId, String zone) {
        FlowTaskExample example = new FlowTaskExample();
        example.createCriteria()
                .andFlowIdEqualTo(flowId)
                .andZoneEqualTo(zone);
        List<Map<String, Object>> list = flowTaskExtMapper.countState(example);
        if (CollectionUtils.isEmpty(list)) return null;
        return calcFlowStatus(list);
    }

    public void executeFlowTask(Integer flowTaskId) {
        FlowTask flowTask = flowTaskMapper.selectByPrimaryKey(flowTaskId);
        taskMsgSender.sendTask(flowTask);
    }


    private String calcFlowStatus(List<Map<String, Object>> list) {
        Map<String, Integer> mapTmp = new HashMap<String, Integer>();
        for (Map<String, Object> o : list) {
            String state = o.get("state").toString();
            int count = Integer.parseInt(o.get("count").toString());
            if (count > 0) {
                mapTmp.put(state, count);
            }
        }
        String stateNew = TaskState.INIT.name();
        //成功
        if (testInt(mapTmp.get(TaskState.SUCCESS.name()))) {
            stateNew = TaskState.SUCCESS.name();
        }
        //初始化
        if (testInt(mapTmp.get(TaskState.INIT.name()))) {
            stateNew = TaskState.INIT.name();
        }
        if (testInt(mapTmp.get(TaskState.INIT.name())) && testInt(mapTmp.get(TaskState.SUCCESS.name()))) {
            stateNew = TaskState.ING.name();
        }
        //运行中
        if (testInt(mapTmp.get(TaskState.ING.name()))) {
            stateNew = TaskState.ING.name();
        }
        //定时
        if (testInt(mapTmp.get(TaskState.TIMER.name()))) {
            stateNew = TaskState.TIMER.name();
        }
        //手动
        if (testInt(mapTmp.get(TaskState.MANUAL.name()))) {
            stateNew = TaskState.MANUAL.name();
        }
        //失败
        if (testInt(mapTmp.get(TaskState.ERROR.name()))) {
            stateNew = TaskState.ERROR.name();
        }
        return stateNew;
    }

    @Override
    public BaseQueryResult<Flow> getFlows(FlowQueryContract req, Flow flow) {
        FlowExample example = new FlowExample();
        FlowExample.Criteria criteria = example.createCriteria();
        criteria.andProjectIdEqualTo(req.getProjectId());

        example.setOrderByClause("id DESC");

        if (req.getCreateDateS() != null) criteria.andCreateTimeGreaterThanOrEqualTo(req.getCreateDateS());
        if (req.getCreateDateE() != null) criteria.andCreateTimeLessThanOrEqualTo(req.getCreateDateE());
        if (req.getFlowId() != null) criteria.andIdEqualTo(req.getFlowId());
        if (StringUtils.isNotBlank(req.getZone())) criteria.andZonesLike(SqlUtils.wrapLike(req.getZone()));
        if (CollectionUtils.isNotEmpty(req.getState())) criteria.andStateIn(req.getState());
        criteria.andPidEqualTo(0);
        return BaseModel.selectByPage(flowMapper, example, req.getPage(), req.getPage() == null);
    }

    @Override
    public BaseQueryResult<Flow> getSubFlows(SubFlowQueryContract req, Flow flow) {
        FlowExample example = new FlowExample();
        FlowExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(req.getPid());
        criteria.andEnvEqualTo(req.getEnv());
        example.setOrderByClause("id DESC");

        if (req.getCreateDateS() != null) criteria.andCreateTimeGreaterThanOrEqualTo(req.getCreateDateS());
        if (req.getCreateDateE() != null) criteria.andCreateTimeLessThanOrEqualTo(req.getCreateDateE());
        if (req.getFlowId() != null) criteria.andIdEqualTo(req.getFlowId());
        if (StringUtils.isNotBlank(req.getZone())) criteria.andZonesLike(SqlUtils.wrapLike(req.getZone()));
        if (CollectionUtils.isNotEmpty(req.getState())) criteria.andStateIn(req.getState());
        return BaseModel.selectByPage(flowMapper, example, req.getPage(), req.getPage() == null);
    }

    @Override
    public Flow getSubFlow(Integer pid, Byte type, Byte env) {
        FlowExample example = new FlowExample();
        example.createCriteria().andPidEqualTo(pid)
                .andTypeEqualTo(type)
                .andEnvEqualTo(env);
        example.setOrderByClause("id DESC");
        List<Flow> subFlows = flowMapper.selectByExample(example);
        return CollectionUtils.isEmpty(subFlows) ? null : subFlows.get(0);
    }

    @Override
    public Flow getFlow(Integer flowId) {
        return flowMapper.selectByPrimaryKey(flowId);
    }

    @Override
    public List<Flow> getSubFlowList(Integer flowId) {
        FlowExample example = new FlowExample();
        example.createCriteria().andPidEqualTo(flowId);
        return flowMapper.selectByExample(example);
    }

    @Override
    public boolean updateFlowReview(Integer flowId, Byte isReview) {
        Flow flow = new Flow();
        flow.setId(flowId);
        flow.setIsreview(isReview);
        return flowMapper.updateByPrimaryKeySelective(flow) > 0;
    }

    @Override
    public boolean reviewFlow(Integer flowId, Byte isReview) {
        Flow flow = new Flow();
        flow.setId(flowId);
        flow.setIsreview(isReview);
        flow.setReviewdate(new Date());
        flow.setReviewer(AuthUtils.getCurrUserName());
        return flowMapper.updateByPrimaryKeySelective(flow) > 0;
    }

    @Override
    public List<Flow> getUserTopFlows(Integer userId) {
        List<AuthPermission> projects = projectService.getProjects4User(userId);
        List<Integer> pIds = new ArrayList<>(projects.size());
        if (CollectionUtils.isEmpty(projects)) return new ArrayList<>();
        projects.forEach(project -> pIds.add(project.getId()));
        FlowExample example = new FlowExample();
        example.createCriteria().andProjectIdIn(pIds);
        example.setOrderByClause("update_time DESC");
        example.setLimitStart(0);
        example.setLimitEnd(10);
        return flowMapper.selectByExample(example);
    }

    @Override
    public boolean rollBackFlow(Integer flowId) throws Exception {
        Flow flow = getFlow(flowId);
        Integer templateId = flow.getTemplateId();
        AutoTemplate subTemplate = templateManagerService.getSubTemplate(templateId);
        if (subTemplate == null) throw new Exception("模板[" + templateId + "]未配置回滚模板");
        Flow rollBackFlow = new Flow();
        rollBackFlow.setName(flow.getName() + "[" + flowId + "]-回滚");
        rollBackFlow.setTemplateId(subTemplate.getId());
        rollBackFlow.setProjectId(flow.getProjectId());
        rollBackFlow.setZones(flow.getZones());
        rollBackFlow.setParams("{\"version\":\"" + flowId + "\"}");
        rollBackFlow.setIsreview(subTemplate.getReview());
        rollBackFlow.setState(TaskState.INIT.name());
        rollBackFlow.setNeedbuild((byte) 1);
        rollBackFlow.setVcsBranch(flow.getVcsBranch());
        rollBackFlow.setCreator(AuthUtils.getCurrUserName());
        rollBackFlow.setCreateTime(new Date());
        return flowMapper.insertSelective(rollBackFlow) > 0;
    }


    @Override
    public Flow getLastFlow(Integer templateId, Integer projectId) {
        FlowExample example = new FlowExample();
        FlowExample.Criteria criteria = example.createCriteria();
        if (templateId != null) criteria.andTemplateIdEqualTo(templateId);
        if (projectId != null) criteria.andProjectIdEqualTo(projectId);
        example.setOrderByClause("id desc");
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<Flow> flows = flowMapper.selectByExample(example);
        return CollectionUtils.isEmpty(flows) ? null : flows.get(0);
    }

    @Override
    public Flow getLastExeFlow(Integer templateId, Integer projectId) {
        FlowExample example = new FlowExample();
        FlowExample.Criteria criteria = example.createCriteria();
        if (templateId != null) criteria.andTemplateIdEqualTo(templateId);
        if (projectId != null) criteria.andProjectIdEqualTo(projectId);
        example.setOrderByClause("update_time DESC");
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<Flow> flows = flowMapper.selectByExample(example);
        return CollectionUtils.isEmpty(flows) ? null : flows.get(0);
    }

    @Override
    public FlowTask getOneFlowTask(Integer flowId) {
        FlowTaskExample example = new FlowTaskExample();
        example.createCriteria().andFlowIdEqualTo(flowId);
        example.setLimitStart(0);
        example.setLimitEnd(1);
        List<FlowTask> flowTasks = flowTaskMapper.selectByExample(example);
        return CollectionUtils.isEmpty(flowTasks) ? null : flowTasks.get(0);
    }

    @Override
    public List<Flow> getAllLastSubFlow(Integer pid) {
        List<Flow> lastSubFlows = new ArrayList<>();
        Flow pFlow = getFlow(pid);
        byte flowStep = pFlow.getFlowStep();
        int stepState = pFlow.getStepState();
        for (int i = 0; i < 6; i++) {
            int tmpStep = 1 << i;
            if ((flowStep & tmpStep) > 0
                    && (stepState & (3 << (i*2))) > 0) {
                Flow subFlow = getLastSubFlow(pid,(byte)tmpStep);
                if (subFlow != null) lastSubFlows.add(subFlow);
            }
        }
        return lastSubFlows;
    }
    @Override
    public Flow getLastSubFlow(Integer pid, Byte flowStep) {
        FlowExample example = new FlowExample();
        example.createCriteria()
                .andPidEqualTo(pid)
                .andFlowStepEqualTo(flowStep);
        example.setOrderByClause("id DESC");
        List<Flow> subFlows = flowMapper.selectByExample(example);
        return CollectionUtils.isEmpty(subFlows) ? null : subFlows.get(0);
    }

    public static void main(String[] args) {
        /*
        String str = "i am /#p#/#p#";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("#p#", "tmp");
        paramMap.put("#vcs#", "tmp");


        Set<String> keySet = paramMap.keySet();
        for (String key : keySet) {
            str = str.replaceAll(key, paramMap.get(key));
        }
        System.out.println(str);


        System.out.println(String.format("sh /opt/auto/local/pullcode.sh -v %s -u %s -b %s -p", "s222", "s333", "b", "p"));
        */
//        String[] strs = "".split(",");
       /* Integer i = null;
        System.out.println(i > 0);*/
       /* Date exeDate = new Date();
        Date tmpDate = DateUtil.addMinutes(exeDate, 1);

        System.out.println("args = [" + tmpDate.after(new Date()) + "]");
*/

        /*int old = 0x01a;
        System.out.println(1<<6);*/
        /*String paramStr = "ea\tb\nc\rd";
        System.out.println(paramStr);
        paramStr=paramStr.replaceAll("[\\t\\n\\r]", " ");
        System.out.println(paramStr);*/
/*

        String str = "/op//123/wrol";
        System.out.println(str.replace("//", "/"));
*/

        System.out.println(1<<4);
    }
}
