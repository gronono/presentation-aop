package aop;

import api.ext.ITransactionManager;
import api.ext.ServiceLocator;
import api.ext.Transaction;

public aspect TransactionalAspect {
	
	private ITransactionManager txMgr = ServiceLocator.INSTANCE.get(ITransactionManager.class);
	
	declare precedence : TransactionalAspect, *;
	
	pointcut tracePointCut():
		execution(* aop.*Service.*(..));
	
	Object around(): tracePointCut() {
		Transaction tx = txMgr.begin();
		try {
			Object result = proceed();
			tx.commit();
			return result;
		} catch (Throwable e) {
			tx.rollback();
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new RuntimeException(e);
			}
		}
	}
}
