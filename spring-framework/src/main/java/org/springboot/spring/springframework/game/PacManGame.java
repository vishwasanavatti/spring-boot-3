package org.springboot.spring.springframework.game;

import org.springframework.stereotype.Component;

@Component
public class PacManGame implements Game {
	
	public void up() {
		System.out.println("pacman up");
	}
	
	public void down() {
		System.out.println("pacman down");
	}
	
	public void left() {
		System.out.println("pacman left");
	}
	
	public void right() {
		System.out.println("pacman right");
	}
}
