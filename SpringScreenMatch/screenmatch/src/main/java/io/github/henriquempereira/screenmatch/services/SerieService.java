package io.github.henriquempereira.screenmatch.services;

import io.github.henriquempereira.screenmatch.dto.EpisodeDTO;
import io.github.henriquempereira.screenmatch.dto.SerieDTO;
import io.github.henriquempereira.screenmatch.model.Genre;
import io.github.henriquempereira.screenmatch.model.Serie;
import io.github.henriquempereira.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> showAllSeries() {
        return convertSerieDTO(repository.findAll());
    }

    public List<SerieDTO> showTop5() {
        return convertSerieDTO(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SerieDTO> showReleases() {
        return convertSerieDTO(repository.findLatestReleases());
    }

    public SerieDTO showById(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getActors(), s.getNumberOfSeasons(), s.getRating(),
                    s.getGenres(), s.getPlot(), s.getPoster(), s.getTitle());
        } else {
            return null;
        }
    }

    private List<SerieDTO> convertSerieDTO(List<Serie> serie) {
        return serie.stream()
                .map(s -> new SerieDTO(s.getId(), s.getActors(), s.getNumberOfSeasons(), s.getRating(),
                        s.getGenres(), s.getPlot(), s.getPoster(), s.getTitle()))
                .toList();
    }

    public List<EpisodeDTO> showAllSeasons(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodeList().stream()
                    .map(e -> new EpisodeDTO(e.getSeason(), e.getEpisodeNumber() ,e.getTitle()))
                    .toList();
        }
        return null;
    }

    public List<EpisodeDTO> showSeasonByNumber(Long id, Long number) {
        return repository.findSeasonByNumber(id, number).stream()
                .map(e -> new EpisodeDTO(e.getSeason(), e.getEpisodeNumber(), e.getTitle()))
                .toList();
    }

    public List<SerieDTO> showSerieByGenre(String genre) {
        Genre genre1 = Genre.fromPortuguese(genre);
        return convertSerieDTO(repository.findSerieByGenres(genre1));

    }
}
