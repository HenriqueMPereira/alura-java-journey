package io.github.henriquempereira.screenmatch.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity // Avisa o Hibernate que essa classe é uma Entidade
@Table(name = "series") // Opcional. Serve para dizer qual o nome da tabela. Se não, será o nome da classe.
public class Serie {

    @Id // Avisa qual campo será a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Diz ao Hibernate como será gerada a chave primária
    private long id;
    @Column(unique = true) // Usado para especificar detalhes da coluna (no caso o título será único)
    private String title;
    private Double rating;
    private Integer numberOfSeasons;
    @Enumerated(EnumType.STRING) // Diz ao Hibernate como é pra gravar o Enum (Ordinal ou String)
    private Categoria genres; // O que é um ENUM
    private String actors;
    private String poster;
    private String plot;

    @OneToMany(mappedBy = "serie")
    private List<Episode> episodeList = new ArrayList<>();

    public Serie(){}

    public Serie(SeriesData seriesData) {
        this.title = seriesData.title();
        this.rating = OptionalDouble.of(Double.valueOf(seriesData.rating())).orElse(0);
        this.numberOfSeasons = seriesData.numberOfSeasons();
        // split separa em cada virgula e guarda em um array de string, pega o primeiro valor e "limpa" com trim
        this.genres = Categoria.fromString(seriesData.genres().split(",")[0].trim());
        this.actors = seriesData.actors();
        this.poster = seriesData.poster();
        this.plot = seriesData.plot();
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
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
