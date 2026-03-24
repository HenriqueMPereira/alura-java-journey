package io.github.henriquempereira.screenmatch;

import io.github.henriquempereira.screenmatch.repository.SerieRepository;
import io.github.henriquempereira.screenmatch.view.InteractiveMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	//@SpringBootApplication
//public class ScreenmatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	//
	@Autowired
	private SerieRepository repository;

	//
	@Override
	public void run(String... args) throws Exception {
//
// 		Menu menu = new Menu();
//		menu.starMenu();
		InteractiveMenu interactiveMenu = new InteractiveMenu(repository);
		interactiveMenu.starMenu();

	}
}
