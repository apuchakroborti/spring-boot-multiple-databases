package com.apu.multiple.database.api.mysql.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class MySqlFlywayConfig {
    @Qualifier("userDataSource")
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void migrateFlyway(){
        Flyway flyway = new Flyway();

        flyway.setDataSource(dataSource);
//        flyway.setLocations("db/specific/mysql");
        flyway.setLocations("db/migration/mysql");
        flyway.migrate();
    }
}
