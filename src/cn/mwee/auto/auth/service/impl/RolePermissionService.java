/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.mwee.auto.auth.dao.AuthRolePermissionMapper;
import cn.mwee.auto.auth.model.AuthRolePermission;
import cn.mwee.auto.auth.model.AuthRolePermissionExample;
import cn.mwee.auto.auth.service.IRolePermissionService;

/**
 * @author mengfanyuan
 * 2016年6月29日下午2:08:23
 */
public class RolePermissionService implements IRolePermissionService {
	
	@Autowired
	private AuthRolePermissionMapper authRolePermissionMapper;
	
	@Override
	public boolean add(Integer roleId, Integer[] permissionIds, Integer creator) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean del(Integer id) {
		// TODO Auto-generated method stub
		return authRolePermissionMapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public boolean delByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		AuthRolePermissionExample example = new AuthRolePermissionExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		return authRolePermissionMapper.deleteByExample(example) > 0;
	}

	@Override
	public List<AuthRolePermission> queryByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return null;
	}

}
