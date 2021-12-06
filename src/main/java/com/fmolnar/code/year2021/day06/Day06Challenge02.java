package com.fmolnar.code.year2021.day06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06Challenge02 {

    public static final int INT_6 = 6;
    private List<String> lines = new ArrayList<>();
    List<Integer> fishes = new ArrayList<>();
    Map<Integer, Long> fishesMap = new HashMap<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day06/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    String[] args = line.split(",");
                    for (int i = 0; i < args.length; i++) {
                        Integer integer = Integer.valueOf(args[i]);
                        fishes.add(Integer.valueOf(args[i]));
                        if (fishesMap.get(integer) == null) {
                            fishesMap.put(integer, 1L);
                        } else {
                            Long occurence = fishesMap.get(integer);
                            fishesMap.put(integer, ++occurence);
                        }
                    }
                }
            }
        }

        Map<Integer, Long> newPopMap = fishesMap;
        for (int i = 1; i <= 256; i++) {
            newPopMap = reproductionMap(newPopMap);
        }

        System.out.println("Sum: " + newPopMap.entrySet().stream().mapToLong(s -> s.getValue()).sum());
    }

    private Map<Integer, Long> reproductionMap(Map<Integer, Long> newPopMap) {
        Map<Integer, Long> newPopMapToSave = new HashMap<>();
        newPopMap.entrySet().stream().forEach(fishMap -> {
            if (fishMap.getKey() == 0) {
                fillOutNextLine(newPopMapToSave, fishMap.getValue(), INT_6);
                newPopMapToSave.put(8, fishMap.getValue());
            } else {
                fillOutNextLine(newPopMapToSave, fishMap.getValue(), fishMap.getKey() - 1);
            }
        });
        return newPopMapToSave;
    }

    private void fillOutNextLine(Map<Integer, Long> newPopMapToSave, Long count, int i) {
        if (newPopMapToSave.containsKey(i)) {
            Long already = newPopMapToSave.get(i);
            newPopMapToSave.put(i, already + count);
        } else {
            newPopMapToSave.put(i, count);
        }
    }

}
