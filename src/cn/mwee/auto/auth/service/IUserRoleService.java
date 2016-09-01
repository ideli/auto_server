/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.service;

import java.util.List;

import cn.mwee.auto.auth.model.AuthUserRole;

/**
 * 用户角色维护
 * 
 * @author mengfanyuan
 * 2016年6月29日下午1:45:13
 */
public interface IUserRoleService {
	/**
	 * 用户角色绑定
	 * @param userId 用户Id
	 * @param roleIds 角色Id
 	 * @param creator 创建用户Id
	 * @return
	 */
	boolean add(Integer userId,Integer[] roleIds);


	/**
	 * 批量插入
	 * @param list
	 * @return
     */
	int insertBatch(List<AuthUserRole> list);

	/**
	 * 删除用户角色 by Id
	 * 
	 * @param userRoleId 用户角色Id
	 * @return
	 */
	boolean del(Integer userRoleId);
	
	/**
	 * 删除用户角色 by userId
	 * 
	 * @param userId
	 * @return
	 */
	boolean delByUserId(Integer userId);
	
	/**
	 * 查询用户角色 by userId
	 * 
	 * @param userId
	 * @return
	 */
	List<AuthUserRole> queryByUserId(Integer userId);
}
