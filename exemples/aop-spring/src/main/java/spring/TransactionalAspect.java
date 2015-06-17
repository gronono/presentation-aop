package spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.ext.ITransactionManager;
import api.ext.Transaction;

@Aspect
@Component
public class TransactionalAspect {

	@Autowired
	private ITransactionManager txMgr;

	@Pointcut("execution(* spring.*Service.*(..))")
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
