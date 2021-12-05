package com.fmolnar.code.year2021.day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day02Challenge01 {

    private List<String> lines = new ArrayList<>();
    int horizontal = 0;
    int depth = 0;

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day02/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    calculateLine(line);
                }
            }
        }

        System.out.println("Szorzat (1): " + Math.abs(depth)*horizontal);
    }

    private void calculateLine(String line) {
        int step = 0;
        if(line.startsWith("forward")){
            step = Integer.valueOf(line.substring("forward".length()+1));
            horizontal = horizontal+step;
        } else if(line.startsWith("down")){
            step = Integer.valueOf(line.substring("down".length()+1));
            depth = depth - step;
        } else if(line.startsWith("up")){
            step = Integer.valueOf(line.substring("up".length()+1));
            depth = depth + step;
        }
    }
}
