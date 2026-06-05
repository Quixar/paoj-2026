package com.pao.proiect.CatalogScolar.model;

import java.util.Objects;

public class Subject {
    private String code;
    private String name;
    private Profesor professor;

    public Subject(String code, String name, Profesor professor) {
        this.code = code;
        this.name = name;
        this.professor = professor;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profesor getProfessor() {
        return professor;
    }

    public void setProfessor(Profesor professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "Materie:\n" +
                "  Denumire: " + name + "\n" +
                "  Cod: " + code + "\n" +
                "  Profesor: " + professor.getFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;
        return Objects.equals(code, subject.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}