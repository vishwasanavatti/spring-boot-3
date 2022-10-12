package com.learn.springboot.mockito.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ListTest {

	@Test
	public void simpleListTest() {	
		List listMock = mock(List.class);
		
		when(listMock.size()).thenReturn(3);
		
		assertEquals(3, listMock.size());
	}
	
	@Test
	public void multipleReturns() {	
		List listMock = mock(List.class);
		
		when(listMock.size()).thenReturn(1).thenReturn(2);
		
		assertEquals(1, listMock.size());
		assertEquals(2, listMock.size());
	}
	
	@Test
	public void parameters() {	
		List listMock = mock(List.class);
		
		when(listMock.get(0)).thenReturn("string");
		
		assertEquals("string", listMock.get(0));
		assertEquals(null, listMock.get(1));
	}
	
	@Test
	public void genericParameters() {	
		List listMock = mock(List.class);
		
		when(listMock.get(anyInt())).thenReturn("some value");
		
		assertEquals("some value", listMock.get(5));
		assertEquals("some value", listMock.get(10));
	}
	
}
