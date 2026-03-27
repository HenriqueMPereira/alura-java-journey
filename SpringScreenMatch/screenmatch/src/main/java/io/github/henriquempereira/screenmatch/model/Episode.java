package io.github.henriquempereira.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episode")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private int season;
    private int episodeNumber;
    private Double rating;
    private LocalDate released;

    @ManyToOne
    private Serie serie;

    public Episode(){}

    public Episode(String season, EpisodeData episodeData) {
        this.title = episodeData.title();
        this.season = Integer.parseInt(season);
        this.episodeNumber = episodeData.episodeNumber();

        try {
            this.released = LocalDate.parse(episodeData.released());
        } catch (DateTimeParseException e) {
            this.released = null;
        }

        try {
            this.rating = Double.valueOf(episodeData.rating());
        } catch (NumberFormatException e){
            this.rating = 0.0;
        }
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getSeason() {
        return season;
    }

    public LocalDate getReleased() {
        return released;
    }

    public Double getRating() {
        return rating;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String toString() {
        return "Title = " + title  +
                ", Season ='" + season +
                ", Released ='" + released +
                ", Rating ='" + rating + "\n";
    }
}
