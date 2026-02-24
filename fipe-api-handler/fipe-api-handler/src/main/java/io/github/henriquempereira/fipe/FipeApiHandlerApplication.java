package io.github.henriquempereira.fipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.henriquempereira.fipe.services.FipeClient;
import io.github.henriquempereira.fipe.view.MenuDisplay;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.http.HttpClient;


/**
 * Classe principal que inicializa a aplicação Spring Boot.
 * Implementa CommandLineRunner para iniciar a interface de linha de comando (CLI)
 * de consulta à API da FIPE logo após o carregamento do contexto do Spring.
 */
@SpringBootApplication
public class FipeApiHandlerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FipeApiHandlerApplication.class, args);
	}

	/**
	 * Configura as dependências manuais e inicia o menu de interação com o usuário.
	 */
	@Override
	public void run(String... args) throws Exception {
		// Inicializa o client e ferramenta de mapeamento para consumo da API
		HttpClient client = HttpClient.newHttpClient();
		ObjectMapper mapper = new ObjectMapper();
		FipeClient fipeClient = new FipeClient(client, mapper);
		MenuDisplay menuDisplay = new MenuDisplay();

		// Inicia o loop de interação no terminal
		menuDisplay.startMenu(fipeClient);
	}
}
