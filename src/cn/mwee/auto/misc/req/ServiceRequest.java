/**
 * 
 */
package cn.mwee.auto.misc.req;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import cn.mwee.auto.misc.common.server.NettyHttpRequest;

/*
 * @copyright (c) find 2014 
 * @author lishihong    2014
 */

/**
 * @author Administrator
 * @param <T>
 *
 */
public class ServiceRequest implements Serializable{

	private static final long serialVersionUID = 4423576079327417300L;

	private String appCode;
	
	private Long currentUser;
	
	private String methodName;
	
	private JSONObject json;
	
	private NettyHttpRequest request;
	
	private boolean appCodeChecked = false;
	
	private Object contract;
	
	private Object model;
	
	@SuppressWarnings("unchecked")
	public <T> T getModel(){
		return (T)model;
	}
	
	public void setModel(Object model){
		this.model = model;
	}	
	
	@SuppressWarnings("unchecked")
	public <T> T getContract(){
		return (T)contract;
	}
	
	public void setContract(Object contract){
		this.contract = contract;
	}
	
	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public NettyHttpRequest getRequest() {
		return request;
	}

	public void setRequest(NettyHttpRequest request) {
		this.request = request;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public boolean isAppCodeChecked() {
		return appCodeChecked;
	}

	public void setAppCodeChecked(boolean appCodeChecked) {
		this.appCodeChecked = appCodeChecked;
	}

	public Long getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Long currentUser) {
		this.currentUser = currentUser;
	}

}
