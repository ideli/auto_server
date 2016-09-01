package cn.mwee.auto.deploy.controller;

import cn.mwee.auto.deploy.model.Zone;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;

/**
 * Created by huming on 16/8/2.
 */
public interface IZoneController extends IController
{
    NormalReturn queryZones(ServiceRequest request);

    NormalReturn addZone(ServiceRequest request);

    NormalReturn getZone(ServiceRequest request);

    NormalReturn deleteZone(ServiceRequest request);

    NormalReturn modifyZone(ServiceRequest request);

}
