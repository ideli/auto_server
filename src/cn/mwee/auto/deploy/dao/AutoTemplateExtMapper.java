package cn.mwee.auto.deploy.dao;

import cn.mwee.auto.deploy.model.AutoTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13.
 */
public interface AutoTemplateExtMapper {

    @Select("SELECT * FROM templates t WHERE t.inuse = 1 AND t.pid = 0 AND t.id in (SELECT template_id FROM template_zones tz WHERE tz.zone_id in (SELECT id FROM zones WHERE ip = #{host}))")
    List<AutoTemplate> getTemplate4Zone(@Param("host") String host);
}
