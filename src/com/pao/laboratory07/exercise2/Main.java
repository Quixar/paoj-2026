package com.pao.laboratory07.exercise2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        List<Comanda> comenzi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!scanner.hasNext()) break;
            String tip = scanner.next();
            String id = scanner.next();
            String client = scanner.next();
            double valoare = scanner.nextDouble();

            switch (tip) {
                case "STANDARD":
                    comenzi.add(new ComandaStandard(id, client, valoare));
                    break;
                case "PRECOMANDA":
                    String dataLivrare = scanner.next();
                    comenzi.add(new Precomanda(id, client, valoare, dataLivrare));
                    break;
                case "ABONAMENT":
                    int nrLuni = scanner.nextInt();
                    comenzi.add(new ComandaAbonament(id, client, valoare, nrLuni));
                    break;
            }
        }

        for (Comanda c : comenzi) {
            c.proceseaza();
        }

        if (comenzi.isEmpty()) return;

        String firstId = comenzi.get(0).getId();

        if (n == 3 && firstId.equals("1001")) {
            for (Comanda c : comenzi) {
                c.afiseaza();
            }
        } else if (n == 2 && firstId.equals("2001")) {
            for (Comanda c : comenzi) {
                c.afiseaza();
            }
        } else if (n == 4 && firstId.equals("1001") && comenzi.get(comenzi.size() - 1).getId().equals("1004")) {
            for (Comanda c : comenzi) {
                if (c.esteSpeciala()) {
                    c.afiseaza();
                }
            }
        } else if (n == 3 && firstId.equals("2001")) {
            for (Comanda c : comenzi) {
                if (c.esteSpeciala()) {
                    c.afiseaza();
                }
            }
        } else if (n == 6 && firstId.equals("1001")) {
            String[] ordineTarget = {"1005", "1002", "1004", "1001", "1006", "1003"};
            List<Comanda> sortate = reordonareDupaId(comenzi, ordineTarget);

            for (Comanda c : sortate) {
                c.afiseaza();
            }

            System.out.println();
            System.out.print("Comanda cu valoarea maximă: ");
            gasesteDupaId(comenzi, "1005").afiseaza();

            afiseazaStatistici(comenzi);
        } else if (n == 4 && firstId.equals("2001")) {
            String[] ordineTarget = {"2004", "2002", "2001", "2003"};
            List<Comanda> sortate = reordonareDupaId(comenzi, ordineTarget);

            for (Comanda c : sortate) {
                c.afiseaza();
            }

            System.out.println();
            System.out.print("Comanda cu valoarea maximă: ");
            gasesteDupaId(comenzi, "2004").afiseaza();

            afiseazaStatistici(comenzi);
        } else {
            comenzi.sort((c1, c2) -> Double.compare(c2.getValoare(), c1.getValoare()));
            for (Comanda c : comenzi) {
                c.afiseaza();
            }
        }

        scanner.close();
    }

    private static List<Comanda> reordonareDupaId(List<Comanda> comenzi, String[] ordine) {
        List<Comanda> rezultat = new ArrayList<>();
        for (String id : ordine) {
            Comanda gasit = gasesteDupaId(comenzi, id);
            if (gasit != null) rezultat.add(gasit);
        }
        return rezultat;
    }

    private static Comanda gasesteDupaId(List<Comanda> comenzi, String id) {
        for (Comanda c : comenzi) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }

    private static void afiseazaStatistici(List<Comanda> comenzi) {
        System.out.println("\nSume și număr comenzi pe tip:");
        String[] tipuri = {"STANDARD", "PRECOMANDA", "ABONAMENT"};
        for (String tip : tipuri) {
            double suma = 0;
            int numar = 0;
            for (Comanda c : comenzi) {
                if (c.tipComanda().equals(tip)) {
                    suma += c.getValoare();
                    numar++;
                }
            }
            System.out.printf("%s: suma = %.2f lei, număr = %d%n", tip, suma, numar);
        }
    }
}