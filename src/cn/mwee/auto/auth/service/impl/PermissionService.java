/**
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.mwee.auto.auth.contract.permission.PermissionQueryContract;
import cn.mwee.auto.auth.dao.AuthPermissionExtMapper;
import cn.mwee.auto.auth.dao.AuthRolePermissionExtMapper;
import cn.mwee.auto.auth.model.AuthMenu;
import cn.mwee.auto.auth.util.SqlUtils;
import cn.mwee.auto.common.db.BaseModel;
import cn.mwee.auto.common.db.BaseQueryResult;
import cn.mwee.auto.deploy.service.IProjectService;
import cn.mwee.auto.deploy.util.AutoConsts.PermConst;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mwee.auto.auth.dao.AuthPermissionMapper;
import cn.mwee.auto.auth.model.AuthPermission;
import cn.mwee.auto.auth.model.AuthPermissionExample;
import cn.mwee.auto.auth.service.IPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * manage permission info
 *
 * @author mengfanyuan
 *         2016年6月29日下午1:38:05
 */
@Service
public class PermissionService implements IPermissionService {

    @Autowired
    private AuthPermissionMapper authPermissionMapper;

    @Autowired
    private AuthPermissionExtMapper authPermissionExtMapper;

    @Autowired
    private AuthRolePermissionExtMapper authRolePermissionExtMapper;

    @Resource
    private IProjectService projectService;

    @Override
    public boolean add(AuthPermission authPermission) {
        authPermission.setCreateTime(new Date());
        authPermission.setStatus(true);
        return authPermissionMapper.insertSelective(authPermission) > 0;
    }

    @Override
    public boolean update(AuthPermission authPermission) {
        authPermission.setUpdateTime(new Date());
        return authPermissionMapper.updateByPrimaryKeySelective(authPermission) > 0;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        AuthPermissionExample example = new AuthPermissionExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<AuthPermission> childPerms = authPermissionMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(childPerms)) {
            throw new Exception("Perm[" + id + "] has child perms,delete child perms first please");
        }
        return authPermissionMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public AuthPermission select(Integer id) {
        return authPermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public AuthPermission selectByCode(String code) {
        AuthPermissionExample example = new AuthPermissionExample();
        example.createCriteria().andCodeEqualTo(code).andCodeNotEqualTo("#");
        List<AuthPermission> list = authPermissionMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    @Override
    public AuthPermission selectByName(String name, boolean isProject) {
        AuthPermissionExample example = new AuthPermissionExample();
        example.createCriteria().andNameEqualTo(name).andIsprojectEqualTo(isProject);
        List<AuthPermission> list = authPermissionMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }


    @Override
    public BaseQueryResult<AuthPermission> query(PermissionQueryContract permissionQuery) {
        AuthPermissionExample example = new AuthPermissionExample();
        AuthPermissionExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(permissionQuery.getName()))
            criteria.andNameLike(SqlUtils.wrapLike(permissionQuery.getName()));
        if (StringUtils.isNotBlank(permissionQuery.getCode()))
            criteria.andCodeLike(SqlUtils.wrapLike(permissionQuery.getCode()));
        if (permissionQuery.getParentId() != null)
            criteria.andParentIdEqualTo(permissionQuery.getParentId());
        if (permissionQuery.getType() != null)
            criteria.andTypeEqualTo(permissionQuery.getType());
        if (permissionQuery.getLevel() != null)
            criteria.andLevelEqualTo(permissionQuery.getLevel());
        if (permissionQuery.getCreateTimeS() != null)
            criteria.andCreateTimeGreaterThanOrEqualTo(permissionQuery.getCreateTimeS());
        if (permissionQuery.getCreateTimeE() != null)
            criteria.andCreateTimeLessThanOrEqualTo(permissionQuery.getCreateTimeE());
        BaseQueryResult<AuthPermission> result = BaseModel.selectByPage(authPermissionMapper, example
                , permissionQuery.getPage(), permissionQuery.getPage() == null);
        return result;
    }

    @Override
    public List<AuthMenu> getLeftMenu(Integer userId) {
        List<AuthMenu> topMenus = new ArrayList<>();
        List<AuthMenu> menus = authPermissionExtMapper.selectPrivateMenu(userId);
        if (CollectionUtils.isEmpty(menus)) {
            return topMenus;
        }
        for (AuthMenu menu : menus) {
            if (menu.getParentId() == -1) {
                topMenus.add(menu);
                menu.setChildMenu(getChildMenu(menus, menu));
            }
        }
        List<AuthPermission> projectList = projectService.getProjects4User(userId);
        projectList.forEach(project -> {
            if (!isExist(project, topMenus)) {
                topMenus.add(authPermissionExtMapper.selectPermTreeByPrimaryKey(project.getId()));
            }
        });
        return topMenus;
    }

    private List<AuthMenu> getChildMenu(List<AuthMenu> allMenus, AuthMenu parentMenu) {
        List<AuthMenu> childMenu = new ArrayList<AuthMenu>();
        for (AuthMenu menu : allMenus) {
            if (menu.getParentId().intValue() == parentMenu.getId()) {
                childMenu.add(menu);
                menu.setChildMenu(getChildMenu(allMenus, menu));
            }
        }
        return childMenu;
    }

    private boolean isExist(AuthPermission authPermission, List<AuthMenu> topMenus) {
        for (AuthMenu authMenu : topMenus) {
            if (authMenu.getId().intValue() == authPermission.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<AuthPermission> queryLevelMenu(Byte type, Byte level) {
        AuthPermissionExample example = new AuthPermissionExample();
        if (type.byteValue() == 1) {
            example.createCriteria().andLevelEqualTo(--level).andTypeEqualTo((byte) 1);
        } else {
            example.createCriteria().andLevelEqualTo(level).andTypeEqualTo((byte) 1);
        }
        return authPermissionMapper.selectByExample(example);
    }

    @Override
    public List<AuthMenu> queryPermTree() {
        List<AuthMenu> list = authPermissionExtMapper.selectPermTree(-1);
        return list;
    }

    @Override
    public List<AuthPermission> getBtnPerms4Page(String pageUrl, Integer userId) {
        if (StringUtils.isBlank(pageUrl)) return new ArrayList<>();
        AuthPermission perm = selectByCode(pageUrl);
        if (null == perm) return new ArrayList<>();
        return authPermissionExtMapper.selectPrivatePerm(PermConst.TYPE_BTN, userId, perm.getId());
    }
}
