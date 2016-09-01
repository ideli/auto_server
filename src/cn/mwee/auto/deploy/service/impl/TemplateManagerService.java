/**
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.service.impl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.mwee.auto.auth.util.AuthUtils;
import cn.mwee.auto.common.db.BaseModel;
import cn.mwee.auto.common.db.BaseQueryResult;
import cn.mwee.auto.common.util.DateUtil;
import cn.mwee.auto.deploy.dao.*;
import cn.mwee.auto.deploy.contract.template.QueryTemplatesRequest;
import cn.mwee.auto.deploy.contract.template.QueryTemplatesResult;
import cn.mwee.auto.deploy.model.*;

import static cn.mwee.auto.deploy.util.AutoConsts.*;

import cn.mwee.auto.deploy.service.ITaskManagerService;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cn.mwee.auto.deploy.contract.template.TemplateTaskContract;
import cn.mwee.auto.deploy.service.ITemplateManagerService;

/**
 * @author mengfanyuan
 *         2016年7月8日下午5:32:47
 */
@Service
public class TemplateManagerService implements ITemplateManagerService {

    @Autowired
    AutoTemplateMapper autoTemplateMapper;

    @Autowired
    TemplateTaskMapper templateTaskMapper;

    @Autowired
    private TemplateZoneMapper templateZoneMapper;

    @Autowired
    private ITaskManagerService taskManagerService;

    @Autowired
    private ZoneMapper zoneMapper;

    @Autowired
    private TemplateZoneExtMapper templateZoneExtMapper;

    @Autowired
    private TemplateZonesMonitorMapper templateZonesMonitorMapper;

    @Value(value = "${git.username}")
    private String gitUserName;

    @Value(value = "${git.password}")
    private String gitPassword;

    @Value(value = "${mw.monitor.shell}")
    private String defaultMonitorShell;

    @Value(value = "${mw.monitor.user}")
    private String defaultMonitorUser;

    @Override
    public AutoTemplate getTemplate(int templateId) {
        return autoTemplateMapper.selectByPrimaryKey(templateId);
    }

    @Override
    public boolean addTemplate(AutoTemplate template) {
        template.setCreateTime(new Date());
        template.setCreator(AuthUtils.getCurrUserName());
        return autoTemplateMapper.insertSelective(template) > 0;
    }

    @Override
    public boolean deleteTemplate(int templateId) {
        AutoTemplate template = new AutoTemplate();
        template.setUpdateTime(new Date());
        template.setId(templateId);
        template.setInuse(InUseType.NOT_USE);
        template.setOperater(AuthUtils.getCurrUserName());
        return autoTemplateMapper.updateByPrimaryKeySelective(template) > 0;
    }

    @Override
    public boolean modifyTemplate(AutoTemplate template) {
        template.setUpdateTime(new Date());
        template.setOperater(AuthUtils.getCurrUserName());
        return autoTemplateMapper.updateByPrimaryKeySelective(template) > 0;
    }

    @Override
    public boolean addTask2Template(int templateId, TemplateTask task) {
        task.setTemplateId(templateId);
        task.setCreateTime(new Date());
        task.setCreator(AuthUtils.getCurrUserName());
        return templateTaskMapper.insertSelective(task) > 0;
    }

    @Override
    public boolean removeTemplateTask(int templateTaskId) {
        TemplateTask task = new TemplateTask();
        task.setInuse(InUseType.NOT_USE);
        task.setId(templateTaskId);
        task.setOperater(AuthUtils.getCurrUserName());
        return templateTaskMapper.updateByPrimaryKeySelective(task) > 0;
    }

    @Override
    public boolean modifyTemplateTask(TemplateTask task) {
        task.setCreateTime(null);
        task.setUpdateTime(new Date());
        task.setOperater(AuthUtils.getCurrUserName());
        return templateTaskMapper.updateByPrimaryKeySelective(task) > 0;
    }

    @Override
    public QueryTemplatesResult getTemplates(QueryTemplatesRequest req) {
        AutoTemplateExample e = new AutoTemplateExample();
        AutoTemplateExample.Criteria c = e.createCriteria();

        c.andInuseEqualTo(InUseType.IN_USE);
        c.andPidEqualTo(0);
        e.setOrderByClause("id desc");

        if (req.getId() != null) {
            c.andIdEqualTo(req.getId());
        } else {
            Date start = DateUtil.parseDate(req.getCreateTimeS());

            Date end = DateUtil.parseDate(req.getCreateTimeE());

            if(req.getProjectId() != null)
            {
                c.andProjectIdEqualTo(req.getProjectId());
            }

            if (start != null) {
                c.andCreateTimeGreaterThanOrEqualTo(start);
            }
            if (end != null) {
                c.andCreateTimeLessThanOrEqualTo(end);
            }

            String templateName = req.getName();


            if (StringUtils.isNotBlank(templateName)) {
                c.andNameLike("%".concat(templateName).concat("%"));
            }

            Byte review = req.getReview();

            if (review != null) {
                c.andReviewEqualTo(review);
            }
        }

        QueryTemplatesResult rs = new QueryTemplatesResult();
        BaseQueryResult<AutoTemplate> qrs = BaseModel.selectByPage(autoTemplateMapper, e, req.getPage());

        rs.setList(qrs.getList());
        rs.setPage(qrs.getPage());

        return rs;
    }

    @Override
    public boolean addTask2RollbackTemplate(int templateId, TemplateTask task)
    {
        AutoTemplate rollbackTemplate = getSubTemplate(templateId);

        if(rollbackTemplate == null)
        {
            rollbackTemplate = createSubTemplate(templateId);
        }

        int rollbackId = rollbackTemplate.getId();

        task.setGroup((byte)1);

        return addTask2Template(rollbackId,task);
    }

    @Override
    public AutoTemplate getSubTemplate(int templateId)
    {
        // 判断是否已有子模板
        AutoTemplateExample example = new AutoTemplateExample();
        AutoTemplateExample.Criteria c = example.createCriteria();

        c.andPidEqualTo(templateId);

        List<AutoTemplate> templates = autoTemplateMapper.selectByExample(example);

        return templates.size() > 0 ? templates.get(0) : null;
    }

    @Override
    public AutoTemplate createSubTemplate(int templateId)
    {
        AutoTemplate autoTemplate = getTemplate(templateId);

        AutoTemplate rollbackTemplate = new AutoTemplate();

        rollbackTemplate.setPid(templateId);

        rollbackTemplate.setName("回滚-".concat(autoTemplate.getName()));

        rollbackTemplate.setProjectId(autoTemplate.getProjectId());

        rollbackTemplate.setCreateTime(new Date());

        rollbackTemplate.setCreator(AuthUtils.getCurrUserName());

        autoTemplateMapper.insertSelective(rollbackTemplate);

        return rollbackTemplate;
    }


    @Override
    public List<TemplateTask> getTemplateTasks(int templateId) {
        TemplateTaskExample example = new TemplateTaskExample();
        TemplateTaskExample.Criteria c = example.createCriteria();
        c.andTemplateIdEqualTo(templateId);
        c.andInuseEqualTo(InUseType.IN_USE);
        example.setOrderByClause("`group` ASC,priority ASC");
        return templateTaskMapper.selectByExample(example);
    }

    @Override
    public List<TemplateTask> getRollbackTemplateTasks(int templateId)
    {
        AutoTemplate rollbackTemplate = getSubTemplate(templateId);

        if(rollbackTemplate == null)
        {
            return Lists.newArrayList();
        }

        return getTemplateTasks(rollbackTemplate.getId());
    }


    @Override
    public List<Zone> getTemplateZones(Integer templateId) {
        TemplateZoneExample example = new TemplateZoneExample();
        example.createCriteria()
                .andTemplateIdEqualTo(templateId);
        List<TemplateZone> tzs = templateZoneMapper.selectByExample(example);
        List<Integer> zoneIds = new ArrayList<>();
        tzs.forEach(item -> zoneIds.add(item.getZoneId()));
        if (CollectionUtils.isEmpty(zoneIds)) return new ArrayList<>();

        ZoneExample zoneExample = new ZoneExample();
        zoneExample.createCriteria()
                .andIdIn(zoneIds);
        return zoneMapper.selectByExample(zoneExample);
    }

    @Override
    public List<AutoTask> getTemplateSimpleTasks(Integer templateId) {
        List<TemplateTask> list = getTemplateTasks(templateId);
        return getTasks4TemplateTaskList(list);
    }

    private List<AutoTask> getTasks4TemplateTaskList(List<TemplateTask> ttList) {
        if (CollectionUtils.isEmpty(ttList)) return new ArrayList<>();
        Set<Integer> taskIdSet = new HashSet<>();
        ttList.forEach(templateTask -> taskIdSet.add(templateTask.getTaskId()));
        return taskManagerService.getAutoTasksByIds(taskIdSet);
    }

    @Override
    public List<String> getTemplateTaskParamKeys(Integer templateId) {
        List<AutoTask> tasks = getTemplateSimpleTasks(templateId);
        Set<String> paramKeySet = new HashSet<>();
        for (AutoTask task : tasks) {
            String paramStr = task.getParams();
            if (StringUtils.isEmpty(paramStr)) continue;
            parseParamKeys(paramStr, paramKeySet);
            /*
            String[] params = paramStr.split(" ");
            for (String param : params) {
                if (StringUtils.isEmpty(param)) continue;
                if (param.startsWith("#") && param.endsWith("#")) {
                    paramKeySet.add(param.replace("#",""));
                }
            }*/
        }
        List<String> paramKeys = new ArrayList<>();
        paramKeys.addAll(paramKeySet);
        return paramKeys;
    }

    /**
     * 参数名解析
     *
     * @param paramStr    参数
     * @param paramKeySet 参数名set
     */
    private void parseParamKeys(String paramStr, Set<String> paramKeySet) {
        Pattern pattern = Pattern.compile("#(.*?)#");
        Matcher matcher = pattern.matcher(paramStr);
        while (matcher.find()) {
            String key = matcher.group();
            if (StringUtils.isNotBlank(key)) paramKeySet.add(key.replace("#", ""));
        }
    }

    @Override
    public VcsModel getGitRepInfo(AutoTemplate template) throws GitAPIException {
        String gitRepUrl = template.getVcsRep();
        VcsModel vcsModel = new VcsModel();
        if (StringUtils.isEmpty(gitRepUrl)) return vcsModel;

        List<String> gitBranches = new ArrayList<>();
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(gitUserName, gitPassword);
        Collection<Ref> refs = Git.lsRemoteRepository()
                .setHeads(true)
                .setTags(true)
                .setRemote(gitRepUrl)
                .setCredentialsProvider(credentialsProvider)
                .call();
        for (Ref ref : refs) {
            String refName = ref.getName();
            gitBranches.add(refName.substring(refName.lastIndexOf('/') + 1));
        }
        vcsModel.setType(template.getVcsType());
        vcsModel.setRepUrl(gitRepUrl);
        vcsModel.setBrachesNames(gitBranches);
        vcsModel.setProjectName(gitRepUrl.substring(gitRepUrl.lastIndexOf('/') + 1, gitRepUrl.lastIndexOf('.')));
        return vcsModel;
    }

    @Override
    public boolean addTemplateZone(TemplateZone templateZone) {
        templateZone.setCreateTime(new Date());

        return templateZoneMapper.insertSelective(templateZone) > 0;
    }

    @Override
    public boolean removeTemplateZone(int templateId, int zoneId) {
        TemplateZoneExample example = new TemplateZoneExample();
        TemplateZoneExample.Criteria c = example.createCriteria();
        c.andTemplateIdEqualTo(templateId);
        c.andZoneIdEqualTo(zoneId);
        return templateZoneMapper.deleteByExample(example) > 0;
    }


    @Override
    public List<AutoTemplate> getTemplates4Project(Integer projectId) {
        AutoTemplateExample example = new AutoTemplateExample();
        example.createCriteria()
                .andProjectIdEqualTo(projectId)
                .andPidEqualTo(0);
        return autoTemplateMapper.selectByExample(example);
    }

    @Override
    public List<ZoneStateModel> getTemplateZoneStatus(Integer templateId) {
        return templateZoneExtMapper.selectZoneState(templateId);
    }

    @Override
    public void cloneTemplate(Integer templateId) {
        AutoTemplate template = getTemplate(templateId);
        List<TemplateTask> ttList = getTemplateTasks(templateId);
        List<AutoTask> taskList = getTasks4TemplateTaskList(ttList);
        Integer newTemplateid = cloneTemplate(template);
        Map<Integer, Integer> taskIdMap = cloneTasks(taskList);
        cloneTemplateTasks(ttList, newTemplateid, taskIdMap);
    }

    private Integer cloneTemplate(AutoTemplate template) {
        AutoTemplate templateClone = new AutoTemplate();
        templateClone.setName(template.getName() + "-copy");
        templateClone.setProjectId(template.getProjectId());
        templateClone.setVcsType(template.getVcsType());
        templateClone.setVcsRep(template.getVcsRep());
        templateClone.setReview(template.getReview());
        templateClone.setCreator(AuthUtils.getCurrUserName());
        addTemplate(templateClone);
        return templateClone.getId();
    }


    private Map<Integer, Integer> cloneTasks(List<AutoTask> taskList) {
        Map<Integer, Integer> taskIdMap = new HashMap<>();
        if (CollectionUtils.isEmpty(taskList)) return taskIdMap;
        taskList.forEach(autoTask -> {
            AutoTask autoTaskClone = new AutoTask();
            autoTaskClone.setName(autoTask.getName() + "-copy");
            autoTaskClone.setExec(autoTask.getExec());
            autoTaskClone.setExecUser(autoTask.getExecUser());
            autoTaskClone.setExecZone(autoTask.getExecZone());
            autoTaskClone.setParams(autoTask.getParams());
            autoTaskClone.setDesc(autoTask.getDesc());
            autoTaskClone.setCreateTime(new Date());
            taskManagerService.addTask(autoTaskClone);
            taskIdMap.put(autoTask.getId(), autoTaskClone.getId());
        });
        return taskIdMap;
    }

    private void cloneTemplateTasks(List<TemplateTask> ttList, Integer newTemplateId, Map<Integer, Integer> taskIdMap) {
        if (CollectionUtils.isEmpty(ttList)) return;
        ttList.forEach(templateTask -> {
            TemplateTask templateTaskClone = new TemplateTask();
            templateTaskClone.setTemplateId(newTemplateId);
            templateTaskClone.setGroup(templateTask.getGroup());
            templateTaskClone.setPriority(templateTask.getPriority());
            templateTaskClone.setTaskId(taskIdMap.get(templateTask.getTaskId()));
            templateTaskClone.setTaskType(templateTask.getTaskType());
            templateTaskClone.setCreator(AuthUtils.getCurrUserName());
            addTask2Template(newTemplateId, templateTaskClone);
        });
    }


    @Override
    public boolean updateTemplateZoneStatus(Integer templateZoneId, String state) {
        TemplateZone templateZone = new TemplateZone();
        templateZone.setId(templateZoneId);
        templateZone.setState(state);
        templateZone.setUpdateTime(new Date());
        int result = templateZoneMapper.updateByPrimaryKeySelective(templateZone);
        return result > 0;
    }

    @Override
    public TemplateZonesMonitor getTemplateZoneMonitor(Integer templateId) {
        TemplateZonesMonitorExample example = new TemplateZonesMonitorExample();
        example.createCriteria().andTemplateidEqualTo(templateId);
        List<TemplateZonesMonitor> list = templateZonesMonitorMapper.selectByExample(example);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public List<AutoTemplate> getAllInUseTemplate() {
        AutoTemplateExample example = new AutoTemplateExample();
        example.createCriteria().andInuseEqualTo((byte) 1);
        return autoTemplateMapper.selectByExample(example);
    }

    @Override
    public List<AutoTemplate> getCanUseTemplate4Project(Integer projectId) {
        AutoTemplateExample example = new AutoTemplateExample();
        example.createCriteria()
                .andInuseEqualTo((byte) 1).andProjectIdEqualTo(projectId);
        example.or(example.createCriteria().andInuseEqualTo((byte) 1)
                .andProjectIdEqualTo(projectId));
        return autoTemplateMapper.selectByExample(example);
    }

    @Override
    public boolean addTemplateZoneMonitor(Integer templateId, Byte monitorType, String monitorParam, Byte inUse) {
        TemplateZonesMonitor monitor = new TemplateZonesMonitor();
        monitor.setTemplateid(templateId);
        monitor.setMonitorshell(defaultMonitorShell);
        monitor.setMonitoruser(defaultMonitorUser);
        monitor.setMonitorreq(buildMonitorReq(monitorType,monitorParam));
        monitor.setInuse(inUse);
        monitor.setMonitortype(monitorType);
        monitor.setMonitorparam(monitorParam);
        monitor.setCreatetime(new Date());
        return templateZonesMonitorMapper.insertSelective(monitor) > 0;
    }

    @Override
    public boolean updateTemplateZoneMonitor(Integer templateId, Byte monitorType, String monitorParam, Byte inUse) {
        TemplateZonesMonitor monitor = new TemplateZonesMonitor();
        monitor.setMonitorreq(buildMonitorReq(monitorType,monitorParam));
        monitor.setInuse(inUse);
        monitor.setMonitortype(monitorType);
        monitor.setMonitorparam(monitorParam);
        monitor.setUpdatetime(new Date());
        TemplateZonesMonitorExample example = new TemplateZonesMonitorExample();
        example.createCriteria().andTemplateidEqualTo(templateId);
        return templateZonesMonitorMapper.updateByExampleSelective(monitor,example) > 0;
    }

    private String buildMonitorReq(Byte monitorType, String param) {
        String monitorReq = "";
        switch (monitorType) {
            case MonitorType.MONITOR_URL:
                monitorReq = "-a %zone% -u "+param;
                break;
            case MonitorType.MONITOR_PORT:
                monitorReq = "-a %zone% -p "+param;
                break;
            case MonitorType.MONITOR_PROCESS:
                monitorReq = "-a %zone% -n "+param;
        }
        return monitorReq;
    }

    public static void main(String[] args) {
        String url = "http://git.9now.net:10080/devops/mw_auto.git";
        System.out.println(url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.')));
    }
}
