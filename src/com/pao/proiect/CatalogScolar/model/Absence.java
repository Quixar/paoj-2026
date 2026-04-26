package com.pao.proiect.CatalogScolar.model;

import java.time.LocalDate;

public class Absence {
    private Student student;
    private Subject subject;
    private LocalDate date;
    private boolean motivated;

    public Absence(Student student, Subject subject, LocalDate date, boolean motivated) {
        this.student = student;
        this.subject = subject;
        this.date = date;
        this.motivated = motivated;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isMotivated() {
        return motivated;
    }

    public void setMotivated(boolean motivated) {
        this.motivated = motivated;
    }

    @Override
    public String toString() {
        return "Absență:\n" +
                "  Student: " + student.getFullName() + "\n" +
                "  Materie: " + subject.getName() + "\n" +
                "  Data: " + date + "\n" +
                "  Status: " + (motivated ? "motivată" : "nemotivată");
    }
}