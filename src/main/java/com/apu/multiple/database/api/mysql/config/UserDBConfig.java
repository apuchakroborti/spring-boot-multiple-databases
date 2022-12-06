package com.apu.multiple.database.api.mysql.config;

import java.util.HashMap;

//import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;
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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "userEntityManagerFactory",
		transactionManagerRef = "userTransactionManager",
		basePackages = {
		"com.apu.multiple.database.api.mysql.repository" })
public class UserDBConfig {

	@Value("${spring.user.datasource.jdbcUrl}")
	private String dataSourceUrl;
	@Value("${spring.user.datasource.username}")
	private String dataSourceUserName;
	@Value("${spring.user.datasource.password}")
	private String dataSourcePassword;

	@Bean(name = "userDataSource")
//	@ConfigurationProperties(prefix = "spring.user.datasource")
	public DataSource dataSource() {
//		return DataSourceBuilder.create().build();
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hibernate.dialect.MySQL5Dialect");
		dataSource.setUrl(dataSourceUrl);
		dataSource.setUsername(dataSourceUserName);
		dataSource.setPassword(dataSourcePassword);

		return dataSource;
	}

	@Bean(name = "userEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("userDataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
//		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.hbm2ddl.auto", "none");
		properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

		/*//working fine
		Flyway userFlyway = new Flyway();
        userFlyway.setDataSource(dataSource);
        userFlyway.setLocations("db/specific/mysql");
//        flyway.setLocations("db/migration/mysql");
        userFlyway.migrate();*/
		Flyway flyway = Flyway.configure()
				.dataSource(dataSource)
				.locations("db/specific/mysql")
				.load();
		flyway.migrate();

		return builder.dataSource(dataSource)
				.properties(properties)
				.packages("com.apu.multiple.database.api.mysql.entity")
				.persistenceUnit("User")
				.build();
	}

	@Bean(name = "userTransactionManager")
	public PlatformTransactionManager userTransactionManager(
			@Qualifier("userEntityManagerFactory") EntityManagerFactory userEntityManagerFactory) {
		return new JpaTransactionManager(userEntityManagerFactory);
	}
	@Bean(value = "mysqlJdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("userDataSource") DataSource dataSource) {
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
