package com.router.app.routers.model;

import io.reactivex.Single;
import io.vertx.core.Future;

public interface ApplicationConfigurationManager<T> {

  Single<T> retrieveApplicationConfiguration();
}
