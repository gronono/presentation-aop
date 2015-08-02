package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import api.ext.ITraceService;
import api.ext.ServiceLocator;

@Aspect
public class TraceAspect {

	private ITraceService traceSrv = ServiceLocator.INSTANCE.get(ITraceService.class);

	@Pointcut("execution(* aop.*Service.*(..))")
	public void tracePointCut() {};

	@Around("tracePointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		traceSrv.entering(joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), joinPoint.getArgs());
		Object result = joinPoint.proceed();
		traceSrv.exiting(joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), result);
		return result;
	}

}
