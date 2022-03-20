package com.router.app.routers.handlers;

import com.router.app.routers.VertxSingletonHolder;
import com.router.app.routers.model.SessionRequest;
import com.router.app.routers.services.SessionService;
import io.vertx.reactivex.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import jakarta.inject.Singleton;

import static com.router.app.routers.VertxSingletonHolder.getSingletonVertx;

@Singleton
public class SessionsHandler implements Handler<RoutingContext> {

  private SessionService sessionService;
  private CircuitBreaker circuitBreaker;

  public SessionsHandler(SessionService sessionService) {
    this.sessionService = sessionService;
    this.circuitBreaker = CircuitBreaker.create("session-breaker", getSingletonVertx(),
      new CircuitBreakerOptions()
        .setFallbackOnFailure(true)
        .setTimeout(2000)
        .setMaxFailures(5)
        .setResetTimeout(5000)
      );
  }


  @Override
  public void handle(RoutingContext routingContext) {
    JsonObject jsonObject = routingContext.getBodyAsJson();
    sessionService.createSession(SessionRequest.create(jsonObject.getString("identifier"), jsonObject.getString("password")))
      .retry(3)
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
