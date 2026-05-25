package com.pao.laboratory13.exercise1;

import java.util.Scanner;

public class Main {

    enum State {
        INIT, AUTH, OPEN, CLOSED
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        State currentState = State.INIT;
        String currentUser = null;
        int historyCount = 0;

        if (!scanner.hasNextInt()) {
            return;
        }

        int q = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < q; i++) {
            if (!scanner.hasNextLine()) {
                break;
            }

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                i--;
                continue;
            }

            String[] tokens = line.split("\\s+");
            String cmd = tokens[0];
            String result = "";

            switch (cmd) {
                case "AUTH":
                    if (tokens.length < 2) {
                        result = "ERR E_PARSE AUTH";
                    } else if (currentState == State.CLOSED) {
                        result = "ERR E_STATE CLOSED";
                    } else {
                        currentUser = tokens[1];
                        currentState = State.AUTH;
                        historyCount = 0;
                        result = "OK AUTH user=" + currentUser;
                    }
                    break;

                case "OPEN":
                    if (tokens.length > 1) {
                        result = "ERR E_PARSE OPEN";
                    } else if (currentState == State.CLOSED) {
                        result = "ERR E_STATE CLOSED";
                    } else if (currentState == State.OPEN) {
                        result = "ERR E_STATE ALREADY_OPEN";
                    } else if (currentState == State.INIT) {
                        result = "ERR E_STATE NOT_OPEN";
                    } else {
                        currentState = State.OPEN;
                        result = "OK OPEN";
                    }
                    break;

                case "SEND":
                    if (tokens.length < 2) {
                        result = "ERR E_PARSE SEND";
                    } else if (currentState == State.CLOSED) {
                        result = "ERR E_STATE CLOSED";
                    } else if (currentState != State.OPEN) {
                        result = "ERR E_STATE NOT_OPEN";
                    } else {
                        historyCount++;
                        result = "OK OPEN sent";
                    }
                    break;

                case "BROADCAST":
                    if (tokens.length < 2) {
                        result = "ERR E_PARSE BROADCAST";
                    } else if (currentState == State.CLOSED) {
                        result = "ERR E_STATE CLOSED";
                    } else if (currentState != State.OPEN) {
                        result = "ERR E_STATE NOT_OPEN";
                    } else {
                        historyCount++;
                        result = "OK OPEN broadcast";
                    }
                    break;

                case "HISTORY":
                    if (tokens.length > 1) {
                        result = "ERR E_PARSE HISTORY";
                    } else if (currentState == State.CLOSED) {
                        result = "ERR E_STATE CLOSED";
                    } else if (currentState != State.OPEN) {
                        result = "ERR E_STATE NOT_OPEN";
                    } else {
                        result = "OK OPEN history=" + historyCount;
                    }
                    break;

                case "CLOSE":
                    if (tokens.length > 1) {
                        result = "ERR E_PARSE CLOSE";
                    } else if (currentState == State.CLOSED) {
                        result = "ERR E_STATE CLOSED";
                    } else if (currentState != State.OPEN) {
                        result = "ERR E_STATE NOT_OPEN";
                    } else {
                        currentState = State.CLOSED;
                        result = "OK CLOSED";
                    }
                    break;

                default:
                    result = "ERR E_PARSE UNKNOWN_COMMAND";
                    break;
            }

            System.out.println(result);
        }
        scanner.close();
    }
}
