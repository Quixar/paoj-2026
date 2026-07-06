package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();

        File file = new File(OUTPUT_FILE);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(file);
             DataOutputStream dos = new DataOutputStream(fos)) {

            for (int i = 0; i < n; i++) {
                int id = scanner.nextInt();
                double suma = scanner.nextDouble();
                String data = scanner.next();
                String tipStr = scanner.next();

                byte tipByte = (byte) (TipTranzactie.valueOf(tipStr) == TipTranzactie.CREDIT ? 0 : 1);
                byte statusByte = 0;

                ByteBuffer buffer = ByteBuffer.allocate(RECORD_SIZE);
                buffer.order(ByteOrder.LITTLE_ENDIAN);

                buffer.putInt(id);
                buffer.putDouble(suma);

                byte[] dataBytes = data.getBytes(StandardCharsets.US_ASCII);
                byte[] dataPadded = new byte[10];
                System.arraycopy(dataBytes, 0, dataPadded, 0, Math.min(dataBytes.length, 10));
                for (int j = dataBytes.length; j < 10; j++) {
                    dataPadded[j] = (byte) ' ';
                }
                buffer.put(dataPadded);

                buffer.put(tipByte);
                buffer.put(statusByte);
                buffer.put(new byte[8]);

                dos.write(buffer.array());
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNext()) {
                String comanda = scanner.next();

                if (comanda.equals("QUIT")) {
                    break;
                }

                switch (comanda) {
                    case "READ": {
                        int idx = scanner.nextInt();
                        readAndPrintRecord(raf, idx);
                        break;
                    }
                    case "UPDATE": {
                        int idx = scanner.nextInt();
                        String statusStr = scanner.next();
                        byte statusByte = getStatusByte(statusStr);

                        raf.seek((long) idx * RECORD_SIZE + 23);
                        raf.write(statusByte);

                        System.out.println("Updated [" + idx + "]: " + statusStr);
                        break;
                    }
                    case "PRINT_ALL": {
                        long totalRecords = raf.length() / RECORD_SIZE;
                        for (int i = 0; i < totalRecords; i++) {
                            readAndPrintRecord(raf, i);
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        }

        scanner.close();
    }

    private static void readAndPrintRecord(RandomAccessFile raf, int idx) throws IOException {
        raf.seek((long) idx * RECORD_SIZE);
        byte[] bytes = new byte[RECORD_SIZE];
        raf.readFully(bytes);

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        int id = buffer.getInt();
        double suma = buffer.getDouble();

        byte[] dataBytes = new byte[10];
        buffer.get(dataBytes);
        String data = new String(dataBytes, StandardCharsets.US_ASCII).trim();

        byte tipByte = buffer.get();
        byte statusByte = buffer.get();

        String tipStr = (tipByte == 0) ? "CREDIT" : "DEBIT";
        String statusStr = getStatusString(statusByte);

        System.out.printf("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                idx, id, data, tipStr, suma, statusStr);
    }

    private static byte getStatusByte(String statusStr) {
        return switch (statusStr) {
            case "PENDING" -> 0;
            case "PROCESSED" -> 1;
            case "REJECTED" -> 2;
            default -> 0;
        };
    }

    private static String getStatusString(byte statusByte) {
        return switch (statusByte) {
            case 0 -> "PENDING";
            case 1 -> "PROCESSED";
            case 2 -> "REJECTED";
            default -> "PENDING";
        };
    }
}