package cn.mwee.auto.deploy.controller.impl;

import cn.mwee.auto.auth.contract.permission.PermissionAddContract;
import cn.mwee.auto.auth.model.AuthPermission;
import cn.mwee.auto.deploy.contract.commom.SelQueryParamContract;
import cn.mwee.auto.deploy.contract.project.*;
import cn.mwee.auto.deploy.controller.IProjectController;
import cn.mwee.auto.deploy.service.IProjectService;
import cn.mwee.auto.deploy.service.ITemplateManagerService;
import cn.mwee.auto.misc.aspect.contract.Contract;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/8/10.
 */
@Controller
public class ProjectController implements IProjectController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Resource
    private IProjectService projectService;

    @Resource
    private ITemplateManagerService templateManagerService;

    @Override
    @RequiresPermissions(value = "/project/addProject", logical = Logical.OR)
    @Contract(ProjectAddContract.class)
    public NormalReturn addProject(ServiceRequest request) {
        ProjectAddContract req = request.getContract();
        try {
            projectService.addProject(req.getProjectName(), "#", req.getDescription());
            return new NormalReturn("success");
        } catch (Exception e) {
            logger.error("", e);
            return new NormalReturn("500", e.getMessage());
        }
    }

    @Override
    @Contract(ProjectIdContract.class)
    public NormalReturn getProject(ServiceRequest request) {
        ProjectIdContract req = request.getContract();
        return new NormalReturn(projectService.getProject(req.getProjectId()));
    }

    @Override
    @Contract(ProjectUpdateContract.class)
    public NormalReturn updateProject(ServiceRequest request) {
        ProjectUpdateContract req = request.getContract();
        try {
            projectService.updateProject(req.getProjectId(),req.getProjectName(),"#",req.getDescription());
            return new NormalReturn("success");
        } catch (Exception e) {
            logger.error("", e);
            return new NormalReturn("500", e.getMessage());
        }
    }

    @Override
    @Contract(ProjectQueryContract.class)
    public NormalReturn queryProjects(ServiceRequest request) {
        ProjectQueryContract req = request.getContract();
        try {
            return new NormalReturn(projectService.getProjectPages(req));
        } catch (Exception e) {
            logger.error("", e);
            return new NormalReturn("500", e.getMessage());
        }
    }

    @Override
    @Contract(ProjectUserAddContract.class)
    public NormalReturn projectUserAdd(ServiceRequest request) {
        ProjectUserAddContract req = request.getContract();
        projectService.addProjectUsers(req.getProjectId(),req.getUserIds());
        return new NormalReturn();
    }

    @Override
    @Contract(ProjectUserIdContract.class)
    public NormalReturn projectUserDel(ServiceRequest request) {
        ProjectUserIdContract req = request.getContract();
        projectService.delProjectUsers(req.getProjectUserId());
        return new NormalReturn();
    }

    @Override
    @Contract(ProjectIdContract.class)
    public NormalReturn getProjectUsers(ServiceRequest request) {
        ProjectIdContract req = request.getContract();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("projectInfo",projectService.getProject(req.getProjectId()));
        resultMap.put("userList",projectService.getProjectUsers(req.getProjectId()));
        return new NormalReturn(resultMap);
    }

    @Override
    @Contract(ProjectIdContract.class)
    public NormalReturn getProjectMenus(ServiceRequest request) {
        ProjectIdContract req = request.getContract();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("projectInfo",projectService.getProject(req.getProjectId()));
        resultMap.put("menuList",projectService.getProjectMenus(req.getProjectId()));
        return new NormalReturn(resultMap);
    }

    @Override
    @Contract(ProjectMenuIdContract.class)
    public NormalReturn getProjectMenu(ServiceRequest request) {
        ProjectMenuIdContract req = request.getContract();
        projectService.getProjectMenu(req.getMenuId());
        return new NormalReturn(projectService.getProjectMenu(req.getMenuId()));
    }

    @Override
    @Contract(ProjectMenuAddContract.class)
    public NormalReturn projectMenuAdd(ServiceRequest request) {
        ProjectMenuAddContract req = request.getContract();
        projectService.addProjectMenu(req.getProjectId(),req.getTemplateId(),
                req.getMenuName(),req.getMenuUrl(),req.getTemplateId()!=null,req.getDesc());
        return new NormalReturn();
    }

    @Override
    @Contract(ProjectMenuUpdateContract.class)
    public NormalReturn projectMenuUpdate(ServiceRequest request) {
        ProjectMenuUpdateContract req = request.getContract();
        projectService.updateProjectMenu(req.getMenuId(),req.getMenuName(),req.getMenuUrl(),req.getDesc());
        return new NormalReturn();
    }

    @Override
    @Contract(ProjectMenuIdContract.class)
    public NormalReturn projectMenuDel(ServiceRequest request) {
        ProjectMenuIdContract req = request.getContract();
        try {
            projectService.delProjectMenu(req.getMenuId());
        } catch (Exception e) {
            return new NormalReturn("500", e.getMessage());
        }
        return new NormalReturn();
    }

    @Override
    @Contract(ProjectIdContract.class)
    public NormalReturn getCanUseTemplate(ServiceRequest request) {
        ProjectIdContract req = request.getContract();
        return new NormalReturn(templateManagerService.getCanUseTemplate4Project(req.getProjectId()));
    }

    @Override
    @Contract(ProjectIdContract.class)
    public NormalReturn getProjectZonesStatus(ServiceRequest request) {
        ProjectIdContract req = request.getContract();
        return new NormalReturn(projectService.getProjectZonesStatus(req.getProjectId()));
    }

    @Override
    @Contract(SelQueryParamContract.class)
    public NormalReturn queryProjects4Sel(ServiceRequest request) {
        SelQueryParamContract req = request.getContract();
        return new NormalReturn(projectService.queryProjects4Sel(req.getQueryParam()));
    }
}
