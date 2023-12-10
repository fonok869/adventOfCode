package com.fmolnar.code.year2023.day08;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day08v2 {

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

        List<String> actualPoints = dict.entrySet().stream().map(Map.Entry::getKey).filter(s -> s.endsWith("Z")).collect(Collectors.toList());
        int actualPointsNumber = actualPoints.size();
        int steps = 0;
        List<String> changedActuaPoints = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String irany = instructions.get(i % (instructions.size()));
            actualPoints.forEach(actualPoint -> {
                Parok parActual = dict.get(actualPoint);
                if ("L".equals(irany)) {
                    actualPoint = parActual.left;
                } else if ("R".equals(irany)) {
                    actualPoint = parActual.right;
                } else {
                    throw new RuntimeException("Nem itt");
                }
                changedActuaPoints.add(actualPoint);
            });

            int nowSize = changedActuaPoints.stream().filter(s -> s.endsWith("Z")).collect(Collectors.toList()).size();
            if (nowSize == actualPointsNumber) {
                steps = i + 1;
                break;
            }
            actualPoints.clear();
            actualPoints.addAll(changedActuaPoints);
            changedActuaPoints.clear();
            System.gc();
        }

        System.out.println("Steps: " + steps);
    }

    record Parok(String left, String right) {
    }

    ;
}
