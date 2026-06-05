package com.pao.laboratory05.audit;

import java.util.Scanner;

/**
 * Exercise 4 (Bonus) — Audit Log
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 4 (Bonus) — Audit Log"
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AngajatService service = AngajatService.getInstance();

        while (true) {
            System.out.println("\n===== Gestionare Angajați (cu Audit) =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("4. Afișează audit log");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            int optiune = Integer.parseInt(scanner.nextLine());

            switch (optiune) {
                case 1: {
                    System.out.print("Nume: ");
                    String nume = scanner.nextLine();

                    System.out.print("Departament (nume): ");
                    String numeDepartament = scanner.nextLine();

                    System.out.print("Departament (locatie): ");
                    String locatieDepartament = scanner.nextLine();

                    System.out.print("Salariu: ");
                    double salariu = Double.parseDouble(scanner.nextLine());

                    Departament departament = new Departament(numeDepartament, locatieDepartament);
                    Angajat angajat = new Angajat(nume, departament, salariu);
                    service.addAngajat(angajat);
                    break;
                }
                case 2: {
                    System.out.println("--- Angajați după salariu (descrescător) ---");
                    service.listBySalary();
                    break;
                }
                case 3: {
                    System.out.print("Departament: ");
                    String numeDept = scanner.nextLine();
                    System.out.println("--- Angajați din " + numeDept + " ---");
                    service.findByDepartament(numeDept);
                    break;
                }
                case 4:
                {
                    service.printAuditLog();
                    break;
                }
                case 0: {
                    System.out.println("La revedere!");
                    return;
                }
                default:
                {
                    System.out.println("Opțiune invalidă.");
                }
            }
        }
    }
}