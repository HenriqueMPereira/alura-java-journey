package io.github.henriquempereira.screenmatch.controller;

import io.github.henriquempereira.screenmatch.dto.EpisodeDTO;
import io.github.henriquempereira.screenmatch.dto.SerieDTO;

import io.github.henriquempereira.screenmatch.services.SerieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> getSerie() {
        return service.showAllSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getTop5() {
        return service.showTop5();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> getReleases() {
        return service.showReleases();
    }

    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable Long id) {
        return service.showById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllSeasons(@PathVariable Long id){
        return service.showAllSeasons(id);
    }

    @GetMapping("/{id}/temporadas/{number}")
    public List<EpisodeDTO> getSeason(@PathVariable Long id, @PathVariable Long number) {
        return service.showSeasonByNumber(id, number);
    }

    @GetMapping("/categoria/{genre}")
    public List<SerieDTO> getSerieByGenre(@PathVariable String genre){
        return service.showSerieByGenre(genre);
    }
}