package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import api.Account;
import api.SecurityException;
import api.ext.IUserService;
import api.ext.ServiceLocator;
import api.ext.User;

public class SecuredProxy implements InvocationHandler {

	@SuppressWarnings("unchecked")
	public static <T> T of(Object o) {
		return (T) Proxy.newProxyInstance(
				 o.getClass().getClassLoader(),
				 o.getClass().getInterfaces(),
				 new SecuredProxy(o));
	}

	private IUserService usrSrv = ServiceLocator.INSTANCE.get(IUserService.class);

	private Object delegate;

	private SecuredProxy(Object delegate) {
		this.delegate = delegate;
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		for (Object arg : args) {
			if (arg instanceof Account) {
				checkOwner((Account) arg);
			}
		}
		return method.invoke(delegate, args);
	}

	private void checkOwner(Account account) throws SecurityException {
		User accountOwner = account.getOwner();
		User currentUser = usrSrv.getCurrentUser();
		if (!accountOwner.equals(currentUser)) {
			throw new SecurityException(currentUser, accountOwner);
		}
	}

}
