package com.fmolnar.code.year2024.day01;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day01Challenge {

    public void calculate() throws IOException {

        List<String> lines = FileReaderUtils.readFile("/2024/day01/input.txt");
        List<Integer> lefts = new ArrayList<>();
        List<Integer> rights = new ArrayList<>();

        for (String line : lines) {
            String[] numbers = line.split("   ");
            lefts.add(Integer.parseInt(numbers[0]));
            rights.add(Integer.parseInt(numbers[1]));
        }

        Collections.sort(lefts);
        Collections.sort(rights);
        System.out.println("First: " + IntStream.range(0, lefts.size()).map(index -> Math.abs(lefts.get(index) - rights.get(index))).sum());

        Map<Integer, Integer> histogram = new HashMap<>();
        rights.forEach(rightValue -> histogram.compute(rightValue, (key, value) -> value == null ? 1 : value + 1));

        System.out.println("Second: " + lefts.stream().mapToInt(leftValue -> leftValue * histogram.getOrDefault(leftValue, 0)).sum());

    }
}
