/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.contract.flow;


import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author mengfanyuan
 * 2016年7月6日下午5:27:18
 */
@Data
public class ExecuteFlowTaskContract {
	/** **/
	@NotNull(message="未指定任务")
	private Integer flowTaskId;
}
