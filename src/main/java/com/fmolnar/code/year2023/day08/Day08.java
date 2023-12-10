package com.fmolnar.code.year2023.day08;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day08 {

    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day08/input.txt");

        List<String> instructions = new ArrayList<>();
        Map<String, Parok> dict = new HashMap<>();
        boolean instruction = true;
        for (String line : lines) {

            if (instruction) {
                for (int i = 0; i < line.length(); i++) {
                    instructions.add(String.valueOf(line.charAt(i)));
                }
                instruction = false;
            } else if (!line.isEmpty()) {
                int equal = line.indexOf("=");
                String key = line.substring(0, equal).trim();
                String inst = line.substring(equal + 1).trim();
                int vesszo = inst.indexOf(",");
                int zarojelnyit = inst.indexOf("(");
                int zarojelzar = inst.indexOf(")");

                String left = inst.substring(zarojelnyit + 1, vesszo).trim();
                String right = inst.substring(vesszo + 1, zarojelzar).trim();

                dict.put(key, new Parok(left, right));
            }
        }

        List<String> actualPoints = dict.entrySet().stream().map(Map.Entry::getKey).filter(s -> s.endsWith("A")).collect(Collectors.toList());
        Map<Integer, String> values = new HashMap<>();
        Map<Integer, String> newValues = new HashMap<>();
        IntStream.range(0, actualPoints.size()).forEach(s -> values.put(s, actualPoints.get(s)));

        int steps = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            final String irany = instructions.get(i % (instructions.size()));
            for (Map.Entry<Integer, String> entry : values.entrySet()) {
                Parok parActual = dict.get(entry.getValue());
                String actualPoint = entry.getValue();
                if ("L".equals(irany)) {
                    actualPoint = parActual.left;
                } else if ("R".equals(irany)) {
                    actualPoint = parActual.right;
                } else {
                    throw new RuntimeException("Nem itt");
                }

                newValues.put(entry.getKey(), actualPoint);

                if (actualPoint.endsWith("Z")) {
                    steps = i + 1;
                    System.out.println("ActualPoint: " + entry.getKey() +  " " + actualPoint + " " + i);
                }
            };

            for (Map.Entry<Integer, String> entry : newValues.entrySet()) {
                values.put(entry.getKey(), entry.getValue());
            }


            System.gc();
        }


        System.out.println("Steps: " + (13019l * 20221l * 16897l * 19667l * 14681l * 18559l));
    }

    record Parok(String left, String right) {
    }


}
