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
		todos.add(new Todo(++todosCount, "vishwas", "learn AWS and GCP", LocalDate.now().plusYears(2), false));
		todos.add(new Todo(++todosCount, "vishwas", "learn React", LocalDate.now().plusYears(1), false));
	}

	public List<Todo> getTodos(String username) {
		return todos.stream().filter(t -> t.getUsername().equals(username)).toList();
	}

	public void addTodo(String username, String description, LocalDate targetDate) {
		Todo todo = new Todo(++todosCount, username, description, targetDate, false);
		todos.add(todo);
	}

	public void deleteTodo(int id) {
		todos.removeIf(t -> t.getId() == id);
	}

	public Todo getTodo(int id) {
		return todos.stream().filter(t -> t.getId() == id).toList().get(0);
	}

	public void updateTodo(Todo todo) {
		Todo updateTodo = getTodo(todo.getId());
		updateTodo.setDescription(todo.getDescription());
		updateTodo.setTargetDate(todo.getTargetDate());
	}

}
