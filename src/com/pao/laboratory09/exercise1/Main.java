package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "output/lab09_ex1.ser";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        File directory = new File("output");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        List<Tranzactie> listaInitiala = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            String sursa = scanner.next();
            String dest = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            Tranzactie t = new Tranzactie(id, suma, data, sursa, dest, tip);
            t.setNote("procesat");
            listaInitiala.add(t);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(listaInitiala);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Tranzactie> tranzactiiRecuperate = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            tranzactiiRecuperate = (List<Tranzactie>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNext()) {
            String comanda = scanner.next();
            switch (comanda) {
                case "LIST":
                    tranzactiiRecuperate.forEach(System.out::println);
                    break;

                case "FILTER":
                    String prefix = scanner.next();
                    boolean found = false;
                    for (Tranzactie t : tranzactiiRecuperate) {
                        if (t.getData().startsWith(prefix)) {
                            System.out.println(t);
                            found = true;
                        }
                    }
                    if (!found) System.out.println("Niciun rezultat.");
                    break;

                case "NOTE":
                    int searchId = scanner.nextInt();
                    Tranzactie gasit = null;
                    for (Tranzactie t : tranzactiiRecuperate) {
                        if (t.getId() == searchId) {
                            gasit = t;
                            break;
                        }
                    }
                    if (gasit != null) {
                        System.out.println("NOTE[" + searchId + "]: " + gasit.getNote());
                    } else {
                        System.out.println("NOTE[" + searchId + "]: not found");
                    }
                    break;
            }
        }
        scanner.close();
    }
}