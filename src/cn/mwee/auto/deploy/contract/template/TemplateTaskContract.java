/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.contract.template;

import javax.validation.constraints.NotNull;


import cn.mwee.auto.common.db.BaseQueryResult;
import cn.mwee.auto.common.db.PageInfo;
import cn.mwee.auto.deploy.contract.commom.BaseContract;
import cn.mwee.auto.deploy.model.AutoTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengfanyuan
 * 2016年7月14日上午11:47:07
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TemplateTaskContract extends BaseContract {
	
	/** 模板Id **/
	@NotNull(message="未指定模板")
	private Integer templateId;

}
