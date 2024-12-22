package com.fmolnar.code.year2024.day22;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day22_2024 {
    public void calculateDay22_2024() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day22/input.txt");

        long sum = 0;
        List<Map<String, Integer>> allMAxes = new ArrayList<>();
        for (String line : lines) {
            long actualValue = Long.valueOf(line);
            Map<String, Integer> maxes = new HashMap<>();
            sum += call2000times(actualValue, maxes);
            allMAxes.add(maxes);
        }

        System.out.println("First: " + sum);
        List<Integer> integs = new ArrayList<>();

        Set<String> allClees = allMAxes.stream().map(map -> map.keySet()).flatMap(Collection::stream).collect(Collectors.toSet());

        Map<Integer, Set<String>> map = new HashMap<>();
        for (String cle : allClees) {
            int sumCle = 0;
            for (Map<String, Integer> maxes : allMAxes) {
                if (maxes.containsKey(cle)) {
                    sumCle += maxes.get(cle);
                }
            }
            // First Seen values
            if (!map.containsKey(sumCle)) {
                map.put(sumCle, new HashSet<>());
            }
            map.get(sumCle).add(cle);
            integs.add(sumCle);
        }


        int max = integs.stream().mapToInt(s -> s).max().getAsInt();
        System.out.println("Cle: " + map.get(max));
        System.out.println("Second: " + max);
    }

    private long call2000times(long secretNumber, Map<String, Integer> maxes) {

        List<Integer> sequences = new ArrayList<>();
        long initDigit = getOneDigit(secretNumber);
        for (int i = 0; i < 2000; i++) {
            long result = secretNumber * 64l;

            secretNumber = mix(secretNumber, result);

            secretNumber = prune(secretNumber);


            long result2 = secretNumber / 32l;

            secretNumber = mix(result2, secretNumber);

            secretNumber = prune(secretNumber);


            long result3 = secretNumber * 2048l;
            secretNumber = mix(result3, secretNumber);
            secretNumber = prune(secretNumber);
            long secondDigit = getOneDigit(secretNumber);
            sequences.add(Integer.valueOf((int) (secondDigit - initDigit)));
            if (3 <= i) {
                String cle = String.valueOf(sequences.get(i - 3)) + "," + String.valueOf(sequences.get(i - 2)) + "," + String.valueOf(sequences.get(i - 1)) + "," + String.valueOf(sequences.get(i));
                if (!maxes.containsKey(cle)) {
                    maxes.put(cle, (int) secondDigit);
                }
            }
            initDigit = secondDigit;
        }
        //System.out.println(secretNumber);

        return secretNumber;
    }

    private static long getOneDigit(long secretNumber) {
        return secretNumber % 10l;
    }

    public static long mix(long secretNumber, long mixer) {
        return secretNumber ^ mixer;
    }

    public static long prune(long second) {
        return second % 16777216l;
    }
}
