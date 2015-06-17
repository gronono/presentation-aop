package api.ext;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

	public static ServiceLocator INSTANCE = new ServiceLocator();

	private Map<Class<?>, Object> services = new HashMap<>();
	private ServiceLocator() {

	}

	public void register(Class<?> clazz, Object service) {
		this.services.put(clazz, service);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<?> clazz) {
		return (T) services.get(clazz);
	}
}
