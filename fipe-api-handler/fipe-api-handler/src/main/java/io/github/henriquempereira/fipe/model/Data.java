package io.github.henriquempereira.fipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa uma estrutura genérica de dados retornada pela API da FIPE.
 * É reaproveitada para mapear Marcas, Modelos e Anos, pois a API
 * utiliza o mesmo padrão de chaves ("codigo" e "nome") para essas três entidades.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(@JsonAlias("nome" ) String dataName,
                   @JsonAlias("codigo") String dataCode) {
}
