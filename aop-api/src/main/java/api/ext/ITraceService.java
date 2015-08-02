package api.ext;

public interface ITraceService {

	public void entering(Class<?> clazz, String methodName, Object ... args);

	public void exiting(Class<?> clazz, String methodName, Object result);
}
