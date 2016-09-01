package cn.mwee.auto.deploy.service.execute;

import cn.mwee.auto.auth.model.AuthUser;
import cn.mwee.auto.common.util.MessageUtil;
import cn.mwee.auto.deploy.model.Mail;
import cn.mwee.auto.deploy.service.IProjectService;
import com.alibaba.druid.sql.visitor.functions.If;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
@Component
public class SimpleMailSender {

    @Resource(name = "mailMQSender")
    private MessageUtil mailMQSender;

    @Resource
    private IProjectService projectService;

    @Resource(name = "simpleExecutor")
    private ThreadPoolTaskExecutor simpleExecutor;

    public void sendNotice4Project(Integer projectId,String content) {
        List<AuthUser> users = projectService.getUsers4Project(projectId);
        users.forEach(authUser -> {
            if (StringUtils.isNotBlank(authUser.getEmail())) {
                Mail mail = new Mail();
                mail.setBccReceiveUser(authUser.getEmail());
                mail.setSubject("美味自动化提醒");
                mail.setSenderNickName("美味自动化");
                mail.setMailBody(content);
                sendMail(mail);
            }
        });
    }
    public void sendNotice4ProjectAsync(Integer projectId,String content) {
        simpleExecutor.submit(() -> {
            List<AuthUser> users = projectService.getUsers4Project(projectId);
            users.forEach(authUser -> {
                if (StringUtils.isNotBlank(authUser.getEmail())) {
                    Mail mail = new Mail();
                    mail.setReceiveUser(authUser.getEmail());
                    mail.setSubject("美味自动化提醒");
                    mail.setSenderNickName("美味自动化");
                    mail.setMailBody(content);
                    sendMailAsync(mail);
                }
            });
        });
    }

    public void sendMail(Mail mail){
        mailMQSender.convertAndSend(mail);
    }

    public void sendMailAsync (Mail mail) {
        simpleExecutor.submit(() -> mailMQSender.convertAndSend(mail));
    }

}
