package cn.mwee.auto.deploy.service.execute;

import cn.mwee.auto.deploy.model.ZoneMonitorTask;
import cn.mwee.auto.deploy.service.ITemplateManagerService;
import cn.mwee.auto.deploy.util.AutoConsts.ZoneState;
import cn.mwee.auto.misc.common.util.SSHManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/12.
 */
@Component
public class SimpleZoneMonitorExecutor {
    private Logger logger = LoggerFactory.getLogger(SimpleZoneMonitorExecutor.class);

    @Value("${auto.ssh.auths}")
    private String sshAuthStrs;

    @Resource
    private ITemplateManagerService templateManagerService;

    private Map<String, String> sshAuthMap = new HashMap<>();

    public void execute(ZoneMonitorTask zoneMonitorTask){
        String state = executeTask(zoneMonitorTask);
        templateManagerService.updateTemplateZoneStatus(zoneMonitorTask.getTemplateZoneId(), state);
    }

    @PostConstruct
    public void init() {
        logger.info("sshAuthStrs:{}",sshAuthStrs);
        String[] sshAuths = sshAuthStrs.split(";");
        for (String sshAuth : sshAuths) {
            if (StringUtils.isEmpty(sshAuth)) continue;
            String[] sshAuthInfo = sshAuth.split("@");
            logger.info("sshAuthInfo[0]:{}",sshAuthStrs);
            sshAuthMap.put(sshAuthInfo[0], sshAuthInfo[1]);
        }
    }

    private String executeTask(ZoneMonitorTask zoneMonitorTask){
        SSHManager instance = null;
        try {
            String command = zoneMonitorTask.getMonitorShell();
            String sshShellUser = zoneMonitorTask.getMonitorUser();
            String sshPriAddr = sshAuthMap.get(sshShellUser);
            String exeTargetHost = zoneMonitorTask.getExeTargetHost();
            logger.info("Monitor task user:[{}],host:[{}],command:[{}]",sshShellUser,exeTargetHost,command);
            instance = new SSHManager(sshShellUser, sshPriAddr, exeTargetHost,null,null,null);
            String errMsg = instance.connect();
            if (errMsg != null) throw new Exception(errMsg);
            BufferedReader reader = new BufferedReader(new InputStreamReader(instance.sendCmd(command)));
            String lineStr;
            StringBuilder outputBuffer = new StringBuilder();
            while ((lineStr = reader.readLine()) != null) {
                outputBuffer.append(lineStr);
            }
            if (outputBuffer.toString().contains("MW_SUCCESS")){
                return ZoneState.RUNNING.name();
            } else {
                return ZoneState.ERROR.name();
            }
        } catch (Exception e) {
            logger.error("",e);
            return ZoneState.UNKNOWN.name();
        } finally {
            try {
                if (instance !=null) instance.close();
            } catch (Exception e) {
                logger.error("",e);
            }
        }
    }

}
