package cn.mwee.auto.deploy.service.impl;

import cn.mwee.auto.auth.util.AuthUtils;
import cn.mwee.auto.deploy.dao.AutoChangeLogMapper;
import cn.mwee.auto.deploy.model.AutoChangeLog;
import cn.mwee.auto.deploy.service.IChangeLogService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/7.
 */
@Service
public class ChangeLogService implements IChangeLogService {

    @Resource(name = "simpleExecutor")
    private ThreadPoolTaskExecutor simpleExecutor;

    @Autowired
    private AutoChangeLogMapper autoChangeLogMapper;

    @Override
    public void addChangeLogAsyn(Byte logType, Byte operateType, Integer dataId, Object changeData) {
        String currentUserName = AuthUtils.getCurrUserName();
        String finalCurrentUserName = StringUtils.isBlank(currentUserName) ? "system" : currentUserName;
        simpleExecutor.execute(() -> {
            AutoChangeLog record = new AutoChangeLog();
            record.setLogType(logType);
            record.setOperateType(operateType);
            record.setDataId(dataId);
            record.setChangeJson(JSON.toJSONString(changeData));
            record.setOperator(finalCurrentUserName);
            record.setCreateTime(new Date());
            autoChangeLogMapper.insert(record);
        });
    }
}
