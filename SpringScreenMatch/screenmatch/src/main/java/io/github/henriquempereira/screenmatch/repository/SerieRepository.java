package io.github.henriquempereira.screenmatch.repository;

import io.github.henriquempereira.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SerieRepository extends JpaRepository <Serie, Long> {
    Optional<Serie> findByTitleContainingIgnoreCase(String serieName);
}
