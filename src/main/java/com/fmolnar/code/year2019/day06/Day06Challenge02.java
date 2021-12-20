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

public class Day06Challenge02 {

    Map<String, Integer> orbitsAndResult = new HashMap<>();
    Map<String, Set<String>> orbits = new HashMap<>();
    Map<String, String> orbitInverse = new HashMap<>();
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
                    orbitInverse.put(second, first);
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
        List<String> youList = new ArrayList<>();
        Map<String, Integer> youMap = parcourir("YOU", youList);
        List<String> sanList = new ArrayList<>();
        Map<String, Integer> sanMap = parcourir("SAN", sanList);
        orbitsAndResult.put(startValue, 0);

        for(String youValue : youList){
            if(sanList.contains(youValue)){
                int youValueReal = youMap.get(youValue)-1;
                int sanValueReal = sanMap.get(youValue)-1;
                System.out.println("Result: " + (youValueReal + sanValueReal));
                break;
            }
        }
        calculateRecursive(startValue, orbits.get(startValue));

        System.out.println("Sum: " + orbitsAndResult.entrySet().stream().mapToInt(s -> s.getValue()).sum());
    }

    private Map<String, Integer> parcourir(String you, List<String> youList) {
        Map<String, Integer> parcourt = new HashMap<>();
        youList.add(you);
        for(int i=0;;i++){
            parcourt.put(you, i);
            you = orbitInverse.get(you);
            youList.add(you);
            if("COM".equals(you)){
                break;
            }
        }
        return parcourt;
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
