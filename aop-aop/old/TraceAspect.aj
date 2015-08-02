package aop;

import api.ext.ITraceService;
import api.ext.ServiceLocator;

public aspect TraceAspect {
	
	private ITraceService traceSrv = ServiceLocator.INSTANCE.get(ITraceService.class);
	
	pointcut tracePointCut():
		execution(* aop.*Service.*(..));
	
	Object around(): tracePointCut() {
		traceSrv.entering(thisJoinPoint.getTarget().getClass(), thisJoinPoint.getSignature().getName(), thisJoinPoint.getArgs());
		Object result = proceed();
		traceSrv.exiting(thisJoinPoint.getTarget().getClass(), thisJoinPoint.getSignature().getName(), result);
		return result;
	}
}
