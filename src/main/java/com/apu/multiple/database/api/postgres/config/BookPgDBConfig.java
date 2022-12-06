package com.apu.multiple.database.api.postgres.config;

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

//import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "bookEntityManagerFactory",
		transactionManagerRef = "bookTransactionManager",
		basePackages = {
		"com.apu.multiple.database.api.postgres.repository" })
public class BookPgDBConfig {
	@Value("${spring.book.datasource.postgres.persistence.unit}")
	public String persistenceUnitName;

	@Value("${spring.book.datasource.jdbcUrl}")
	private String dataSourceUrl;
	@Value("${spring.book.datasource.username}")
	private String dataSourceUserName;
	@Value("${spring.book.datasource.password}")
	private String dataSourcePassword;

	@Bean(name = "bookDataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hibernate.dialect.PostgreSQL9Dialect");
		dataSource.setUrl(dataSourceUrl);
		dataSource.setUsername(dataSourceUserName);
		dataSource.setPassword(dataSourcePassword);

		return dataSource;
	}

	@Bean(name = "bookEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean bookEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("bookDataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
//		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.hbm2ddl.auto", "none");
		properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");

		/*//working fine
		Flyway userFlyway = new Flyway();
		userFlyway.setDataSource(dataSource);
		userFlyway.setLocations("db/specific/postgres");
		userFlyway.migrate();*/
		//working fine
		Flyway bookFlyway = Flyway.configure()
				.dataSource(dataSource)
				.locations("db/specific/postgres")
				.load();
		bookFlyway.migrate();

		return builder.dataSource(dataSource)
				.properties(properties)
				.packages("com.apu.multiple.database.api.postgres.entity")
				.persistenceUnit("Book")
				.build();
	}

	@Bean(name = "bookTransactionManager")
	public PlatformTransactionManager bookTransactionManager(
			@Qualifier("bookEntityManagerFactory") EntityManagerFactory bookEntityManagerFactory) {
		return new JpaTransactionManager(bookEntityManagerFactory);
	}

	@Bean(value = "postgresqlJdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("bookDataSource") DataSource dataSource) {
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
