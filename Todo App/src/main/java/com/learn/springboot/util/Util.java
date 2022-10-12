package com.learn.springboot.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {
	public static String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return authentication.getName();
	}
}
