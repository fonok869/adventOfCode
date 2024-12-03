package com.fmolnar.code.year2024.day02;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {

    public void calculate() throws IOException {

        List<String> lines = FileReaderUtils.readFile("/2024/day02/input.txt");

        int safe1 = 0;
        int safe2 = 0;

        for (String line : lines) {
            safe1 = getSafeTrajectories(line, safe1, true);
            safe2 = getSafeTrajectories(line, safe2, false);
        }
        System.out.println("Safe1: " + safe1);
        System.out.println("Safe2: " + safe2);
    }

    private static int getSafeTrajectories(String line, int safe, boolean firstPart) {
        String[] lineNumbers = line.split(" ");
        for (int index2 = -1; index2 < (firstPart ? 0 : lineNumbers.length); index2++) {

            List<String> strings = new ArrayList<>(Arrays.asList(lineNumbers));
            if (index2 != -1) {
                strings.remove(index2);
            }

            String[] numbersInALine = strings.toArray(new String[strings.size()]);
            int previous = Integer.valueOf(numbersInALine[0]);
            List<Integer> differences = new ArrayList<>();

            calculateDifferences(numbersInALine, differences, previous);

            if (isDecrasing(differences, numbersInALine)) {
                safe++;
                break;

            } else if (
                    isIncreasing(differences, numbersInALine)) {
                safe++;
                break;
            }
        }
        return safe;
    }

    private static void calculateDifferences(String[] numbersInALine, List<Integer> differences, int previous) {
        for (int index = 1; index < numbersInALine.length; index++) {
            int actuity = Integer.valueOf(numbersInALine[index]);
            differences.add(actuity - previous);
            previous = actuity;
        }
    }

    private static boolean isIncreasing(List<Integer> differences, String[] lineNumbers) {
        return differences.stream().mapToInt(s -> s).filter(s -> s < 0).count() == lineNumbers.length - 1 &&
                differences.stream().mapToInt(s -> s).filter(s -> s < 0).min().getAsInt() >= -3;
    }

    private static boolean isDecrasing(List<Integer> differences, String[] lineNumbers) {
        return differences.stream().mapToInt(s -> s).filter(s -> 0 < s).count() == lineNumbers.length - 1 &&
                differences.stream().mapToInt(s -> s).filter(s -> 0 < s).max().getAsInt() <= 3;
    }
}
