package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import api.ext.ITraceService;
import api.ext.ServiceLocator;

public class TraceProxy implements InvocationHandler {
	@SuppressWarnings("unchecked")
	public static <T> T of(Object o) {
		return (T) Proxy.newProxyInstance(
				o.getClass().getClassLoader(),
				o.getClass().getInterfaces(),
				new TraceProxy(o));
	}

	private ITraceService traceSrv = ServiceLocator.INSTANCE.get(ITraceService.class);

	private Object delegate;

	private TraceProxy(Object delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		traceSrv.entering(delegate.getClass(), method.getName(), args);

		Object result = method.invoke(delegate, args);

		traceSrv.exiting(WithdrawService.class, method.getName(), result);

		return result;
	}
}