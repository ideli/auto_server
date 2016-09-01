package cn.mwee.auto.auth.dao;

import cn.mwee.auto.auth.model.AuthRole;
import cn.mwee.auto.auth.model.AuthRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    int countByExample(AuthRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    int deleteByExample(AuthRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    int insert(AuthRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    int insertSelective(AuthRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    List<AuthRole> selectByExample(AuthRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    AuthRole selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") AuthRole record, @Param("example") AuthRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") AuthRole record, @Param("example") AuthRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AuthRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auth_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AuthRole record);
}