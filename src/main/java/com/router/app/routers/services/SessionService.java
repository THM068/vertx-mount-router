package com.router.app.routers.services;

import com.router.app.routers.model.SessionRequest;
import com.router.app.routers.repository.SessionRepository;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.RxHelper;
import jakarta.inject.Singleton;

import java.util.concurrent.TimeUnit;

import static com.router.app.routers.VertxSingletonHolder.*;

@Singleton
public class SessionService {

  private SessionRepository sessionRepository;

  public SessionService(SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  public Single<JsonObject> createSession(SessionRequest sessionRequest) {
      return sessionRepository.createSession(sessionRequest)
        .map(bufferHttpResponse ->  bufferHttpResponse.bodyAsJsonObject())
        .subscribeOn(RxHelper.scheduler(getSingletonVertx()));
//        .timeout(5, TimeUnit.SECONDS)
//        .onErrorReturn(t ->  new JsonObject().put("message", "D'oh! Timeout"));
  }

}
