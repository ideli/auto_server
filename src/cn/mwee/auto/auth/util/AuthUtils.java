package cn.mwee.auto.auth.util;

import cn.mwee.auto.auth.model.AuthUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.apache.shiro.util.ThreadContext;

/**
 * Created by Administrator on 2016/7/29.
 */
public class AuthUtils {
    public static String getCurrUserName() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        return principal == null ? "" : principal.toString();
    }

    public static AuthUser getCurrentUser(String token) {
        AuthUser user = null;
        try {
            Session session = SecurityUtils.getSecurityManager().getSession(new DefaultSessionKey(token));
            user = (AuthUser) session.getAttribute("currentUser");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static Subject getSubject(String token) {
        Subject subject = null;
        try {
            Session session = SecurityUtils.getSecurityManager().getSession(new DefaultSessionKey(token));
            session.touch();
            subject = new DelegatingSubject((PrincipalCollection) session.getAttribute("principals"),true,null,session,false,SecurityUtils.getSecurityManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }

}
