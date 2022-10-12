package com.learn.springboot.restapi.user;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private UserDetailsRepository repository;

	public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(Arrays.toString(args));
		
		repository.save(new UserDetails("user", "Admin"));
		repository.save(new UserDetails("user 1", "Admin"));
		repository.save(new UserDetails("user 2", "Editor"));
		
		List<UserDetails> users = repository.findByRole("Editor");
		users.forEach(user -> logger.info(user.toString()));
	}

}
