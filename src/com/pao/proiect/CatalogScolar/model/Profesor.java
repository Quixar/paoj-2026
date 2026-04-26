package com.pao.proiect.CatalogScolar.model;

import java.util.Objects;

public class Profesor extends Person {
    private String department;

    public Profesor(String name, String surname, String email, String department) {
        super(name, surname, email);
        this.department = department;
    }

    @Override
    public String getRole() {
        return "Profesor";
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Profesor:\n" +
                "  Nume: " + getFullName() + "\n" +
                "  Email: " + email + "\n" +
                "  Departament: " + department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profesor profesor = (Profesor) o;
        return Objects.equals(email, profesor.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}