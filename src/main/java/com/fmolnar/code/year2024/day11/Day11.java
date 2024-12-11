package com.fmolnar.code.year2024.day11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {
    public void calculateDay11_2024() throws IOException {

        String input = "125 17";

        Map<Long, Long> frequences = new HashMap<>();
        for (String actual : Arrays.asList(input.trim().split(" "))) {
            frequences.compute(Long.valueOf(actual), (key, value) -> value == null ? 1 : value + 1);
        }

        Map<Long, List<Long>> cache = new HashMap<>();

        for (int i = 0; i < 75; i++) {

            Map<Long, Long> newFrequences = new HashMap<>();
            for (Map.Entry<Long, Long> entry : frequences.entrySet()) {
                Long numberActual = entry.getKey();
                String actualValueInString = String.valueOf(numberActual);
                Long numberOccurrences = entry.getValue();

                if (cache.containsKey(numberActual)) {
                    for (Long cachedNumber : cache.get(numberActual)) {
                        newFrequences.compute(cachedNumber, (key, value) -> value == null ? numberOccurrences : value + numberOccurrences);
                    }
                } else {
                    if (numberActual.equals(0l)) {
                        firstCase(newFrequences, numberOccurrences, cache, numberActual);
                    } else if (actualValueInString.length() % 2 == 0) {
                        secondCase(actualValueInString, cache, numberActual, newFrequences, numberOccurrences);
                    } else {
                        resteCase(numberActual, cache, newFrequences, numberOccurrences);
                    }
                }

            }
            frequences = new HashMap<>(newFrequences);
            if (i == 24) {
                System.out.println("First: " + frequences.values().stream().reduce(0L, Long::sum));
            }

        }

        System.out.println("Second: " + frequences.values().stream().reduce(0L, Long::sum));
    }

    private static void resteCase(Long numberActual, Map<Long, List<Long>> cache, Map<Long, Long> newFrequences, Long numberOccurrences) {
        Long newValue = numberActual * 2024l;
        cache.put(numberActual, List.of(newValue));
        newFrequences.compute(newValue, (key, value) -> value == null ? numberOccurrences : value + numberOccurrences);
    }

    private static void secondCase(String actualValueInString, Map<Long, List<Long>> cache, Long numberActual, Map<Long, Long> newFrequences, Long numberOccurrences) {
        List<Long> newNumbers = new ArrayList<>();
        Long firstValue = Long.valueOf(actualValueInString.substring(0, actualValueInString.length() / 2));
        newNumbers.add(firstValue);
        Long secondValue = Long.valueOf(actualValueInString.substring(actualValueInString.length() / 2));
        newNumbers.add(secondValue);
        cache.put(numberActual, newNumbers);
        newFrequences.compute(firstValue, (key, value) -> value == null ? numberOccurrences : value + numberOccurrences);
        newFrequences.compute(secondValue, (key, value) -> value == null ? numberOccurrences : value + numberOccurrences);
    }

    private static void firstCase(Map<Long, Long> newFrequences, Long numberOccurrences, Map<Long, List<Long>> cache, Long numberActual) {
        newFrequences.compute(1l, (key, value) -> value == null ? numberOccurrences : value + numberOccurrences);
        cache.put(numberActual, List.of(1l));
    }
}
