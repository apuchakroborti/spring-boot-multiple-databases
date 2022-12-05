package com.apu.multiple.database.api.config;

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
@EnableJpaRepositories(entityManagerFactoryRef = "bookEntityManagerFactory",
		transactionManagerRef = "bookTransactionManager",
		basePackages = {
		"com.apu.multiple.database.api.book.repository" })
public class BookPgDBConfig {
	@Value("${spring.book.datasource.postgres.persistence.unit}")
	public String persistenceUnitName;

	/*@Value("${spring.book.datasource.jpa.properties.hibernate.dialect}")
	public String hibernateDialect;*/

	@Value("${spring.book.datasource.jdbcUrl}")
	private String dataSourceUrl;
	@Value("${spring.book.datasource.username}")
	private String dataSourceUserName;
	@Value("${spring.book.datasource.password}")
	private String dataSourcePassword;

	/*@Bean(name = "bookDataSource")
	@ConfigurationProperties(prefix = "spring.book.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}*/
	@Bean(name = "bookDataSource")
//	@ConfigurationProperties(prefix = "spring.book.datasource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(hibernateDialect);
		dataSource.setDriverClassName("org.hibernate.dialect.PostgreSQL9Dialect");
		dataSource.setUrl(dataSourceUrl);
		dataSource.setUsername(dataSourceUserName);
		dataSource.setPassword(dataSourcePassword);

		return dataSource;
	}

	@Bean(name = "bookEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean bookEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("bookDataSource") DataSource dataSource) {
//		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(dataSource);
//		em.setPackagesToScan("com.javatechie.multiple.ds.api.model.book");
//		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
//		properties.put("hibernate.dialect", hibernateDialect);
		properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
//		em.setJpaPropertyMap(properties);
//		em.setPersistenceUnitName(persistenceUnitName);
//		em.setPersistenceUnitName("Book");

		return builder.dataSource(dataSource).properties(properties)
				.packages("com.apu.multiple.database.api.model.book").persistenceUnit("Book").build();

//		return em;
		/*HashMap<String, Object> properties = new HashMap<>();
//		properties.put("hibernate.hbm2ddl.auto", "update");
//		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		return builder.dataSource(dataSource).properties(properties)
				.packages("com.javatechie.multiple.ds.api.model.book").persistenceUnit("Book").build();*/
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
		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
	}
}
