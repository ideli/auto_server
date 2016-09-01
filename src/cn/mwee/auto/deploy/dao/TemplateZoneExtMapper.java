package cn.mwee.auto.deploy.dao;

import cn.mwee.auto.deploy.model.ZoneStateModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TemplateZoneExtMapper {

    @Select("SELECT z.*,tz.state,tz.id as templateZoneId FROM template_zones tz LEFT JOIN zones z ON tz.zone_id=z.id WHERE tz.template_id = #{templateId}")
    @ResultType(ZoneStateModel.class)
    List<ZoneStateModel> selectZoneState(@Param("templateId") Integer templateId);
}