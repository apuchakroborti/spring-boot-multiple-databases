package com.apu.multiple.database.api.h2.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class H2FlywayConfig {
    @Qualifier("hhDataSource")
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void migrateFlyway(){
        Flyway flyway = new Flyway();

        flyway.setDataSource(dataSource);
//        flyway.setLocations("db/specific/h2");
        flyway.setLocations("db/migration/h2");

        flyway.migrate();
    }
}
