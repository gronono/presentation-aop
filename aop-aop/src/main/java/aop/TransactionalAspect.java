package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;
import org.aspectj.lang.annotation.Pointcut;

import api.ext.ITransactionManager;
import api.ext.ServiceLocator;
import api.ext.Transaction;

@Aspect
@DeclarePrecedence("TransactionalAspect, *")
public class TransactionalAspect {

	private ITransactionManager txMgr = ServiceLocator.INSTANCE.get(ITransactionManager.class);

	@Pointcut("execution(* aop.*Service.*(..))")
	public void transactionPointCut() {};

	@Around("transactionPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Transaction tx = txMgr.begin();
		try {
			Object result = joinPoint.proceed();
			tx.commit();
			return result;
		} catch (Throwable e) {
			tx.rollback();
			throw e;
		}
	}
}
