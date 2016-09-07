package cn.mwee.auto.deploy.service;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface IChangeLogService {
    void addChangeLogAsyn(Byte logType, Byte operateType,Integer dataId,Object changeData);
}
