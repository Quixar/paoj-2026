package com.pao.proiect.CatalogScolar.services;

import com.pao.proiect.CatalogScolar.exception.StudentNotFoundException;
import com.pao.proiect.CatalogScolar.exception.SubjectNotFoundException;
import com.pao.proiect.CatalogScolar.model.Absence;
import com.pao.proiect.CatalogScolar.model.CodInmatriculare;
import com.pao.proiect.CatalogScolar.model.Grade;
import com.pao.proiect.CatalogScolar.model.Student;
import com.pao.proiect.CatalogScolar.model.Subject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogService {
    private static CatalogService instance;

    private final StudentService studentService;
    private final SubjectService subjectService;

    private final List<Grade> grades;
    private final List<Absence> absences;
    private final Map<CodInmatriculare, List<Grade>> gradesByStudent;
    private final Map<CodInmatriculare, List<Absence>> absencesByStudent;

    private CatalogService() {
        studentService = StudentService.getInstance();
        subjectService = SubjectService.getInstance();
        grades = new ArrayList<>();
        absences = new ArrayList<>();
        gradesByStudent = new HashMap<>();
        absencesByStudent = new HashMap<>();
    }

    public static CatalogService getInstance() {
        if (instance == null) {
            instance = new CatalogService();
        }
        return instance;
    }

    public void addGrade(CodInmatriculare studentCode, String subjectCode, int value)
            throws StudentNotFoundException, SubjectNotFoundException {
        Student student = studentService.findByCode(studentCode);
        Subject subject = subjectService.findByCode(subjectCode);

        Grade grade = new Grade(student, subject, value, LocalDate.now());

        grades.add(grade);
        gradesByStudent
                .computeIfAbsent(studentCode, key -> new ArrayList<>())
                .add(grade);
    }

    public void addAbsence(CodInmatriculare studentCode, String subjectCode, boolean motivated)
            throws StudentNotFoundException, SubjectNotFoundException {
        Student student = studentService.findByCode(studentCode);
        Subject subject = subjectService.findByCode(subjectCode);

        Absence absence = new Absence(student, subject, LocalDate.now(), motivated);

        absences.add(absence);
        absencesByStudent
                .computeIfAbsent(studentCode, key -> new ArrayList<>())
                .add(absence);
    }

    public List<Grade> getGradesForStudent(CodInmatriculare studentCode) {
        return new ArrayList<>(gradesByStudent.getOrDefault(studentCode, new ArrayList<>()));
    }

    public List<Absence> getAbsencesForStudent(CodInmatriculare studentCode) {
        return new ArrayList<>(absencesByStudent.getOrDefault(studentCode, new ArrayList<>()));
    }

    public double calculateAverage(CodInmatriculare studentCode) {
        List<Grade> studentGrades = gradesByStudent.getOrDefault(studentCode, new ArrayList<>());

        if (studentGrades.isEmpty()) {
            return 0;
        }

        int sum = 0;
        for (Grade grade : studentGrades) {
            sum += grade.getValue();
        }

        return (double) sum / studentGrades.size();
    }

    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades);
    }

    public List<Absence> getAllAbsences() {
        return new ArrayList<>(absences);
    }

    public String getGradesForStudentAsString(CodInmatriculare studentCode) {
        List<Grade> studentGrades = gradesByStudent.getOrDefault(studentCode, new ArrayList<>());

        if (studentGrades.isEmpty()) {
            return "Studentul cu codul " + studentCode.getValoare() + " nu are note.";
        }

        StringBuilder result = new StringBuilder("Note pentru studentul cu codul ")
                .append(studentCode.getValoare())
                .append(":\n");

        for (Grade grade : studentGrades) {
            result.append(grade)
                    .append("\n\n");
        }

        return result.toString();
    }

    public String getAbsencesForStudentAsString(CodInmatriculare studentCode) {
        List<Absence> studentAbsences = absencesByStudent.getOrDefault(studentCode, new ArrayList<>());

        if (studentAbsences.isEmpty()) {
            return "Studentul cu codul " + studentCode.getValoare() + " nu are absențe.";
        }

        StringBuilder result = new StringBuilder("Absențe pentru studentul cu codul ")
                .append(studentCode.getValoare())
                .append(":\n");

        for (Absence absence : studentAbsences) {
            result.append(absence)
                    .append("\n\n");
        }

        return result.toString();
    }

    public String getAllGradesAsString() {
        if (grades.isEmpty()) {
            return "Nu există note în catalog.";
        }

        StringBuilder result = new StringBuilder("Lista tuturor notelor:\n");

        for (Grade grade : grades) {
            result.append(grade)
                    .append("\n\n");
        }

        return result.toString();
    }

    public String getAllAbsencesAsString() {
        if (absences.isEmpty()) {
            return "Nu există absențe în catalog.";
        }

        StringBuilder result = new StringBuilder("Lista tuturor absențelor:\n");

        for (Absence absence : absences) {
            result.append(absence)
                    .append("\n\n");
        }

        return result.toString();
    }
}