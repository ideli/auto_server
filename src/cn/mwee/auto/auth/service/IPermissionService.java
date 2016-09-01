/**
 * 
 */
package cn.mwee.auto.auth.service;

import java.util.List;

import cn.mwee.auto.auth.contract.permission.PermissionContract;

import cn.mwee.auto.auth.contract.permission.PermissionQueryContract;
import cn.mwee.auto.auth.model.AuthMenu;
import cn.mwee.auto.auth.model.AuthPermission;
import cn.mwee.auto.common.db.BaseQueryResult;

/**
 * 权限信息维护服务
 * 
 * @author mengfanyuan
 * 2016年6月28日下午7:45:35
 */
public interface IPermissionService {
	/**
	 * create AutePermission
	 * 
	 * @param authPermission
	 * @return
	 */
	boolean add(AuthPermission authPermission);
	
	/**
	 * update authPermission by Id
	 * 
	 * @param authPermission
	 * @return
	 */
	boolean update(AuthPermission authPermission);
	
	/**
	 * delete authPermission by Id
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Integer id) throws Exception;
	
	/**
	 * query authPermission by Id
	 * @param Id
	 * 
	 * @return
	 */
	AuthPermission select(Integer Id);

	/**
	 * 根据code查找
	 * @param code
	 * @return
     */
	AuthPermission selectByCode(String code);

	/**
	 * 名称查找
	 * @param name 名称
	 * @param isProject 是否是项目
	 * @return
     */
	AuthPermission selectByName(String name,boolean isProject);

	/**
	 * query authPermission by condition
	 * 
	 * @param permissionQuery
	 * @return
	 */
	BaseQueryResult<AuthPermission> query(PermissionQueryContract permissionQuery);


	/**
	 * 获取当前用户左侧菜单树
	 * @param userId
	 * @return
     */
	List<AuthMenu> getLeftMenu(Integer userId);

	/**
	 * 根据级别查询菜单
	 * @param level
	 * @return
     */
	List<AuthPermission> queryLevelMenu(Byte type,Byte level);

    /**
     * 获取权限树
     * @return
     */
	List<AuthMenu> queryPermTree() ;

	/**
	 * 获取页面下的按钮权限
	 * @param pageUrl 页面地址
	 * @param userId 用户Id
     * @return
     */
    List<AuthPermission> getBtnPerms4Page(String pageUrl,Integer userId);

}
