package com.router.app.routers.handlers;

import com.router.app.routers.services.endpoints.Country;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import io.vertx.reactivex.sqlclient.Row;
import io.vertx.reactivex.sqlclient.RowSet;
import io.vertx.reactivex.sqlclient.Tuple;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class GetCustomerHandler implements Handler<RoutingContext> {
  private MySQLPool mysqlPool;

  public GetCustomerHandler(MySQLPool mysqlPool) {
    this.mysqlPool = mysqlPool;
  }

  @Override
  public void handle(RoutingContext context) {

    mysqlPool.preparedQuery("select * from country where country=? Limit 5")
      .rxExecute(Tuple.of("Angola"))
      .map(this::createListOfCountries)
      .doOnSuccess( s -> System.out.println("Countries have been retrieved"))
      .subscribe( countries -> {
        JsonArray list = new JsonArray();
        countries.forEach( c -> {
          JsonObject jsonObject = new JsonObject();
          jsonObject.put("country", c.getCountry());
          jsonObject.put("country_id", c.getCountryId());
          list.add(jsonObject);
        });
        context.response().putHeader("content-type", "application/json")
          .setStatusCode(200)
          .end(list.encodePrettily());

      }, throwable -> {
        context.response().putHeader("content-type", "application/json")
          .setStatusCode(500)
          .end(new JsonObject().put("error", throwable).encode());
      });
  }

  private List<Country> createListOfCountries(RowSet<Row> rows) {
    List<Country> countries = new ArrayList<>();
    rows.forEach(row -> countries.add(Country.create(row.getString("country"), row.getLong("country_id"))));
    return countries;
  }
}
