package com.router.app.routers.handlers;

import com.router.app.routers.model.SessionRequest;
import com.router.app.routers.services.SessionService;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import jakarta.inject.Singleton;

@Singleton
public class SessionsHandler implements Handler<RoutingContext> {

  private SessionService sessionService;

  public SessionsHandler(SessionService sessionService) {
    this.sessionService = sessionService;
  }


  @Override
  public void handle(RoutingContext routingContext) {
    JsonObject jsonObject = routingContext.getBodyAsJson();
    sessionService.createSession(new SessionRequest(jsonObject.getString("identifier"), jsonObject.getString("password")))
      .subscribe(result -> this.successResponse(result, routingContext),
        throwable -> this.errorResponse(throwable, routingContext));
  }

  private void errorResponse(Throwable throwable, RoutingContext context) {
    context.response().putHeader("content-type", "application/json")
      .setStatusCode(500)
      .end(new JsonObject().put("error", throwable).encode());
  }

  private void successResponse(JsonObject result, RoutingContext context) {
    context.response().putHeader("content-type", "application/json")
      .setStatusCode(200)
      .end(result.encodePrettily());
  }
}
