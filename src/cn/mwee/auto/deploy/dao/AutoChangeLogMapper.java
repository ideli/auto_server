package cn.mwee.auto.deploy.dao;

import cn.mwee.auto.deploy.model.AutoChangeLog;
import cn.mwee.auto.deploy.model.AutoChangeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AutoChangeLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    int countByExample(AutoChangeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    int deleteByExample(AutoChangeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    int insert(AutoChangeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    int insertSelective(AutoChangeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    List<AutoChangeLog> selectByExample(AutoChangeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    AutoChangeLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") AutoChangeLog record, @Param("example") AutoChangeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") AutoChangeLog record, @Param("example") AutoChangeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AutoChangeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table auto_change_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AutoChangeLog record);
}