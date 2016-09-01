package cn.mwee.auto.deploy.model;

import lombok.Data;

/**
 * Created by raoshaoquan on 16/8/10.
 */
@Data
public class Mail {
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

    private String subject;
    private String mailBody;
    private String senderNickName;
    private String receiveUser;
    private String ccReceiveUser;
    private String bccReceiveUser;
}
