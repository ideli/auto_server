/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.service.impl;

import java.util.List;

import cn.mwee.auto.auth.dao.AuthUserRoleExtMapper;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mwee.auto.auth.dao.AuthUserRoleMapper;
import cn.mwee.auto.auth.model.AuthRoleExample;
import cn.mwee.auto.auth.model.AuthUserRole;
import cn.mwee.auto.auth.model.AuthUserRoleExample;
import cn.mwee.auto.auth.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * manage userRole 
 * @author mengfanyuan
 * 2016年6月29日下午2:07:33
 */
@Service
public class UserRoleService implements IUserRoleService {
	
	@Autowired
	private AuthUserRoleMapper authUserRoleMapper;

	@Autowired
	private AuthUserRoleExtMapper authUserRoleExtMapper;

	@Override
	public boolean add(Integer userId, Integer[] roleIds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int insertBatch(List<AuthUserRole> list) {
		return authUserRoleExtMapper.insertBatch(list);
	}

	@Override
	public boolean del(Integer id) {
		// TODO Auto-generated method stub
		return authUserRoleMapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public boolean delByUserId(Integer userId) {
		// TODO Auto-generated method stub
		AuthUserRoleExample example = new AuthUserRoleExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return authUserRoleMapper.deleteByExample(example) > 0;
	}

	@Override
	public List<AuthUserRole> queryByUserId(Integer userId) {
		// TODO Auto-generated method stub
		AuthUserRoleExample example = new AuthUserRoleExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return authUserRoleMapper.selectByExample(example);
	}

}
