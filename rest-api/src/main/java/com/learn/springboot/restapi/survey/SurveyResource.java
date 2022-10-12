package com.learn.springboot.restapi.survey;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SurveyResource {

	private SurveyService service;

	public SurveyResource(SurveyService surveyService) {
		this.service = surveyService;
	}

	@RequestMapping("/surveys")
	public List<Survey> retrieveAllSurveys() {
		return service.getAllSurveys();
	}

	@RequestMapping("/surveys/{surveyId}")
	public Survey retrieveSurveyById(@PathVariable String surveyId) {
		Survey survey = service.getSurveyById(surveyId);

		if (survey == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return survey;
	}

	@RequestMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId) {
		List<Question> questions = service.getAllSurveyQuestions(surveyId);
		if (questions == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return questions;
	}

	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveSingleSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
		Question question = service.getSurveyQuestion(surveyId, questionId);
		if (question == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return question;
	}

	@RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.POST)
	public ResponseEntity<Object> addSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
		String questionId = service.addQuestionToSurvey(surveyId, question);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId)
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId) {
		String value = service.deleteSurveyQuestion(surveyId, questionId);

		if (value == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
		String value = service.updateSurveyQuestion(surveyId, question);

		if (value == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.noContent().build();
	}

}
