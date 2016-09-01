package cn.mwee.auto.util;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Created by huming on 16/8/10.
 */
public class TestSendMail {

    public static void main(String[] args) {
        boolean isSSL = true;
        String host = "smtp.exmail.qq.com";
        int port = 465;
        String from = "auto@puscene.com";
        String to = "ma.jinglong@puscene.com";
        String username = "auto@puscene.com";
        String password = "Mw!321Wm#@#";

        try {
            Email email = new SimpleEmail();
//            email.setSSLOnConnect(isSSL);
            email.setHostName(host);
//            email.setSmtpPort(port);
            email.setAuthentication(username, password);
            email.setFrom(from,"听说你好屌");
            email.addTo(to);
            email.setSubject("技术专家");
            email.setMsg("你再屌一下!");
            email.setSocketTimeout(3000);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }

        System.out.println("发送完毕！");
    }
}