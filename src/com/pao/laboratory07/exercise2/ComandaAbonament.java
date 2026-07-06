package com.pao.laboratory07.exercise2;

public final class ComandaAbonament extends Comanda {
    private int nrLuni;

    public ComandaAbonament(String id, String client, double valoare, int nrLuni) {
        super(id, client, valoare);
        this.nrLuni = nrLuni;
    }

    @Override
    public void procesare() {
    }

    @Override
    public String tipComanda() {
        return "ABONAMENT";
    }

    @Override
    public boolean esteSpeciala() {
        return true;
    }

    @Override
    public void afiseaza() {
        System.out.printf("ABONAMENT: %s %s, valoare: %.2f lei, luni: %d%n", id, client, valoare, nrLuni);
    }
}