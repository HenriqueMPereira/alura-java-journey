package io.github.henriquempereira.screenmatch.model;

public enum Genre {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime");

    private String genreOmdb;
    private String genrePortuguese;

    Genre(String genreOmdb, String genrePortuguese){
        this.genreOmdb = genreOmdb;
        this.genrePortuguese = genrePortuguese;
    }

    public static Genre fromString(String text) {
        for (Genre categoria : Genre.values()) {
            if (categoria.genreOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Genre fromPortuguese(String text) {
        for (Genre categoria : Genre.values()) {
            if (categoria.genrePortuguese.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
