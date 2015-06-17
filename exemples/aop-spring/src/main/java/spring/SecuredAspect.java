package spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import api.Account;
import api.SecurityException;
import api.ext.IUserService;
import api.ext.User;

@Aspect
@Component
public class SecuredAspect {

	@Autowired
	private IUserService usrSrv;

	@Pointcut("execution(* spring.*Service.*(..))")
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
