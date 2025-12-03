package com.fmolnar.code.year2025.day03;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day03Challenge2025Beauty {

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day03/input.txt");

        long sum = lines.stream()
                .map(String::toCharArray)
                .map(this::convertToIntArray)
                .mapToLong(values -> getMaxJoltage(values, 12))
                .sum();
        System.out.println(sum);
        ;
    }

    private int[] convertToIntArray(char[] chars) {
        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            ints[i] = chars[i] - '0';
        }
        return ints;
    }

    private long getMaxJoltage(int[] digits, int goalLength) {
        int removalRemaining = digits.length - goalLength;
        List<Integer> list = new ArrayList<>();

        for (int actualDigit : digits) {
            while (!list.isEmpty() && removalRemaining > 0 && list.getLast() < actualDigit) {
                list.removeLast();
                removalRemaining--;
            }
            list.addLast(actualDigit);
        }

        IntStream.range(0, Math.max(list.size() - goalLength, 0)).forEach(value -> list.removeLast());

        return Long.parseLong(list.stream().map(String::valueOf).collect(Collectors.joining()));
    }
}
