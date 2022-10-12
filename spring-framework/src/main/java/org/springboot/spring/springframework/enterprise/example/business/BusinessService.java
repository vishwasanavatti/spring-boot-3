package org.springboot.spring.springframework.enterprise.example.business;

import java.util.List;

import org.springboot.spring.springframework.enterprise.example.data.DataService;
import org.springframework.stereotype.Component;

@Component
public class BusinessService {
//	@Autowired
	private DataService dataService;

//	@Autowired
	public void setDataService(DataService dataService) {
		System.out.println("setter injection");
		this.dataService = dataService;
	}

	public BusinessService(DataService dataService) {
		super();
		System.out.println("constructor injection");
		this.dataService = dataService;
	}

	public long calculateSum() {
		List<Integer> data = dataService.getData();
		return data.stream().reduce(Integer::sum).get();
	}

}
