/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.contract.flow;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author mengfanyuan
 * 2016年7月6日下午5:27:18
 */
@Data
public class SubFlowZoneStateContract {

	@NotNull(message="未指定pid")
	private Integer pid;
	/** **/
	@NotNull(message="未指定流程类型")
	private Byte type;

	@NotNull(message="未指定环境")
	private Byte env;
}
