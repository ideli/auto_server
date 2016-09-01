/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.deploy.contract.commom;


import javax.validation.constraints.Min;

import cn.mwee.auto.common.db.PageInfo;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import lombok.Data;

/**
 * @author mengfanyuan
 * 2016年7月6日下午5:38:58
 */
@Data
public class BaseContract {

	/**
	 * 用户token
	 */
	private String token;

	/**
	 * 当前页面url
	 */
	private String currentPageUrl;

	/**
	 * 分页信息
	 */
	private PageInfo page;

}
