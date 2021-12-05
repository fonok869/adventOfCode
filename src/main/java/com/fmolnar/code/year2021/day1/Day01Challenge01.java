package com.fmolnar.code.year2021.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day01Challenge01 {

    private List<Integer> lines = new ArrayList<>();
    int counter = 0;

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day01/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    int szam = Integer.valueOf(line);
                    if (lines.size() > 0 && lines.get(lines.size() - 1) < szam) {
                        counter++;
                    }
                    lines.add(szam);
                }
            }
        }

        System.out.println("Increases (1): " + counter);
    }

}
