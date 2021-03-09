package com.fmolnar.code.day25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day25Challenge01 {

    public Day25Challenge01() {
    }

    Long cardPublicKey = null;
    Long doorPublicKey = null;
    Long constant = 20201227L;
    private List<String> lines = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day25/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    lines.add(line);
                }
            }
        }
        cardPublicKey = Long.valueOf(lines.get(0));
        doorPublicKey = Long.valueOf(lines.get(1));

        long loopSizeCard = caulculateLoopSize(cardPublicKey);
        long loopSizeDoor = caulculateLoopSize(doorPublicKey);
        long enciptionKey1 = caulculateEncryptionKey(doorPublicKey, loopSizeCard);
        long enciptionKey2 = caulculateEncryptionKey(cardPublicKey, loopSizeDoor);
        if (enciptionKey1 == enciptionKey2) {
            System.out.println("EncryptionKey: " + enciptionKey1);
        }

        System.out.println("Sum: " + lines);
    }

    private long caulculateEncryptionKey(long subjectNumber, long loopSize) {
        long init = subjectNumber;
        long osszeg = 1L;
        for (int max = 0; max < loopSize; max++) {
            osszeg = ((osszeg * init) % constant);
        }
        return osszeg;
    }

    private long caulculateLoopSize(Long cardPublicKey) {
        Long init = 7L;
        Long osszeg = 1L;
        for (int max = 1; ; max++) {
            osszeg = ((osszeg * init) % constant);
            if (osszeg.equals(cardPublicKey)) {
                return max;
            }
        }
    }
}
