package com.learn.springboot.welcome;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {WelcomeController.class})
@WebMvcTest
public class WelcomeControllerTest {
	@Autowired
    MockMvc mvc;
	
	@WithMockUser(value = "testUser")
	@Test
    void get_returnWelcomeView_onHappyPath() throws Exception {
        mvc.perform(get("/"))
        	.andExpect(status().isOk())
        	.andExpect(view().name("welcome"))
        	.andExpect(forwardedUrl("/WEB-INF/jsp/welcome.jsp"))
        	.andExpect(model().attribute("name", is("testUser")));
    }
	
}
