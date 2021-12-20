package com.fmolnar.code.year2021.day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day12Challenge01 {

    private List<String> lines = new ArrayList<>();
    Map<String, Integer> orbitsAndResult = new HashMap<>();
    Map<String, Set<String>> steps = new HashMap<>();
    Map<String, Integer> stepsNumber = new HashMap<>();
    List<Integer> counter = new ArrayList<>();
    Set<String> firsts = new HashSet<>();
    Set<String> seconds = new HashSet<>();
    Set<String> alls = new HashSet<>();

    Set<String> egyesek1 = new HashSet<>();


    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day12/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    String[] strings = line.split("-");
                    String first = strings[0];
                    String second = strings[1];
                    calculateStepNumber(first);
                    calculateStepNumber(second);
                    // first
                    saveDirection(second, first);
                    // second
                    saveDirection(first, second);
                    firsts.add(first);
                    seconds.add(second);
                    alls.add(first);
                    alls.add(second);
                }
            }
        }
        Set<String> allsCopy = new HashSet<>(alls);
        allsCopy.removeAll(seconds);

        String startValue = "start";
        orbitsAndResult.put("start", 0);
        List<String> palyak = new ArrayList<>();
        palyak.add("start");
        String[] ujPalya = new String[1];
        Set<String> egyesek = stepsNumber.entrySet().stream().filter(s->s.getValue() == 1).map(s->s.getKey()).collect(Collectors.toSet());
        egyesek.remove("start");
        egyesek.remove("end");
        egyesek1.addAll(egyesek);
        ujPalya[0] = "start";
       boolean already1 = false;
        calculateRecursive(startValue, steps.get(startValue), ujPalya, already1);


        System.out.println("Sum: " + counter.stream().mapToInt(s->s).count());
    }

    private void calculateRecursive(String origin, Set<String> nextSteps, String[] palyak, boolean already) {
        if(origin.equals("end")){

            print(palyak);
            counter.add(1);
            return;
        }
        for (String start : nextSteps) {
            if(start.equals("start")){
                continue;
            }
            if(start.equals("end")){
                print(palyak);
                counter.add(1);
                continue;
            }
            // visszafele
            if (Arrays.asList(palyak).contains(start)) {
                int count = (int) Arrays.asList(palyak).stream().filter(s -> s.equals(start)).count();
                int occurence = stepsNumber.get(start);
                if (occurence <= count && occurence == 1) {
                    boolean alredy = false;
                    for(String check : egyesek1){
                        int occurence2 = (int) Arrays.asList(palyak).stream().filter(s -> s.equals(check)).count();
                        if(occurence2>1){
                            alredy = true;
                            break;
                        }
                    }
                    if(alredy){
                        continue;
                    }
                } else if(occurence <= count && occurence == 2){

                }
            }
            String[] ujArray = new String[palyak.length+1];
            for(int i=0; i<palyak.length; i++){
                ujArray[i] = palyak[i];
            }
            ujArray[palyak.length] = start;
            Set<String> nexts = steps.get(start);
            if (nexts != null) {
                calculateRecursive(start, nexts, ujArray, already);
            }
        }
    }

    private void print(String[] palyak) {
        System.out.println("-----------------");
        for(int i=0; i<palyak.length; i++){
            System.out.print(palyak[i]+", ");
        }
        System.out.print("end");
        System.out.println(" ");
    }

    private void calculateStepNumber(String first) {
        if (first.equals(first.toUpperCase())) {
            stepsNumber.put(first, 2);
        } else {
            stepsNumber.put(first, 1);
        }
    }

    private void saveDirection(String first, String second) {
        if (steps.containsKey(second)) {
            Set<String> orbitsNew = steps.get(second);
            orbitsNew.add(first);
            steps.put(second, orbitsNew);
        } else {
            Set<String> orbitsNew = new HashSet<>();
            orbitsNew.add(first);
            steps.put(second, orbitsNew);
        }
    }
}
