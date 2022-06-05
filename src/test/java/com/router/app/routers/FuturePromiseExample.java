package com.router.app.routers;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

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

  @Test
  public void nullResultTest(Vertx vertx, VertxTestContext testContext) {
      testContext.verify(() ->{
        Future<Object> future = Future.succeededFuture(null);
        future.onSuccess( result -> {
          Assertions.assertNull(result);
          testContext.completeNow();
        });
      });
  }

  @Test
  public void nullWitHOptionalTest(Vertx vertx, VertxTestContext testContext) {
    testContext.verify(() -> {
      Future<Object> future = Future.succeededFuture(null);
      future.map(r -> Optional.ofNullable(r))
        .onSuccess( result -> {
          Assertions.assertTrue(result.isEmpty());
          testContext.completeNow();
        });
    });
  }

  @Test
  public void mapFuture(Vertx vertx, VertxTestContext testContext) {
    Future<String> future = Future.succeededFuture("Hello world");

    testContext.verify(() -> {
      future.map( r-> r.toUpperCase())
        .onSuccess( result -> {
          Assertions.assertEquals(result, "HELLO WORLD");
          testContext.completeNow();
        });
    });
  }

  @Test
  public void allCompositionSuccess(Vertx vertx, VertxTestContext testContext) {
    Future<BigDecimal> f1 = Future.succeededFuture(new BigDecimal(1000));
    Future<BigDecimal> f2 = Future.succeededFuture(new BigDecimal(2000));

    testContext.verify(() ->{
      Future<CompositeFuture> composed = CompositeFuture.all(f1, f2);
      composed.onSuccess( r-> {
        System.out.println(r.<BigDecimal>resultAt(0));
        System.out.println(r.<BigDecimal>list().get(1));
        testContext.completeNow();
      });
    });
  }
}
