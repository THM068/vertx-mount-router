package com.router.app.routers.repository;
import com.router.app.routers.model.Customer;
import io.reactivex.Single;
import jakarta.inject.Singleton;

import java.util.concurrent.CompletionStage;

@Singleton
public class CustomerRepository implements Repository<Customer> {


  @Override
  public Single<Customer> find(Long id) {
    return null;
  }
}
