package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import api.Account;
import api.SecurityException;
import api.ext.IUserService;
import api.ext.ServiceLocator;
import api.ext.User;

@Aspect
public class SecuredAspect {

	private IUserService usrSrv = ServiceLocator.INSTANCE.get(IUserService.class);

	@Pointcut("execution(* aop.*Service.*(..))")
	public void securedPointCut() {};

	@Before("securedPointCut()")
	public void before(JoinPoint joinPoint) {
		for (Object arg : joinPoint.getArgs()) {
			if (arg instanceof Account) {
				checkOwner((Account) arg);
			}
		}
	}

	private void checkOwner(Account account) throws SecurityException {
		User accountOwner = account.getOwner();
		User currentUser = usrSrv.getCurrentUser();
		if (!accountOwner.equals(currentUser)) {
			throw new SecurityException(currentUser, accountOwner);
		}
	}
}
