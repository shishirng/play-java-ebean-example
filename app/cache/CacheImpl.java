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
import controllers.HomeController;

/**
 * Created by shishir on 15/05/17.
 */
@Component
@Aspect
public class CacheImpl {

    /*@Pointcut("call(public * *(..))")
    public void publicMethod() {}

    //@Around("execution(@PsbCachable public * (@controllers.HomeController *).*(..))")
    //@Around("execution(@PsbCache public * (@DisabledForBlockedAccounts *).*(..))")
    @Around("publicMethod() && @within(PsbCachable)")
    */
    @Around("execution(@PsbCachable * controllers.HomeController.*(..))")
    public Object LogExecutionTimeByClass(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger.info("here in Around");
        return joinPoint.proceed();

    }


    /*    public Object invoke(MethodInvocation method) throws Throwable{
            Logger.info("class {}",method.getClass().getName());
            if (method.getMethod().getName() == "list") {
                Logger.info("interceptor in list");
            } else {
                Logger.info("in interceptor for  {}", method.getMethod().getName());

            }
            return method.proceed();

        }
        */
}
