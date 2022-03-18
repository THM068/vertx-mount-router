package com.router.app.routers.services;

import com.router.app.routers.model.SessionRequest;
import com.router.app.routers.repository.SessionRepository;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Singleton;

@Singleton
public class SessionService {

  private SessionRepository sessionRepository;

  public SessionService(SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  public Single<JsonObject> createSession(SessionRequest sessionRequest) {
      return sessionRepository.createSession(sessionRequest)
        .map(bufferHttpResponse ->  bufferHttpResponse.bodyAsJsonObject());
  }

}
