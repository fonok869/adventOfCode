package com.fmolnar.code.year2020.day25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day25Challenge02 {

    public Day25Challenge02() {
    }

    private List<String> lines = new ArrayList<>();
    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day18/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    lines.add(line);
                }
            }
        }

        System.out.println("Sum: " + lines);
    }
}
