package com.fmolnar.code.year2019.day06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day06Challenge01 {

    private List<String> lines = new ArrayList<>();
    Map<String, Integer> orbitsAndResult = new HashMap<>();
    Map<String, Set<String>> orbits = new HashMap<>();
    Set<String> firsts = new HashSet<>();
    Set<String> seconds = new HashSet<>();
    Set<String> alls = new HashSet<>();


    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2019/day06/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    String[] strings = line.split("\\)");
                    String first = strings[0];
                    String second = strings[1];
                    if (orbits.containsKey(first)) {
                        Set<String> orbitsNew = orbits.get(first);
                        orbitsNew.add(second);
                        orbits.put(first, orbitsNew);
                    } else {
                        Set<String> orbitsNew = new HashSet<>();
                        orbitsNew.add(second);
                        orbits.put(first, orbitsNew);
                    }
                    firsts.add(first);
                    seconds.add(second);
                    alls.add(first);
                    alls.add(second);
                }
            }
        }
        Set<String> allsCopy = new HashSet<>(alls);
        allsCopy.removeAll(seconds);

        String startValue = allsCopy.iterator().next();
        orbitsAndResult.put(startValue, 0);
        calculateRecursive(startValue, orbits.get(startValue));


        int counter = 0;
        System.out.println("Sum: " + orbitsAndResult.entrySet().stream().mapToInt(s -> s.getValue()).sum());
    }

    private void calculateRecursive(String origin, Set<String> nextSteps) {
        for (String start : nextSteps) {
            int newValue = orbitsAndResult.get(origin) + 1;
            orbitsAndResult.put(start, newValue);
            Set<String> nexts = orbits.get(start);
            if(nexts != null){
                calculateRecursive(start, nexts);
            }
        }
    }
}
