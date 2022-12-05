package com.apu.multiple.database.api.postgres.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class PostgresFlywayConfig {
    @Qualifier("bookDataSource")
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void migrateFlyway(){
        Flyway flyway = new Flyway();

        flyway.setDataSource(dataSource);
//        flyway.setLocations("db/specific/postgres");
        flyway.setLocations("db/migration/postgres");
        flyway.migrate();
    }
}
