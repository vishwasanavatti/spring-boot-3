package com.learn.springboot.restapi.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	@Bean
	public InMemoryUserDetailsManager createuserDetailsManager() {
		UserDetails userDetails = createNewUser("user", "pass");
		UserDetails userDetails1 = createNewUser("testUser", "pass");

		return new InMemoryUserDetailsManager(userDetails, userDetails1);
	}

	private UserDetails createNewUser(String username, String password) {
		UserDetails userDetails = User.builder().passwordEncoder(input -> passwordEncoder().encode(input))
				.username(username).password(password).roles("USER", "ADMIN").build();
		return userDetails;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

		http.httpBasic(withDefaults());

		http.csrf().disable();
		http.headers().frameOptions().disable();

		return http.build();
	}
}