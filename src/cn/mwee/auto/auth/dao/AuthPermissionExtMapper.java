package cn.mwee.auto.auth.dao;

import cn.mwee.auto.auth.model.AuthMenu;
import cn.mwee.auto.auth.model.AuthPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public interface AuthPermissionExtMapper {
    /**
     * 获取所有菜单权限
     * @return
     */
    List<AuthMenu> selectPermTree(Integer parentId);

    /**
     * 获取子菜单权限
     * @param parentId
     * @return
     */
    List<AuthMenu> selectChildMenu(Integer parentId);

    /**
     * 获取用户菜单权限
     * @param userId
     * @return
     */
    List<AuthMenu> selectPrivateMenu(@Param("userId") Integer userId);

    /**
     * 主键查询
     * @param id
     * @return
     */
    AuthMenu selectPermTreeByPrimaryKey(Integer id);


    /**
     * 查询用户权限
     * @param type 权限类型
     * @param userId 用户Id
     * @param parentId 父级权限Id
     * @return
     */
    List<AuthPermission> selectPrivatePerm(@Param("type") Byte type
            ,@Param("userId")Integer userId,@Param("parentId")Integer parentId);

}
