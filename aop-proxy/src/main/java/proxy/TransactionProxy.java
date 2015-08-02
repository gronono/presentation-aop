package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import api.ext.ITransactionManager;
import api.ext.ServiceLocator;
import api.ext.Transaction;

public class TransactionProxy implements InvocationHandler {

	@SuppressWarnings("unchecked")
	public static <T> T of(Object o) {
		return (T) Proxy.newProxyInstance(
				o.getClass().getClassLoader(),
				o.getClass().getInterfaces(),
				new TransactionProxy(o));
	}

	private ITransactionManager txMgr = ServiceLocator.INSTANCE.get(ITransactionManager.class);

	private Object delegate;

	private TransactionProxy(Object delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Transaction tx = txMgr.begin();
		try {
			Object result = method.invoke(delegate, args);
			tx.commit();
			return result;
		} catch (Exception e) {
			tx.rollback();
			throw e;
		}
	}

}