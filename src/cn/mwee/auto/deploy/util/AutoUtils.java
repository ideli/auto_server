package cn.mwee.auto.deploy.util;

import cn.mwee.auto.deploy.model.MwGroupData;
import cn.mwee.auto.misc.common.exception.ServiceException;

import java.util.List;
import static cn.mwee.auto.deploy.util.AutoConsts.*;

/**
 * Created by huming on 16/6/24.
 */
public class AutoUtils {

    public static final ServiceException NO_TASK_IN_TEMPLATE_EXCEPTION	= new ServiceException("当前模板没有任务组");

    public static MwGroupData mwGroupData(List<Byte> groups) throws ServiceException
    {
        if(groups == null || groups.size() == 0)
        {
            throw NO_TASK_IN_TEMPLATE_EXCEPTION;
        }

        MwGroupData mwGroupData = new MwGroupData();

        for (Byte group : groups)
        {
            if(group == GroupType.PreGroup)
            {
                mwGroupData.setPreGroup(group);
                continue;
            }
            if(group == GroupType.PostGroup)
            {
                mwGroupData.setPreGroup(group);
                continue;
            }
            mwGroupData.addConcurrentGroup(group);
        }

        return mwGroupData;
    }

}
