package com.apu.multiple.database.api.h2.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceProvider;
import org.flywaydb.core.Flyway;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "hhEntityManagerFactory",
        transactionManagerRef = "hhTransactionManager",
        basePackages = {
                "com.apu.multiple.database.api.h2.repository" })
public class H2DbConfig {

//    @Value("${spring.hh.datasource.url}")
//    private String dataSourceUrl;

//    @Value("${spring.hh.datasource.username}")
//    private String dataSourceUserName;
//    @Value("${spring.hh.datasource.password}")
//    private String dataSourcePassword;

    @Bean(name = "hhDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hibernate.dialect.H2Dialect");
//        dataSource.setUrl(dataSourceUrl);
//        dataSource.setUsername(dataSourceUserName);
//        dataSource.setPassword(dataSourcePassword);
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean(name = "hhEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean hhEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("hhDataSource") DataSource dataSource) {
        Flyway userFlyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/specific/h2")
                .load();
        userFlyway.migrate();



       /* HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");*/


/*
        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("com.apu.multiple.database.api.h2.models")
                .persistenceUnit("hh")
                .build();*/
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setJpaProperties(properties);
        bean.setPersistenceProvider(new HibernatePersistenceProvider());
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("com.apu.multiple.database.api.h2.models");
        bean.setPersistenceUnitName("hh");
        return bean;
    }

    @Bean(name = "hhTransactionManager")
    public PlatformTransactionManager hhTransactionManager(
            @Qualifier("hhEntityManagerFactory") EntityManagerFactory hhEntityManagerFactory) {
        return new JpaTransactionManager(hhEntityManagerFactory);
    }
    /*@Bean(value = "hhJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("hhDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }*/
    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                new HashMap<>(),
                null);
    }
}
