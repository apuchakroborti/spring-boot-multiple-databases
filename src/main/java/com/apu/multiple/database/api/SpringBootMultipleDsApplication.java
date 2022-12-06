package com.apu.multiple.database.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
//@ComponentScan(basePackages = { "com.apu.multiple.database.api.h2" })
//@EntityScan("com.apu.multiple.database.api.h2.models")
//@EnableJpaRepositories("com.apu.multiple.database.api.h2.repository.*")

public class SpringBootMultipleDsApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBootMultipleDsApplication.class, args);
	}
}
