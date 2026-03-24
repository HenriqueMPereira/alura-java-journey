package io.github.henriquempereira.screenmatch.repository;

import io.github.henriquempereira.screenmatch.model.Episode;
import io.github.henriquempereira.screenmatch.model.Genre;
import io.github.henriquempereira.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository <Serie, Long> {
    Optional<Serie> findByTitleContainingIgnoreCase(String serieName);

    List<Serie> findByActorsContainingIgnoreCaseAndRatingGreaterThan(String actorName, double rating);

    List<Serie> findTop5ByOrderByRatingDesc();

    List<Serie> findSerieByGenres(Genre genre);

    @Query("SELECT s FROM Serie s WHERE s.numberOfSeasons <= :numOfSeason AND s.rating >= :minRating ")
    List<Serie> findSerieBySeasonAndRating(int numOfSeason, Double minRating);

    @Query("SELECT e FROM Serie s JOIN s.episodeList e WHERE e.title ILIKE %:episodeName%")
    List<Episode> findEpisodeByTitle(String episodeName);

    @Query("SELECT e FROM Serie s JOIN s.episodeList e WHERE s =:serie ORDER BY e.rating DESC LIMIT 5")
    List<Episode> findTop5Episodes(Serie serie);
}
