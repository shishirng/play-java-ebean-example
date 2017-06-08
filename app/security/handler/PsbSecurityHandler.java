package security.handler;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.java.models.Subject;
import javax.inject.Inject;
import play.Logger;
import play.api.libs.iteratee.RunQueue;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.redirect;

/**
 * Created by shishir on 09/05/17
 */
public class PsbSecurityHandler implements DeadboltHandler{

    @Override
    public CompletionStage<Optional<Result>> beforeAuthCheck(Http.Context context) {
        return CompletableFuture.supplyAsync(()-> Optional.empty());
    }

    @Override
    public CompletionStage<Optional<? extends Subject>> getSubject(Http.Context context) {
        String uuid = context.request().getHeader("X-UUID");
        UserSubject subject = userdb.find_subject(uuid);
        return CompletableFuture.supplyAsync(()->Optional.ofNullable(subject));
    }

    @Override
    public CompletionStage<Result> onAuthFailure(Http.Context context, Optional<String> content) {
        Logger.info("in onAuthFailure");
        return CompletableFuture.completedFuture(redirect("/"));
    }

    @Override
    public CompletionStage<Optional<DynamicResourceHandler>> getDynamicResourceHandler(Http.Context context) {
        return CompletableFuture.completedFuture(Optional.of(new PsbSecurityDynamicResourceHandler()));
    }


}