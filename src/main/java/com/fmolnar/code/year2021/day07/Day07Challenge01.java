package com.fmolnar.code.year2021.day07;

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

public class Day07Challenge01 {

    private List<String> lines = new ArrayList<>();
    Map<Integer, Integer> positions = new HashMap<>();
    Map<Integer, Integer> sums =new HashMap<>();
    Set<Integer> values = new HashSet<>();
    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day07/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    String[] args = line.split(",");
                    for (int i = 0; i < args.length; i++) {
                        Integer number = Integer.valueOf(args[i]);
                        values.add(number);
                        if (positions.get(number) == null) {
                            positions.put(number, 1);
                        } else {
                            Integer occurence = positions.get(number);
                            positions.put(number, ++occurence);
                        }
                    }
                }
            }
        }

        int max = values.stream().mapToInt(s->s).max().getAsInt();
        int min = values.stream().mapToInt(s->s).min().getAsInt();

        for(int i=min; i<=max; i++){
            calculateTofind(i);
        }


        System.out.println("Min Steps: " + sums.entrySet().stream().mapToInt(s->s.getValue()).min().getAsInt());
    }

    private void calculateTofind(int i) {
        int sum = 0;
        for(Map.Entry<Integer, Integer> position: positions.entrySet()){
            sum += Math.abs(i-position.getKey())*position.getValue();
        }
        sums.put(i, sum);

    }
}
