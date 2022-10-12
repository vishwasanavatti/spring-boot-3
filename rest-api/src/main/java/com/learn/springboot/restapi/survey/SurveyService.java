package com.learn.springboot.restapi.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SurveyService {

	private static List<Survey> surveys = new ArrayList<>();

	static {
		Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2", "Fastest Growing Cloud Platform",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3", "Most Popular DevOps Tool",
				Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));

		Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

		surveys.add(survey);
	}

	public List<Survey> getAllSurveys() {
		return surveys;
	}

	public Survey getSurveyById(String id) {
		return surveys.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
	}

	public List<Question> getAllSurveyQuestions(String surveyId) {
		Survey survey = getSurveyById(surveyId);

		if (survey == null)
			return null;

		return survey.getQuestions();
	}

	public Question getSurveyQuestion(String surveyId, String questionId) {
		List<Question> questions = getAllSurveyQuestions(surveyId);

		if (questions == null)
			return null;

		Optional<Question> question = questions.stream().filter(q -> q.getId().equals(questionId)).findFirst();
		if (question.isEmpty())
			return null;

		return question.get();
	}

	public String addQuestionToSurvey(String surveyId, Question question) {
		Survey survey = getSurveyById(surveyId);
		SecureRandom secureRandom = new SecureRandom();
		String randomId = new BigInteger(32, secureRandom).toString();

		question.setId(randomId);
		survey.getQuestions().add(question);

		return question.getId();
	}

	public String deleteSurveyQuestion(String surveyId, String questionId) {
		List<Question> questions = getAllSurveyQuestions(surveyId);

		if (questions == null)
			return null;

		boolean removed = questions.removeIf(q -> q.getId().equals(questionId));
		if (!removed)
			return null;

		return questionId;
	}

	public String updateSurveyQuestion(String surveyId, Question question) {
		List<Question> questions = getAllSurveyQuestions(surveyId);

		if (questions == null)
			return null;

		questions.removeIf(q -> q.getId().equals(question.getId()));
		questions.add(question);

		return question.getId();
	}

}
