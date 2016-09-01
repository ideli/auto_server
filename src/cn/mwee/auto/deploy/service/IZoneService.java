package cn.mwee.auto.deploy.service;

import cn.mwee.auto.deploy.contract.zone.QueryZoneRequest;
import cn.mwee.auto.deploy.contract.zone.QueryZonesResult;
import cn.mwee.auto.deploy.model.Zone;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;

/**
 * Created by huming on 16/8/2.
 */
public interface IZoneService {

    QueryZonesResult queryZones(QueryZoneRequest request);

    int addZone(Zone zone);

    int addZone(String ip);

    Zone getZone(int zoneId);

    boolean deleteZone(int zoneId);

    boolean modifyZone(Zone zone);
}
