package org.springboot.spring.springframework.game;

import org.springframework.stereotype.Component;

@Component
public class SuperContraGame implements Game {
	
	public void up() {
		System.out.println("Super Up");
	}
	
	public void down() {
		System.out.println("Super Down");
	}
	
	public void left() {
		System.out.println("Super Left");
	}
	
	public void right() {
		System.out.println("Super Right");
	}
}
