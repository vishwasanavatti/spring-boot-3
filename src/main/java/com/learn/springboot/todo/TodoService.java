package com.learn.springboot.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

	private static List<Todo> todos = new ArrayList<>();

	static {
		todos.add(new Todo(1, "vishwas", "learn springboot", LocalDate.now().plusYears(1), false));
		todos.add(new Todo(2, "vishwas", "learn AWS", LocalDate.now().plusYears(2), false));
		todos.add(new Todo(3, "vishwas", "learn React", LocalDate.now().plusYears(1), false));
	}

	public List<Todo> getTodos(String username) {
		return todos;
	}

}
