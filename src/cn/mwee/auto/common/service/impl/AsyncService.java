package cn.mwee.auto.common.service.impl;

import java.lang.reflect.Method;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.mwee.auto.common.service.IAsyncService;

import static cn.mwee.auto.misc.common.exception.ServiceException.*;

@Service("asyncService")
public class AsyncService implements IAsyncService
{
	@Async
	@Override
	public void runAsync(Runnable task)
	{
		task.run();
	}

	@Async
	@Override
	public void invokeAsync(Object obj, Method method, Object ... args)
	{
		try
		{
			method.invoke(obj, args);
		}
		catch(Exception e)
		{
			throw wrapServiceException(e);
		}
	}

}
