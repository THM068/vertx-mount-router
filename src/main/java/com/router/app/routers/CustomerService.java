package com.router.app.routers;

import com.router.app.routers.model.Customer;
import com.router.app.routers.repository.CustomerRepository;
import io.reactivex.Single;
import io.vertx.core.Future;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class CustomerService {
  private Logger logger = LoggerFactory.getLogger(CustomerService.class);
  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Single<Customer> find(Long id) {
    logger.info("Retrieving customer");

    return Single.just(new Customer());

  }
}
