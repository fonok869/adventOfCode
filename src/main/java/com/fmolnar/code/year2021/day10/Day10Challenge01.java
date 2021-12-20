package com.fmolnar.code.year2021.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Day10Challenge01 {

    private List<String> lines = new ArrayList<>();


    public void calculate() throws IOException {
        int sum = 0;
        InputStream reader = getClass().getResourceAsStream("/2021/day10/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    sum += calculateMissingSign(line);
                }
            }
        }
        System.out.println("Sum: " + sum);
    }

    private int calculateMissingSign(String line) {
        for (; ; ) {
            Map<Integer, String> mapPosition = new HashMap<>();

            if (line.contains(")")) {
                mapPosition.put(line.indexOf(")"), ")");
            }

            if (line.contains("]")) {
                mapPosition.put(line.indexOf("]"), "]");
            }
            if (line.contains("}")) {
                mapPosition.put(line.indexOf("}"), "}");
            }
            if (line.contains(">")) {
                mapPosition.put(line.indexOf(">"), ">");
            }

            OptionalInt indexToStartOp = mapPosition.keySet().stream().mapToInt(s -> s).min();
            if(indexToStartOp.isPresent()) {
                int indexToStart = indexToStartOp.getAsInt();
                String toBe = line.substring(indexToStart - 1, indexToStart);
                String elle = mapPosition.get(indexToStart);
                if (ifPresent(mapPosition, toBe)) {
                    if (indexToStart - 1 < 0 && indexToStart + 1 > line.length()) {
                        return 0;
                    }
                    line = line.substring(0, indexToStart - 1) + line.substring(indexToStart + 1);
                } else {
                    switch (elle) {
                        case ">":
                            return 25137;
                        case "}":
                            return 1197;
                        case "]":
                            return 57;
                        case ")":
                            return 3;
                    }
                }
            }
            else {
                return 0;
            }
        }
    }

    private boolean ifPresent(Map<Integer, String> mapPosition, String toBe) {
        if ("<".equals(toBe) && mapPosition.entrySet().stream().map(s -> s.getValue()).collect(Collectors.toList()).contains(">")) {
            return true;
        } else if ("{".equals(toBe) && mapPosition.entrySet().stream().map(s -> s.getValue()).collect(Collectors.toList()).contains("}")) {
            return true;
        } else if ("(".equals(toBe) && mapPosition.entrySet().stream().map(s -> s.getValue()).collect(Collectors.toList()).contains(")")) {
            return true;
        } else if ("[".equals(toBe) && mapPosition.entrySet().stream().map(s -> s.getValue()).collect(Collectors.toList()).contains("]")) {
            return true;
        }
        return false;
    }
}
