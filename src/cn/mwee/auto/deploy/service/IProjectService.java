package cn.mwee.auto.deploy.service;

import cn.mwee.auto.auth.model.AuthPermission;
import cn.mwee.auto.auth.model.AuthUser;
import cn.mwee.auto.auth.model.ProjectUserExtModel;
import cn.mwee.auto.common.db.BaseQueryResult;
import cn.mwee.auto.deploy.contract.project.ProjectQueryContract;
import cn.mwee.auto.deploy.model.Model4Sel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface IProjectService {

    /**
     * 新增醒目
     * @param projectName 项目名称
     * @param projectUrl 项目url
     * @param desc 项目描述
     * @return 结果
     */
    boolean addProject(String projectName, String projectUrl, String desc ) throws Exception;

    /**
     * 查询项目信息
     * @param projectId 项目Id
     * @return
     */
    AuthPermission getProject(Integer projectId);

    /**
     * 更新项目
     * @param projectId 项目Id
     * @param projectName 项目名称
     * @param projectUrl 项目url地址
     * @param desc 项目描述
     * @return 更新结果
     */
    boolean updateProject(Integer projectId, String projectName, String projectUrl, String desc) throws Exception;

    /**
     * 项目查询
     * @param queryContract query params
     * @return
     */
    BaseQueryResult<AuthPermission> getProjectPages(ProjectQueryContract queryContract);


    /**
     * 项目菜单列表
     * @param projectId
     * @return
     */
    List<AuthPermission> getProjectMenus(Integer projectId);

    /**
     * 获取项目所有模板中的区域服务状态
     * @return
     */
    List<Map<String,Object>> getProjectZonesStatus(Integer projectId);

    /**
     * 获取项目成员
     * @param projectId
     * @return
     */
    List<AuthUser> getUsers4Project(Integer projectId);

    /**
     * 获取用户项目
     * @param userId
     * @return
     */
    List<AuthPermission> getProjects4User(Integer userId);

    /**
     * 获取项目下的成员列表
     * @param projectId 项目Id
     * @return
     */
    List<ProjectUserExtModel> getProjectUsers(Integer projectId);

    /**
     * 新增项目成员
     * @param projectId 项目Id
     * @return
     */
    boolean addProjectUsers(Integer projectId, Integer[] userIds);

    /**
     *  删除项目成员
     * @param projectUserId 项目成员Id
     * @return
     */
    boolean delProjectUsers(Integer projectUserId);

    /**
     * 新增项目子菜单
     * @param projectId 项目Id
     * @return
     */
    boolean addProjectMenu(Integer projectId, Integer templateId, String menuName,String menuUrl,  boolean isDeploy, String desc);

    /**
     * 查看项目子菜单
     * @param menuId 菜单Id
     * @return
     */
    AuthPermission getProjectMenu(Integer menuId);

    /**
     * 删除项目子菜单
     * @param menuId 菜单Id
     * @return
     */
    boolean delProjectMenu(Integer menuId) throws Exception;

    /**
     * 修改项目子菜单
     * @param menuId 菜单Id
     * @param menuName 菜单标题
     * @param menuUrl 菜单url
     * @param desc 描述
     * @return
     */
    boolean updateProjectMenu(Integer menuId, String menuName,String menuUrl,String desc);

    List<Model4Sel> queryProjects4Sel(String param);

}
