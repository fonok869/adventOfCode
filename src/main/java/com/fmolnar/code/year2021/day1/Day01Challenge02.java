package com.fmolnar.code.year2021.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day01Challenge02 {

    private List<Integer> lines = new ArrayList<>();
    private List<Integer> sums = new ArrayList<>();
    int counter = 0;

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day01/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    int szam = Integer.valueOf(line);
                    lines.add(szam);
                    if (lines.size() > 2) {
                        int sizeMax = lines.size();
                        Integer szam1 = lines.get(sizeMax - 1);
                        Integer szam2 = lines.get(sizeMax - 2);
                        Integer szam3 = lines.get(sizeMax - 3);
                        sums.add(szam1 + szam2 + szam3);
                    }
                }
            }
        }
        calculateIncreases();
    }

    private void calculateIncreases() {
        int count = 0;
        for (int i = 1; i < sums.size(); i++) {
            if (sums.get(i) > sums.get(i - 1)) {
                count++;
            }
        }
        System.out.println("Increases (3): " + count);
    }
}
