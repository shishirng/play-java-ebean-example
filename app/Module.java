import cache.cacheInterceptor;
import cache.cacheable;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers.*;
import controllers.HomeController;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;
import static com.google.inject.matcher.Matchers.subclassesOf;

/**
 * Created by shishir on 11/05/17.
 */
public class Module extends AbstractModule {
    protected void configure() {

        cacheInterceptor cacheInterceptor = new cacheInterceptor();
        requestInjection(cacheInterceptor);
        bindInterceptor(annotatedWith(cacheable.class), any(), cacheInterceptor);
    }

}
