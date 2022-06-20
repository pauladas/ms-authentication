package br.com.devjansen.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {

	private final ApplicationConfig applicationConfig;

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder
				.create()
				.type(HikariDataSource.class)
				.driverClassName(applicationConfig.getDatabaseDriverClassName())
				.url(applicationConfig.getDatabaseUrl())
				.username(applicationConfig.getDatabaseUsername())
				.password(applicationConfig.getDatabaseUsername())
				.build();
	}

}
