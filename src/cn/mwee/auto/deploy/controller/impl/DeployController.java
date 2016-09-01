 /**
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.controller.impl;

import java.util.*;

import cn.mwee.auto.auth.model.AuthUser;
import cn.mwee.auto.auth.util.AuthUtils;
import cn.mwee.auto.deploy.contract.*;
import cn.mwee.auto.deploy.contract.commom.BaseContract;
import cn.mwee.auto.deploy.contract.flow.*;
import cn.mwee.auto.deploy.model.*;
import cn.mwee.auto.deploy.service.ITaskManagerService;
import cn.mwee.auto.misc.aspect.contract.Model;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.mwee.auto.deploy.contract.template.TemplateTaskContract;
import cn.mwee.auto.deploy.controller.IDeployController;
import cn.mwee.auto.deploy.service.IFlowManagerService;
import cn.mwee.auto.deploy.service.IFlowTaskLogService;
import cn.mwee.auto.deploy.service.ITemplateManagerService;
import cn.mwee.auto.misc.aspect.contract.Contract;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;

/**
 * @author mengfanyuan
 * 2016年7月6日下午5:43:37
 */
@Controller
public class DeployController implements IDeployController {
	private static final Logger logger = LoggerFactory.getLogger(DeployController.class);
	@Autowired
	private IFlowManagerService flowManagerService;

	@Autowired
	private IFlowTaskLogService flowTaskLogService;
	
	@Autowired
	private ITemplateManagerService templateManagerService;

	@Autowired
	private ITaskManagerService taskManagerService;

	@Override
	@Contract(FlowAddContract.class)
	public NormalReturn addFlow(ServiceRequest request) {
		FlowAddContract req = request.getContract();
		try {
			
			int flowId = flowManagerService.createFlow(req);
			if (flowId > 0) {
                if (req.getExeNow() == 1) {
                    flowManagerService.executeFlow(flowId);
                }
				return new NormalReturn("200", "success",flowId);
			} else {
				return new NormalReturn("500", "error","error");
			}
		} catch (Exception e) {
			logger.error("addFlow error:", e);
			return new NormalReturn("500", e.getMessage(),"error");
		}
	}

	@Override
	@Contract(FlowStartContract.class)
	public NormalReturn startFlow(ServiceRequest request) {
		FlowStartContract req = request.getContract();
		try {
			
			if (flowManagerService.executeFlow(req.getFlowId())) {
				return new NormalReturn("200", "success", "success");
			} else {
				return new NormalReturn("500", "error", "error");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			return new NormalReturn("500",e.getMessage(), "error");
		}
		
	}

	@Override
	@Contract(GitBrancheContract.class)
	public NormalReturn getGitBranches(ServiceRequest request) {
		// TODO Auto-generated method stub
		GitBrancheContract req = request.getContract();
		try {
            AutoTemplate template = new AutoTemplate();
            template.setVcsType("git");
            template.setVcsRep(req.getGitRepUrl());
			return new NormalReturn("200","success", templateManagerService.getGitRepInfo(template));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			return new NormalReturn("500",e.getMessage(), "error");
		}
	}
	


	@Override
	@Contract(FlowStartContract.class)
	public NormalReturn zonesState(ServiceRequest request) {
		FlowStartContract req = request.getContract();
		try {
			return new NormalReturn("200","success", flowManagerService.getZonesState(req.getFlowId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			return new NormalReturn("500",e.getMessage(), "error");
		}
	}

	@Override
	@Contract(ZoneStateContract.class)
	public NormalReturn zonesTasksInfo(ServiceRequest request) {
		ZoneStateContract req = request.getContract();
		try {
			return new NormalReturn("200","success", flowManagerService.getZoneFlowTaskInfo(req.getFlowId(), req.getZone()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			return new NormalReturn("500",e.getMessage(), "error");
		}
	}

	@Override
	@Contract(ZoneStateContract.class)
	public NormalReturn zonesTasksInfoSimple(ServiceRequest request) {
		ZoneStateContract req = request.getContract();
		try {
			return new NormalReturn("200","success", flowManagerService.getZoneFlowTaskInfoSimple(req.getFlowId(), req.getZone()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
			return new NormalReturn("500",e.getMessage(), "error");
		}
	}
	
	@Override
	@Contract(ExecuteFlowTaskContract.class)
	public NormalReturn executeFlowTask(ServiceRequest request) {
		ExecuteFlowTaskContract req = request.getContract();
		try {
			flowManagerService.executeFlowTask(new Integer(req.getFlowTaskId()));
			return new NormalReturn("200","success", "success");
		} catch (Exception e) {
			logger.error("", e);
			return new NormalReturn("500",e.getMessage(), "error");
		}
	}

	@Override
    @Model(contract = FlowQueryContract.class, model = Flow.class)
	public NormalReturn getFlows(ServiceRequest request) {
        FlowQueryContract req = request.getContract();
        Flow model = request.getModel();
		try {
			return new NormalReturn("200","success", flowManagerService.getFlows(req,model));
		} catch (Exception e) {
			logger.error("", e);
			return new NormalReturn("200",e.getMessage(), "error");
		}
	}

	@Override
	@Contract(ExecuteFlowTaskContract.class)
	public NormalReturn getFolwTaskLog(ServiceRequest request) {
		ExecuteFlowTaskContract req = request.getContract();
		try {
			return new NormalReturn("200","success", flowTaskLogService.getLog4FlowTask(new Integer(req.getFlowTaskId())));
		} catch (Exception e) {
			logger.error("", e);
			return new NormalReturn("500",e.getMessage(), "error");
		}
	}

	@Override
	@Contract(ZoneStateContract.class)
	public NormalReturn getZoneLogs(ServiceRequest request) {
		ZoneStateContract req = request.getContract();
		try {
            List<FlowTaskLogExtModle> logs = flowTaskLogService.getZoneLogs(req.getFlowId(), req.getZone());
            String state = flowManagerService.getZoneState(req.getFlowId(), req.getZone());
            Map<String,Object> result = new HashMap<>();
            result.put("state",state);
            result.put("logs",logs);
			return new NormalReturn("200","success", result);
		} catch (Exception e) {
			logger.error("", e);
			return new NormalReturn("500",e.getMessage(), "error");
		}
	}

	@Override
	@Contract(TemplateTaskContract.class)
	public NormalReturn getTemplateTasks(ServiceRequest request) {
		TemplateTaskContract req = request.getContract();
		try {
			List<TemplateTask>  list = templateManagerService.getTemplateTasks(req.getTemplateId());
            Set<Integer> taskIdSet = new HashSet<>();
            for (TemplateTask tt : list) {
                taskIdSet.add(tt.getTaskId());
            }
			return new NormalReturn("200","success", taskManagerService.getAutoTasksByIds(taskIdSet));
		} catch (Exception e) {
			logger.error("", e);
			return new NormalReturn("500",e.getMessage(), "error");
		}
	}

    @Override
    @Contract(FlowReviewContract.class)
    public NormalReturn reviewFlow(ServiceRequest request) {
        FlowReviewContract req = request.getContract();
        try {
            flowManagerService.reviewFlow(req.getFlowId(),req.getIsReview());
            return new NormalReturn("200","success", "success");
        } catch (Exception e) {
            logger.error("", e);
            return new NormalReturn("500",e.getMessage(), "error");
        }
    }

    @Override
    @Contract(FlowStartContract.class)
    public NormalReturn getFlowInfo(ServiceRequest request) {
        FlowStartContract req = request.getContract();
        try {
			Flow flow = flowManagerService.getFlow(req.getFlowId());
            if (flow != null) {
				Map<String,Object> result = new HashMap<>();
				result.put("flowInfo",flow);
				result.put("strategyInfo",flowManagerService.getFlowStrategy(req.getFlowId()));
                return new NormalReturn("200","success",result);
            } else {
                return new NormalReturn("500","flow not exists for id["+req.getFlowId()+"]");
            }
        } catch (Exception e) {
            logger.error("", e);
            return new NormalReturn("500",e.getMessage());
        }
    }

	@Override
	@Contract(BaseContract.class)
	public NormalReturn getUserTopFlows(ServiceRequest request) {
		BaseContract req = request.getContract();
		AuthUser currentUser = AuthUtils.getCurrentUser(req.getToken());
		if (currentUser == null) {
			return new NormalReturn("500","用户状态异常");
		}
		return new NormalReturn(flowManagerService.getUserTopFlows(currentUser.getId()));
	}

    @Override
    @RequiresAuthentication
    @Contract(FlowStartContract.class)
    public NormalReturn rollBackFlow(ServiceRequest request) {
        FlowStartContract req = request.getContract();
        try {
            if (flowManagerService.rollBackFlow(req.getFlowId())) {
                return new NormalReturn();
            } else {
                return new NormalReturn("500","回滚失败");
            }
        } catch (Exception e) {
            return new NormalReturn("500",e.getMessage());
        }

    }
}
