package cn.mwee.auto.auth.dao;


import cn.mwee.auto.auth.model.AuthRole;
import cn.mwee.auto.auth.model.AuthUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface AuthUserRoleExtMapper {

    /**
     * 批量插入
     * @return
     */
    int insertBatch(List<AuthUserRole> list);


    @Select("SELECT r.* FROM auth_user_role ur LEFT JOIN auth_role r ON ur.role_id = r.id WHERE ur.user_id = #{userId}")
    @ResultMap("cn.mwee.auto.auth.dao.AuthRoleMapper.BaseResultMap")
    List<AuthRole> queryRoles4User(@Param("userId") Integer userId);

    @Select("SELECT p.`code` FROM auth_permission p " +
            "LEFT JOIN auth_role_permission rp on p.id = rp.permission_id " +
            "LEFT JOIN auth_user_role ur ON rp.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    Set<String> queryPerms4User(@Param("userId") Integer userId);
}