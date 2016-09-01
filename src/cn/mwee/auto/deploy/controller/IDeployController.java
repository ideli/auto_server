/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.controller;

import cn.mwee.auto.deploy.contract.ZoneStateContract;
import cn.mwee.auto.misc.aspect.contract.Contract;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;

/**
 * @author mengfanyuan
 * 2016年7月6日下午5:24:27
 */
public interface IDeployController extends IController {
	/**
	 * 新增流程
	 * @param request
	 * @return
	 */
	NormalReturn addFlow(ServiceRequest request);
	/**
	 * 执行流程
	 * @param request
	 * @return
	 */
	NormalReturn startFlow(ServiceRequest request);
	/**
	 * 流程区域状态
	 * @param request
	 * @return
	 */
	NormalReturn zonesState(ServiceRequest request);
	/**
	 * 区域任务信息列表
	 * @param request
	 * @return
	 */
	NormalReturn zonesTasksInfo(ServiceRequest request);
	/**
	 * 区域任务信息列表
	 * @param request
	 * @return
	 */
	NormalReturn zonesTasksInfoSimple(ServiceRequest request);
	/**
	 * 手动执行任务
	 * @param request
	 * @return
	 */
	NormalReturn executeFlowTask(ServiceRequest request);
	/**
	 * git仓库分支信息
	 * @param request
	 * @return
	 */
	NormalReturn getGitBranches(ServiceRequest request);
	/**
	 * 流程列表
	 * @param request
	 * @return
	 */
	NormalReturn getFlows(ServiceRequest request);
	/**
	 * 流程任务日志
	 * @param request
	 * @return
	 */
	NormalReturn getFolwTaskLog(ServiceRequest request);

    /**
     * 区域日志
     * @param request
     * @return
     */
	@Contract(ZoneStateContract.class)
	NormalReturn getZoneLogs(ServiceRequest request);

	/**
	 * 模板任务列表
	 * @param request
	 * @return
	 */
	NormalReturn getTemplateTasks(ServiceRequest request);

    /**
     * 流程审核
     * @param request
     * @return
     */
    NormalReturn reviewFlow(ServiceRequest request);

    /**
     * 获取流程信息
     * @param request
     * @return
     */
    NormalReturn getFlowInfo(ServiceRequest request);

	/**
	 * 获取流程信息
	 * @param request
	 * @return
	 */
	NormalReturn getUserTopFlows(ServiceRequest request);

	/**
	 * 回滚到当前流程
	 * @param request
	 * @return
	 */
	NormalReturn rollBackFlow(ServiceRequest request);

}
