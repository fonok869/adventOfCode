package com.fmolnar.code.year2021.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Day10Challenge02 {

    private List<String> lines = new ArrayList<>();
    private List<Long> integs = new ArrayList<>();


    public void calculate() throws IOException {
        int sum = 0;
        InputStream reader = getClass().getResourceAsStream("/2021/day10/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    lines.add(line);
                    int value = calculateMissingCucc(line);
                    System.out.println(value);
                    sum += value;
                }
            }
        }

        Collections.sort(integs);
        int number = integs.size()/2;
        System.out.println("Sum: " + integs.get(number));
    }

    private int calculateMissingCucc(String line) {
        for (; ; ) {
            Map<Integer, String> mapPosition = new HashMap<>();

            String toLookFor = "";
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
                if (ifPresent(mapPosition, toBe, elle)) {
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
                // Incomplete
                String reversed = new StringBuilder(line).reverse().toString();
                reversed = swap(reversed);
                integs.add(calculateReversedSum(reversed));
                return 0;
            }
        }
    }

    private String swap(String reversed) {
        String newString = "";
        for(int i=0; i<reversed.length(); i++){
            switch (reversed.charAt(i)){
                case '(' : newString = newString +  ")"; break;
                case '{' : newString = newString +  "}"; break;
                case '[' : newString = newString +  "]"; break;
                case '<' : newString = newString +  ">"; break;
            }
        }
        return newString;
    }

    private long calculateReversedSum(String reversed) {
        long newSum = 0L;
        for(int i=0; i<reversed.length(); i++){
            long number = 0L;
            switch (reversed.charAt(i)){
                case ')' : number = 1L; break;
                case ']' : number = 2L; break;
                case '}' : number = 3L; break;
                case '>' : number = 4L; break;
            }
            if(number == 0){
                throw new RuntimeException("Baj van");
            }
            newSum = 5* newSum + number;
        }
        return newSum;

    }

    private boolean ifPresent(Map<Integer, String> mapPosition, String toBe, String elle) {
        if (">".equals(elle) && "<".equals(toBe) && mapPosition.entrySet().stream().map(s -> s.getValue()).collect(Collectors.toList()).contains(">")) {
            return true;
        } else if ("}".equals(elle) &&"{".equals(toBe) && mapPosition.entrySet().stream().map(s -> s.getValue()).collect(Collectors.toList()).contains("}")) {
            return true;
        } else if (")".equals(elle) &&"(".equals(toBe) && mapPosition.entrySet().stream().map(s -> s.getValue()).collect(Collectors.toList()).contains(")")) {
            return true;
        } else if ("]".equals(elle) && "[".equals(toBe) && mapPosition.entrySet().stream().map(s -> s.getValue()).collect(Collectors.toList()).contains("]")) {
            return true;
        }
        return false;
    }
}
