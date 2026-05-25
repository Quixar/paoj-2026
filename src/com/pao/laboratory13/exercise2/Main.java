package com.pao.laboratory13.exercise2;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int PORT = 9000;
    private static final int CLIENT_COUNT = 2;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(CLIENT_COUNT);
        ExecutorService executor = Executors.newCachedThreadPool();

        Thread serverThread = new Thread(() -> runServer(latch));
        serverThread.start();

        Thread.sleep(500);

        executor.execute(() -> runClient(1, new String[]{"AUTH alice", "OPEN", "SEND hello", "CLOSE"}));
        executor.execute(() -> runClient(2, new String[]{"AUTH bob", "OPEN", "BROADCAST hi", "HISTORY", "CLOSE"}));

        latch.await();
        executor.shutdown();
        System.out.println("[SERVER] All clients done. Shutting down.");
        System.exit(0);
    }

    private static void runServer(CountDownLatch latch) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[SERVER] Listening on port " + PORT);
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, latch)).start();
            }
        } catch (IOException e) {
            System.err.println("[SERVER] error: " + e.getMessage());
        }
    }

    private static void runClient(int id, String[] commands) {
        try (Socket socket = new Socket("localhost", PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("[CLIENT-" + id + "] Connected");
            for (String cmd : commands) {
                out.println(cmd);
                String response = in.readLine();
                System.out.println("[CLIENT-" + id + "] >> " + cmd + "  =>  " + response);
                Thread.sleep(200);
            }
            System.out.println("[CLIENT-" + id + "] Disconnected");

        } catch (IOException | InterruptedException e) {
            System.err.println("[CLIENT-" + id + "] error: " + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private final CountDownLatch latch;
        private State currentState = State.INIT;
        private int historyCount = 0;

        enum State { INIT, AUTH, OPEN, CLOSED }

        public ClientHandler(Socket socket, CountDownLatch latch) {
            this.socket = socket;
            this.latch = latch;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String line;
                while ((line = in.readLine()) != null) {
                    String response = processCommand(line.trim());
                    out.println(response);
                    if (currentState == State.CLOSED) break;
                }
            } catch (IOException e) {
                System.err.println("[HANDLER] Connection error: " + e.getMessage());
            } finally {
                try { socket.close(); } catch (IOException ignored) {}
                latch.countDown();
            }
        }

        private String processCommand(String line) {
            if (line.isEmpty()) return "";
            String[] tokens = line.split("\\s+");
            String cmd = tokens[0];

            switch (cmd) {
                case "AUTH":
                    if (tokens.length < 2) return "ERR E_PARSE AUTH";
                    if (currentState == State.CLOSED) return "ERR E_STATE CLOSED";
                    currentState = State.AUTH;
                    historyCount = 0;
                    return "OK AUTH user=" + tokens[1];

                case "OPEN":
                    if (tokens.length > 1) return "ERR E_PARSE OPEN";
                    if (currentState == State.CLOSED) return "ERR E_STATE CLOSED";
                    if (currentState == State.OPEN) return "ERR E_STATE ALREADY_OPEN";
                    if (currentState == State.INIT) return "ERR E_STATE NOT_OPEN";
                    currentState = State.OPEN;
                    return "OK OPEN";

                case "SEND":
                    if (tokens.length < 2) return "ERR E_PARSE SEND";
                    if (currentState != State.OPEN) return "ERR E_STATE NOT_OPEN";
                    historyCount++;
                    return "OK OPEN sent";

                case "BROADCAST":
                    if (tokens.length < 2) return "ERR E_PARSE BROADCAST";
                    if (currentState != State.OPEN) return "ERR E_STATE NOT_OPEN";
                    historyCount++;
                    return "OK OPEN broadcast";

                case "HISTORY":
                    if (currentState != State.OPEN) return "ERR E_STATE NOT_OPEN";
                    return "OK OPEN history=" + historyCount;

                case "CLOSE":
                    currentState = State.CLOSED;
                    return "OK CLOSED";

                default:
                    return "ERR E_PARSE UNKNOWN_COMMAND";
            }
        }
    }
}
