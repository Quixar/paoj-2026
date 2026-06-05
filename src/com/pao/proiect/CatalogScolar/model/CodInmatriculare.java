package com.pao.proiect.CatalogScolar.model;

import java.util.Objects;

public final class CodInmatriculare {
    private final String valoare;

    public CodInmatriculare(String valoare) {
        if (valoare == null || valoare.isBlank()) {
            throw new IllegalArgumentException("Codul de înmatriculare nu poate fi gol.");
        }
        this.valoare = valoare;
    }

    public String getValoare() {
        return valoare;
    }

    @Override
    public String toString() {
        return valoare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodInmatriculare that = (CodInmatriculare) o;
        return Objects.equals(valoare, that.valoare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valoare);
    }
}