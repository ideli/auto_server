/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.controller;

import cn.mwee.auto.auth.contract.role.RoleUpdateContract;
import cn.mwee.auto.misc.aspect.contract.Contract;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @author mengfanyuan
 * 2016年6月30日上午9:49:55
 */
public interface IRoleController extends IController {

	/**
	 * 新增角色
	 * @param request
	 * @return
	 */
	NormalReturn addRole(ServiceRequest request);

    /**
     * 获取角色信息
     * @param request
     * @return
     */
    NormalReturn getRole(ServiceRequest request);

	/**
	 * 更新角色
	 * @param request
	 * @return
	 */
	NormalReturn updateRole(ServiceRequest request);

	/**
	 * 删除角色
	 * @param request
	 * @return
	 */
	NormalReturn delRole(ServiceRequest request);

	/**
	 * 查询角色
	 * @param request
	 * @return
	 */
	NormalReturn queryRole(ServiceRequest request);

	/**
	 * 角色授权
	 * @param request
	 * @return
	 */
	NormalReturn roleAuth(ServiceRequest request);

	/**
	 * 查询角色权限
	 * @param request
	 * @return
     */
	NormalReturn getRoleAuths(ServiceRequest request);
}
