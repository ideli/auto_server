package cn.mwee.auto.misc.handler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

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
import cn.mwee.auto.misc.resp.NormalReturn;

/**
 * request  body 为 json 
 * response body 为 json
 * @author huming
 *
 */
public class BaseHandler implements Handler{
	protected IController adaptorController;
	
	protected static Logger log = LoggerFactory.getLogger(BaseHandler.class);
	
	public BaseHandler(){
		super();
	}
	
	public IController getAdaptorService() {
		return adaptorController;
	}

	@Override
	public Resp handleRequest(NettyHttpRequest request) {
		NormalResp resp = null;
		NormalReturn nr = new NormalReturn();
		CostTime costTime = new CostTime();
		costTime.start();
		try{
			ServiceRequest serviceReq = parseReq(request);

			String body = null;

			if(serviceReq.getJson() == null)
			{
				//GET METHOD
				serviceReq.setJson(JSONObject.parseObject(JSON.toJSONString(request.params())));
			}

			body = serviceReq.getJson().toJSONString();

			/*
			String key = "secret";
			if(body!=null && body.indexOf(key)!=-1){
				JSONObject jsonObj= JSONObject.parseObject(body, Feature.AllowISO8601DateFormat);
				if(jsonObj.containsKey(key)){
					jsonObj.put(key, "***");
				}
				body = jsonObj.toJSONString();
				jsonObj = null;
			}
			*/
			
			if(log.isInfoEnabled())
				log.info(" [ REQUEST: {} ] -> {}", request.params(), body);
			
			nr = this.doAction(serviceReq,request);			
		}catch(Exception e){
			if (e instanceof JSONException) {
				nr.setMsg("请求参数格式错误:"+e.getMessage());
			} else {
				nr.setMsg("服务器内部错误:"+e.getMessage());
			}
			log.error("parse error:", e);
			nr.setStatusCode("500");
		}
		
		nr.setCostTime(costTime.cost());
		
		if(log.isDebugEnabled()){
			log.debug(" [ RESPONSE: {} ] -> {}", request.params(), JSONObject.toJSONString(nr, NormalResp.JSON_SERIAL_FEATURES));
		}

		resp = new NormalResp(nr);
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
		JSONObject json = JSONObject.parseObject(content);
		serviceReq.setJson(json);
//		serviceReq.setAppCode(json.getString("appCode"));
		serviceReq.setRequest(request);
		serviceReq.setMethodName(request.param("label"));
		return serviceReq;
	}
	
	protected NormalReturn doAction(ServiceRequest serviceReq, NettyHttpRequest request) {
		NormalReturn nr = null;
		Method method = null;
		try {
			method = adaptorController.getClass().getMethod(serviceReq.getMethodName(), ServiceRequest.class);
			if(method != null){
				nr = (NormalReturn)method.invoke(adaptorController, serviceReq);
			}else{
				nr = new NormalReturn();
				nr.setStatusCode(ServiceException.GENERAL_ERROR);
				nr.setMsg(String.format("方法%s未找到", serviceReq.getMethodName()));
			}
		} catch (Exception e) {
			if(e instanceof InvocationTargetException){
				Exception cause = (Exception)e.getCause();
				if(cause != null) e = cause;
			}
			if(e instanceof ServiceException){
				nr = new NormalReturn();
				nr.setStatusCode(((ServiceException)e).getStatusCode());
				nr.setMsg(e.getMessage());
			}else{
				log.error(method.toString(), e);
				throw new RuntimeException("API General Error: " + e.getMessage(), e);
			}
		}
		return nr;
	}
}
