package io.github.henriquempereira.screenmatch.model;

public enum Genre {
    ACAO("Action"),
    DRAMA("Drama"),
    CRIME("Crime"),
    ROMANCE("Romance"),
    COMEDIA("Comedy");

    private String genreOmdb;

    Genre(String genreOmdb) {
        this.genreOmdb = genreOmdb;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.genreOmdb.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
