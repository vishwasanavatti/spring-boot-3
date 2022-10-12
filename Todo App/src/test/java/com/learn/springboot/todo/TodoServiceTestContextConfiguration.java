package com.learn.springboot.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TodoServiceTestContextConfiguration {
	@Bean
    public TodoService todoService() {
        return new TodoService() { 
            @Override
            public List<Todo> getTodos(String username) {
            	// TODO Auto-generated method stub
            	return super.getTodos(username);
            }
            
            @Override
            public void addTodo(String username, String description, LocalDate targetDate) {
            	// TODO Auto-generated method stub
            	super.addTodo(username, description, targetDate);
            }
            
            @Override
            public void deleteTodo(int id) {
            	// TODO Auto-generated method stub
            	super.deleteTodo(id);
            }
            
            @Override
            public void updateTodo(Todo todo) {
            	// TODO Auto-generated method stub
            	super.updateTodo(todo);
            }
        };
    }
}
