package io.github.henriquempereira.screenmatch;

import io.github.henriquempereira.screenmatch.view.InteractiveMenu;
import io.github.henriquempereira.screenmatch.view.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		Menu menu = new Menu();
//		menu.starMenu();
		InteractiveMenu interactiveMenu = new InteractiveMenu();
		interactiveMenu.starMenu();

	}
}
