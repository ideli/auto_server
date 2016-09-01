package cn.mwee.auto.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yangjunming on 6/20/16.
 */
public final class MessageUtil {

    private Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    private ConnectionFactory connectionFactory;
    private long receiveTimeout;//接收超时时间
    private JmsTemplate jmsTemplate;
    private List<String> destinationQueueNames;
    private final static String BACKUP_QUEUE_SUFFIX = "_B";
    private boolean autoBackup = false;//是否自动将消息备份到_b的队列，方便调试


    public MessageUtil(final ConnectionFactory connectionFactory, final long receiveTimeout, final List<String> destinationQueueNames) {
        this.connectionFactory = connectionFactory;
        this.receiveTimeout = receiveTimeout;
        this.destinationQueueNames = new ArrayList<>();
        this.destinationQueueNames.addAll(destinationQueueNames.stream().collect(Collectors.toList()));
        jmsTemplate = new JmsTemplate(this.connectionFactory);
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTargetType(MessageType.TEXT);
        jmsTemplate.setMessageConverter(messageConverter);
        jmsTemplate.setReceiveTimeout(this.receiveTimeout);
    }

    public MessageUtil(ConnectionFactory connectionFactory, List<String> destinationQueueNames) {
        this(connectionFactory, 10000, destinationQueueNames);
    }

    public MessageUtil() {
    }

    public void convertAndSend(Object message) {
        if (ListUtil.isEmpty(destinationQueueNames)) {
            logger.error("目标队列为空，无法发送，请检查配置！message => " + message.toString());
            return;
        }
        for (String dest : destinationQueueNames) {
            jmsTemplate.convertAndSend(dest, message);
            if (autoBackup) {
                jmsTemplate.convertAndSend(dest + BACKUP_QUEUE_SUFFIX, message);
            }
        }
    }

    public void convertAndSend(Object message, MessagePostProcessor messagePostProcessor) {
        if (ListUtil.isEmpty(destinationQueueNames)) {
            logger.error("目标队列为空，无法发送，请检查配置！message => " + message.toString());
            return;
        }
        for (String dest : destinationQueueNames) {
            jmsTemplate.convertAndSend(dest, message, messagePostProcessor);
            if (autoBackup) {
                jmsTemplate.convertAndSend(dest + BACKUP_QUEUE_SUFFIX, message, messagePostProcessor);
            }
        }
    }

    public void convertAndSendDelay(Object message, long delay) {
        convertAndSend(message,new ScheduleMessagePostProcessor(delay));
    }

    public void convertAndSend(String destinationName, Object message) {
        if (StringUtils.isBlank(destinationName)) {
            logger.error("目标队列为空，无法发送，请检查配置！message => " + message.toString());
            return;
        }
        jmsTemplate.convertAndSend(destinationName, message);
        if (autoBackup) {
            jmsTemplate.convertAndSend(destinationName + BACKUP_QUEUE_SUFFIX, message);
        }
    }


    public void convertAndSend(String destinationName, Object message, MessagePostProcessor messagePostProcessor) {
        if (StringUtils.isBlank(destinationName)) {
            logger.error("目标队列为空，无法发送，请检查配置！message => " + message.toString());
            return;
        }
        jmsTemplate.convertAndSend(destinationName, message, messagePostProcessor);
        if (autoBackup) {
            jmsTemplate.convertAndSend(destinationName + BACKUP_QUEUE_SUFFIX, message, messagePostProcessor);
        }
    }

    public static String getText(javax.jms.Message message) {
        if (message instanceof TextMessage) {
            try {
                return ((TextMessage) message).getText();
            } catch (JMSException e) {
                return message.toString();
            }
        }
        return message.toString();
    }

    public String getFirstDestination() {
        if (ListUtil.isEmpty(destinationQueueNames)) {
            return null;
        }
        return destinationQueueNames.get(0);
    }


    public boolean isAutoBackup() {
        return autoBackup;
    }

    public void setAutoBackup(boolean autoBackup) {
        this.autoBackup = autoBackup;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public List<String> getDestinationQueueNames() {
        return destinationQueueNames;
    }

    public void setDestinationQueueNames(List<String> destinationQueueNames) {
        this.destinationQueueNames = destinationQueueNames;
    }
}
