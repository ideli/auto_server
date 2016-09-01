/**
 * 
 */
package cn.mwee.auto.auth.service.impl;

import java.util.*;

import javax.annotation.Resource;

import cn.mwee.auto.auth.contract.user.UserQueryContract;
import cn.mwee.auto.auth.dao.AuthUserRoleExtMapper;
import cn.mwee.auto.auth.model.*;
import cn.mwee.auto.auth.service.IRoleService;
import cn.mwee.auto.auth.service.IUserRoleService;
import cn.mwee.auto.auth.util.AuthUtils;
import cn.mwee.auto.auth.util.SqlUtils;
import cn.mwee.auto.common.db.BaseModel;
import cn.mwee.auto.common.db.BaseQueryResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.mwee.auto.auth.dao.AuthUserMapper;
import cn.mwee.auto.auth.model.AuthUserExample.Criteria;
import cn.mwee.auto.auth.service.IUserService;
import cn.mwee.auto.auth.util.PasswordHelper;

/**
 * manager user info
 * 
 * @author mengfanyuan
 * 2016年6月29日上午9:11:27
 */
@Service
public class UserService implements IUserService {
	
	@Autowired
	private AuthUserMapper authUserMapper;

    @Autowired
    private AuthUserRoleExtMapper authUserRoleExtMapper;

	@Resource
	private PasswordHelper passwordHelper;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleService roleService;

    @Value("${user.default.password}")
    private String defaultPassword;

	@Override
	public Integer addUser(AuthUser authUser) throws Exception {
		//不能增加超级用户
		if ("admin".equals(authUser.getUsername())) throw new Exception("You can not add admin user");
		//用户已存在
		if (queryByUserName(authUser.getUsername()) != null) throw new Exception("user ["+authUser.getUsername()+"] already exist");
		encryptPassword(authUser);
		authUser.setStatus(true);
		authUser.setCreateTime(new Date());
		authUser.setCreator(AuthUtils.getCurrUserName());
		int result = authUserMapper.insertSelective(authUser);
		return result > 0 ? authUser.getId() : null;
	}

	@Override
	public boolean updateUser(AuthUser authUser) {
		authUser.setUpdateTime(new Date());
		int result = authUserMapper.updateByPrimaryKeySelective(authUser);
		return result > 0;
	}

	@Override
	public boolean delUser(Integer authUserId) {
		int result = authUserMapper.deleteByPrimaryKey(authUserId);
		return result > 0;
	}

	@Override
	public AuthUser select(Integer authUserId) {
		AuthUser authUser = authUserMapper.selectByPrimaryKey(authUserId);
		if (authUser != null && !authUser.getStatus()) {
			return  authUser;
		}
		return null;
	}

	@Override
	public AuthUser queryByUserName(String userName) {
		AuthUserExample example = ctreteExample();
		example.createCriteria()
				.andUsernameEqualTo(userName)
				.andStatusEqualTo(true);
		List<AuthUser> users = authUserMapper.selectByExample(example);
		return users.size() > 0 ? users.get(0) : null;
	}
	
	@Override
	public List<AuthUser> query(AuthUser authUser) {
		AuthUserExample example = new AuthUserExample();
		Criteria criteria = example.createCriteria();
		criteria
				.andUsernameLike(authUser.getUsername())
				.andStatusEqualTo(true);
		return authUserMapper.selectByExample(example);
	}

	@Override
	public List<AuthRole> queryRoles(Integer userId) {
        return authUserRoleExtMapper.queryRoles4User(userId);
	}

    @Override
    public List<AuthRole> queryUnAuthRoles(Integer userId,List<AuthRole> authRoles) {
        List<AuthRole> unAuthRoles = new ArrayList<>();
        List<AuthRole> allRoles = roleService.queryAllRoles();
        if (CollectionUtils.isEmpty(allRoles)){
            return unAuthRoles;
        }
        Set<Integer> authRoleIds = new HashSet<>();
        authRoles.forEach(authRole -> {
            authRoleIds.add(authRole.getId());
        });
        allRoles.forEach(authRole -> {
            if (!authRoleIds.contains(authRole.getId()))
                unAuthRoles.add(authRole);
        });

        return unAuthRoles;
    }

	@Override
	public Set<String> queryRoleCodes(String username) {
        AuthUser authUser = queryByUserName(username);
        Set<String> roleCodeSet = new HashSet<>();
        if (authUser == null) return roleCodeSet;
        List<AuthRole> roles = queryRoles(authUser.getId());
        if (CollectionUtils.isNotEmpty(roles)) {
            roles.forEach(authRole -> roleCodeSet.add(authRole.getRolecode()));
        }
        return roleCodeSet;
	}

	@Override
	public Set<String> queryPermissions(String username) {
        //获取用户
        AuthUser authUser = queryByUserName(username);
        if (authUser != null) {
            return authUserRoleExtMapper.queryPerms4User(authUser.getId());
        }
        return null;
	}
	
	@Override
	public boolean updatePassword(String oldPassword , String newPassword) throws Exception {

		String currentUserName = AuthUtils.getCurrUserName();
		AuthUser currentUser = queryByUserName(currentUserName);
		if (!passwordHelper.checkPassword(currentUser.getSalt(),oldPassword,currentUser.getPassword())) {
			throw new Exception("原密码错误");
		}
		AuthUser authUser = new AuthUser();
        authUser.setId(currentUser.getId());
		authUser.setPassword(newPassword);
        authUser.setUpdateTime(new Date());
		encryptPassword(authUser);
		return updateUser(authUser);

	}

    public boolean resetPassword(String userName, String newPassword){
        AuthUser oldAuthUser = queryByUserName(userName);
        AuthUser authUser = new AuthUser();
        authUser.setId(oldAuthUser.getId());
        authUser.setPassword(StringUtils.isBlank(newPassword) ?defaultPassword : newPassword);
        authUser.setUpdateTime(new Date());
        encryptPassword(authUser);
        return updateUser(authUser);
    }

	@Override
	public BaseQueryResult<AuthUser> queryUsers(UserQueryContract userQueryContract) {
		AuthUserExample example = new AuthUserExample();
        AuthUserExample.Criteria criteria =  example.createCriteria();
        criteria.andStatusEqualTo(true);
        if (StringUtils.isNotBlank(userQueryContract.getUserName()))
            criteria.andUsernameLike(SqlUtils.wrapLike(userQueryContract.getUserName()));
        return BaseModel.selectByPage(authUserMapper,example
                ,userQueryContract.getPage(),userQueryContract.getPage()==null);
	}

    @Override
    public int updateUserGrant(AuthUser authUser,List<Integer> roleIds) {
        String currentUser = SecurityUtils.getSubject().getPrincipal().toString();
        List<AuthUserRole> userRoles = new ArrayList<>();
        roleIds.forEach(roleId -> {
            AuthUserRole userRole = new AuthUserRole();
            userRole.setUserId(authUser.getId());
            userRole.setRoleId(roleId);
            userRole.setCreateTime(new Date());
            userRole.setCreator(currentUser);
            userRoles.add(userRole);
        });
		userRoleService.delByUserId(authUser.getId());
		if (CollectionUtils.isNotEmpty(userRoles)) {
			return userRoleService.insertBatch(userRoles);
		} else {
			return 1;
		}
    }

	@Override
	public int delUserLogic(String username) {
		AuthUser authUser = new AuthUser();
		authUser.setUpdateTime(new Date());
		authUser.setStatus(false);
        AuthUserExample example = new AuthUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        return authUserMapper.updateByExampleSelective(authUser,example);
	}

	/**
     * 用户密码加密
     * @param authUser
     */
	private void encryptPassword(AuthUser authUser) {
		Map<String, String> map = passwordHelper.encryptPassword(authUser.getPassword());
		authUser.setSalt(map.get("salt"));
		authUser.setPassword(map.get("password"));
	}

	private AuthUserExample ctreteExample() {
		return new AuthUserExample();
	}


}
