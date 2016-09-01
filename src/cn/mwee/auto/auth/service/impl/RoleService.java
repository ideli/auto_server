/**
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.mwee.auto.auth.contract.role.RoleQueryContract;
import cn.mwee.auto.auth.dao.AuthPermissionMapper;
import cn.mwee.auto.auth.dao.AuthRolePermissionExtMapper;
import cn.mwee.auto.auth.dao.AuthRolePermissionMapper;
import cn.mwee.auto.auth.model.*;
import cn.mwee.auto.auth.service.IPermissionService;
import cn.mwee.auto.auth.util.AuthUtils;
import cn.mwee.auto.auth.util.SqlUtils;
import cn.mwee.auto.common.db.BaseModel;
import cn.mwee.auto.common.db.BaseQueryResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mwee.auto.auth.dao.AuthRoleMapper;
import cn.mwee.auto.auth.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * manage role info
 *
 * @author mengfanyuan
 * 2016年6月29日下午1:37:12
 */
@Service
public class RoleService implements IRoleService {

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthRolePermissionExtMapper authRolePermissionExtMapper;

    @Autowired
    private AuthRolePermissionMapper authRolePermissionMapper;

    @Autowired
    private AuthPermissionMapper authPermissionMapper;

    @Autowired
    private IPermissionService permissionService;


    @Override
    public boolean add(AuthRole authRole) {
        // TODO Auto-generated method stub
        authRole.setCreator(SecurityUtils.getSubject().getPrincipal().toString());
        authRole.setCreateTime(new Date());
        authRole.setRolecode(authRole.getRolename());
        authRole.setStatus(true);
        return authRoleMapper.insertSelective(authRole) > 0;
    }

    @Override
    public boolean update(AuthRole authRole) {
        authRole.setStatus(true);
        authRole.setUpdateTime(new Date());
        return authRoleMapper.updateByPrimaryKeySelective(authRole) > 0;
    }

    @Override
    public boolean del(Integer id) {
        return authRoleMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public AuthRole select(Integer id) {
        return authRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public AuthRole selectByName(String roleName) {
        AuthRoleExample example = new AuthRoleExample();
        example.createCriteria().andRolenameEqualTo(roleName);
        List<AuthRole> roles = authRoleMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(roles) ? roles.get(0) : null;
    }

    @Override
    public List<AuthRole> queryAllRoles() {
        AuthRoleExample example = new AuthRoleExample();
        return authRoleMapper.selectByExample(example);
    }

    @Override
    public BaseQueryResult<AuthRole> query(RoleQueryContract roleQueryContract) {
        AuthRoleExample example = new AuthRoleExample();
        if (StringUtils.isNotBlank(roleQueryContract.getRoleName()))
            example.createCriteria().andRolenameLike(SqlUtils.wrapLike(roleQueryContract.getRoleName()));
        BaseQueryResult<AuthRole> result = BaseModel.selectByPage(authRoleMapper, example
                , roleQueryContract.getPage(), roleQueryContract.getPage() == null);
        return result;
    }

    @Override
    public int updateRoleAuth(Integer roleId, List<Integer> permissionIds) {
        List<AuthRolePermission> list = new ArrayList<>(permissionIds.size());
        permissionIds.forEach(permId -> {
            AuthRolePermission rolePermission = new AuthRolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permId);
            rolePermission.setCreator(AuthUtils.getCurrUserName());
            rolePermission.setCreateTime(new Date());
            list.add(rolePermission);
        });
        AuthRolePermissionExample example = new AuthRolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        authRolePermissionMapper.deleteByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return authRolePermissionExtMapper.insertBatch(list);
        } else {
            return 1;
        }
    }

    @Override
    public List<AuthPermission> queryRoleAuths(Integer roleId) {
        /*AuthRolePermissionExample example = new AuthRolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<AuthRolePermission> list = authRolePermissionMapper.selectByExample(example);
        List<Integer> permIds = new ArrayList<>();
        list.forEach(authRolePermission -> permIds.add(authRolePermission.getPermissionId()));
        AuthPermissionExample example1 = new AuthPermissionExample();
        example1.createCriteria().andIdIn(permIds);
        return authPermissionMapper.selectByExample(example1);*/
        return authRolePermissionExtMapper.queryPerms4Role(roleId);
    }

    @Override
    public List<ZtreePerm> queryRoleAuths4Ztree(Integer roleId) {
        List<ZtreePerm> ztreePerms = new ArrayList<>();
        List<AuthMenu> allPerms = permissionService.queryPermTree();
        Set<Integer> authPerms = authRolePermissionExtMapper.queryPermIds4Role(roleId);
        buildZtreePerms(allPerms,ztreePerms,authPerms);
        return ztreePerms;
    }
    private void buildZtreePerms(List<AuthMenu> allPerms, List<ZtreePerm> ztreePerms, Set<Integer> authPerms) {
        allPerms.forEach(authMenu -> {
            ZtreePerm ztreePerm = new ZtreePerm();
            ztreePerm.setId(authMenu.getId());
            ztreePerm.setPId(authMenu.getParentId());
            ztreePerm.setName(authMenu.getName());
            ztreePerm.setOpen((authMenu.getChildMenu()!=null && (authMenu.getChildMenu().size()) > 0));
            ztreePerm.setChecked(authPerms.contains(authMenu.getId()));
            ztreePerms.add(ztreePerm);
            if ((authMenu.getChildMenu()!=null && (authMenu.getChildMenu().size()) > 0))
                buildZtreePerms(authMenu.getChildMenu(),ztreePerms,authPerms);
        });
    }
}
