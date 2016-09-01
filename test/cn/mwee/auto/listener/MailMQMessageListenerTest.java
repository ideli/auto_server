package cn.mwee.auto.listener;

import cn.mwee.auto.common.util.MessageUtil;
import cn.mwee.auto.deploy.model.Mail;
import cn.mwee.auto.misc.mq.AppEvent;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by raoshaoquan on 16/8/10.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/applicationContext.xml",})
public class MailMQMessageListenerTest {

    @Resource(name = "mailMQSender")
    private MessageUtil mailMQSender;

    @Test
    public void sendMail() {
        Mail mail = new Mail();
        mail.setSubject("美味不用等自动化提醒");
        mail.setMailBody("邮件发送测试!");
        AppEvent event = new AppEvent(0, "soa.notify.mail.create", mail);
        mailMQSender.convertAndSend(event);
    }

    @Test
    public void simpleEmail() {
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.exmail.qq.com");
        email.setAuthentication("alert.sender@puscene.com", "dongpoShi1geshuaige");// 邮件服务器验证：用户名/密码
        email.setCharset("utf-8");// 必须放在前面，否则乱码
        try {
            email.addTo("rao.shaoquan@puscene.com");
            email.setFrom("alert.sender@puscene.com", "mw_auto");
            email.setSubject("美味不用等自动化");
            email.setMsg("邮件发送,嘿嘿嘿!");
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
