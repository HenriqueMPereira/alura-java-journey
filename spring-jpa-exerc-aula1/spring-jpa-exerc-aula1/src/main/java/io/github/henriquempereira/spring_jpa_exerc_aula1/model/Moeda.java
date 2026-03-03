package io.github.henriquempereira.spring_jpa_exerc_aula1.model;

public enum Moeda {
    REAL(1.0),
    DOLAR(5.29),
    EURO(6.14);

    private final Double valorMoeda;

    Moeda(double v) {
        this.valorMoeda = v;
    }

    public Double converterPara(int x) {
        return x/valorMoeda;
    }
}
