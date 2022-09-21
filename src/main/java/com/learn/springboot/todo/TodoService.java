package com.learn.springboot.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

	private static List<Todo> todos = new ArrayList<>();

	private static int todosCount = 0;
	
	static {
		todos.add(new Todo(++todosCount, "vishwas", "learn springboot", LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++todosCount, "vishwas", "learn AWS", LocalDate.now().plusYears(2), false));
		todos.add(new Todo(++todosCount, "vishwas", "learn React", LocalDate.now().plusYears(1), false));
	}

	public List<Todo> getTodos(String username) {
		return todos;
	}
	
	public void addTodo(String username, String description, LocalDate targetDate) {
		Todo todo = new Todo(++todosCount, username, description, targetDate, false);
		todos.add(todo);
	}

}
