package io.github.henriquempereira.fipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Record de encapsulamento para mapear a resposta de modelos da API.
 * Criado especificamente porque o endpoint de modelos da FIPE retorna um objeto
 * contendo um array, em vez de retornar o array diretamente.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VehicleModels(@JsonAlias("modelos") List<Data> models) {
}
