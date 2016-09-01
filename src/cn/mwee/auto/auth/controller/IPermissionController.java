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
public interface IPermissionController extends IController {

	/**
	 * 新增菜单
	 * @param request
	 * @return
	 */
	NormalReturn addPermission(ServiceRequest request);

	/**
	 * 新增菜单
	 * @param request
	 * @return
	 */
	NormalReturn getPermission(ServiceRequest request);

	/**
	 * 更新菜单
	 * @param request
	 * @return
     */
	NormalReturn updatePermission(ServiceRequest request);

	/**
	 * 删除菜单
	 * @param request
	 * @return
     */
	NormalReturn delPermission(ServiceRequest request);

	/**
	 * 查询菜单
	 * @param request
	 * @return
     */
	NormalReturn queryPermission(ServiceRequest request);

	/**
	 * 获取菜单
	 * @param request
	 * @return
	 */
	NormalReturn queryPermTree(ServiceRequest request);

	/**
	 * 获取菜单
	 * @param request
	 * @return
	 */
	NormalReturn queryLevelMenu(ServiceRequest request);
}
