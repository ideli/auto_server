package cn.mwee.auto.deploy.dao;

import cn.mwee.auto.auth.model.AuthPermission;
import cn.mwee.auto.auth.model.AuthUser;
import cn.mwee.auto.auth.model.ProjectUserExtModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProjectUserExtMapper {

    @Select("SELECT u.* FROM auth_user u LEFT JOIN project_user pu ON u.id = pu.userId WHERE pu.projectId = #{projectId}")
    @ResultMap("cn.mwee.auto.auth.dao.AuthUserMapper.BaseResultMap")
    List<AuthUser> selectUsers4Project(@Param("projectId") Integer projectId);

    @Select("SELECT p.* FROM project_user pu LEFT JOIN auth_permission p ON p.id = pu.projectId WHERE pu.userId = #{userId}")
    @ResultMap("cn.mwee.auto.auth.dao.AuthPermissionMapper.BaseResultMap")
    List<AuthPermission> selectProjects4User(@Param("userId") Integer userId);

    @Select("SELECT pu.id as id , pu.userType,u.id userId,u.email,u.`name`,u.username,u.phoneNo,u.department " +
            "FROM auth_user u LEFT JOIN project_user pu ON u.id = pu.userId WHERE pu.projectId = #{projectId}")
    @ResultType(ProjectUserExtModel.class)
    List<ProjectUserExtModel> selectProjectUsers(@Param("projectId") Integer projectId);

}