package com.learn.springboot.restapi.helloworld;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HellowWorldResource {

	@RequestMapping("/hello-world")
	public String helloWorld() {
		return "Hello World!";
	}

	@RequestMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}

	@RequestMapping("/hello-world-path-param/{name}/message/{message}")
	public HelloWorldBean helloWorldMultipPathParam(@PathVariable String name, @PathVariable String message) {
		return new HelloWorldBean("Hello World " + name + "," + message);
	}

}