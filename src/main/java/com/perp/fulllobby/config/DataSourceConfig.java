package com.perp.fulllobby.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DataSourceConfig {
    
    @Bean
    public DataSource dataSource() {
        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setUrl(dotenv.get("DB_URL"));
        return source;
    }

}
