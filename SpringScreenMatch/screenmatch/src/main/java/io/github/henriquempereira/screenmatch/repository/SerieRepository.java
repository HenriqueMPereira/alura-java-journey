package io.github.henriquempereira.screenmatch.repository;

import io.github.henriquempereira.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository <Serie, Long> {
}
