package cn.mwee.auto.misc.handler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import cn.mwee.auto.misc.common.exception.ServiceException;
import cn.mwee.auto.misc.common.server.Handler;
import cn.mwee.auto.misc.common.server.NettyHttpRequest;
import cn.mwee.auto.misc.common.server.resp.Resp;
import cn.mwee.auto.misc.common.util.CostTime;
import cn.mwee.auto.misc.controller.IController;
import cn.mwee.auto.misc.req.ServiceRequest;
import cn.mwee.auto.misc.resp.NormalResp;
import cn.mwee.auto.misc.resp.StringResp;

/**
 * request  body 为 xml 
 * response body 为 xml
 * @author huming
 *
 */
public class StringHandler implements Handler{
	protected IController adaptorController;
	
	protected static Logger log = LoggerFactory.getLogger(StringHandler.class);
	
	public StringHandler(){
		super();
	}
	
	public IController getAdaptorService() {
		return adaptorController;
	}

	@Override
	public Resp handleRequest(NettyHttpRequest request) 
	{
		StringResp resp = null;
		String nr = null;
		CostTime costTime = new CostTime();
		costTime.start();
		try{
			ServiceRequest serviceReq = parseReq(request);
			String body = serviceReq.getRequest().contentAsString();
			
			if(log.isInfoEnabled())
				log.info(" [ REQUEST: {} ] -> {}", request.params(), body);
			
			nr = this.doAction(serviceReq,request);
		}catch(Exception e){
			log.error("parse error:", e);
			log.error("服务器内部错误:",e.getMessage());
		}
		
		log.info("[ REQUEST: {} ] cost:{}",request.param("label"),costTime.cost());
		
		if(log.isDebugEnabled()){
			log.debug(" [ RESPONSE: {} ] -> {}", request.params(), JSONObject.toJSONString(nr, NormalResp.JSON_SERIAL_FEATURES));
		}

		resp = new StringResp(nr);
		return resp;
	}

	protected ServiceRequest parseReq(NettyHttpRequest request) {
		ServiceRequest serviceReq = new ServiceRequest();
		String content = request.contentAsString();
		if(StringUtils.startsWith(content, "finddata=")){
			content = StringUtils.removeStart(content, "finddata=");
			try {
				content = URLDecoder.decode(content, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.warn("", e);
			}
		}
		serviceReq.setRequest(request);
		serviceReq.setMethodName(request.param("label"));
		return serviceReq;
	}
	
	protected String doAction(ServiceRequest serviceReq, NettyHttpRequest request) {
		String nr = null;
		Method method = null;
		try
		{
			method = adaptorController.getClass().getMethod(serviceReq.getMethodName(), ServiceRequest.class);
			if(method != null)
			{
				nr = (String)method.invoke(adaptorController, serviceReq);
			}
			else
			{
				throw new RuntimeException(String.format("方法%s未找到", serviceReq.getMethodName()));
			}
		} 
		catch (Exception e) 
		{
			if(e instanceof InvocationTargetException)
			{
				Exception cause = (Exception)e.getCause();
				if(cause != null) e = cause;
			}
			if(e instanceof ServiceException)
			{
				throw new RuntimeException("API General Error: " + e.getMessage(), e);
			}
			else
			{
				log.error(method.toString(), e);
				throw new RuntimeException("API General Error: " + e.getMessage(), e);
			}
		}
		return nr;
	}
}
