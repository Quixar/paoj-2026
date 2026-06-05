package com.pao.proiect.CatalogScolar.services;

import com.pao.proiect.CatalogScolar.exception.SubjectNotFoundException;
import com.pao.proiect.CatalogScolar.model.Subject;
import com.pao.proiect.CatalogScolar.repository.SubjectRepository;

import java.util.List;

public class SubjectService {
    private static SubjectService instance;
    private final SubjectRepository subjectRepository;
    private final AuditService auditService;

    private SubjectService() {
        this.subjectRepository = new SubjectRepository();
        this.auditService = AuditService.getInstance();
    }

    public static SubjectService getInstance() {
        if (instance == null) {
            instance = new SubjectService();
        }
        return instance;
    }

    public void addSubject(Subject subject) {
        auditService.logAction("adauga_materie");
        subjectRepository.save(subject);
    }

    public void deleteSubject(String code) throws SubjectNotFoundException {
        auditService.logAction("sterge_materie");
        findByCode(code);
        subjectRepository.delete(code);
    }

    public Subject findByCode(String code) throws SubjectNotFoundException {
        auditService.logAction("cauta_materie_dupa_cod");
        return subjectRepository.findById(code)
                .orElseThrow(() -> new SubjectNotFoundException("Materia cu codul " + code + " nu a fost găsită."));
    }

    public List<Subject> getAllSubjects() {
        auditService.logAction("listeaza_materii");
        return subjectRepository.findAll();
    }

    public String getAllSubjectsAsString() {
        List<Subject> allSubjects = getAllSubjects();
        if (allSubjects.isEmpty()) {
            return "Nu există materii în catalog.";
        }
        StringBuilder result = new StringBuilder("Lista materiilor din DB:\n");
        for (Subject subject : allSubjects) {
            result.append(subject).append("\n\n");
        }
        return result.toString();
    }
}