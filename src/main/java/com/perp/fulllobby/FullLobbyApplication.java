package com.perp.fulllobby;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.perp.fulllobby.config.RSAKeyProperties;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.Role;
import com.perp.fulllobby.repository.RoleRepository;
import com.perp.fulllobby.repository.UserRepository;

@EnableConfigurationProperties(RSAKeyProperties.class)
@SpringBootApplication
public class FullLobbyApplication {

	public static void main(String[] args) {

		SpringApplication.run(FullLobbyApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Role r = roleRepository.save(new Role(1, "USER"));

			Set<Role> roles = new HashSet<>();

			roles.add(r);

			MyUser user = new MyUser();
			user.setAuthorities(roles);
			user.setFirstName("Perp");
			user.setLastName("Derpy");
			user.setEmail("jacob.cuison7@gmail.com");
			user.setUsername("perp");
			user.setPassword(passwordEncoder.encode("password"));
			user.setVerified(true);

			userRepository.save(user);
		};
	}

}
