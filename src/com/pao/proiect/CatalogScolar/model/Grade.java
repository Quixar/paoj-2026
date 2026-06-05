package com.pao.proiect.CatalogScolar.model;

import java.time.LocalDate;

public class Grade {
    private Student student;
    private Subject subject;
    private int value;
    private LocalDate date;

    public Grade(Student student, Subject subject, int value, LocalDate date) {
        if (value < 1 || value > 10) {
            throw new IllegalArgumentException("Nota trebuie să fie între 1 și 10.");
        }
        this.student = student;
        this.subject = subject;
        this.value = value;
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < 1 || value > 10) {
            throw new IllegalArgumentException("Nota trebuie să fie între 1 și 10.");
        }
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Notă:\n" +
                "  Student: " + student.getFullName() + "\n" +
                "  Materie: " + subject.getName() + "\n" +
                "  Valoare: " + value + "\n" +
                "  Data: " + date;
    }
}