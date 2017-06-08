package security;

/**
 * Created by shishir on 09/05/17.
 */
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.cache.HandlerCache;

import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Module;
import com.google.inject.Singleton;
import play.api.inject.Binding;
import scala.collection.Seq;

import security.handler.PsbSecurityHandler;
import security.handler.PsbSecurityHandlerCache;

public class PsbSecurityHook  extends Module{

    @Override
    public Seq<Binding<?>> bindings(Environment environment, Configuration configuration) {
        return seq(bind(DeadboltHandler.class).to(PsbSecurityHandler.class).in(Singleton.class),
                    bind(HandlerCache.class).to(PsbSecurityHandlerCache.class).in(Singleton.class));
    }

}