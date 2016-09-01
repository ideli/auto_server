/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.contract;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * @author mengfanyuan
 * 2016年7月6日下午5:27:18
 */
@Data
public class ZoneStateContract {
	/** **/
	@NotNull(message="未指定流程")
	private Integer flowId;
	/** **/
	@NotBlank(message="未指定区域")
	private String zone;
}
