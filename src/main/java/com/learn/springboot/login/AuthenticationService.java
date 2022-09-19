package com.learn.springboot.login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	public boolean authenticate(String username, String password) {
		return username.equalsIgnoreCase("vishwas") && password.equalsIgnoreCase("pass");
	}
}
