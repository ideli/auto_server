package cn.mwee.auto.deploy.service;

import java.util.List;
import cn.mwee.auto.deploy.contract.template.QueryTemplatesRequest;
import cn.mwee.auto.deploy.contract.template.QueryTemplatesResult;
import cn.mwee.auto.deploy.model.*;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * Created by huming on 16/6/24.
 */
public interface ITemplateManagerService {

    AutoTemplate getTemplate(int templateId);

    boolean addTemplate(AutoTemplate template);

    boolean deleteTemplate(int templateId);

    boolean modifyTemplate(AutoTemplate template);

    /**
     * 获取模板
     * @return
     */
    QueryTemplatesResult getTemplates(QueryTemplatesRequest request);

    boolean addTask2RollbackTemplate(int templateId,TemplateTask task);

    boolean addTask2Template(int templateId,TemplateTask task);

    AutoTemplate getSubTemplate(int templateId);

    AutoTemplate createSubTemplate(int templateId);

    boolean removeTemplateTask(int templateTaskId);

    boolean modifyTemplateTask(TemplateTask task);

	/**
	 * 获取模板任务信息
	 * @return
	 */
	List<TemplateTask> getTemplateTasks(int templateId);


    /**
     * 获取-回滚模板-任务信息
     * @return
     */
    List<TemplateTask> getRollbackTemplateTasks(int templateId);

    /**
     * 获取模板区域信息
     * @param templateId
     * @return
     */
    List<Zone> getTemplateZones(Integer templateId);

    /**
     * 获取模板中的任务
     * @param templateId
     * @return
     */
    List<AutoTask> getTemplateSimpleTasks(Integer templateId);

    /**
     * 获取模板任务中的参数可以列表
     * @param templateId
     * @return
     */
    List<String> getTemplateTaskParamKeys(Integer templateId);

    /**
     * git分支信息
     * @param template
     * @return
     */
    VcsModel getGitRepInfo(AutoTemplate template) throws GitAPIException;


    boolean addTemplateZone(TemplateZone templateZone);

    boolean removeTemplateZone(int templateId,int zoneId);

    /**
     * 获取项目模板
     * @param projectId
     * @return
     */
    List<AutoTemplate> getTemplates4Project(Integer projectId);

    /**
     * 获取模板下区域主机状态
     * @param templateId
     * @return
     */
    List<ZoneStateModel> getTemplateZoneStatus(Integer templateId);

    /**
     * 克隆模板
     */
    void cloneTemplate(Integer templateId);

    /**
     * 更新模板区域状态
     * @param templateZoneId
     * @return
     */
    boolean updateTemplateZoneStatus(Integer templateZoneId, String state);

    /**
     * 获取模板监控
     * @param templateId
     * @return
     */
    TemplateZonesMonitor getTemplateZoneMonitor(Integer templateId);

    /**
     * 获取使用中的模板
     * @return
     */
    List<AutoTemplate> getAllInUseTemplate();

    /**
     * 获取项目可以使用模板
     * @param projectId
     * @return
     */
    List<AutoTemplate> getCanUseTemplate4Project(Integer projectId);

    /**
     * 新增模板区域监控配置
     * @param templateId
     * @return
     */
    boolean addTemplateZoneMonitor(Integer templateId,Byte monitorType,String monitorParam,Byte inUse);

    /**
     * 更新模板区域监控配置
     * @param templateId
     * @return
     */
    boolean updateTemplateZoneMonitor(Integer templateId,Byte monitorType,String monitorParam,Byte inUse);
}
