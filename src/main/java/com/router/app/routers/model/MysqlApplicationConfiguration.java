package com.router.app.routers.model;

import io.reactivex.Single;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.Vertx;

public class MysqlApplicationConfiguration implements ApplicationConfigurationManager<MysqlApplicationConfigurationData> {

  private ConfigRetriever configRetriever;

  public MysqlApplicationConfiguration(Vertx vertx, ConfigRetrieverOptions configRetrieverOptions) {
    this.configRetriever = ConfigRetriever.create(vertx, configRetrieverOptions);
  }

  @Override
  public Single<MysqlApplicationConfigurationData> retrieveApplicationConfiguration() {
    return configRetriever.rxGetConfig()
      .map(config -> {
          MysqlApplicationConfigurationData mysqlApplicationConfigurationData = new MysqlApplicationConfigurationData();
          mysqlApplicationConfigurationData.setPassword(config.getString("mysql.db.password"));
          mysqlApplicationConfigurationData.setUsername(config.getString("mysql.db.username"));
          return mysqlApplicationConfigurationData;
      });
  }
}
