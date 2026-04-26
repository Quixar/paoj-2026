package com.pao.proiect.CatalogScolar.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private Diriginte diriginte;
    private List<Student> students;

    public Group(String name, Diriginte diriginte) {
        this.name = name;
        this.diriginte = diriginte;
        this.students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Diriginte getDiriginte() {
        return diriginte;
    }

    public void setDiriginte(Diriginte diriginte) {
        this.diriginte = diriginte;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    @Override
    public String toString() {
        return "Grupă:\n" +
                "  Nume: " + name + "\n" +
                "  Diriginte: " + diriginte.getFullName() + "\n" +
                "  Număr studenți: " + students.size() + "\n" +
                "  Studenți: " + students;
    }
}