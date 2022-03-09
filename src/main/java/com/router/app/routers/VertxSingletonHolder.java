package com.router.app.routers;

import io.vertx.reactivex.core.Vertx;

public class VertxSingletonHolder {

  private static Vertx vertx;

  public static Vertx getSingletonVertx() {
    if(vertx == null) {
      vertx = Vertx.currentContext().owner();
    }

    return vertx;
  }
}
