package cache;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import play.Logger;
import cache.PsbCachable;

import java.lang.annotation.Annotation;

/**
 * Created by shishir on 15/05/17.
 */
@Component
@Aspect
public class CacheImpl {


    /*@Pointcut("@within(PsbCachable) && call(public * models.*.*(..))")
    public void getNameMethod() {}

    @Around("getNameMethod()")
    public Object CacheGetNameHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger.info("Around for {}->{}->{}", joinPoint.getTarget().getClass().getSuperclass().getSimpleName(),
                joinPoint.getTarget().getClass().getSimpleName(),joinPoint.getSignature().getName());
        return joinPoint.proceed();

    }
    */

    @Pointcut("call(public * com.avaje.ebean.Model.save(..))")
    public void psbhandler() {}

    @Around("psbhandler()")
    public Object cachehandler(ProceedingJoinPoint joinPoint) throws Throwable {
        if(joinPoint.getTarget().getClass().isAnnotationPresent(PsbCachable.class)) {
            Class targetclass = joinPoint.getTarget().getClass();
            Annotation[] annotations = targetclass.getAnnotations();
            String keyvalue = "";
            for(Annotation annotation : annotations) {
                if (annotation instanceof PsbCachable) {
                    PsbCachable psbcache = (PsbCachable) annotation;
                    keyvalue = psbcache.keyname();
                }
            }
            Logger.info("Around for psbhandler {}->{}->{} id: {}", joinPoint.getTarget().getClass().getSuperclass().getSimpleName(),
                    joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(),
                    keyvalue);
        }
        return joinPoint.proceed();
    }


}
