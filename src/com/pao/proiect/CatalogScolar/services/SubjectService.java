package com.pao.proiect.CatalogScolar.services;

import com.pao.proiect.CatalogScolar.exception.SubjectNotFoundException;
import com.pao.proiect.CatalogScolar.model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectService {
    private static SubjectService instance;

    private final List<Subject> subjects;
    private final Map<String, Subject> subjectsByCode;

    private SubjectService() {
        subjects = new ArrayList<>();
        subjectsByCode = new HashMap<>();
    }

    public static SubjectService getInstance() {
        if (instance == null) {
            instance = new SubjectService();
        }
        return instance;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        subjectsByCode.put(subject.getCode(), subject);
    }

    public void deleteSubject(String code) throws SubjectNotFoundException {
        Subject subject = findByCode(code);
        subjects.remove(subject);
        subjectsByCode.remove(code);
    }

    public Subject findByCode(String code) throws SubjectNotFoundException {
        Subject subject = subjectsByCode.get(code);

        if (subject == null) {
            throw new SubjectNotFoundException("Materia cu codul " + code + " nu a fost găsită.");
        }

        return subject;
    }

    public Subject findByName(String name) throws SubjectNotFoundException {
        for (Subject subject : subjects) {
            if (subject.getName().equalsIgnoreCase(name)) {
                return subject;
            }
        }

        throw new SubjectNotFoundException("Materia cu numele " + name + " nu a fost găsită.");
    }

    public List<Subject> getAllSubjects() {
        return new ArrayList<>(subjects);
    }

    public String getAllSubjectsAsString() {
        if (subjects.isEmpty()) {
            return "Nu există materii în catalog.";
        }

        StringBuilder result = new StringBuilder("Lista materiilor:\n");

        for (Subject subject : subjects) {
            result.append(subject)
                    .append("\n\n");
        }

        return result.toString();
    }
}