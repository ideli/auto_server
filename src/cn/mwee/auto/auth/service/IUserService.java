/**
 * 
 */
package cn.mwee.auto.auth.service;

import java.util.List;
import java.util.Set;

import cn.mwee.auto.auth.contract.user.UserQueryContract;
import cn.mwee.auto.auth.model.AuthRole;
import cn.mwee.auto.auth.model.AuthUser;
import cn.mwee.auto.common.db.BaseQueryResult;
import cn.mwee.auto.deploy.contract.commom.BaseContract;

/**
 * 用户信息维护
 * 
 * @author mengfanyuan
 * 2016年6月28日下午7:44:37
 */
public interface IUserService {

	/**
	 * query user by userId
	 * @param authUserId
	 * @return
	 */
	AuthUser select(Integer authUserId);

	/**
	 * query by userName
	 * @param userName
	 * @return
     */
	AuthUser queryByUserName(String userName);
	
	/**
	 * query user by condition
	 * @param authUser
	 * @return
	 */
	List<AuthUser> query(AuthUser authUser);
	
	/**
	 * create user
	 * @param authUser
	 * @return
	 */
	Integer addUser(AuthUser authUser) throws Exception;
	
	/**
	 * update user by userId
	 * @param authUser
	 * @return
	 */
	boolean updateUser(AuthUser authUser);

	/**
	 * 修改密码
	 * @param oldPassw
	 * @param newPassword
	 * @return
	 * @throws Exception
     */
	boolean updatePassword(String oldPassw , String newPassword) throws Exception;

	/**
	 * 重置密码
	 * @param userName 用户名
	 * @param newPassword 新密码
     * @return
     */
	boolean resetPassword(String userName, String newPassword);

	/**
	 * delete user by userId
	 * @param authUserId
	 * @return
	 */
	boolean delUser(Integer authUserId);
	
	/**
	 * 用户角色
	 * @param userId
	 * @return
	 */
	List<AuthRole> queryRoles(Integer userId);

	/**
	 * 用户未授权的角色
	 * @param userId 用户Id
	 * @param authRoles 已授权的角色；
     * @return
     */
	List<AuthRole> queryUnAuthRoles(Integer userId,List<AuthRole> authRoles);

	/**
	 * 用户角色codes
	 * @param username
	 * @return
	 */
	Set<String> queryRoleCodes(String username);

	/**
	 * 用户权限
	 * @param username
	 * @return
	 */
	Set<String> queryPermissions(String username);

	/**
	 * 查询用户列表
	 * @return
     */
	BaseQueryResult<AuthUser> queryUsers(UserQueryContract contract);

	/**
	 * 用户授权角色
	 * @param authUser
	 * @param roles
     */
	int updateUserGrant(AuthUser authUser, List<Integer> roleIds);

	/**
	 * 逻辑删除用户
	 * @param username
	 * @return
     */
	int delUserLogic(String username);
}
