package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        int n = scanner.nextInt();
        List<Tranzactie> tranzactii = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            String contSursa = scanner.next();
            String contDestinatie = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            Tranzactie tranzactie = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            tranzactie.setNote("procesat");

            tranzactii.add(tranzactie);
        }

        File outputDirectory = new File("output");
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            out.writeObject(tranzactii);
        }

        List<Tranzactie> tranzactiiDeserializate;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            tranzactiiDeserializate = (List<Tranzactie>) in.readObject();
        }

        while (scanner.hasNext()) {
            String command = scanner.next();

            if (command.equals("LIST")) {
                for (Tranzactie tranzactie : tranzactiiDeserializate) {
                    System.out.println(tranzactie);
                }
            } else if (command.equals("FILTER")) {
                String prefix = scanner.next();
                boolean found = false;

                for (Tranzactie tranzactie : tranzactiiDeserializate) {
                    if (tranzactie.getData().startsWith(prefix)) {
                        System.out.println(tranzactie);
                        found = true;
                    }
                }

                if (!found) {
                    System.out.println("Niciun rezultat.");
                }
            } else if (command.equals("NOTE")) {
                int id = scanner.nextInt();
                Tranzactie foundTransaction = null;

                for (Tranzactie tranzactie : tranzactiiDeserializate) {
                    if (tranzactie.getId() == id) {
                        foundTransaction = tranzactie;
                        break;
                    }
                }

                if (foundTransaction == null) {
                    System.out.println("NOTE[" + id + "]: not found");
                } else {
                    System.out.println("NOTE[" + id + "]: " + foundTransaction.getNote());
                }
            }
        }
    }
}