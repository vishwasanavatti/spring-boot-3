package org.springboot.spring.springframework.enterprise.example.web;

import org.springboot.spring.springframework.enterprise.example.business.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebController {

	@Autowired
	private BusinessService service;

	public long returnValureFromBusinessService() {
		return service.calculateSum();
	}
}

