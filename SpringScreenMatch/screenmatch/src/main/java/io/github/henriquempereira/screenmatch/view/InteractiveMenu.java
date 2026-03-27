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

    Optional<Serie> serieSearched;

    public void starMenu() {
        var menu = """
                    Escolha uma das opções abaixo
                    1 - Escolher séries
                    2 - Escolher episódios
                    3 - Lista séries buscadas
                    4 - Buscar série pelo nome
                    5 - Buscar série pelo nome do ator e avaliação
                    6 - Buscar top 5 séries
                    7 - Buscar série por genre
                    8 - Buscar por qnt temporada e avaliação mínima
                    9 - Buscar episodios por trecho do título
                    10 - Buscar top 5 episódios
                    
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
                case "4":
                    findSerieByName();
                    break;
                case "5":
                    findSerieByNameAndRating();
                    break;
                case "6":
                    findTopByRating();
                    break;
                case "7":
                    findSerieByCategoria();
                    break;
                case "8":
                    findSerieBySeasonAndRating();
                    break;
                case "9":
                    findEpisodeByTitle();
                    break;
                case "10":
                    findTopEpisodes();
                    break;
                case "0":
                    break;
            }
        }
        scanner.close();
        return;
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
        showSeries();
        System.out.println("Digite o nome da série: ");
        var serieName = scanner.nextLine();

        Optional<Serie> serie = repository.findByTitleContainingIgnoreCase(serieName);

        if(serie.isPresent()){
            var serieSearched = serie.get();
            List<SeasonData> seasonDataList = new ArrayList<>();
            for(int i = 1; i <= serieSearched.getNumberOfSeasons(); i++){
                var json = apiClient.fetchData(ADRESS + URLEncoder.encode(serieSearched.getTitle(), StandardCharsets.UTF_8) +
                        "&season=" + i + "&" + API_KEY);
                seasonDataList.add(dataConverter.convertTo(json, SeasonData.class));
            }
            seasonDataList.forEach(System.out::println);

            List<Episode> episodes = seasonDataList.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.season(), e)))
                    .collect(Collectors.toList());

            serieSearched.setEpisodeList(episodes);

            repository.save(serieSearched);

        } else {
            System.out.println("Série não encontrada!");
        }
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

    private void findSerieByName() {
        System.out.println("Digite a serie que deseja buscar:");
        var serieName = scanner.nextLine();
        serieSearched = repository.findByTitleContainingIgnoreCase(serieName);

        if(serieSearched.isPresent()){
            System.out.println("Dados da série: " + serieSearched.get());
        } else {
            System.out.println("Série não encontra!!!");
        }
    }

    private void findSerieByNameAndRating() {
        System.out.println("Digite o nome do ator: ");
        var actorName = scanner.nextLine();
        System.out.println("Digite a avaliação mínima: ");
        var rating = scanner.nextDouble();
        var cleaning = scanner.nextLine();
        List<Serie> serieSearched = repository.findByActorsContainingIgnoreCaseAndRatingGreaterThan(actorName, rating);

        serieSearched.stream()
                .forEach(s -> System.out.println("Série: " + s.getTitle() + " -> Avaliação: " + s.getRating()));
    }

    private void findTopByRating() {
        List<Serie> series = repository.findTop5ByOrderByRatingDesc();
        series.forEach(System.out::println);
    }

    private void findSerieByCategoria() {
        System.out.println("Digite a genre que deseja buscar: ");
        var userGenre = scanner.nextLine();
        Genre genre = Genre.fromString(userGenre);
        List<Serie> serie = repository.findSerieByGenres(genre);
        serie.forEach(System.out::println);
    }

    private void findSerieBySeasonAndRating(){
        System.out.println("Digite a quantidade máxima de temporadas: ");
        var numOfSeason = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Digite a avaliação mínima: ");
        var minRating = scanner.nextDouble();
        scanner.nextLine();
        List<Serie> series = repository.findSerieBySeasonAndRating(numOfSeason,minRating);
        series.forEach(System.out::println);
    }

    private void findEpisodeByTitle() {
        System.out.println("Digite o nome do episódio que deseja buscar: ");
        var episodeName = scanner.nextLine();
        List<Episode> episodesSearched = repository.findEpisodeByTitle(episodeName);
        episodesSearched.stream()
                .forEach(e -> System.out.println("Título: " + e.getTitle() +
                        " -> Temporada: " + e.getSeason() + " -> Série: " + e.getSerie().getTitle() +
                        " -> Avaliação: " + e.getRating()));
    }

    private void findTopEpisodes() {
        findSerieByName();
        if(serieSearched.isPresent()){
            Serie serie = serieSearched.get();
            List<Episode> episodes = repository.findTop5Episodes(serie);
            episodes.stream()
                    .forEach(e -> System.out.println("Título: " + e.getTitle() +
                            " -> Temporada: " + e.getSeason() + " -> Nome da Série: " + e.getSerie().getTitle()
                     + " -> Avaliação: " + e.getRating()));
        }
    }
}
