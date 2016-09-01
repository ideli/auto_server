package cn.mwee.auto.misc.aspect.slave;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Repository;
import cn.mwee.auto.misc.DataSource.DataSourceContextHolder;

@Aspect
@Repository
public class SlaveManager {
	
	@Pointcut("@annotation (cn.mwee.auto.misc.aspect.slave.UseSlave)")
	private void useSlave(){}
	
	@Pointcut("@annotation (cn.mwee.auto.misc.aspect.slave.UseMaster)")
	private void useMaster(){}
	
	@Around("useMaster()")
	public Object useMaster(ProceedingJoinPoint joinPoint) throws Throwable{
		DataSourceContextHolder.setDataSourceKey("master");
		
		try{
			return joinPoint.proceed();
		}catch(Throwable e){
			throw e;
		}finally{
			DataSourceContextHolder.clearDataSourceKey();
		}
	}
	
	@Around("useSlave()")
	public Object useSlave(ProceedingJoinPoint joinPoint) throws Throwable{
		String key = DataSourceContextHolder.getDataSourceKey();
		if(!"master".equals(key)){
			DataSourceContextHolder.setDataSourceKey("slave");
		}
		
		try{
			return joinPoint.proceed();
		}catch(Throwable e){
			throw e;
		}finally{
			DataSourceContextHolder.clearDataSourceKey();
		}
	}
}
