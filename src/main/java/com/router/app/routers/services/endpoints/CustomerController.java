package com.router.app.routers.services.endpoints;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import jakarta.inject.Singleton;

@Singleton
public class CustomerController implements ServiceEndpoint {

  @Override
  public String mountPoint() {
    return "/api/customer";
  }

  @Override
  public Router router(Vertx vertx) {
    Router router = Router.router(vertx);
    router.get("/one").handler(ctx -> ctx.response().end("Customer One OK"));
    router.get("/two").handler(ctx -> ctx.response().end("Customer Two OK"));
    return router;
  }
}
