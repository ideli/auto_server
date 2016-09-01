/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.dao;

import cn.mwee.auto.deploy.model.FlowTaskLog;
import cn.mwee.auto.deploy.model.FlowTaskLogExample;
import cn.mwee.auto.deploy.model.FlowTaskLogExtModle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author mengfanyuan
 * 2016年7月8日下午1:27:43
 */
public interface FlowTaskLogExtMapper {
	@Select("UPDATE flow_task_log SET log =concat(log,#{log}) WHERE id = #{logId};")
	void addLineLog(@Param("logId")Integer logId, @Param("log")String log);


    List<FlowTaskLogExtModle> selectZoneLogsByExample(FlowTaskLogExample example);

}
