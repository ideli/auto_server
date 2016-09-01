package cn.mwee.auto.auth.controller.impl;

import cn.mwee.auto.auth.contract.role.*;
import cn.mwee.auto.auth.controller.IRoleController;
import cn.mwee.auto.auth.model.AuthRole;
import cn.mwee.auto.auth.service.IRoleService;
import cn.mwee.auto.misc.aspect.contract.Contract;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
@Controller
public class RoleController implements IRoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);



    @Autowired
    private IRoleService roleService;



    @Override
    @RequiresPermissions(value = "/role/addRole",logical = Logical.OR)
    @Contract(RoleAddContract.class)
    public NormalReturn addRole(ServiceRequest request) {
        RoleAddContract req = request.getContract();
        try {
            if (roleService.selectByName(req.getRoleName()) != null ) {
                return new NormalReturn("500","error","角色名称已使用");
            }
            AuthRole authRole = new AuthRole();
            authRole.setRolename(req.getRoleName());
            authRole.setDescription(req.getDesc());
            roleService.add(authRole);
            return new NormalReturn("200","success","success");
        } catch (Exception e) {
            logger.error("",e);
            return new NormalReturn("500","error",e.getMessage());
        }
    }

    @Override
    @RequiresAuthentication
    @Contract(RoleIdContract.class)
    public NormalReturn getRole(ServiceRequest request) {
        RoleIdContract req = request.getContract();
        try {
            Map<String,Object> result = new HashMap<>();
            result.put("roleInfo",roleService.select(req.getRoleId()));
            result.put("authInfo",roleService.queryRoleAuths4Ztree(req.getRoleId()));
            return new NormalReturn("200","success",result);
        } catch (Exception e) {
            logger.error("",e);
            return new NormalReturn("500","error",e.getMessage());
        }
    }

    @Override
    @RequiresPermissions(value = "/role/updateRole",logical = Logical.OR)
    @Contract(RoleUpdateContract.class)
    public NormalReturn updateRole(ServiceRequest request) {
        RoleUpdateContract req = request.getContract();
        try {
            AuthRole authRole = new AuthRole();
            authRole.setId(req.getRoleId());
            if (StringUtils.isNotBlank(req.getRoleName())) authRole.setRolename(req.getRoleName());
            if (StringUtils.isNotBlank(req.getDesc())) authRole.setDescription(req.getDesc());
            roleService.update(authRole);
            return new NormalReturn("200","success","success");
        } catch (Exception e) {
            logger.error("",e);
            return new NormalReturn("500","error",e.getMessage());
        }
    }

    @Override
    @RequiresPermissions(value = "/role/delRole",logical = Logical.OR)
    @Contract(RoleIdContract.class)
    public NormalReturn delRole(ServiceRequest request) {
        RoleIdContract req = request.getContract();
        try {
            roleService.del(req.getRoleId());
            return new NormalReturn("200","success","success");
        } catch (Exception e) {
            logger.error("",e);
            return new NormalReturn("500","error",e.getMessage());
        }
    }

    @Override
//    @RequiresPermissions(value = "/role/queryRole",logical = Logical.OR)
    @RequiresAuthentication
    @Contract(RoleQueryContract.class)
    public NormalReturn queryRole(ServiceRequest request) {
        RoleQueryContract req = request.getContract();
        try {
            return new NormalReturn("200","success",roleService.query(req));
        } catch (Exception e) {
            logger.error("",e);
            return new NormalReturn("500","error",e.getMessage());
        }
    }

    @Override
    @RequiresPermissions(value = "/role/roleAuth",logical = Logical.OR)
    @Contract(RoleGrantContract.class)
    public NormalReturn roleAuth(ServiceRequest request) {
        RoleGrantContract req = request.getContract();
        try {
            if (roleService.select(req.getRoleId()) == null) {
                return new NormalReturn("500","error","角色不存在");
            }
            roleService.updateRoleAuth(req.getRoleId(), req.getPermissionIds());
            return new NormalReturn("200","success","success");
        } catch (Exception e) {
            logger.error("",e);
            return new NormalReturn("500","error",e.getMessage());
        }
    }

    @Override
//    @RequiresPermissions(value = "/role/getRoleAuths",logical = Logical.OR)
    @RequiresAuthentication
    @Contract(RoleUpdateContract.class)
    public NormalReturn getRoleAuths(ServiceRequest request) {
        RoleUpdateContract req = request.getContract();
        try {
            return new NormalReturn("200","success",roleService.queryRoleAuths(req.getRoleId()));
        } catch (Exception e) {
            logger.error("",e);
            return new NormalReturn("500","error",e.getMessage());
        }
    }


}
