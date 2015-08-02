package spring;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class ProfilingAspect {

	@Around("@annotation(spring.Profile)")
	public Object profile(ProceedingJoinPoint call) throws Throwable {
        StopWatch clock = new StopWatch("Profiling for '" + call.getSignature().getName() + " : " + Arrays.asList(call.getArgs()));
        try {
            clock.start(call.toShortString());
            return call.proceed();
        } finally {
            clock.stop();
            System.out.println(clock.prettyPrint());
        }
    }
}
