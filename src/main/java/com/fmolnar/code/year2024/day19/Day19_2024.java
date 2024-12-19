package com.fmolnar.code.year2024.day19;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day19_2024 {
    public void calulateday192024() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day19/input.txt");

        Set<String> towels = new HashSet<>();
        long ways = 0l;
        int counter = 0;
        for (int index = 0; index < lines.size(); index++) {
            String patterns = lines.get(index);
            if (index == 0) {
                String[] words = patterns.split(",");
                for (String word : words) {
                    towels.add(word.trim());
                }
            }

            if (1 < index) {
                long way = canBeDone(patterns, towels);
                if (0 < way) {
                    counter++;
                }
                ways += way;
            }
        }
        System.out.println("First: " + counter);
        System.out.println("Second:  " + ways);
    }

    private long canBeDone(String word, Set<String> towels) {
        Map<String, Long> cache = new HashMap<>();
        long ways = 0l;
        for (String towel : towels) {
            if (word.startsWith(towel)) {
                String substring = word.substring(towel.length());
                ways += doRecursive(substring, towels, cache);
            }
        }
        return ways;
    }

    private long doRecursive(String word, Set<String> towels, Map<String, Long> cache) {

        if (word.length() == 0) {
            return 1l;
        }

        if (cache.containsKey(word)) {
            return cache.get(word);
        }

        long ways = 0l;
        for (String towel : towels) {
            if (word.startsWith(towel)) {
                String newWord = word.substring(towel.length());
                ways += doRecursive(newWord, towels, cache);
            }
        }
        cache.put(word, ways);
        return ways;
    }
}
