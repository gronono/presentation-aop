package api.ext;

public class TraceService implements ITraceService {

	@Override
	public void entering(Class<?> clazz, String methodName, Object... args) {
		System.out.print("enter into " + clazz.getName() + "#" + methodName + "(");
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				System.out.print(args[i]);
				if (i != args.length - 1) {
					System.out.print(", ");
				}
			}
		}
		System.out.println(")");
	}

	@Override
	public void exiting(Class<?> clazz, String methodName, Object result) {
		System.out.println("exiting from " + clazz.getName() + "#" + methodName + " : " + result);
	}

}
