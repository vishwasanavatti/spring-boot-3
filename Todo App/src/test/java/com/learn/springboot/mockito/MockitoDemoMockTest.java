package com.learn.springboot.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockitoDemoMockTest {
	
	@Mock
	private DataService dataServiceMock;
	
	@InjectMocks
	private MockitoDemo mockitoDemo;
	
	@Test
	void findGreatestValueInArray() {
		
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] {10, 50, 25});
		
		assertEquals(50, mockitoDemo.findthegreatestFromAllData());
	}
	
	@Test
	void findGreatestValueInArray_onValueTest() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] {150});
		
		assertEquals(150, mockitoDemo.findthegreatestFromAllData());
	}
	
}
