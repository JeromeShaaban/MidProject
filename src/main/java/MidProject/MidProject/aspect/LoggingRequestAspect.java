package MidProject.MidProject.aspect;

import MidProject.MidProject.utils.Misc;
import MidProject.MidProject.utils.MonitoringItem;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingRequestAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void requestMappingsMethods() { }

    @Around(value = "requestMappingsMethods() && !@annotation(MidProject.MidProject.aspect.Unmonitored)")
    public Object profileWithin(ProceedingJoinPoint pjp) throws Throwable {
        return handle(pjp);
    }

    private Object handle(ProceedingJoinPoint pjp) throws Throwable {
        MonitoringItem item = new MonitoringItem();
        item.setComponent(pjp.getSignature().getDeclaringType().getSimpleName());
        item.setAction(pjp.getSignature().getName());
        item.setParameters(Misc.getParams(pjp));

        long start = System.currentTimeMillis();
        Object output;
        try {
            output = pjp.proceed();
            item.setDuration(System.currentTimeMillis() - start);
            item.setException(null);

            log.info(item.toString());

            return output;
        } catch (Exception e) {
            item.setDuration(System.currentTimeMillis() - start);
            item.setException(e.getClass().getSimpleName());

            log.info(item.toString());

            throw e;
        }
    }


}