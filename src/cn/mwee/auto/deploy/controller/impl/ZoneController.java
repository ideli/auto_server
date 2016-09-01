package cn.mwee.auto.deploy.controller.impl;

import cn.mwee.auto.deploy.AutoAbstractController;
import cn.mwee.auto.deploy.contract.template.AddTemplateRequest;
import cn.mwee.auto.deploy.contract.template.QueryTemplatesRequest;
import cn.mwee.auto.deploy.contract.zone.*;
import cn.mwee.auto.deploy.controller.ITemplateController;
import cn.mwee.auto.deploy.controller.IZoneController;
import cn.mwee.auto.deploy.model.AutoTemplate;
import cn.mwee.auto.deploy.model.Zone;
import cn.mwee.auto.deploy.service.IZoneService;
import cn.mwee.auto.misc.aspect.contract.Contract;
import cn.mwee.auto.misc.aspect.contract.Model;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by huming on 16/8/2.
 */
@Controller
public class ZoneController extends AutoAbstractController implements IZoneController
{
    @Autowired
    private IZoneService zoneService;

    @Override
    @Contract(QueryZoneRequest.class)
    public NormalReturn queryZones(ServiceRequest request) {
        QueryZoneRequest contract = request.getContract();

        QueryZonesResult result = zoneService.queryZones(contract);

        return new NormalReturn(result);
    }

    @Override
    @Model(contract = AddZoneRequest.class, model = Zone.class)
    @RequiresAuthentication
    public NormalReturn addZone(ServiceRequest request) {
        Zone zone = request.getModel();
        int zoneId = zoneService.addZone(zone);
        return new NormalReturn(zoneId);
    }

    @Override
    @Contract(ZoneIdQuery.class)
    @RequiresAuthentication
    public NormalReturn getZone(ServiceRequest request) {
        ZoneIdQuery contract = request.getContract();
        Zone zone = zoneService.getZone(contract.getZoneId());
        return new NormalReturn(zone);
    }

    @Override
    @Contract(ZoneIdQuery.class)
    @RequiresAuthentication
    public NormalReturn deleteZone(ServiceRequest request) {
        ZoneIdQuery contract = request.getContract();
        boolean delSuccess = zoneService.deleteZone(contract.getZoneId());
        return new NormalReturn(delSuccess);
    }

    @Override
    @Model(contract = ModifyZoneRequest.class, model = Zone.class)
    @RequiresAuthentication
    public NormalReturn modifyZone(ServiceRequest request) {
        Zone zone = request.getModel();
        boolean modifySuccess = zoneService.modifyZone(zone);
        return new NormalReturn(modifySuccess);
    }
}
