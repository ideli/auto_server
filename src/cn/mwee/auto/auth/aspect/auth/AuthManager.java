/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.aspect.auth;

import cn.mwee.auto.auth.util.AuthUtils;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalReturn;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.apache.shiro.util.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author mengfanyuan
 * 2016年6月29日下午7:42:50
 */
@Aspect
@Order(0)
@Component
public class AuthManager {
	@Pointcut("@annotation(cn.mwee.auto.misc.aspect.contract.Contract)||@annotation(cn.mwee.auto.misc.aspect.contract.Model)")
	public void auth(){}

	@Around("auth()")
	public Object auth(ProceedingJoinPoint joinPoint) throws Throwable{
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Object[] args = joinPoint.getArgs();
		if(args != null && args.length > 0 && args[0] instanceof ServiceRequest
				&& signature.getReturnType().getName().equals("cn.mwee.auto.misc.resp.NormalReturn")){
			ServiceRequest req = (ServiceRequest)args[0];
			String token = req.getJson().getString("token");
			if (StringUtils.isNotBlank(token)) {
				try {
                    /*
					Session session = SecurityUtils.getSecurityManager().getSession(new DefaultSessionKey(token));
					session.touch();
					Subject subject = (Subject)session.getAttribute("subject");
                    Subject newSubject = new DelegatingSubject(subject.getPrincipals(),true,null,session,SecurityUtils.getSecurityManager());
                    */
                    Subject subject = AuthUtils.getSubject(token);
                    if (subject!=null){
                        ThreadContext.bind(AuthUtils.getSubject(token));
                    }
                    if (SecurityUtils.getSubject().isAuthenticated()) {
                        return new NormalReturn("503","用户状态异常");
                    }
				} catch (Exception e) {

				}
			}
		}
		try {
			return joinPoint.proceed();
		} catch (Exception e){
			if (e instanceof UnauthenticatedException) {
				return new NormalReturn("502",e.getMessage(),"noLogin");
			} else if (e instanceof UnauthorizedException) {
				return new NormalReturn("502",e.getMessage(),"noPermission");
			} else {
				throw e;
			}
		}

	}
/*
	@AfterThrowing(pointcut = "auth()",throwing = "e")
	public Object handleException(JoinPoint joinPoint, Throwable e) throws Throwable {
		if (e instanceof UnauthenticatedException) {
			return new NormalReturn("502","noLogin",e.getMessage());
		} else if (e instanceof UnauthorizedException) {
			return new NormalReturn("502","noPermission",e.getMessage());
		} else {
			throw e;
		}
	}*/
}
