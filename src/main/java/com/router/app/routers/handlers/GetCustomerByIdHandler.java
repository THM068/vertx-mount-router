package com.router.app.routers.handlers;

import com.router.app.routers.CustomerService;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.LoggerHandler;
import jakarta.inject.Singleton;

@Singleton
public class GetCustomerByIdHandler implements Handler<RoutingContext> {

  private CustomerService customerService;

  public GetCustomerByIdHandler(CustomerService customerService) {
    this.customerService = customerService;
  }
  @Override
  public void handle(RoutingContext routingContext) {
    String id = routingContext.pathParam("id");

    if(id == null) {
      routingContext.fail(400);
    }
    else {
      customerService.find(Long.valueOf(id))
        .subscribe(customer -> {
           JsonObject result =  new JsonObject()
              .put("name", customer.getName())
              .put("address", customer.getAddress())
              .put("zip", customer.getZip_code())
              .put("city", customer.getCity())
              .put("country", customer.getCountry());

            routingContext.response().putHeader("content-type", "application/json")
              .setStatusCode(200)
              .end(new JsonObject().put("error", result).encode());

        },
          throwable -> {
            routingContext.response().putHeader("content-type", "application/json")
              .setStatusCode(500)
              .end(new JsonObject().put("error", throwable).encode());
          });
    }

  }
}
