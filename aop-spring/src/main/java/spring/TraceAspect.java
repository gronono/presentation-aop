package spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.ext.ITraceService;

@Aspect
@Component
public class TraceAspect {

	@Autowired
	private ITraceService traceSrv;

	@Pointcut("execution(* spring.*Service.*(..))")
	public void tracePointCut() {};

	@Around("tracePointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		traceSrv.entering(joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), joinPoint.getArgs());
		Object result = joinPoint.proceed();
		traceSrv.exiting(joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), result);
		return result;
	}

}
