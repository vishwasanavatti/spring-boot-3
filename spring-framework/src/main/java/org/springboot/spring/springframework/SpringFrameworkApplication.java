package org.springboot.spring.springframework;

import org.springboot.spring.springframework.enterprise.example.web.WebController;
import org.springboot.spring.springframework.game.GameRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringFrameworkApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringFrameworkApplication.class, args);

//		Game game = new MarioGame();
//		Game game1 = new SuperContraGame();
//		Game game2 = new PacManGame();
//		GameRunner runner = new GameRunner(game2);

		GameRunner runner = context.getBean(GameRunner.class);

		runner.run();

		WebController controller = context.getBean(WebController.class);
		System.out.println(controller.returnValureFromBusinessService());
	}

}
