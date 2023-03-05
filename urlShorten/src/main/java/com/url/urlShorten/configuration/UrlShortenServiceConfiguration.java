package com.url.urlShorten.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class UrlShortenServiceConfiguration {

	@Bean(destroyMethod = "close")
	public DataSource dataSource(Environment environment) {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(prepareJdbcUri(environment));
		hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
		hikariConfig.setPoolName("urlShorten_pool");
//		hikariConfig.setUsername("root");
//		hikariConfig.setPassword("password");
		hikariConfig.setUsername(loadSecret("mysql_username"));
		hikariConfig.setPassword(loadSecret("mysql_password"));
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMinimumIdle(0);
		hikariConfig.setConnectionTimeout(60 * 1000);
		hikariConfig.setMaxLifetime(35 * 1000); // after 35 sec, connection in the pool will timeout
		hikariConfig.setIdleTimeout(30 * 1000); // after 30 sec idle, connection in the pool will timeout
		hikariConfig.setValidationTimeout(30 * 1000);
		return new HikariDataSource(hikariConfig);
	}

	private String prepareJdbcUri(Environment environment) {
		String dbHost = environment.getProperty("DB_HOST", "localhost");
		String dbPort = environment.getProperty("DB_PORT", "3306");

		UriBuilderFactory builderFactory = new DefaultUriBuilderFactory();
		return new StringBuilder().append("jdbc:")
				.append(builderFactory.builder().scheme("mysql").host(dbHost).port(dbPort).path("url_shorten")
						.queryParam("useSSL", false).queryParam("serverTimezone", "Asia/Singapore")
						.queryParam("useLegacyDatetimeCode", false).build().toString())
				.toString();
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	/**
	 * Returns the docker secret of the DataSource
	 * 
	 * @param name key of the docker secret
	 * @return value of the docker secret
	 * @see String
	 */
	private String loadSecret(String name) {
		String secret = "";
		String secretPath = String.format("/run/secrets/%s", name);
		File secretFile = new File(secretPath);

		if (secretFile.exists()) {
			try (FileReader fileReader = new FileReader(secretFile)) {
				try (BufferedReader reader = new BufferedReader(fileReader)) {
					secret = reader.readLine();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return secret;
	}
}
