package com.pao.laboratory07.exercise1;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<StareComanda> istoric = new Stack<>();

        if (!scanner.hasNext()) {
            return;
        }

        String stareaInitialaStr = scanner.next();
        StareComanda stareCurenta;

        try {
            stareCurenta = StareComanda.valueOf(stareaInitialaStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Stare inițială invalidă.");
            return;
        }

        System.out.println(stareCurenta);

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            if (comanda.equals("QUIT")) {
                break;
            }

            switch (comanda) {
                case "next":
                    if (stareCurenta.esteFinala()) {
                        System.out.println("Comanda este in stare finala.");
                    } else {
                        istoric.push(stareCurenta);

                        if (stareCurenta == StareComanda.PLASATA) {
                            stareCurenta = StareComanda.PROCESATA;
                        } else if (stareCurenta == StareComanda.PROCESATA) {
                            stareCurenta = StareComanda.EXPEDIATA;
                        } else if (stareCurenta == StareComanda.EXPEDIATA) {
                            stareCurenta = StareComanda.LIVRATA;
                        }

                        System.out.println(stareCurenta);

                        // Если только что перешли в финальное состояние
                        if (stareCurenta.esteFinala()) {
                            System.out.println("Comanda este in stare finala.");
                        }
                    }
                    break;

                case "cancel":
                    if (stareCurenta.esteFinala()) {
                        System.out.println("Comanda este in stare finala.");
                    } else {
                        istoric.push(stareCurenta);
                        stareCurenta = StareComanda.ANULATA;

                        System.out.println(stareCurenta);
                        System.out.println("Comanda este in stare finala.");
                    }
                    break;

                case "undo":
                    if (!istoric.isEmpty()) {
                        stareCurenta = istoric.pop();
                        System.out.println(stareCurenta);
                    } else {
                        System.out.println(stareCurenta);
                    }
                    break;

                default:
                    System.out.println(stareCurenta);
                    break;
            }
        }

        scanner.close();
    }
}