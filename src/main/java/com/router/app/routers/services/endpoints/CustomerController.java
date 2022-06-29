package com.router.app.routers.services.endpoints;

import com.router.app.routers.handlers.GetCustomerByIdHandler;
import com.router.app.routers.handlers.GetCustomerHandler;
import com.router.app.routers.handlers.SessionsHandler;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import jakarta.inject.Singleton;

@Singleton
public class CustomerController implements ServiceEndpoint {

  private GetCustomerHandler getCustomerHandler;
  private GetCustomerByIdHandler getCustomerByIdHandler;
  private SessionsHandler sessionsHandler;

  public CustomerController( GetCustomerHandler getCustomerHandler, GetCustomerByIdHandler getCustomerByIdHandler, SessionsHandler sessionsHandler) {
    this.getCustomerHandler = getCustomerHandler;
    this.getCustomerByIdHandler = getCustomerByIdHandler;
    this.sessionsHandler = sessionsHandler;
  }


  @Override
  public Router router(Vertx vertx) {
    String x = null;
    Router router = Router.router(vertx);
    router.get("/customer/one").handler(this.getCustomerHandler);
    router.get("/customer/two").handler(ctx -> ctx.response().end( x.toString()));
    router.get("/customer/id/:id").handler(this.getCustomerByIdHandler);
    router.post("/customer/sessions").handler(BodyHandler.create()).handler(this.sessionsHandler);

    return router;
  }

}
