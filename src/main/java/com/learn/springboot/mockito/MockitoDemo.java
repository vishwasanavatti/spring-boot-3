package com.learn.springboot.mockito;

public class MockitoDemo {
	private DataService dataService;
	
	public MockitoDemo(DataService dataService) {
		this.dataService = dataService;
	}

	public int findthegreatestFromAllData() {
		int[] data = this.dataService.retrieveAllData();
		int greatestValue = Integer.MIN_VALUE;
		
		for(int value : data) {
			if(value > greatestValue) {
				greatestValue = value;
			}
		}
		
		return greatestValue;
	}
}

interface DataService {
	int[] retrieveAllData();
}