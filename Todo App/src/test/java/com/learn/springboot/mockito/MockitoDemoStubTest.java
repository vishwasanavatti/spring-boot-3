package com.learn.springboot.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MockitoDemoStubTest {
	
	@Test
	void findGreatestValueInArray() {
		DataService dataService = new DataServiceStub();
		MockitoDemo demo = new MockitoDemo(dataService);
		assertEquals(50, demo.findthegreatestFromAllData());
	}
	
}

class DataServiceStub implements DataService {

	@Override
	public int[] retrieveAllData() {
		// TODO Auto-generated method stub
		return new int[] {10, 50, 25};
	}
	
}
