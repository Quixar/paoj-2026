package com.pao.proiect.CatalogScolar.services;

import com.pao.proiect.CatalogScolar.exception.StudentNotFoundException;
import com.pao.proiect.CatalogScolar.model.CodInmatriculare;
import com.pao.proiect.CatalogScolar.model.Student;
import com.pao.proiect.CatalogScolar.repository.StudentRepository;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StudentService {
    private static StudentService instance;
    private final StudentRepository studentRepository;
    private final AuditService auditService;

    private StudentService() {
        this.studentRepository = new StudentRepository();
        this.auditService = AuditService.getInstance();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(Student student) {
        auditService.logAction("adauga_student");
        studentRepository.save(student);
    }

    public void deleteStudent(CodInmatriculare code) throws StudentNotFoundException {
        auditService.logAction("sterge_student");
        // Проверяем существование перед удалением
        findByCode(code);
        studentRepository.delete(code.getValoare());
    }

    public Student findByCode(CodInmatriculare code) throws StudentNotFoundException {
        auditService.logAction("cauta_student_dupa_cod");
        return studentRepository.findById(code.getValoare())
                .orElseThrow(() -> new StudentNotFoundException("Studentul cu codul " + code + " nu a fost găsit."));
    }

    public List<Student> getAllStudents() {
        auditService.logAction("listeaza_studenti");
        return studentRepository.findAll();
    }

    public Set<Student> getSortedStudents() {
        auditService.logAction("listeaza_studenti_sortati");
        return new TreeSet<>(studentRepository.findAll());
    }

    public String getAllStudentsAsString() {
        List<Student> allStudents = getAllStudents();
        if (allStudents.isEmpty()) {
            return "Nu există studenți în catalog.";
        }
        StringBuilder result = new StringBuilder("Lista studenților din DB:\n");
        for (Student student : allStudents) {
            result.append(student).append("\n\n");
        }
        return result.toString();
    }

    public String getSortedStudentsAsString() {
        Set<Student> sorted = getSortedStudents();
        if (sorted.isEmpty()) {
            return "Nu există studenți în catalog.";
        }
        StringBuilder result = new StringBuilder("Lista studenților sortați alfabetic din DB:\n");
        for (Student student : sorted) {
            result.append(student).append("\n\n");
        }
        return result.toString();
    }
}