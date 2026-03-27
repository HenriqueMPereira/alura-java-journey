package io.github.henriquempereira.screenmatch.dto;

import io.github.henriquempereira.screenmatch.model.Genre;

public record SerieDTO(Long id,
                       String titulo,
                       Integer totalTemporadas,
                       Double avaliacao,
                       Genre genero,
                       String atores,
                       String poster,
                       String sinopse) {
}
