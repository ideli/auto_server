package cn.mwee.auto.auth.controller.impl;

import cn.mwee.auto.auth.contract.user.*;
import cn.mwee.auto.auth.controller.IUserController;
import cn.mwee.auto.auth.model.AuthRole;
import cn.mwee.auto.auth.model.AuthUser;
import cn.mwee.auto.auth.service.IUserService;
import cn.mwee.auto.auth.util.AuthUtils;
import cn.mwee.auto.deploy.contract.commom.BaseContract;
import cn.mwee.auto.misc.aspect.contract.Contract;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
@Controller
public class UserController implements IUserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Value("${user.default.password}")
    private String defaultPassword;

    @Override
    @RequiresPermissions(value = {"/user/addUser"}, logical = Logical.OR)
    @Contract(UserAddContract.class)
    public NormalReturn userAdd(ServiceRequest request) {
        UserAddContract req = request.getContract();
        try {
            AuthUser authUser = new AuthUser();
            authUser.setUsername(req.getUserName());
            authUser.setPassword(defaultPassword);
            authUser.setName(req.getName());
            authUser.setEmail(req.getEmail());
            authUser.setPhoneno(req.getPhoneNo());
            authUser.setDepartment(req.getDepartment());
            Integer userId = userService.addUser(authUser);
            if (userId != null) {
                return new NormalReturn("200", "success", authUser.getUsername());
            }
            return new NormalReturn("500", "error");
        } catch (Exception e) {
            logger.error("", e);
            return new NormalReturn("500", e.getMessage());
        }
    }

    @Override
//    @RequiresPermissions(value = {"/user/userUpdate"}, logical = Logical.OR)
    @RequiresAuthentication
    @Contract(UserAddContract.class)
    public NormalReturn userUpdate(ServiceRequest request) {
        UserAddContract req = request.getContract();
        try {
            AuthUser user = userService.queryByUserName(req.getUserName());
            if (user == null) {
                return new NormalReturn("500", "user["+req.getUserName()+"] not exist");
            }
            AuthUser authUser = new AuthUser();
            authUser.setId(user.getId());
            authUser.setName(req.getName());
            authUser.setEmail(req.getEmail());
            authUser.setPhoneno(req.getPhoneNo());
            authUser.setDepartment(req.getDepartment());
            userService.updateUser(authUser);
            return new NormalReturn();
        } catch (Exception e) {
            logger.error("", e);
            return new NormalReturn("500", e.getMessage());
        }
    }

    @Override
//    @RequiresPermissions(value = "/user/userInfo",logical = Logical.OR)
    @RequiresAuthentication
    @Contract(UserDelContract.class)
    public NormalReturn userInfo(ServiceRequest request) {
        UserDelContract req = request.getContract();
        try {
            Map<String, Object> result = new HashMap<>();
            AuthUser userInfo = userService.queryByUserName(req.getUserName());
            result.put("userInfo", userInfo);
            List<AuthRole> authRoles = userService.queryRoles(userInfo.getId());
            result.put("authRoles", authRoles);
            result.put("unAuthRoles", userService.queryUnAuthRoles(userInfo.getId(), authRoles));
            return new NormalReturn("200", "success", result);
        } catch (Exception e) {
            logger.error("", e);
            return new NormalReturn("500", e.getMessage());
        }
    }

    @Override
//    @RequiresPermissions(value = {"/user/userList"}, logical = Logical.OR)
    @RequiresAuthentication
    @Contract(UserQueryContract.class)
    public NormalReturn userList(ServiceRequest request) {
        UserQueryContract req = request.getContract();
        try {
            return new NormalReturn("200", "success", userService.queryUsers(req));
        } catch (Exception e) {
            logger.error("", e);
            return new NormalReturn("500", e.getMessage());
        }
    }

    @Override
    @RequiresAuthentication
    @Contract(PassWordContract.class)
    public NormalReturn updatePassword(ServiceRequest request) {
        PassWordContract req = request.getContract();
        try {
            if (userService.updatePassword(req.getOldPassword(), req.getNewPassword())) {
                return new NormalReturn("200", "success");
            } else {
                return new NormalReturn("500", "Can not find User");
            }

        } catch (Exception e) {
            logger.error("updatePassword error", e);
            return new NormalReturn("500", e.getMessage());
        }
    }

    @Override
    @RequiresPermissions(value = "/user/userGrant", logical = Logical.OR)
    @Contract(UserGrantContract.class)
    public NormalReturn userGrant(ServiceRequest request) {
        UserGrantContract req = request.getContract();
        try {
            AuthUser authUser = userService.queryByUserName(req.getUserName());
            if (authUser == null) {
                return new NormalReturn("500", "Can not find User:" + req.getUserName());
            }
            userService.updateUserGrant(authUser, req.getRoleIds());
            return new NormalReturn("200", "success");
        } catch (Exception e) {
            logger.error("userGrant error", e);
            return new NormalReturn("500", e.getMessage());
        }
    }

    @Override
    @RequiresPermissions(value = "/user/userDel", logical = Logical.OR)
    @Contract(UserDelContract.class)
    public NormalReturn userDel(ServiceRequest request) {
        UserDelContract req = request.getContract();
        try {
            userService.delUserLogic(req.getUserName());
            return new NormalReturn("200", "success");
        } catch (Exception e) {
            logger.error("", e);
            return new NormalReturn("500", e.getMessage());
        }
    }

    @Override
    @RequiresPermissions(value = "/user/resetPassword", logical = Logical.OR)
    @Contract(PassWordResetContract.class)
    public NormalReturn resetPassword(ServiceRequest request) {
        PassWordResetContract req = request.getContract();
        try {
            if (userService.resetPassword(req.getUserName(), req.getPassword())) {
                return new NormalReturn("200", "success");
            } else {
                return new NormalReturn("500", "error");
            }
        } catch (Exception e) {
            logger.error("resetPassword error", e);
            return new NormalReturn("500", e.getMessage());
        }
    }
}
