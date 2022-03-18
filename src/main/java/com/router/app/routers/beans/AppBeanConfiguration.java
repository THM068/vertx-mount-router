package com.router.app.routers.beans;

import io.micronaut.context.annotation.Factory;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.spi.ConnectionFactory;
import jakarta.inject.Singleton;
import org.hibernate.reactive.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.router.app.routers.VertxSingletonHolder.getSingletonVertx;

@Factory
public class AppBeanConfiguration {

  private MySQLConnectOptions getConnectOptions() {
    return  new MySQLConnectOptions()
      .setPort(3306)
      .setHost("localhost")
      .setDatabase("sakila")
      .setUser("root")
      .setPassword("");
  }

  private PoolOptions getPoolOptions() {
    return new PoolOptions().setMaxSize(5);
  }

  @Singleton
  MySQLPool client() {
    return MySQLPool.pool(getSingletonVertx(), getConnectOptions(), getPoolOptions());
  }

  @Singleton
  EntityManagerFactory entityManagerFactory() {
    return Persistence.createEntityManagerFactory("customer-reactive-demo");
  }

  @Singleton
  Stage.SessionFactory sessionFactory(EntityManagerFactory emf) {
    return emf.unwrap(Stage.SessionFactory.class);
  }

}
