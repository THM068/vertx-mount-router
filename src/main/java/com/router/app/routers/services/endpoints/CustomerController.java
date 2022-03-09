package com.router.app.routers.services.endpoints;

import com.router.app.routers.handlers.GetCustomerHandler;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import io.vertx.reactivex.sqlclient.Row;
import io.vertx.reactivex.sqlclient.RowSet;
import io.vertx.reactivex.sqlclient.Tuple;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class CustomerController implements ServiceEndpoint {

  private GetCustomerHandler getCustomerHandler;

  public CustomerController( GetCustomerHandler getCustomerHandler) {
    this.getCustomerHandler = getCustomerHandler;
  }

  @Override
  public String mountPoint() {
    return "/api/customer";
  }

  @Override
  public Router router(Vertx vertx) {
    String x = null;
    Router router = Router.router(vertx);
    router.get("/one").handler(this.getCustomerHandler);
    router.get("/two").handler(ctx -> ctx.response().end( x.toString()));
    return router;
  }

}
