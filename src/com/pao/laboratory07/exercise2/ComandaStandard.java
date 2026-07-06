package com.pao.laboratory07.exercise2;

public final class ComandaStandard extends Comanda {
    public ComandaStandard(String id, String client, double valoare) {
        super(id, client, valoare);
    }

    @Override
    public void procesare() {
    }

    @Override
    public String tipComanda() {
        return "STANDARD";
    }

    @Override
    public void afiseaza() {
        System.out.printf("STANDARD: %s %s, valoare: %.2f lei%n", id, client, valoare);
    }
}