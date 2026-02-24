package io.github.henriquempereira.fipe.services;

import io.github.henriquempereira.fipe.model.Data;

import java.util.List;
import java.util.Scanner;

/**
 * Serviço responsável por orquestrar a lógica de negócio da busca de veículos.
 * Interage com o FipeClient para buscar os dados e com o Scanner para guiar o usuário
 * pelo funil de escolhas (Marca -> Modelo -> Detalhes por modelo final).
 */
public class VehicleSearchService {

    private FipeClient fipeClient;
    private Scanner scanner;

    public VehicleSearchService(FipeClient fipeClient, Scanner scanner) {
        this.fipeClient = fipeClient;
        this.scanner = scanner;
    }

    /**
     * Inicia o fluxo interativo de consulta de um veículo específico.
     * @param vehicleType O tipo de veículo selecionado no menu principal (ex: "carros").
     */
    public void searchVehicle(String vehicleType) {

        // Busca e exibe todas as marcas disponíveis para o tipo de veículo
        var brandList = fipeClient.fetchDataType(vehicleType);
        brandList.forEach(brand -> System.out.println("Marca: " +
                brand.dataName() + " -> Código: " + brand.dataCode()));

        // Valida a escolha da marca garantindo que o código existe na lista retornada
        var userBrandChoice = validateUserInput(brandList, "marca");

        // Busca e exibe todos os modelos atrelados à marca escolhida
        var modelsList = fipeClient.fetchDataModel(vehicleType, userBrandChoice);
        modelsList.forEach(model -> System.out.println("Modelo: " + model.dataName() +
                " -> " + "Código: " + model.dataCode()));

        // Filtra por texto para facilitar a busca do usuário em listas longas
        System.out.println("Digite o nome do modelo que deseja consultar: ");
        var modelsName = scanner.nextLine();
        modelsList.stream()
                .filter(m -> m.dataName().toUpperCase().contains(modelsName.toUpperCase()))
                .forEach(m -> System.out.printf("Modelo: %s -> Código: %s\n", m.dataName(),m.dataCode() ));

        // Valida a escolha do modelo específico
        var modelsCode = validateUserInput(modelsList, "modelo");

        // Busca os anos disponíveis para o modelo e mapeia cada ano para seus detalhes de preço
        var modelsByCodeList = fipeClient.fetchDataModelByCode(vehicleType, userBrandChoice, modelsCode);
        modelsByCodeList.stream()
                // Para cada ano retornado, faz uma nova requisição buscando o valor da tabela FIPE
                .map(data -> fipeClient.fetchVehicleData(vehicleType, userBrandChoice, modelsCode, data.dataCode()))
                .toList()
                .forEach(v -> System.out.println(v.brand() + " " + v.name() + " " + v.year() + " " + v.fuel() + " por " + v.price()));
    }

    /**
     * Método utilitário para garantir que o usuário digite um código válido que exista na lista atual.
     * @param dataList A lista de opções (marcas ou modelos) válida no momento.
     * @param entityName Nome da entidade para exibição no console (ex: "marca" ou "modelo").
     * @return O código válido digitado pelo usuário.
     */
    private String validateUserInput(List<Data> dataList, String entityName) {
        String userInput;
        boolean isValid;

        do{
            System.out.println("\nEscolha o código da " + entityName + " que você deseja:");
            userInput = scanner.nextLine();

            String finalUserInput = userInput;
            isValid = dataList.stream().anyMatch(data ->
                    data.dataCode().equalsIgnoreCase(finalUserInput));

        } while(!isValid);

        return userInput;
    }
}
