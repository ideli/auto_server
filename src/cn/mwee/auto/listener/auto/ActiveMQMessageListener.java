package cn.mwee.auto.listener.auto;

import cn.mwee.auto.deploy.model.FlowTask;
import cn.mwee.auto.misc.mq.AppEvent;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by raoshaoquan on 16/7/7.
 */
public class ActiveMQMessageListener implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(ActiveMQMessageListener.class);

    private FlowTaskListener flowTaskListener;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                flowTaskListener.replyMessage(((TextMessage) message).getText());
            } else {
                logger.error("receive a nonsupport message {}",message.getJMSMessageID().toString());
            }
        } catch (Exception e) {
            logger.error("ActiveMQMessageListener.onMessage => ", e);
        }
    }

    public FlowTaskListener getFlowTaskListener() {
        return flowTaskListener;
    }

    public void setFlowTaskListener(FlowTaskListener flowTaskListener) {
        this.flowTaskListener = flowTaskListener;
    }
}
