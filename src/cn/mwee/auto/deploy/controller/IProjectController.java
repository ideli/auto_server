package cn.mwee.auto.deploy.controller;

import cn.mwee.auto.deploy.contract.project.ProjectAddContract;
import cn.mwee.auto.deploy.contract.template.QueryTemplatesRequest;
import cn.mwee.auto.misc.aspect.contract.Contract;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface IProjectController extends IController {

    /**
     * 新增项目
     * @param request 请求
     * @return 响应
     */
    NormalReturn addProject(ServiceRequest request);

    /**
     * 项目信息
     * @param request 请求
     * @return 响应
     */
    NormalReturn getProject(ServiceRequest request);

    /**
     * 修改项目
     * @param request 请求
     * @return 响应
     */
    NormalReturn updateProject(ServiceRequest request);

    /**
     * 项目列表
     * @param request 请求
     * @return 响应
     */
    NormalReturn queryProjects(ServiceRequest request);

    /**
     * 新增项目成员
     * @param request 请求
     * @return 响应
     */
    NormalReturn projectUserAdd(ServiceRequest request);

    /**
     * 删除项目成员
     * @param request 请求
     * @return 响应
     */
    NormalReturn projectUserDel(ServiceRequest request);

    /**
     * 项目成员列表
     * @param request 请求
     * @return 响应
     */
    NormalReturn getProjectUsers(ServiceRequest request);

    /**
     * 项目菜单列表
     * @param request 请求
     * @return 响应
     */
    NormalReturn getProjectMenus(ServiceRequest request);

    /**
     * 项目菜单列表
     * @param request 请求
     * @return 响应
     */
    NormalReturn getProjectMenu(ServiceRequest request);

    /**
     * 新增项目菜单
     * @return
     */
    NormalReturn projectMenuAdd(ServiceRequest request);

    /**
     * 修改项目子菜单
     * @param request
     * @return
     */
    NormalReturn projectMenuUpdate(ServiceRequest request);

    /**
     * 删除项目子菜单
     * @param request
     * @return
     */
    NormalReturn projectMenuDel(ServiceRequest request);

    /**
     * 获取项目可用模板
     * @param request
     * @return
     */
    NormalReturn getCanUseTemplate(ServiceRequest request);

    /**
     * 获取项目所有模板中的区域服务状态
     * @return
     */
    NormalReturn getProjectZonesStatus(ServiceRequest request);

    /**
     * 获取项目所有模板中的区域服务状态
     * @return
     */
    NormalReturn queryProjects4Sel(ServiceRequest request);

}
