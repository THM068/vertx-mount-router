package com.router.app.routers.services.endpoints;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import jakarta.inject.Singleton;

@Singleton
public class PersonController implements ServiceEndpoint {
  @Override
  public String mountPoint() {
    return "/api/person";
  }

  @Override
  public Router router(Vertx vertx) {
    Router router = Router.router(vertx);
    router.get("/one").handler(this::getOne);
    return router;
  }

  private void getOne(RoutingContext ctx) {
    ctx.response().end("Person One OK");
  }
}
