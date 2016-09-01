package cn.mwee.auto.deploy.service.impl;

import cn.mwee.auto.auth.dao.AuthPermissionMapper;
import cn.mwee.auto.auth.dao.ProjectUserMapper;
import cn.mwee.auto.auth.model.*;
import cn.mwee.auto.auth.service.IPermissionService;
import cn.mwee.auto.auth.util.AuthUtils;
import cn.mwee.auto.auth.util.SqlUtils;
import cn.mwee.auto.common.db.BaseModel;
import cn.mwee.auto.common.db.BaseQueryResult;
import cn.mwee.auto.deploy.contract.project.ProjectQueryContract;
import cn.mwee.auto.deploy.dao.ProjectUserExtMapper;
import cn.mwee.auto.deploy.model.AutoTemplate;
import cn.mwee.auto.deploy.model.Model4Sel;
import cn.mwee.auto.deploy.service.IProjectService;
import cn.mwee.auto.deploy.service.ITemplateManagerService;
import cn.mwee.auto.deploy.util.AutoConsts.PermConst;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2016/8/10.
 */
@Service
public class ProjectService implements IProjectService {

    @Resource
    private ITemplateManagerService templateManagerService;

    @Autowired
    private ProjectUserMapper projectUserMapper;

    @Autowired
    private ProjectUserExtMapper projectUserExtMapper;

    @Resource
    private IPermissionService permissionService;

    @Autowired
    private AuthPermissionMapper authPermissionMapper;

    @Override
    public boolean addProject(String projectName, String projectUrl, String desc) throws Exception {
        if (permissionService.selectByName(projectName, true) != null)
            throw new Exception("project name[" + projectName + "] is exist already");
        AuthPermission authPermission = new AuthPermission();
        authPermission.setParentId(-1);
        authPermission.setName(projectName);
        authPermission.setCode(projectUrl);
        authPermission.setLevel((byte) 1);
        authPermission.setType((byte) 1);
        authPermission.setDescription(desc);
        authPermission.setIsproject(true);
        if (permissionService.add(authPermission)) {
            addDefaultProjectMenu(authPermission.getId());
            return true;
        }
        return false;
    }

    /**
     * 增加项目下的流程列表菜单和服务器状态菜单
     * @param projectId
     */
    private void addDefaultProjectMenu(Integer projectId) {
        AuthPermission flowListMenu = new AuthPermission();
        flowListMenu.setParentId(projectId);
        flowListMenu.setName("流程列表");
        flowListMenu.setCode("/pages/flow_list.html?projectId="+projectId);
        flowListMenu.setLevel((byte) 2);
        flowListMenu.setType((byte) 1);
        flowListMenu.setDescription("项目["+projectId+"]流程列表菜单");
        flowListMenu.setIsproject(true);
        if (permissionService.add(flowListMenu)) {
            addDefaultBtn(flowListMenu.getId(),"流程审核","flowReview-"+projectId);
            addDefaultBtn(flowListMenu.getId(),"流程回滚","flowRollBack-"+projectId);
        }
        AuthPermission zoneStatusMenu = new AuthPermission();
        zoneStatusMenu.setParentId(projectId);
        zoneStatusMenu.setName("服务器状态");
        zoneStatusMenu.setCode("/pages/monitor/zones.html?projectId="+projectId);
        zoneStatusMenu.setLevel((byte) 2);
        zoneStatusMenu.setType((byte) 1);
        zoneStatusMenu.setDescription("项目["+projectId+"]服务状态信息菜单");
        zoneStatusMenu.setIsproject(true);
        permissionService.add(zoneStatusMenu);
    }

    @Override
    public AuthPermission getProject(Integer projectId) {
        return permissionService.select(projectId);
    }

    @Override
    public boolean updateProject(Integer projectId, String projectName, String projectUrl, String desc) throws Exception {
        if (isExist(projectName)) throw new Exception("project name [" + projectName + "] exist already");
        AuthPermission authPermission = new AuthPermission();
        authPermission.setId(projectId);
        authPermission.setName(projectName);
        authPermission.setCode(projectUrl);
        authPermission.setDescription(desc);
        return permissionService.update(authPermission);
    }

    /**
     * 项目名是否已存在
     *
     * @return
     */
    private boolean isExist(String projectName) {
        return permissionService.selectByName(projectName, true) != null;
    }

    @Override
    public BaseQueryResult<AuthPermission> getProjectPages(ProjectQueryContract queryContract) {
        AuthPermissionExample example = new AuthPermissionExample();
        AuthPermissionExample.Criteria criteria = example.createCriteria();
        criteria.andIsprojectEqualTo(true)
                .andLevelEqualTo((byte) 1)
                .andTypeEqualTo((byte) 1);
        if (StringUtils.isNotBlank(queryContract.getProjectName()))
            criteria.andNameLike(SqlUtils.wrapLike(queryContract.getProjectName()));
        example.setOrderByClause("id desc");
        return BaseModel.selectByPage(authPermissionMapper, example, queryContract.getPage(), queryContract.getPage() == null);
    }

    @Override
    public List<AuthPermission> getProjectMenus(Integer projectId) {
        AuthPermissionExample example = new AuthPermissionExample();
        example.createCriteria().andParentIdEqualTo(projectId)
                .andIsprojectEqualTo(true);
        return authPermissionMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, Object>> getProjectZonesStatus(Integer projectId) {
        List<AutoTemplate> templates = templateManagerService.getTemplates4Project(projectId);
        if (CollectionUtils.isEmpty(templates)) return new ArrayList<>();
        List<Map<String, Object>> resultList = new ArrayList<>(templates.size());
        templates.forEach(template -> {
            Map<String, Object> map = new HashMap<>();
            map.put("template", template);
            map.put("zoneStates", templateManagerService.getTemplateZoneStatus(template.getId()));
            resultList.add(map);
        });
        return resultList;
    }

    @Override
    public List<AuthUser> getUsers4Project(Integer projectId) {
        return projectUserExtMapper.selectUsers4Project(projectId);
    }

    @Override
    public List<AuthPermission> getProjects4User(Integer userId) {
        return projectUserExtMapper.selectProjects4User(userId);
    }

    @Override
    public List<ProjectUserExtModel> getProjectUsers(Integer projectId) {
        return projectUserExtMapper.selectProjectUsers(projectId);
    }

    @Override
    public boolean addProjectUsers(Integer projectId, Integer[] userIds) {
        Stream.of(userIds).parallel().forEach(userId -> {
            ProjectUserExample example = new ProjectUserExample();
            example.createCriteria().andProjectidEqualTo(projectId).andUseridEqualTo(userId);
            if (!(projectUserMapper.countByExample(example) > 0)) {
                ProjectUser projectUser = new ProjectUser();
                projectUser.setProjectid(projectId);
                projectUser.setUserid(userId);
                projectUser.setCreator(AuthUtils.getCurrUserName());
                projectUser.setCreatetime(new Date());
                projectUser.setUsertype("DEV");
                projectUserMapper.insertSelective(projectUser);
            }
        });
        return true;
    }

    @Override
    public boolean delProjectUsers(Integer projectUserId) {
        return  projectUserMapper.deleteByPrimaryKey(projectUserId) > 0;
    }

    @Override
    public boolean addProjectMenu(Integer projectId,Integer templateId, String menuName, String menuUrl, boolean isDeploy, String desc) {
        AuthPermission projectMenu = new AuthPermission();
        projectMenu.setParentId(projectId);
        projectMenu.setName(menuName);
        projectMenu.setCode(menuUrl);
        projectMenu.setLevel((byte) 2);
        projectMenu.setType((byte) 1);
        projectMenu.setDescription(desc);
        projectMenu.setIsproject(true);
        boolean result = permissionService.add(projectMenu);
        if (result && isDeploy) {
            addDefaultBtn(projectMenu.getId(),menuName+"-流程动态ip添加",
                    "addFlowZone"+projectId+"-"+templateId);
        }
        return result;
    }

    private boolean addDefaultBtn(Integer parentId, String name, String code) {
        AuthPermission defaultBtn = new AuthPermission();
        defaultBtn.setParentId(parentId);
        defaultBtn.setName(name);
        defaultBtn.setCode(code);
        defaultBtn.setLevel((byte) 2);
        defaultBtn.setType(PermConst.TYPE_BTN);
        defaultBtn.setDescription(name+"按钮权限");
        defaultBtn.setIsproject(true);
        return permissionService.add(defaultBtn);
    }

    @Override
    public AuthPermission getProjectMenu(Integer menuId) {
        return permissionService.select(menuId);
    }

    @Override
    public boolean delProjectMenu(Integer menuId) throws Exception {
        return permissionService.delete(menuId);
    }

    @Override
    public boolean updateProjectMenu(Integer menuId, String menuName, String menuUrl, String desc) {
        AuthPermission projectMenu = new AuthPermission();
        projectMenu.setId(menuId);
        projectMenu.setName(menuName);
        projectMenu.setCode(menuUrl);
        projectMenu.setDescription(desc);
        return permissionService.update(projectMenu);
    }

    @Override
    public List<Model4Sel> queryProjects4Sel(String param) {
        AuthPermissionExample example = new AuthPermissionExample();
        AuthPermissionExample.Criteria criteria = example.createCriteria();
        criteria.andIsprojectEqualTo(true)
                .andLevelEqualTo((byte) 1)
                .andTypeEqualTo((byte) 1);
        if (StringUtils.isNotBlank(param))
            criteria.andNameLike(SqlUtils.wrapLike(param));
        List<AuthPermission> list = authPermissionMapper.selectByExample(example);
        List<Model4Sel> result = new ArrayList<>(list.size());
        list.forEach(project -> {
            Model4Sel model4Sel = new Model4Sel();
            model4Sel.setId(project.getId());
            model4Sel.setText(project.getName());
            result.add(model4Sel);
        });
        return result;
    }
}
