package com.learn.springboot.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String authenticateUser(@RequestParam String name, @RequestParam String password, ModelMap model) {
		if (authenticationService.authenticate(name, password)) {
			model.put("name", name);
			return "welcome";
		}
		
		model.put("error", "User could not be authenticated");
		return "login";
	}

}
