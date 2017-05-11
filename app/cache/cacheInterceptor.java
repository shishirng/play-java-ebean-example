package cache;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import play.Logger;

/**
 * Created by shishir on 08/05/17.
 */

public class cacheInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation method) throws Throwable{
        if (method.getMethod().getName() == "list") {
            Logger.info("interceptor in list");
        } else {
            Logger.info("in interceptor for  {}", method.getMethod().getName());

        }
        return method.proceed();

    }
}

