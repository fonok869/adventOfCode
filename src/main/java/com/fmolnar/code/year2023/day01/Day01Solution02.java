package com.fmolnar.code.year2023.day01;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01Solution02 {

    public static Map<String, Integer> mapper = new HashMap<>();

    public static void calculate() throws IOException {
        initMapper();
        List<String> lines = FileReaderUtils.readFile("/2023/day01/input.txt");

        List<Couple> couples = new ArrayList<>();
        for (String line : lines) {
            Couple couple = new Couple(0, 0);
            for (int cursor = 0; cursor < line.length(); cursor++) {
                String remain = line.substring(cursor);
                if (Character.isDigit(remain.charAt(0))) {
                    couple = couple.addNumber(Integer.valueOf(remain.substring(0, 1)));
                } else {
                    for (Map.Entry<String, Integer> entry : mapper.entrySet()) {
                        if (remain.startsWith(entry.getKey())) {
                            couple = couple.addNumber(entry.getValue());
                        }
                    }
                }
            }
            couples.add(couple);
        }

        Integer sum = couples.stream().map(Couple::sumUp).reduce(0, Integer::sum);
        System.out.println("Sum : " + sum);
    }

    public record Couple(int first, int second) {
        Couple addNumber(int number) {
            if (first == 0) {
                return new Couple(number, 0);
            } else {
                return new Couple(first, number);
            }
        }

        int sumUp() {
            return second != 0 ? first * 10 + second : first * 10 + first;
        }
    }

    private static void initMapper() {
        mapper.put("one", 1);
        mapper.put("zero", 0);
        mapper.put("two", 2);
        mapper.put("three", 3);
        mapper.put("four", 4);
        mapper.put("five", 5);
        mapper.put("six", 6);
        mapper.put("seven", 7);
        mapper.put("eight", 8);
        mapper.put("nine", 9);
    }
}
