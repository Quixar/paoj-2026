package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează

        List<Student> students = new ArrayList<>();
        
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",");

            String name = parts[0].trim();
            int age = Integer.parseInt(parts[1].trim());
            String city = parts[2].trim();
            String street = parts[3].trim();

            Adresa adresa = new Adresa(city, street);
            Student student = new Student(name, age, adresa);

            students.add(student);
        }

        reader.close();

        Scanner scanner = new Scanner(System.in);
        String commandLine = scanner.nextLine();

        String[] commandParts = commandLine.split(" ", 2);
        String command = commandParts[0];

        if (command.equals("PRINT")) {
            for (Student student : students) {
                System.out.println(student);
            }
        } else if (command.equals("SHALLOW")) {
            String searchedName = commandParts[1];

            Student original = null;

            for (Student student : students) {
                if (student.getNume().equals(searchedName)) {
                    original = student;
                    break;
                }
            }

            Student clone = original.shallowClone();

            clone.getAdresa().setOras("MODIFICAT");

            System.out.println("Original: " + original);
            System.out.println("Clona: " + clone);
        } else if (command.equals("DEEP")) {
            String searchedName = commandParts[1];

            Student original = null;

            for (Student student : students) {
                if (student.getNume().equals(searchedName)) {
                    original = student;
                    break;
                }
            }

            Student clone = original.deepClone();

            clone.getAdresa().setOras("MODIFICAT");

            System.out.println("Original: " + original);
            System.out.println("Clona: " + clone);
        }
    }
}
