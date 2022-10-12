package org.springboot.spring.springframework.enterprise.example.data;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DataService {

	public List<Integer> getData() {
		return Arrays.asList(1, 2, 3, 4);
	}
}