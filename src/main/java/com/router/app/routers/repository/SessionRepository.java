package com.router.app.routers.repository;

import com.router.app.routers.VertxSingletonHolder;
import com.router.app.routers.model.SessionRequest;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.RxHelper;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;
import jakarta.inject.Singleton;

@Singleton
public class SessionRepository {

  public Single<HttpResponse<Buffer>> createSession(SessionRequest sessionRequest) {
    var vertx = VertxSingletonHolder.getSingletonVertx();
    WebClient client = WebClient.create(vertx);
    return client.postAbs("https://f02agg.oogway.bskyb.com/sessions")
      .putHeader("Accept", "application/vnd.bridge.v1+json")
      .putHeader("Content-Type", "application/vnd.bridge.v1+json")
      .putHeader("Authorization", "Basic cmFuZ29f YnJpZGdl OnJhbmdv")
      .putHeader("X-SkyOTT-Proposition", "SKY")
      .putHeader("X-SkyOTT-Territory", "GB")
      .putHeader("X-SkyOTT-Provider", "SKY")
      .rxSendJsonObject(this.sessionsJsonObject(sessionRequest));
  }

  private  JsonObject sessionsJsonObject(SessionRequest sessionRequest) {
    return new JsonObject().put("identifier", sessionRequest.getUsername())
      .put("password", sessionRequest.getPassword())
      .put("longlived", true);
  }
}
