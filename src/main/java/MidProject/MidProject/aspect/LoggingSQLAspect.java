package MidProject.MidProject.aspect;

import MidProject.MidProject.utils.Misc;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingSQLAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* MidProject.MidProject.persistence.repository.*.*(..))")
    public void repoMethods() { }

    @Around(value = "repoMethods() && !@annotation(MidProject.MidProject.aspect.Unmonitored)")
    public Object profileWithin(ProceedingJoinPoint pjp) throws Throwable {
        return handle(pjp);
    }

    private Object handle(ProceedingJoinPoint pjp) throws Throwable {
        String params = Misc.getParams(pjp);
        String name = pjp.getSignature().getDeclaringType().getSimpleName() + "." + ((MethodSignature)(pjp.getSignature())).getMethod().getName();
        long start = System.currentTimeMillis();
        Object output;
        try {
            output = pjp.proceed();
            long duration = (System.currentTimeMillis() - start);
            log.info(name + "(" + params + ") = " + duration);
            return output;
        } catch (Exception e) {
            log.error(name + "(" + params + ") = " + e.getMessage());
            throw e;
        }
    }
}