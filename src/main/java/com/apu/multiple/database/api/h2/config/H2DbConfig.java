package com.apu.multiple.database.api.h2.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "hhEntityManagerFactory",
        transactionManagerRef = "hhTransactionManager",
        basePackages = {
                "com.apu.multiple.database.api.h2.repository" })
public class H2DbConfig {

    @Value("${spring.hh.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.hh.datasource.username}")
    private String dataSourceUserName;
    @Value("${spring.hh.datasource.password}")
    private String dataSourcePassword;

    @Bean(name = "hhDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hibernate.dialect.H2Dialect");
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUserName);
        dataSource.setPassword(dataSourcePassword);
        return dataSource;
    }

    @Bean(name = "hhEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean hhEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("hhDataSource") DataSource dataSource) {
        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("com.apu.multiple.database.api.h2.entity")
                .persistenceUnit("hh")
                .build();
    }

    @Bean(name = "hhTransactionManager")
    public PlatformTransactionManager hhTransactionManager(
            @Qualifier("hhEntityManagerFactory") EntityManagerFactory hhEntityManagerFactory) {
        return new JpaTransactionManager(hhEntityManagerFactory);
    }
    @Bean(value = "hhJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("hhDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                new HashMap<>(),
                null);
    }
}
