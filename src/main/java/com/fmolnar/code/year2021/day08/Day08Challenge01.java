package com.fmolnar.code.year2021.day08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day08Challenge01 {

    public void calculate() throws IOException {
        int counter = 0;
        InputStream reader = getClass().getResourceAsStream("/2021/day08/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    int lineIndex = line.indexOf("|");
                    String[] args = line.substring(lineIndex + 1).split(" ");
                    for (int i = 0; i < args.length; i++) {
                        if (args[i].length() == 2 || args[i].length() == 3 || args[i].length() == 4 || args[i].length() == 7) {
                            counter++;
                        }
                    }
                }
            }
        }

        System.out.println("Sum: " + counter);
    }
}
