package com.router.app.routers.services.endpoints;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public interface ServiceEndpoint {
  String mountPoint();
  Router router(Vertx vertx);
}
