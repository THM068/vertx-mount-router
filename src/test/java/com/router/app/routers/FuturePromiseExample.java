package com.router.app.routers;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExample {

  @Test
  public void test_promise(Vertx vertx, VertxTestContext testContext) throws Throwable {
    Promise<String> promise = Promise.promise();
    promise.complete("I am complete");

    Future<String> future = promise.future();
    future.onSuccess(result -> System.out.println(result));
    testContext.completeNow();
  }
}
