package security.handler;

/**
 * Created by shishir on 09/05/17.
 */
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import play.Logger;
import play.mvc.Http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class PsbSecurityDynamicResourceHandler implements DynamicResourceHandler{

    private static final Map<String, DynamicResourceHandler> allowedHandler =new HashMap<>();
    private static final Map<String, DynamicResourceHandler> permissionHandler = new HashMap<>();

    @Override
    public CompletionStage<Boolean> isAllowed(String name, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
        return allowedHandler.get(name).isAllowed(name, meta, deadboltHandler, ctx);
        }

    @Override
    public CompletionStage<Boolean> checkPermission(String permissionValue, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
        Logger.info("perm {} meta {}", permissionValue, meta);
        return permissionHandler.get(permissionValue).checkPermission(permissionValue, meta, deadboltHandler, ctx);
    }

    static {
        allowedHandler.put("tibco", new TibcoDynamicResourceHandler());
        allowedHandler.put("seco", new secoDynamicResourceHandler());
        permissionHandler.put("tibco", new TibcoDynamicResourceHandler());
        permissionHandler.put("seco", new secoDynamicResourceHandler());
    }
}

class TibcoDynamicResourceHandler implements DynamicResourceHandler {

    @Override
    public CompletionStage<Boolean> isAllowed(String name, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
        return CompletableFuture.completedFuture(Boolean.FALSE);
    }


    @Override
    public CompletionStage<Boolean> checkPermission(String permissionValue, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
        Logger.info("in tibco checkPermissions");
        String api = ctx.request().getHeader("X-API-KEY");

        if ((api == null) || api.isEmpty())
            return CompletableFuture.completedFuture(Boolean.FALSE);
        else
            return CompletableFuture.completedFuture(Boolean.TRUE);
    }
}


class secoDynamicResourceHandler implements DynamicResourceHandler {

    @Override
    public CompletionStage<Boolean> isAllowed(String name, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
        return CompletableFuture.completedFuture(Boolean.FALSE);
    }


    @Override
    public CompletionStage<Boolean> checkPermission(String permissionValue, Optional<String> meta, DeadboltHandler deadboltHandler, Http.Context ctx) {
        Logger.info("in seco checkPermissions");
        String uuid = ctx.request().getHeader("X-UUID");
        String api = ctx.request().getHeader("X-API-KEY");

        if ((api == null || api.isEmpty())  || (uuid == null || uuid.isEmpty()))
            return CompletableFuture.completedFuture(Boolean.FALSE);
        else
            return CompletableFuture.completedFuture(Boolean.TRUE);
    }
}