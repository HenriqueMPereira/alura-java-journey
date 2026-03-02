package io.github.henriquempereira.screenmatch.model;

import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Double rating;
    private Integer numberOfSeasons;
    private Categoria genres; // O que é um ENUM
    private String actors;
    private String poster;
    private String plot;

    public Serie(SeriesData seriesData) {
        this.title = seriesData.title();
        this.rating = OptionalDouble.of(Double.valueOf(seriesData.rating())).orElse(0);
        this.numberOfSeasons = seriesData.numberOfSeasons();
        // split separa em cada virgula e guuarda em um array de string, pega o primeiro valor e "limpa" com trim
        this.genres = Categoria.fromString(seriesData.genres().split(",")[0].trim());
        this.actors = seriesData.actors();
        this.poster = seriesData.poster();
        this.plot = seriesData.plot();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public Categoria getGenres() {
        return genres;
    }

    public void setGenres(Categoria genres) {
        this.genres = genres;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public String toString() {
        return
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", numberOfSeasons=" + numberOfSeasons +
                ", genres=" + genres +
                ", actors='" + actors + '\'' +
                ", poster='" + poster + '\'' +
                ", plot='" + plot + '\'';
    }
}
