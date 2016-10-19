/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.contract.template;

import cn.mwee.auto.deploy.contract.commom.BaseContract;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author mengfanyuan
 * 2016年7月6日下午5:27:18
 */
@Data
public class TemplateStrategyAddContract extends BaseContract {
	/**
     * 模板
     */
	@NotNull(message="未指定模板")
	private Integer templateId;

	/**
	 * 流程策略-区域组大小
	 */
    @NotNull(message="未指定组主机个数")
	private Integer strategyZoneSize;

	/**
	 * 流程策略-延迟时间
	 */
    @NotNull(message="未指定时间间隔")
	private Integer strategyInterval;
}
