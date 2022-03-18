package com.router.app.routers.repository;

import io.reactivex.Single;

import java.util.concurrent.CompletionStage;

public interface Repository<T> {

  Single<T> find(Long id);
}
