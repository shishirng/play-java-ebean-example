package security.handler;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.cache.HandlerCache;
import be.objectify.deadbolt.java.ExecutionContextProvider;
import security.handler.PsbSecurityHandler;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by shishir on 09/05/17.
 */
@Singleton
public class PsbSecurityHandlerCache implements HandlerCache {

    private final DeadboltHandler defaultHandler;
    //private final Map<String, DeadboltHandler> handlers = new HashMap<>();

    @Inject
    public PsbSecurityHandlerCache() {
        defaultHandler = new PsbSecurityHandler();
    }

    @Override
    public DeadboltHandler apply(final String key) {
        return defaultHandler;
    }

    @Override
    public DeadboltHandler get() {
        return defaultHandler;
    }
}
