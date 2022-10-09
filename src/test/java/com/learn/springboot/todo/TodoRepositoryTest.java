package com.learn.springboot.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TodoRepositoryTest {
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Test
	public void findByUserName() {
		Todo todo = new Todo(1, "test", "learn spring boot", LocalDate.now(), false);
		
		todoRepository.save(todo);
		
		List<Todo> todos = todoRepository.findByUsername("test");
		
		assertEquals(1, todos.size());
	}
	
}
