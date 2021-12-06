package com.fmolnar.code.year2021.day06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06Challenge01 {

    List<Integer> fishes = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day06/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    String[] args = line.split(",");
                    for(int i=0; i<args.length; i++){
                        fishes.add(Integer.valueOf(args[i]));
                    }
                }
            }
        }

        List<Integer> population = fishes;
        for(int i=1; i<=80; i++){
            population = reproduction(population);
        }

        System.out.println("Sum: " + population.stream().mapToInt(s->s).count());
    }

    private List<Integer> reproduction(List<Integer> population) {
        List<Integer> newPopulation = new ArrayList<>();
        for(Integer fish : population){
            if(fish == 0){
                newPopulation.add(6);
                newPopulation.add(8);
            }
            else {
                newPopulation.add(fish-1);
            }
        }
        return newPopulation;
    }
}
