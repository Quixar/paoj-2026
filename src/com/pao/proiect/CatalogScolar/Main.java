package com.pao.proiect.CatalogScolar;

import com.pao.proiect.CatalogScolar.model.*;
import com.pao.proiect.CatalogScolar.repository.*;
import com.pao.proiect.CatalogScolar.services.AuditService;
import com.pao.proiect.CatalogScolar.services.CatalogService;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Pornire Aplicație Catalog Școlar ===");

        AuditService audit = AuditService.getInstance();
        CatalogService catalogService = new CatalogService();

        ProfesorRepository profRepo = new ProfesorRepository();
        StudentRepository studRepo = new StudentRepository();
        SubjectRepository subRepo = new SubjectRepository();
        GradeRepository gradeRepo = new GradeRepository();

        audit.logAction("init_aplicatie");

        Profesor prof1 = new Profesor("Ion", "Creanga", "ion.creanga@scoala.ro", "Limba Romana");
        profRepo.save(prof1);
        audit.logAction("adauga_profesor");

        Profesor prof2 = new Profesor("Albert", "Einstein", "albert.e@scoala.ro", "Fizica");
        profRepo.save(prof2);
        audit.logAction("adauga_profesor");

        Student stud1 = new Student("Popescu", "Dan", "dan.popescu@student.ro", new CodInmatriculare("ST101"), 1);
        studRepo.save(stud1);
        audit.logAction("adauga_student");

        Student stud2 = new Student("Ionescu", "Ana", "ana.ionescu@student.ro", new CodInmatriculare("ST102"), 1);
        studRepo.save(stud2);
        audit.logAction("adauga_student");

        Subject sub1 = new Subject("ROM01", "Limba Romana", prof1);
        subRepo.save(sub1);
        audit.logAction("adauga_materie");

        Subject sub2 = new Subject("FIZ02", "Fizica", prof2);
        subRepo.save(sub2);
        audit.logAction("adauga_materie");

        Grade g1 = new Grade(stud1, sub1, 10, LocalDate.now());
        gradeRepo.save(g1);
        audit.logAction("adauga_nota");

        Grade g2 = new Grade(stud2, sub1, 9, LocalDate.now());
        gradeRepo.save(g2);
        audit.logAction("adauga_nota");

        Diriginte diriginte = new Diriginte(prof1.getName(), prof1.getSurname(), prof1.getEmail(), prof1.getDepartment());
        Group grupaA = new Group("Clasa 9-A", diriginte);
        grupaA.addStudent(stud1);
        grupaA.addStudent(stud2);

        try {
            catalogService.assignDiriginteToGroupTransaction(grupaA);
        } catch (Exception e) {
            System.err.println("Tranzactia a esuat!");
        }

        audit.logAction("generare_rapoarte_join");

        System.out.println("\n--- RAPORT 1: TOP STUDENTI (JOIN) ---");
        List<String> topStudents = gradeRepo.getTopStudentsReport();
        topStudents.forEach(System.out::println);

        System.out.println("\n--- RAPORT 2: PERFORMÂNȚĂ MATERII (JOIN) ---");
        List<String> subjectPerf = gradeRepo.getSubjectPerformanceReport();
        subjectPerf.forEach(System.out::println);

        System.out.println("\n--- RAPORT 3: ABSENȚE (JOIN) ---");
        List<String> absencesReport = gradeRepo.getUnmotivatedAbsencesReport();
        absencesReport.forEach(System.out::println);
    }
}