package com.krazymood.app.configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @Profile("no-pool")
    public DataSource nonPoolingDataSource(
            DataSourceProperties properties) {

        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();

        dataSource.setDriverClassName(properties.getDriverClassName());

        dataSource.setUrl(properties.getUrl());

        dataSource.setUsername(
                properties.getUsername()
        );

        dataSource.setPassword(
                properties.getPassword()
        );

        return dataSource;
    }
}