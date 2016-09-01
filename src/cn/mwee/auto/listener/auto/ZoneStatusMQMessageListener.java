package cn.mwee.auto.listener.auto;

import cn.mwee.auto.common.util.MailService;
import cn.mwee.auto.deploy.model.Mail;
import cn.mwee.auto.deploy.model.TemplateZone;
import cn.mwee.auto.deploy.model.ZoneMonitorTask;
import cn.mwee.auto.deploy.service.execute.SimpleZoneMonitorExecutor;
import cn.mwee.auto.misc.mq.AppEvent;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by raoshaoquan on 16/8/10.
 */
public class ZoneStatusMQMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(ZoneStatusMQMessageListener.class);

    @Resource
    private SimpleZoneMonitorExecutor simpleZoneMonitorExecutor;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                try {
                    String jsonString = ((TextMessage) message).getText();
                    logger.debug("MailMQMessageListener.onMessage => {}", jsonString);
                    AppEvent evt = JSON.parseObject(jsonString, AppEvent.class);
                    ZoneMonitorTask zoneMonitorTask = evt.getContentObject(ZoneMonitorTask.class);
                    simpleZoneMonitorExecutor.execute(zoneMonitorTask);
                } catch (Exception e) {
                    logger.error("MailMQMessageListener.onMessage => ", e);
                }
            } else {
                logger.error("MailMQMessageListener.onMessage nonsupport message {}", message.getJMSMessageID().toString());
            }
        } catch (Exception e) {
            logger.error("MailMQMessageListener.onMessage => ", e);
        }
    }

}
