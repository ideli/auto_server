package cn.mwee.auto.misc.mq;

import com.alibaba.fastjson.JSON;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 应用程序事件对象 */
public class AppEvent
{
	/** 应用程序代码 */
	private Integer clientId;
	/** 事件类型 */
	private String eventType;
	/** 触发时间 */
	private Date triggerTime = new Date();
	/** Tags */
	private Map<String, String> tags;
	/** JSON 格式事件内容 */
	private JSON content;
	
	public AppEvent()
	{

	}

	public AppEvent(Integer clientId, String eventType)
	{
		this(clientId, eventType, new Date(), null, null);
	}

	public AppEvent(Integer clientId, String eventType, Object contentObject)
	{
		this(clientId, eventType, new Date(), null, contentObject);
	}

	public AppEvent(Integer clientId, String eventType, Date triggerTime, Map<String, String> tags, Object contentObject)
	{
		this.clientId = clientId;
		this.eventType = eventType;
		this.triggerTime = triggerTime;
		this.tags = tags;
		
		setContentObject(contentObject);
	}
	
	public AppEvent addTag(String name, String value)
	{
		if(tags == null)
			tags = new HashMap<String, String>();
		
		tags.put(name, value);
		
		return this;
	}
	
	public AppEvent delTag(String name)
	{
		if(tags != null)
			tags.remove(name);
		
		return this;
	}	

	public Integer getAppCode()
	{
		return clientId;
	}

	public void setAppCode(Integer clientId)
	{
		this.clientId = clientId;
	}

	public String getEventType()
	{
		return eventType;
	}

	public void setEventType(String eventType)
	{
		this.eventType = eventType;
	}

	public Date getTriggerTime()
	{
		return triggerTime;
	}

	public void setTriggerTime(Date triggerTime)
	{
		this.triggerTime = triggerTime;
	}

	public Map<String, String> getTags()
	{
		return tags;
	}

	public void setTags(Map<String, String> tags)
	{
		this.tags = tags;
	}
	
	public JSON getContent()
	{
		return content;
	}

	public void setContent(JSON content)
	{
		this.content = content;
	}

	public <T> T getContentObject(Class<T> clazz)
	{
		if(content != null)
			return JSON.toJavaObject(content, clazz);
		
		return null;
	}
	
	public void setContentObject(Object contentObject)
	{
		this.content = (JSON)JSON.toJSON(contentObject);
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
