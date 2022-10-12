package com.learn.springboot.restapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

	private final String expectedResponse1 = """
			{
				"id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"options":
					["AWS","Azure",
					"Google Cloud",
					"Oracle Cloud"],
				"correctAnswer":"AWS"
			}
			""";

	private final String expectedResponse2 = """
			{
				"id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"correctAnswer":"AWS"
			}
			""";

	private final String expectedQuestions = """
			[{"id":"Question1",
			"description":"Most Popular Cloud Platform Today",
			"options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"},
			{"id":"Question2","description":"Fastest Growing Cloud Platform",
			"options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"Google Cloud"},
			{"id":"Question3","description":"Most Popular DevOps Tool",
			"options":["Kubernetes","Docker","Terraform","Azure DevOps"],
			"correctAnswer":"Kubernetes"}]
			""";
	
	private static String SINGLE_QUESTION_URL = "/surveys/Survey1/questions/Question1";
	private static String QUESTIONs_URL = "/surveys/Survey1/questions";
	
	@Autowired
	private TestRestTemplate template;

	@Test
	public void retrieveSingleSurveyQuestion_returnsQuestion_onHappyPath() throws JSONException {
		HttpHeaders headers = createHttpContentTypeANdAuth();
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
 		
		ResponseEntity<String> responseEntity = template.exchange(SINGLE_QUESTION_URL, HttpMethod.GET, httpEntity, String.class);

		// checks expected and response to be completely same
		JSONAssert.assertEquals(expectedResponse1, responseEntity.getBody(), true);
		// checks response to be same as mentioned expectation
		JSONAssert.assertEquals(expectedResponse2, responseEntity.getBody(), false);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
	}

	@Test
	public void retrieveAllSurveyQuestions_returnsQuestionsList_onHappyPath() throws JSONException {
		HttpHeaders headers = createHttpContentTypeANdAuth();
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);
 		
		ResponseEntity<String> responseEntity = template.exchange(QUESTIONs_URL, HttpMethod.GET, httpEntity, String.class);

		// checks expected and response to be completely same
		JSONAssert.assertEquals(expectedQuestions, responseEntity.getBody(), true);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
	}
	
	@Test
	public void addSurveyQuestion_addsQuestionToSurvey_onHappyPath() throws JSONException {
		String requestBody = """
				{"id":"Question4","description":"Add new Question","options":["Kubernetes","Docker","Terraform","Azure DevOps"],"correctAnswer":"Kubernetes"}
				""";
		HttpHeaders headers = createHttpContentTypeANdAuth();
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);
 		
		ResponseEntity<String> responseEntity = template.exchange(QUESTIONs_URL, HttpMethod.POST, httpEntity, String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/Survey1/questions"));
		
		ResponseEntity<String> responseEntityDelete = template.exchange(locationHeader, HttpMethod.DELETE, httpEntity, String.class);
		assertTrue(responseEntityDelete.getStatusCode().is2xxSuccessful());
	}

	private HttpHeaders createHttpContentTypeANdAuth() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
//		headers.add("Authorization", "Basic dXNlcjpwYXNz");
		headers.add("Authorization", "Basic "+performBasicAuthEncoding("user", "pass"));
		return headers;
	}
	
	String performBasicAuthEncoding(String user, String password) {
		String combined = user + ":"+password;
		
		byte[] encodedBytes = Base64.getEncoder().encode(combined.getBytes());
		
		return new String(encodedBytes);
	}

}
