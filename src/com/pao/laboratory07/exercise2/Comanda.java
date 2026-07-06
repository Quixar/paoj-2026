package com.pao.laboratory07.exercise2;

public abstract sealed class Comanda implements ActiuneComanda
        permits ComandaStandard, Precomanda, ComandaAbonament {

    protected String id;
    protected String client;
    protected double valoare;

    public Comanda(String id, String client, double valoare) {
        this.id = id;
        this.client = client;
        this.valoare = valoare;
    }

    public String getId() {
        return id;
    }

    public double getValoare() {
        return valoare;
    }

    public abstract void procesare();

    @Override
    public void proceseaza() {
        procesare();
    }
}