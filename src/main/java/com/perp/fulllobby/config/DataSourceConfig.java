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
        source.setDriverClassName("org.hibernate.dialect.MySQL8Dialect");
        source.setUrl(dotenv.get("DB_URL"));
        source.setUsername(dotenv.get("DB_USER"));
        source.setPassword(dotenv.get("DB_PASS"));
        return source;
    }

}
