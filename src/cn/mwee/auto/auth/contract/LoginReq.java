/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.contract;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author mengfanyuan
 * 2016年6月30日上午10:01:34
 */
@Data
public class LoginReq {
	
	/** 用户名 **/
	@NotNull(message="用户名不能为空")
	private String userName;
	
	/** 密码 **/
	@NotNull(message="密码不能为空")
	private String password;

}
