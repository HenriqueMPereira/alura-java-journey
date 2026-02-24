package io.github.henriquempereira.fipe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.henriquempereira.fipe.model.Data;
import io.github.henriquempereira.fipe.model.VehicleModels;
import io.github.henriquempereira.fipe.model.Vehicle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Cliente HTTP responsável por encapsular toda a comunicação com a API REST pública da FIPE.
 * Centraliza as requisições e a desserialização dos dados (JSON para Objetos Java).
 */
public class FipeClient {

    private final HttpClient client;
    private final ObjectMapper mapper;
    private final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/";

    /**
     * Construtor com injeção de dependências.
     * Facilita o controle de instâncias e a criação de testes automatizados.
     */
    public FipeClient(HttpClient client, ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    /**
     * Método utilitário privado que centraliza o disparo das requisições GET.
     * @param url O endereço completo da requisição.
     * @return O corpo da resposta HTTP em formato de String (JSON).
     */
    private String fetchData(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca a lista de marcas disponíveis para um tipo específico de veículo.
     * @param typeOfVehicle O tipo do veículo (ex: "carros", "motos", "caminhoes").
     * @return Lista de objetos Data contendo os códigos e nomes das marcas.
     */
    public List<Data> fetchDataType(String typeOfVehicle) {
        String json = fetchData(BASE_URL + typeOfVehicle + "/marcas");
        try {
            return mapper.readValue(json, new TypeReference<List<Data>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca os modelos de uma marca específica.
     * @param typeOfVehicle O tipo do veículo.
     * @param modelOfVehicle O código numérico da marca (ex: "59" para VW).
     * @return Lista de objetos Data representando os modelos disponíveis.
     */
    public List<Data> fetchDataModel(String typeOfVehicle, String modelOfVehicle) throws RuntimeException {
        String json = fetchData(BASE_URL + typeOfVehicle + "/marcas/" + modelOfVehicle + "/modelos");
        try {
            VehicleModels response = mapper.readValue(json, VehicleModels.class);
            return response.models();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca os anos de fabricação disponíveis para um modelo específico.
     * @param typeOfVehicle O tipo do veículo.
     * @param modelOfVehicle O código da marca.
     * @param codeOfVehicle O código do modelo.
     * @return Lista de objetos Data representando os anos disponíveis.
     */
    public List<Data> fetchDataModelByCode(String typeOfVehicle, String modelOfVehicle, String codeOfVehicle) {
        String json = fetchData(BASE_URL + typeOfVehicle + "/marcas/" + modelOfVehicle +
                "/modelos/" + codeOfVehicle + "/anos");
        try {
            return mapper.readValue(json, new TypeReference<List<Data>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca informações detalhadas (preço e especificações) de um veículo.
     * @param typeOfVehicle O tipo do veículo.
     * @param modelOfVehicle O código da marca.
     * @param codeOfVehicle O código do modelo.
     * @param code O código do ano de fabricação.
     * @return Um objeto Vehicles contendo os dados finais de precificação.
     */
    public Vehicle fetchVehicleData(String typeOfVehicle, String modelOfVehicle, String codeOfVehicle, String code){
        String json = fetchData(BASE_URL + typeOfVehicle + "/marcas/" + modelOfVehicle +
                "/modelos/" + codeOfVehicle + "/anos/" + code);
        try {
            return mapper.readValue(json, Vehicle.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
