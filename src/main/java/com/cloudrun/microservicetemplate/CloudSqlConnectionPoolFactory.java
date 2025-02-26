package com.cloudrun.microservicetemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class CloudSqlConnectionPoolFactory {
  private static final String INSTANCE_CONNECTION_NAME = "tactile-oxygen-449819-u9:us-central1:mtui-24-k78";
  private static final String DB_USER = "root";
  private static final String DB_PASS = "s4H&Apb1xe9";
  private static final String DB_NAME = "helloworld";

  public static DataSource createConnectionPool() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(String.format("jdbc:mysql:///" + DB_NAME));
    config.setUsername(DB_USER);
    config.setPassword(DB_PASS);
    config.addDataSourceProperty("socketFactory","com.google.cloud.sql.mysql.SocketFactory");
    config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);

    return new HikariDataSource(config);
  }

}