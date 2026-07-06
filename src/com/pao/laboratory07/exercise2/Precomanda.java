package com.pao.laboratory07.exercise2;

public final class Precomanda extends Comanda {
    private String dataLivrare;

    public Precomanda(String id, String client, double valoare, String dataLivrare) {
        super(id, client, valoare);
        this.dataLivrare = dataLivrare;
    }

    @Override
    public void procesare() {
    }

    @Override
    public String tipComanda() {
        return "PRECOMANDA";
    }

    @Override
    public boolean esteSpeciala() {
        return true;
    }

    @Override
    public void afiseaza() {
        System.out.printf("PRECOMANDA: %s %s, valoare: %.2f lei, livrare: %s%n", id, client, valoare, dataLivrare);
    }
}