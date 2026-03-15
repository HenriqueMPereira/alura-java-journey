package io.github.henriquempereira.screenmatch.repository;

import io.github.henriquempereira.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository <Serie, Long> {
    Optional<Serie> findByTitleContainingIgnoreCase(String serieName);

    List<Serie> findByActorsContainingIgnoreCaseAndRatingGreaterThan(String actorName, double rating);

    List<Serie> findTop5ByOrderByRatingDesc();
}
