package com.router.app.routers;

import com.router.app.routers.services.endpoints.ServiceEndpoint;
import io.micronaut.context.BeanContext;
import io.reactivex.Completable;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;



public class MainVerticle extends AbstractVerticle {

  @Override
  public Completable rxStart() {
    BeanContext beanContext = BeanContext.run();

    Router main = beanContext.streamOfType(ServiceEndpoint.class)
    .collect(() -> Router.router(vertx), //the main router
      (r, s) -> r.mountSubRouter(s.mountPoint(), s.router(vertx)),
      (r1, r2) -> {});

    //bind the main router to the http server

    return vertx.createHttpServer().requestHandler(main)
      .rxListen(8888)
      .ignoreElement();

  }
}
