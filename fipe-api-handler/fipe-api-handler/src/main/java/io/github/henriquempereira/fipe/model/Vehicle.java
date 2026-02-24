package io.github.henriquempereira.fipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa os dados finais de um veículo retornados pela API da FIPE.
 * Utiliza o 'record' para garantir imutabilidade dos dados.
 * * A anotação @JsonIgnoreProperties garante que a aplicação não quebre
 * caso a API adicione novos campos no JSON original que não mapeamos aqui.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Vehicle(@JsonAlias ("Modelo") String name,
                      @JsonAlias ("Marca") String brand,
                      @JsonAlias ("AnoModelo") String year,
                      @JsonAlias ("Combustivel") String fuel,
                      @JsonAlias ("Valor") String price) {
}