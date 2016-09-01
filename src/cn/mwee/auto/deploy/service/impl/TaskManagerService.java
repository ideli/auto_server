/**
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.service.impl;

import cn.mwee.auto.auth.util.SqlUtils;
import cn.mwee.auto.common.db.BaseModel;
import cn.mwee.auto.common.db.BaseQueryResult;
import cn.mwee.auto.common.util.DateUtil;
import cn.mwee.auto.deploy.contract.task.QueryTasksRequest;
import cn.mwee.auto.deploy.contract.task.QueryTasksResult;
import cn.mwee.auto.deploy.dao.TemplateTaskMapper;
import cn.mwee.auto.deploy.model.*;

import static cn.mwee.auto.deploy.util.AutoConsts.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mwee.auto.deploy.dao.AutoTaskMapper;
import cn.mwee.auto.deploy.service.ITaskManagerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author mengfanyuan
 * 2016年7月6日上午10:16:54
 */
@Service
public class TaskManagerService implements ITaskManagerService {

    @Autowired
    private AutoTaskMapper autoTaskMapper;

    @Autowired
    private TemplateTaskMapper templateTaskMapper;

    @Override
    public boolean addTask(AutoTask task) {
        return autoTaskMapper.insertSelective(task) > 0;
    }

    @Override
    public AutoTask getTask(Integer taskId) {
        return autoTaskMapper.selectByPrimaryKey(taskId);
    }

    @Override
    public boolean deleteTask(Integer taskId) {
        AutoTask task = new AutoTask();
        task.setId(taskId);
        task.setInuse(InUseType.NOT_USE);
        task.setUpdateTime(new Date());
        return autoTaskMapper.updateByPrimaryKeySelective(task) > 0;
    }

    @Override
    public boolean checkTaskCanDel(Integer taskId) {
        TemplateTaskExample example = new TemplateTaskExample();
        example.createCriteria()
                .andInuseEqualTo(InUseType.IN_USE)
                .andTaskIdEqualTo(taskId);
        return templateTaskMapper.countByExample(example) == 0;
    }

    @Override
    public boolean modifyTask(AutoTask task) {
        task.setCreateTime(null);
        task.setUpdateTime(new Date());
        return autoTaskMapper.updateByPrimaryKeySelective(task) > 0;
    }

    @Override
    public QueryTasksResult getAutoTasks(QueryTasksRequest req) {
        AutoTaskExample e = new AutoTaskExample();
        AutoTaskExample.Criteria c = e.createCriteria();

        c.andInuseEqualTo(InUseType.IN_USE);
        e.setOrderByClause("id DESC");

        if (req.getId() != null) {
            c.andIdEqualTo(req.getId());
        } else {
            Date start = DateUtil.parseDate(req.getCreateTimeS());

            Date end = DateUtil.parseDate(req.getCreateTimeE());

            if (start != null) {
                c.andCreateTimeGreaterThanOrEqualTo(start);
            }
            if (end != null) {
                c.andCreateTimeLessThanOrEqualTo(end);
            }

            String taskName = req.getName();


            if (StringUtils.isNotBlank(taskName)) {
                c.andNameLike("%".concat(taskName).concat("%"));
            }

            String desc = req.getDesc();

            if (StringUtils.isNotBlank(desc)) {
                c.andDescEqualTo(desc);
            }

            String exec = req.getExec();

            if (StringUtils.isNotBlank(exec)) {
                c.andExecEqualTo(exec);
            }

            String params = req.getParams();

            if (StringUtils.isNotBlank(params)) {
                c.andParamsEqualTo(params);
            }
        }

        QueryTasksResult rs = new QueryTasksResult();
        BaseQueryResult<AutoTask> qrs = BaseModel.selectByPage(autoTaskMapper, e, req.getPage());

        rs.setList(qrs.getList());
        rs.setPage(qrs.getPage());

        return rs;
    }

    @Override
    public List<AutoTask> getAutoTasksByIds(Set<Integer> ids) {
        List idList = new ArrayList(ids);
        AutoTaskExample example = new AutoTaskExample();
        example.createCriteria().andIdIn(idList);
        return autoTaskMapper.selectByExample(example);
    }


    @Override
    public List<Model4Sel> getAutoTask4Sel2(String param) {
        String likeParam = SqlUtils.wrapLike(param);
        AutoTaskExample example = new AutoTaskExample();
        if (StringUtils.isNumeric(param)){
            example.createCriteria().andIdEqualTo(new Integer(param));
        }
        example.or(example.createCriteria().andNameLike(likeParam));
        example.or(example.createCriteria().andDescLike(likeParam));
        example.or(example.createCriteria().andExecLike(likeParam));
        example.or(example.createCriteria().andParamsLike(likeParam));
        List<AutoTask> list = autoTaskMapper.selectByExample(example);
        List<Model4Sel> result = new ArrayList<>(list.size());
        list.forEach(autoTask -> {
            Model4Sel selModel = new Model4Sel();
            selModel.setId(autoTask.getId());
            selModel.setText(autoTask.getName() + (StringUtils.isBlank(autoTask.getDesc()) ? "" : "(" + autoTask.getDesc() + ")"));
            result.add(selModel);
        });
        return result;
    }


}
