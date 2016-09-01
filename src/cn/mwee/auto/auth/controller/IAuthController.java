/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.controller;

import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;

/**
 * @author mengfanyuan
 * 2016年6月30日上午9:49:55
 */
public interface IAuthController extends IController {
	/**
	 * 登录
	 * @param request
	 * @return
     */
	NormalReturn login(ServiceRequest request);

	/**
	 * 登出
	 * @param request
	 * @return
     */
	NormalReturn logout(ServiceRequest request);

    /**
	 * 检查用户是否登录
	 * @param request
	 * @return
     */
	NormalReturn check(ServiceRequest request);

	/**
	 * 获取左侧菜单
	 * @param request
	 * @return
	 */
	NormalReturn leftMenu(ServiceRequest request);

	/**
	 * 当前页中的按钮权限
	 * @param request
	 * @return
	 */
	NormalReturn getCurrPageBtnPerm(ServiceRequest request);

}
