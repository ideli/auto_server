package cn.mwee.auto.deploy.controller;

import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;

/**
 * Created by huming on 16/7/21.
 */
public interface ITemplateController extends IController  {

    NormalReturn queryTemplates(ServiceRequest request);

    NormalReturn addTemplate(ServiceRequest request);

    NormalReturn getTemplate(ServiceRequest request);

    NormalReturn deleteTemplate(ServiceRequest request);

    NormalReturn modifyTemplate(ServiceRequest request);

    NormalReturn addTask2Template(ServiceRequest request);

    NormalReturn removeTemplateTask(ServiceRequest request);

    NormalReturn modifyTemplateTask(ServiceRequest request);

    /**
     * 获取模板任务信息
     * @return
     */
    NormalReturn getTemplateTasks(ServiceRequest request);

    /**
     * 获取流程模板详细信息
     * @return
     */
    NormalReturn getTemplateInfo(ServiceRequest request);

    /**
     * 获取模板详细信息
     * @return
     */
    NormalReturn getTemplateDetail(ServiceRequest request);


    NormalReturn addTemplateZone(ServiceRequest request);

    NormalReturn addBatchTemplateZone(ServiceRequest request);


    NormalReturn removeTemplateZone(ServiceRequest request);

    NormalReturn cloneTemplate(ServiceRequest request);

    NormalReturn addTempZoneMonitor(ServiceRequest request);

    NormalReturn updateTempZoneMonitor(ServiceRequest request);

}
