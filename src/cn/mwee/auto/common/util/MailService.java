package cn.mwee.auto.common.util;


import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Properties;
import java.util.concurrent.FutureTask;

/**
 * 邮件发送
 */
public class MailService {

    private static Logger logger = LoggerFactory.getLogger(MailService.class);

    private String smtpHost = "";
    private int smtpPort = 25;
    private String senderUserName = "";
    private String senderPassword = "";
    private String senderNickName = "";
    private String receiver = "";
    private String ccReceiver = "";
    private String bccReceiver = "";

    public MailService(Properties properties, boolean debug) {
        this.smtpHost = properties.getProperty("mail.smtp.host");
        this.smtpPort = Integer.parseInt(properties.getProperty("mail.smtp.port"));
        this.senderUserName = properties.getProperty("mail.sender.username");
        this.senderPassword = properties.getProperty("mail.sender.password");
        this.senderNickName = properties.getProperty("mail.sender.nickname");

//        if (!StringUtils.isEmpty(senderNickName)) {
//
//            EncodingUtil parse = new EncodingUtil();
//            logger.debug(parse.getEncoding(senderNickName));
//            try {
//                senderNickName = new String(senderNickName.getBytes("ISO-8859-1"), "gbk");
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("发件人呢称编码转换失败！", e);
//                senderNickName = properties.getProperty("mail.sender.nickname");
//            }
//        }
        this.receiver = properties.getProperty("mail.to");
        this.ccReceiver = properties.getProperty("mail.cc");
        this.bccReceiver = properties.getProperty("mail.bcc");
    }

    /**
     * 发送纯文本邮件
     *
     * @param subject     邮件标题
     * @param mailBody    邮件内容
     * @param receiveUser 收件人地址
     */
    public void sendTextMail(String subject, String mailBody,
                             String receiveUser) {
        sendEmail(subject, mailBody, null, receiveUser, null, null);
    }


    /**
     * 发送纯文件邮件
     *
     * @param subject  邮件标题
     * @param mailBody 邮件内容
     */
    public boolean sendTextMail(String subject, String mailBody) {
        return sendEmail(subject, mailBody, this.senderNickName, this.receiver, this.ccReceiver, this.bccReceiver);
    }

    /**
     * 发送纯文本邮件
     *
     * @param subject        邮件标题
     * @param mailBody       邮件内容
     * @param receiveUser    收件人地址
     * @param ccReceiverUser 抄送地址
     */
    public void sendTextMail(String subject, String mailBody,
                             String receiveUser, String ccReceiverUser) {
        sendEmail(subject, mailBody, null, receiveUser, ccReceiverUser, null);
    }

    /**
     * 发送邮件
     *
     * @param subject        邮件主题
     * @param mailBody       邮件内容
     * @param senderNickName 发件人NickName
     * @param receiveUser    收件人地址
     * @param ccReceiveUser  抄送地址
     * @param bccReceiveUser 密送地址
     */
    public boolean sendEmail(final String subject, String mailBody, String senderNickName,
                          String receiveUser, String ccReceiveUser, String bccReceiveUser) {


        try {
            SimpleEmail email = new SimpleEmail();
            email.setHostName(smtpHost);
            email.setAuthentication(senderUserName, senderPassword);// 邮件服务器验证：用户名/密码
            email.setCharset("utf-8");// 必须放在前面，否则乱码

            // 发件人
            email.setFrom(senderUserName, senderNickName);

            // 收件人(支持多个，中间用";"分隔)
            String[] arrTo = receiveUser.split(";");
            for (String to : arrTo) {
                email.addTo(to);
            }

            //抄送人
            if (!StringUtils.isEmpty(ccReceiveUser)) {
                String[] arrCC = ccReceiveUser.split(";");
                for (String cc : arrCC) {
                    email.addCc(cc);
                }
            }

            //密送人
            if (!StringUtils.isEmpty(bccReceiveUser)) {
                String[] arrBCC = bccReceiveUser.split(";");
                for (String bcc : arrBCC) {
                    email.addBcc(bcc);
                }
            }

            email.setSubject(subject);
            String content = mailBody.toString();
            email.setMsg(content);
            email.send();
            logger.info(senderUserName + " 向 " + receiveUser + " 发送邮件成功！");
            return true;
        } catch (Exception e) {
            logger.error("sendEmail失败！", e);
        }
        return false;
    }

    /**
     * 异步发送邮件
     *
     * @param subject        邮件主题
     * @param mailBody       邮件内容
     * @param senderNickName 发件人NickName
     * @param receiveUser    收件人地址
     * @param ccReceiveUser  抄送地址
     * @param bccReceiveUser 密送地址
     */
    public void sendAsyncEmail(final String subject, String mailBody, String senderNickName,
                          String receiveUser, String ccReceiveUser, String bccReceiveUser) {

        FutureTask<String> task = new FutureTask<>(() -> {
            sendEmail(subject, mailBody, senderNickName, receiveUser, ccReceiveUser, bccReceiveUser);
            return null;
        });

        //异步发送
        new Thread(task).start();
    }

}
