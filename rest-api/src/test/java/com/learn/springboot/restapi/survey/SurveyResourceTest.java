package com.learn.springboot.restapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = SurveyResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class SurveyResourceTest {

	@MockBean
	private SurveyService serviceMock;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void retrieveAllSurveys_returnsAllSurvey_onHappyPath() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/surveys")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void retrieveSurveyById_throwsNotFound_SurveyServiceNotSpecified() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/surveys/Survey1")).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}

	@Test
	public void retrieveSingleSurveyQuestion_returnsQuestion_OnHappyPath() throws Exception {
		Question question = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

		String expectedResponse = """
				{"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
				""";

		when(serviceMock.getSurveyQuestion(anyString(), anyString())).thenReturn(question);

		MvcResult mvcResult = mockMvc.perform(get("/surveys/Survey1/questions/Question1")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), true);
	}

	@Test
	public void addSurveyQuestion_addsQuestion_OnHappyPath() throws Exception {
		String requestBody = """
				{"id":"Question4","description":"Add new Question","options":["Kubernetes","Docker","Terraform","Azure DevOps"],"correctAnswer":"Kubernetes"}
				""";

		when(serviceMock.addQuestionToSurvey(anyString(), any())).thenReturn("4");

		MvcResult mvcResult = mockMvc.perform(post("/surveys/Survey1/questions").accept(MediaType.APPLICATION_JSON)
				.content(requestBody).contentType(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(201, mvcResult.getResponse().getStatus());
		assertTrue(mvcResult.getResponse().getHeader("Location").contains("/surveys/Survey1/questions/4"));
	}

}
