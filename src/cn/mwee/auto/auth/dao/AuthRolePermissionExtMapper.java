package cn.mwee.auto.auth.dao;

import cn.mwee.auto.auth.model.AuthPermission;
import cn.mwee.auto.auth.model.AuthRolePermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface AuthRolePermissionExtMapper {
    /**
     * batch insert
     * @param list
     * @return
     */
    int insertBatch(List<AuthRolePermission> list);


    @Select("SELECT p.* FROM auth_permission p LEFT JOIN auth_role_permission rp ON p.id = rp.permission_id WHERE rp.role_id = #{roleId}")
    @ResultMap("cn.mwee.auto.auth.dao.AuthPermissionMapper.BaseResultMap")
    List<AuthPermission> queryPerms4Role(@Param("roleId") Integer roleId);

    @Select("SELECT p.id FROM auth_permission p LEFT JOIN auth_role_permission rp ON p.id = rp.permission_id WHERE rp.role_id = #{roleId}")
    Set<Integer> queryPermIds4Role(@Param("roleId") Integer roleId);

}