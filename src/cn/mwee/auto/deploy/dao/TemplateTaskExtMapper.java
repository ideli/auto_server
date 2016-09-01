package cn.mwee.auto.deploy.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by huming on 16/6/24.
 */
public interface TemplateTaskExtMapper extends TemplateTaskMapper {

    @Select("SELECT DISTINCT `group` FROM template_tasks WHERE template_id=#{template_id} ORDER BY `group` ASC")
    List<Byte> distinctGroups(@Param("template_id")int templateId);


}
