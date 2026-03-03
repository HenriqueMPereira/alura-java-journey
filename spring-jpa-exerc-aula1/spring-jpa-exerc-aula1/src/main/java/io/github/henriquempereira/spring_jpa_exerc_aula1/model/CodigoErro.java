package io.github.henriquempereira.spring_jpa_exerc_aula1.model;

public enum CodigoErro {
    NOT_FOUND (404, "404 Not Found"),
    BAD_REQUEST (400, "400 Bad Request"),
    INTERNAL_SERVER_ERROR (500, "500 Internal Server Error");

    private final int code;
    private final String message;

    CodigoErro(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCodigo() {
        return code;
    }

    public String getDescricao() {
        return message;
    }
}
