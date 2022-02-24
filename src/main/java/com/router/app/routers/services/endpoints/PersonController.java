package com.router.app.routers.services.endpoints;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
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
    router.get("/one").handler(ctx -> ctx.response().end("Person One OK"));
    return router;
  }
}
