package com.router.app.routers;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class TestingTutorial {

  @Test
  void simpleTest(Vertx vertx, VertxTestContext testContext) {
    Future<String> future = Future.succeededFuture("Hello World");

    testContext.verify(() ->{
      future.onSuccess( r -> {
        Assertions.assertEquals("Hello World", r);
        testContext.completeNow();
      })
        .onFailure(throwable -> testContext.failNow(throwable));
    });
  }

  @Test
  void publishSubScribeTest(Vertx vertx, VertxTestContext vertxTestContext) {
    var payload = new JsonObject()
      .put("message", "hello-world");
    EventBus eventBus = vertx.eventBus();


    vertxTestContext.verify(() ->{
      eventBus.<JsonObject>consumer("publish.bus", result -> {
        var jsonObject = result.body();
        Assertions.assertEquals("hello-world", jsonObject.getString("message"));
        vertxTestContext.completeNow();
      });
    });
    eventBus.publish("publish.bus", payload);
  }


}
