package cn.mwee.auto.deploy;

import cn.mwee.auto.misc.controller.IController;
import org.apache.shiro.SecurityUtils;

/**
 * Created by huming on 16/7/21.
 */
public abstract class AutoAbstractController{

    public String getCurrentUserName()
    {
       return SecurityUtils.getSubject().getPrincipal().toString();
    }

}
