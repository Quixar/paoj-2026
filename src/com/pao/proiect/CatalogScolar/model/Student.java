package com.pao.proiect.CatalogScolar.model;

import java.util.Objects;

public class Student extends Person implements Comparable<Student> {
    private CodInmatriculare codInmatriculare;
    private int year;

    public Student(String name, String surname, String email, CodInmatriculare codInmatriculare, int year) {
        super(name, surname, email);
        this.codInmatriculare = codInmatriculare;
        this.year = year;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    public CodInmatriculare getCodInmatriculare() {
        return codInmatriculare;
    }

    public void setCodInmatriculare(CodInmatriculare codInmatriculare) {
        this.codInmatriculare = codInmatriculare;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(Student other) {
        int surnameCompare = this.surname.compareToIgnoreCase(other.surname);
        if (surnameCompare != 0) {
            return surnameCompare;
        }
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return "Student:\n" +
                "  Nume: " + getFullName() + "\n" +
                "  Email: " + email + "\n" +
                "  Cod înmatriculare: " + codInmatriculare.getValoare() + "\n" +
                "  An: " + year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;
        return Objects.equals(codInmatriculare, student.codInmatriculare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codInmatriculare);
    }
}