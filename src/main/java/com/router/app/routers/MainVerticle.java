package com.router.app.routers;
import com.router.app.routers.services.endpoints.ServiceEndpoint;
import io.micronaut.context.BeanContext;
import io.reactivex.Completable;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.LoggerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.router.app.routers.VertxSingletonHolder.getSingletonVertx;


public class MainVerticle extends AbstractVerticle {
  private Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public Completable rxStart() {
    BeanContext beanContext = BeanContext.run();
    io.vertx.reactivex.core.Vertx vertxInstance = getSingletonVertx();
    Router main = beanContext.streamOfType(ServiceEndpoint.class)
    .collect(() -> Router.router(vertxInstance), //the main router
      (r, s) -> {
             r.route().handler(BodyHandler.create()).handler(LoggerHandler.create())
                 .failureHandler(this::handleFailure);
             r.mountSubRouter(s.mountPoint(), s.router(vertxInstance)); },
      (r1, r2) -> {});

    //bind the main router to the http server

    return vertxInstance.createHttpServer().requestHandler(main)
      .rxListen(8888)
      .doOnSuccess(r -> logger.info("Server started on port 8888"))
      .ignoreElement();

  }

  private void handleFailure(RoutingContext routingContext) {
    if(routingContext.response().ended()) {
      return;
    }
    HttpServerResponse response = routingContext.response();
    response.setStatusCode(500)
      .rxEnd(new JsonObject().put("Error", "An error has been thrown").encode());

  }
}
