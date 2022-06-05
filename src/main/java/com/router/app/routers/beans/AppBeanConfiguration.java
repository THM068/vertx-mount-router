package com.router.app.routers.beans;

import com.router.app.routers.VertxSingletonHolder;
import com.router.app.routers.model.MysqlApplicationConfiguration;
import io.micronaut.context.annotation.Factory;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.config.spi.ConfigStore;
import io.vertx.core.json.JsonObject;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.reactivex.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.spi.ConnectionFactory;
import jakarta.inject.Named;
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

  @Singleton
  MysqlApplicationConfiguration mysqlApplicationConfiguration() {
    ConfigStoreOptions configStoreOptions = new ConfigStoreOptions();
    configStoreOptions.setType("file")
      .setFormat("properties")
      .setConfig(new JsonObject().put("path", "application.properties"));

    ConfigRetrieverOptions configRetrieverOptions = new ConfigRetrieverOptions();
    configRetrieverOptions.addStore(configStoreOptions);

    return new MysqlApplicationConfiguration(VertxSingletonHolder.getSingletonVertx(), configRetrieverOptions);
  }

}
