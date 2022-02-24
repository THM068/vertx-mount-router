package com.router.app.routers;

import com.router.app.routers.services.endpoints.ServiceEndpoint;
import io.micronaut.context.BeanContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;



public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    BeanContext beanContext = BeanContext.run();

    Router main = beanContext.streamOfType(ServiceEndpoint.class)
    .collect(() -> Router.router(vertx), //the main router
      (r, s) -> r.mountSubRouter(s.mountPoint(), s.router(vertx)),
      (r1, r2) -> {});

    //bind the main router to the http server
    vertx.createHttpServer().requestHandler(main).listen(8080, res -> {
      if (res.succeeded()) {
        System.out.println("Server started on 8080");
        startPromise.complete();
      } else {
        startPromise.fail(res.cause());
      }
    });
  }
}
