package com.pao.proiect.CatalogScolar;

import com.pao.proiect.CatalogScolar.exception.StudentNotFoundException;
import com.pao.proiect.CatalogScolar.exception.SubjectNotFoundException;
import com.pao.proiect.CatalogScolar.model.CodInmatriculare;
import com.pao.proiect.CatalogScolar.model.Diriginte;
import com.pao.proiect.CatalogScolar.model.Group;
import com.pao.proiect.CatalogScolar.model.Profesor;
import com.pao.proiect.CatalogScolar.model.Student;
import com.pao.proiect.CatalogScolar.model.Subject;
import com.pao.proiect.CatalogScolar.services.CatalogService;
import com.pao.proiect.CatalogScolar.services.StudentService;
import com.pao.proiect.CatalogScolar.services.SubjectService;

public class Main {
    public static void main(String[] args) {
        StudentService studentService = StudentService.getInstance();
        SubjectService subjectService = SubjectService.getInstance();
        CatalogService catalogService = CatalogService.getInstance();

        try {
            CodInmatriculare codAna = new CodInmatriculare("ST001");
            CodInmatriculare codMihai = new CodInmatriculare("ST002");
            CodInmatriculare codElena = new CodInmatriculare("ST003");

            Student ana = new Student("Ana", "Popescu", "ana.popescu@email.com", codAna, 1);
            Student mihai = new Student("Mihai", "Ionescu", "mihai.ionescu@email.com", codMihai, 1);
            Student elena = new Student("Elena", "Dumitrescu", "elena.dumitrescu@email.com", codElena, 2);

            Profesor profesorMate = new Profesor("Ion", "Georgescu", "ion.georgescu@email.com", "Matematică");
            Profesor profesorInfo = new Profesor("Maria", "Stan", "maria.stan@email.com", "Informatică");

            Diriginte diriginte = new Diriginte("Andrei", "Marinescu", "andrei.marinescu@email.com", "Informatică");
            Group group = new Group("10A", diriginte);
            diriginte.setGroup(group);

            Subject matematica = new Subject("MATH", "Matematică", profesorMate);
            Subject informatica = new Subject("INFO", "Informatică", profesorInfo);

            System.out.println("1. Adăugare studenți");
            studentService.addStudent(ana);
            studentService.addStudent(mihai);
            studentService.addStudent(elena);

            System.out.println("2. Listare studenți");
            System.out.println(studentService.getAllStudentsAsString());

            System.out.println("3. Căutare student după cod");
            System.out.println(studentService.findByCode(codAna));
            System.out.println();

            System.out.println("4. Listare studenți sortați alfabetic");
            System.out.println(studentService.getSortedStudentsAsString());

            System.out.println("5. Adăugare materii");
            subjectService.addSubject(matematica);
            subjectService.addSubject(informatica);

            System.out.println("6. Listare materii");
            System.out.println(subjectService.getAllSubjectsAsString());

            System.out.println("7. Căutare materie după cod");
            System.out.println(subjectService.findByCode("INFO"));
            System.out.println();

            System.out.println("8. Adăugare note");
            catalogService.addGrade(codAna, "MATH", 10);
            catalogService.addGrade(codAna, "INFO", 9);
            catalogService.addGrade(codMihai, "MATH", 8);
            catalogService.addGrade(codElena, "INFO", 10);

            System.out.println("9. Afișare note pentru Ana");
            System.out.println(catalogService.getGradesForStudentAsString(codAna));

            System.out.println("10. Calculare medie pentru Ana");
            System.out.println(catalogService.calculateAverage(codAna));
            System.out.println();

            System.out.println("11. Adăugare absență");
            catalogService.addAbsence(codAna, "INFO", false);

            System.out.println("12. Afișare absențe pentru Ana");
            System.out.println(catalogService.getAbsencesForStudentAsString(codAna));

            System.out.println("13. Adăugare studenți în grupă");
            group.addStudent(ana);
            group.addStudent(mihai);
            System.out.println(group);

            System.out.println("14. Ștergere student");
            studentService.deleteStudent(codElena);
            System.out.println(studentService.getAllStudentsAsString());

            System.out.println("15. Tratare excepție custom");
            studentService.findByCode(new CodInmatriculare("ST999"));

        } catch (StudentNotFoundException | SubjectNotFoundException exception) {
            System.out.println("Eroare tratată: " + exception.getMessage());
        }
    }
}