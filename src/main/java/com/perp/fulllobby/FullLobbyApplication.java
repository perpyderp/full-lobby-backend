package com.perp.fulllobby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.perp.fulllobby.config.RSAKeyProperties;

@EnableConfigurationProperties(RSAKeyProperties.class)
@SpringBootApplication
public class FullLobbyApplication {

	public static void main(String[] args) {

		SpringApplication.run(FullLobbyApplication.class, args);
	}

}
