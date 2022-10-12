package com.learn.springboot.todo;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { TodoController.class })
@WebMvcTest
@Import(TodoServiceTestContextConfiguration.class)
public class TodoControllerTest {
	private final TodoService service = new TodoService();
	private final String testUser = "testUser";

	@Autowired
	MockMvc mvc;
	
	@Test
	void listTodos_throwsUnauthorized_ifUserNotMentioned() throws Exception {
		mvc.perform(get("/list-todos")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = testUser)
	@Test
	void listTodos_returnWelcomeView_onHappyPath() throws Exception {
		mvc.perform(get("/list-todos")).andExpect(status().isOk()).andExpect(view().name("listTodos"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/listTodos.jsp")).andExpect(model().attribute("todos",
						hasItem(allOf(hasProperty("id", is(1)), hasProperty("username", is(testUser))))));
	}
	
	@Test
	void addTodo_throwsUnauthorized_ifUserNotMentioned() throws Exception {
		mvc.perform(get("/add-todo")).andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = testUser)
	@Test
	void addTodo_returnTodoView_onHappyPath() throws Exception {
		mvc.perform(get("/add-todo")).andExpect(status().isOk()).andExpect(view().name("todo"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/todo.jsp"))
				.andExpect(model().attribute("todo", hasProperty("username", is(testUser))));
	}

	@WithMockUser(value = testUser)
	@Test
	void addTodo_returnTodoView_ifAttributesHaveValidationError() throws Exception {
		String description = "abc";
		String targetDate = LocalDate.now().minusYears(1).toString();

		mvc.perform(post("/add-todo").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
				.param("description", description).param("targetDate", targetDate).sessionAttr("name", testUser))
				.andExpect(status().isOk()).andExpect(view().name("todo"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/todo.jsp"))
				.andExpect(model().attributeHasFieldErrors("todo", "targetDate"))
				.andExpect(model().attributeHasFieldErrors("todo", "description"));
	}

	@WithMockUser(value = testUser)
	@Test
	void addTodo_returnListTodosView_onHappyPath() throws Exception {
		String description = "learn spring boot";
		String targetDate = LocalDate.now().plusYears(1).toString();
		List<Todo> todosBefore = service.getTodos(testUser);

		mvc.perform(post("/add-todo").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
				.param("username", testUser).param("description", description).param("targetDate", targetDate)
				.sessionAttr("name", testUser)).andExpect(status().isFound())
				.andExpect(view().name("redirect:list-todos"));

		List<Todo> todosAfter = service.getTodos(testUser);
		assertEquals(todosBefore.size(), 3);
		assertEquals(todosAfter.size(), 4);
	}
	
	@Test
	void deleteTodo_throwsUnauthorized_ifUserNotMentioned() throws Exception {
		mvc.perform(get("/delete-todo")).andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = testUser)
	@Test
	void deleteTodo_deletesTodoAndreturnListTodosView_onHappyPath() throws Exception {
		mvc.perform(post("/delete-todo").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
				.param("id", "4").sessionAttr("name", testUser)).andExpect(status().isFound())
				.andExpect(view().name("redirect:list-todos"));

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> service.getTodo(4));
	}
	
	@Test
	void updateTodo_throwsUnauthorized_ifUserNotMentioned() throws Exception {
		mvc.perform(get("/update-todo")).andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = testUser)
	@Test
	void update_returnListTodosView_ifTodoIsNotFound() throws Exception {
		mvc.perform(get("/update-todo").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
				.param("id", "100").sessionAttr("name", testUser)).andExpect(status().isFound())
				.andExpect(view().name("redirect:list-todos"));
	}

	@WithMockUser(value = testUser)
	@Test
	void update_returnTodoView_ifTodoIsFound() throws Exception {
		mvc.perform(get("/update-todo").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf()).param("id", "1")
				.sessionAttr("name", testUser)).andExpect(status().isOk()).andExpect(view().name("todo"));
	}

	@WithMockUser(value = testUser)
	@Test
	void updateTodo_returnListTodosView_onHappyPath() throws Exception {
		String description = "java is the best";
		String targetDate = LocalDate.now().plusYears(2).toString();

		mvc.perform(post("/update-todo").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
				.param("id", "1").param("description", description).param("targetDate", targetDate)
				.sessionAttr("name", testUser)).andExpect(status().isFound())
				.andExpect(view().name("redirect:list-todos"));
		
		Todo todo = service.getTodo(1);
		assertEquals(todo.getDescription(), description);
		assertEquals(todo.getTargetDate().toString(), targetDate);
	}

	@WithMockUser(value = testUser)
	@Test
	void updateTodo_throwsError_ifTodoAttributesFailValidation() throws Exception {
		String description = "abc";
		String targetDate = LocalDate.now().minusYears(1).toString();

		mvc.perform(post("/update-todo").contentType(MediaType.APPLICATION_FORM_URLENCODED).with(csrf())
				.param("id", "1").param("description", description).param("targetDate", targetDate)
				.sessionAttr("name", testUser)).andExpect(status().isOk()).andExpect(view().name("todo"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/todo.jsp"))
				.andExpect(model().attributeHasFieldErrors("todo", "targetDate"))
				.andExpect(model().attributeHasFieldErrors("todo", "description"));
	}

}
