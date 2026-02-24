package io.github.henriquempereira.fipe.view;

import io.github.henriquempereira.fipe.services.FipeClient;
import io.github.henriquempereira.fipe.services.VehicleSearchService;

import java.util.Scanner;

/**
 * Classe responsável por exibir a interface de usuário no terminal.
 * Gerencia o loop do menu principal e roteia a escolha do usuário para o serviço de busca adequado.
 */
public class MenuDisplay {

    // O Scanner é mantido como atributo da classe para ser reaproveitado em múltiplas leituras
    private Scanner scanner = new Scanner(System.in);
    private String userChoice;

    /**
     * Inicia a interação com o usuário, apresentando as opções de veículos disponíveis.
     * O loop continua até que o usuário decida encerrar a aplicação.
     *
     * @param fipeClient O cliente HTTP configurado para realizar as requisições externas.
     */
    public void startMenu(FipeClient fipeClient) {
        // Inicializa o serviço de busca injetando as dependências necessárias
        VehicleSearchService vehicleSearchService = new VehicleSearchService(fipeClient, scanner);
        System.out.println("\nEste é o aplicativo de consultas de preço de carro de acordo com a FIPE.");

        do {
            System.out.println("\nDigite uma das opções abaixo");
            System.out.println("Carro: caso queira consultar carro\n" +
                    "Caminhão: caso queira consultar caminhão\n" +
                    "Moto: caso queira consultar moto\n" +
                    "Sair: caso queira sair do aplicativo de consultas");

            userChoice = scanner.nextLine();

            // Padroniza a entrada para maiúsculo para garantir que a validação seja case-insensitive
            switch (userChoice.toUpperCase()) {
                case "CARRO":
                    // Exibindo todos as marcas de CARRO
                    System.out.println("VOCÊ ESCOLHEU CARRO!");
                    vehicleSearchService.searchVehicle("carros");
                    break;
                case "CAMINHÃO", "CAMINHAO":
                    System.out.println("VOCÊ ESCOLHEU CAMINHÃO!");
                    vehicleSearchService.searchVehicle("caminhoes");
                    break;
                case "MOTO":
                    System.out.println("VOCÊ ESCOLHEU MOTO!");
                    vehicleSearchService.searchVehicle("motos");
                    break;
                case "SAIR":
                    System.out.println("VOCÊ ESCOLHEU SAIR DO APP...");
                    break;
                default:
                    System.out.println("VOCÊ NÃO DIGITOU CORRETAMENTE...");
                    break;
            }

        } while (!userChoice.equalsIgnoreCase("SAIR"));

        scanner.close();
    }
}
