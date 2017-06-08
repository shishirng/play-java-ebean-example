package cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import play.Logger;
import play.api.Play;
import play.cache.CacheApi;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by shishir on 15/05/17.
 */
@Component
@Aspect
public class CacheImpl {

    @Inject
    private static CacheApi cache = Play.current().injector().instanceOf(CacheApi.class);

    @Pointcut("call(public * com.avaje.ebean.Model.save(..))")
    public void psbSaveHandler() {
    }

    @Pointcut("call(public * com.avaje.ebean.Model.update(..))")
    public void psbUpdateHandler() {
    }

    @Pointcut("call(public * com.avaje.ebean.Model.refresh(..))")
    public void psbRefreshHandler() {
    }

    @Pointcut("call(public * com.avaje.ebean.Model.delete(..))")
    public void psbDeleteHandler() {
    }


    //@Pointcut("call(public * com.avaje.ebean.Model$Find.byId(..))")
    @Pointcut("call(public * models..finder(..))")
    public void psbFindHandler() {
    }

    @Around ("psbSaveHandler() || psbUpdateHandler() || psbRefreshHandler()")
    public void cacheSaveHandler(ProceedingJoinPoint pjp) throws Throwable {

        pjp.proceed();

        String keyIdentifier = getAnnotationValue(pjp, pjp.getTarget().getClass());

        if (keyIdentifier == null) {
            return;
        }
        Logger.debug("Around Save {}->{} id: {}",
                pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName(),
                keyIdentifier);

        // get identifier from object and build cache key
        String key = getCacheKey(pjp, keyIdentifier);

        if (key == null) {
            Logger.error("Cache: Could not fetch key for {}->{}",
                         pjp.getTarget().getClass().getSimpleName(), keyIdentifier);
            return;
        }

        Object target = pjp.getTarget();
        cache.set(key, target);

        return;

    }


    @Around ("psbDeleteHandler()")
    public Object cacheDeleteHandler(ProceedingJoinPoint pjp) throws Throwable {

        String keyIdentifier = getAnnotationValue(pjp, pjp.getTarget().getClass());

        if (keyIdentifier == null) {
            return pjp.proceed();
        }

        Logger.debug("Around {}->{} for id: {}",
                pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName(),
                keyIdentifier);

        // get identifier from object and build cache key
        String key = getCacheKey(pjp, keyIdentifier);

        if (key == null) {
            Logger.error("Cache: Could not find cache key for {}:{}",
                        pjp.getTarget().getClass().getSimpleName(), keyIdentifier);
            return pjp.proceed();
        }

        cache.remove(key);

        return pjp.proceed();
    }


    @Around("psbFindHandler() && args(key,..)")
    public Object cacheFindhandler(ProceedingJoinPoint pjp, Long key) throws Throwable {

        Class cls = pjp.getSignature().getDeclaringType();

        String keyvalue = getAnnotationValue(pjp, cls);

        if(keyvalue == null) {
            return pjp.proceed();
        }

        Logger.debug("Around {}->finder for {}:{}", cls.getSimpleName(),
                     keyvalue, key);


        //we have key from args, create key string
        String cachekey = keyBuilder(cls.getSimpleName(), keyvalue, key.toString());

        Object value = cache.get(cachekey);

        if (value == null) {
            Logger.debug("Not found in cache {} ", cachekey);
            value = pjp.proceed();
            if (value != null)
                cache.set(cachekey, value);
        } else {
            Logger.debug("Found {} in cache", cachekey);
        }

        return value;

    }


    /* Try to get annotation keyvalue if present */
    public String getAnnotationValue(ProceedingJoinPoint joinPoint, Class targetclass) {
        String keyvalue = null;
        if (targetclass.isAnnotationPresent(PsbCachable.class)) {
            Annotation[] annotations = targetclass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof PsbCachable) {
                    PsbCachable psbcache = (PsbCachable) annotation;
                    keyvalue = psbcache.keyname();
                    }
            }
        }
        return keyvalue;
    }

    /* Build cache key identifier: table_id_123 */
    public String keyBuilder(String classname, String id, String key) {
        String cachekey = classname + "_" + id + "_" + key;
        Logger.trace("cache key {}", cachekey);
        return cachekey;
    }

    /* try to get actual value of key from the object to build key */
    public String getCacheKey(ProceedingJoinPoint pjp, String keyIdentifier) {
        Object target = pjp.getTarget();
        Class targetclass = target.getClass();
        Field identifier = null;
        Object value = null;

        try {
            identifier = targetclass.getField(keyIdentifier);
        } catch (NoSuchFieldException ex) {
            Logger.error("class {} has no such field {}", targetclass.getSimpleName(), keyIdentifier);
        }

        try {
            value = identifier.get(target);
        } catch (IllegalAccessException ex) {
            Logger.error("Could not fetch {}->{}", targetclass.getSimpleName(), keyIdentifier);
        }

        if (value == null)
            return null;

        String key = keyBuilder(targetclass.getSimpleName(), keyIdentifier, value.toString()) ;


        return key;
    }

}
