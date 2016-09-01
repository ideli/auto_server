/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.service;

import java.util.List;

import cn.mwee.auto.auth.model.AuthRolePermission;

/**
 * 角色权限维护服务
 * 
 * @author mengfanyuan
 * 2016年6月29日下午1:47:24
 */
public interface IRolePermissionService {
	/**
	 * 角色授权
	 * 
	 * @param roleId 角色Id
	 * @param permissionIds 权限Ids
	 * @param creator 创建用户Id
	 * @return
	 */
	boolean add(Integer roleId,Integer[] permissionIds,Integer creator);
	
	/**
	 * 主键删除
	 * @param rolePermissionId
	 * @return
	 */
	boolean del(Integer rolePermissionId);
	
	/**
	 * 删除角色授权
	 * @param roleId 角色Id
	 * @return
	 */
	boolean delByRoleId(Integer roleId);
	
	/**
	 * 查询角色权限
	 * @param roleId
	 * @return
	 */
	List<AuthRolePermission> queryByRoleId(Integer roleId);
}
