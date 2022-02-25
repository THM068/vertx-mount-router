package com.router.app.routers.services.endpoints;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;

public interface ServiceEndpoint {
  String mountPoint();
  Router router(Vertx vertx);
}
