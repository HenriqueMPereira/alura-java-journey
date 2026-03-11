package io.github.henriquempereira.screenmatch.view;

import io.github.henriquempereira.screenmatch.model.*;
import io.github.henriquempereira.screenmatch.repository.SerieRepository;
import io.github.henriquempereira.screenmatch.services.ApiClient;
import io.github.henriquempereira.screenmatch.services.DataConverter;

import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InteractiveMenu {
    private final String ADRESS = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e8be24d1";

    private final SerieRepository repository;

    public InteractiveMenu(SerieRepository repository) {
        this.repository = repository;
    }

    private Scanner scanner = new Scanner(System.in);
    private ApiClient apiClient = new ApiClient();
    private DataConverter dataConverter = new DataConverter();
    private String userChoice = "1";

    private List<Serie> serieList = new ArrayList<>();

    private List<SeriesData> seriesDataList = new ArrayList<>();

    public void starMenu() {
        var menu = """
                    Escolha uma das opções abaixo
                    1 - Escolher séries
                    2 - Escolher episódios
                    3 - Lista séries buscadas
                    
                    0 - Sair
                    """;
        while (!userChoice.equals("0")) {
            System.out.println(menu);
            userChoice = scanner.nextLine();

            switch (userChoice){
                case "1":
                    getSeries();
                    break;
                case "2":
                    getEpisodeData();
                    break;
                case "3":
                    showSeries();
                    break;
                case "0":
                    break;
            }
        }
    }

    private void getSeries() {
        SeriesData seriesData = getSeriesData();
        //seriesDataList.add(seriesData);
        Serie serie = new Serie(seriesData);
        repository.save(serie);
        System.out.println(seriesData);
    }

    private SeriesData getSeriesData() {
        System.out.println("Digite a serie que deseja buscar:");
        var serieName = scanner.nextLine();
        var json = apiClient.fetchData(ADRESS + URLEncoder.encode(serieName, StandardCharsets.UTF_8) + API_KEY);
        return dataConverter.convertTo(json, SeriesData.class);
    }

    private void getEpisodeData() {
        SeriesData seriesData = getSeriesData();
        showSeries();
        System.out.println("Digite o noem do episódio : ");
        var serieName = scanner.nextLine();
        List<EpisodeData> episodeDataList = new ArrayList<>();

        for(int i = 0; i < seriesData.numberOfSeasons(); i++){
            var json = apiClient.fetchData(ADRESS + URLEncoder.encode(seriesData.title(), StandardCharsets.UTF_8) +
                    "&season=" + i + "&" + API_KEY);
            episodeDataList.add(dataConverter.convertTo(json, EpisodeData.class));
        }
        episodeDataList.forEach(System.out::println);
    }

    private void showSeries() {
        serieList = repository.findAll();
//        serieList = seriesDataList.stream()
//                .map(d -> new Serie(d))
//                .collect(Collectors.toList());

        serieList.stream()
                .sorted(Comparator.comparing(Serie::getGenres))
                .forEach(System.out::println);
    }
}
