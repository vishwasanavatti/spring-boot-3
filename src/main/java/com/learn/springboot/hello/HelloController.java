package com.learn.springboot.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@RequestMapping("say-hello")
	@ResponseBody
	public String sayHello() {
		return "Hello! How are you?";
	}
	
	@RequestMapping("hello-html")
	@ResponseBody
	public String helloHtml() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<html>");
		stringBuilder.append("<head>");
		stringBuilder.append("<title>My page</title>");
		stringBuilder.append("</head>");
		stringBuilder.append("<body>My first html page</body>");
		stringBuilder.append("</html>");
		
		return stringBuilder.toString();
	}
	
	@RequestMapping("hello-jsp")
	public String helloJsp() {	
		return "hello";
	}
	
}