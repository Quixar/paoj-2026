package com.pao.proiect.CatalogScolar.services;

import com.pao.proiect.CatalogScolar.exception.StudentNotFoundException;
import com.pao.proiect.CatalogScolar.model.CodInmatriculare;
import com.pao.proiect.CatalogScolar.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Set;

public class StudentService {
    private static StudentService instance;

    private final List<Student> students;
    private final Map<CodInmatriculare, Student> studentsByCode;
    private final Set<Student> sortedStudents;

    private StudentService() {
        students = new ArrayList<>();
        studentsByCode = new HashMap<>();
        sortedStudents = new TreeSet<>();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(Student student) {
        students.add(student);
        studentsByCode.put(student.getCodInmatriculare(), student);
        sortedStudents.add(student);
    }

    public void deleteStudent(CodInmatriculare code) throws StudentNotFoundException {
        Student student = findByCode(code);
        students.remove(student);
        studentsByCode.remove(code);
        sortedStudents.remove(student);
    }

    public Student findByCode(CodInmatriculare code) throws StudentNotFoundException {
        Student student = studentsByCode.get(code);

        if (student == null) {
            throw new StudentNotFoundException("Studentul cu codul " + code + " nu a fost găsit.");
        }

        return student;
    }

    public Student findByName(String name) throws StudentNotFoundException {
        for (Student student : students) {
            if (student.getFullName().equalsIgnoreCase(name)) {
                return student;
            }
        }

        throw new StudentNotFoundException("Studentul cu numele " + name + " nu a fost găsit.");
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Set<Student> getSortedStudents() {
        return new TreeSet<>(sortedStudents);
    }

    public String getAllStudentsAsString() {
        if (students.isEmpty()) {
            return "Nu există studenți în catalog.";
        }

        StringBuilder result = new StringBuilder("Lista studenților:\n");

        for (Student student : students) {
            result.append(student)
                    .append("\n\n");
        }

        return result.toString();
    }

    public String getSortedStudentsAsString() {
        if (sortedStudents.isEmpty()) {
            return "Nu există studenți în catalog.";
        }

        StringBuilder result = new StringBuilder("Lista studenților sortați alfabetic:\n");

        for (Student student : sortedStudents) {
            result.append(student)
                    .append("\n\n");
        }

        return result.toString();
    }
}