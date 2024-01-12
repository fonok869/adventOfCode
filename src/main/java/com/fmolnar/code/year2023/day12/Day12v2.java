package com.fmolnar.code.year2023.day12;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day12v2 {


    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day12/input.txt");
        long sum = calculateFor1(lines, false);
        System.out.println("Sum: " + sum);
    }

    public long calculateFor1(List<String> lines, boolean first) {
        long startDate = new Date().getTime();
        List<Long> szummak = new ArrayList<>();
        for (String line : lines) {
            String firstPart = calculateStringFromExercise(line.substring(0, line.indexOf(' ')), first, "?");
            firstPart = firstPart + ".";
            String secondPart = calculateStringFromExercise(line.substring(line.indexOf(' ') + 1), first, ",");
            String[] bitesString = secondPart.split(",");

            byte[] groups = new byte[bitesString.length];
            for (int k = 0; k < bitesString.length; k++) {
                groups[k] = Byte.valueOf(bitesString[k]);
            }

            int sumAllLine = Stream.of(secondPart.split(",")).mapToInt(Integer::parseInt).sum();
            Map<State, Long> cachedValues = new HashMap<>();
            szummak.add(solutionFinder(0, 0, 0, firstPart, groups, (byte) (sumAllLine + (groups.length - 1)), cachedValues));
        }
        long endDate = new Date().getTime();
        System.out.println("Time: " + ((endDate - startDate)));
        return szummak.stream().mapToLong(s -> s).sum();
    }

    private String calculateStringFromExercise(String beforePart, boolean first, String splitter) {
        String beforePartShort = beforePart;
        for (int i = 0; i < (first ? 0 : 4); i++) {
            beforePartShort = beforePartShort + splitter + beforePart;
        }
        return beforePartShort;
    }

    private long solutionFinder(int index, int groupIndex, int actualLength, String firstPart, byte[] groups, byte sumUntilNow, Map<State, Long> cachedValues) {

        State newState = new State(index, groupIndex, actualLength);
        if (cachedValues.containsKey(newState)) {
            return cachedValues.get(newState);
        }

        if (firstPart.length() <= index) {
            // .  miatt ok
            return 0;
        }

        // Melyen van akkor vege
        if (actualLength == 0 && ((firstPart.length() - (index + 1)) < sumUntilNow)) {
            return 0;
        }

        long valueToReturn = 0l;
        Character charActual = firstPart.charAt(index);
        if ('.' == charActual) {
            valueToReturn = pointTreat(index, groupIndex, actualLength, firstPart, groups, sumUntilNow, cachedValues);
        } else if ('#' == charActual) {
            valueToReturn = hashtagTreat(index, groupIndex, actualLength, firstPart, groups, sumUntilNow, cachedValues);
        } else if ('?' == charActual) {
            valueToReturn += pointTreat(index, groupIndex, actualLength, firstPart, groups, sumUntilNow, cachedValues);
            valueToReturn += hashtagTreat(index, groupIndex, actualLength, firstPart, groups, sumUntilNow, cachedValues);
        } else {
            valueToReturn = 0;
        }
        cachedValues.put(newState, valueToReturn);
        return valueToReturn;
    }

    private long hashtagTreat(int index, int groupIndex, int actualLength, String firstPart, byte[] groups, byte sumUntilNow, Map<State, Long> cachedValues) {
        if (groups[groupIndex] < (actualLength + 1)) {
            return 0;
        }
        return solutionFinder(index + 1, groupIndex, actualLength + 1, firstPart, groups, sumUntilNow, cachedValues);
    }

    private long pointTreat(int index, int groupIndex, int actualLength, String firstPart, byte[] groups, byte sumUntilNow, Map<State, Long> cachedValues) {
        // Vagy pont van elotte Vagy kezdodik
        if (actualLength == 0) {
            return solutionFinder(index + 1, groupIndex, actualLength, firstPart, groups, sumUntilNow, cachedValues);
        } else if (actualLength == groups[groupIndex]) {

            if ((groupIndex + 1) == groups.length) {
                // Vagy Group kesz --> utana csak . es ? lehet
                if (IntStream.range(index, firstPart.length()).allMatch(indexToCheck -> Arrays.asList('?', '.').contains(firstPart.charAt(indexToCheck)))) {
                    return 1l;
                }
            }
            // Vagy Group es next group
            if ((groupIndex + 1) < groups.length) {
                return solutionFinder(index + 1, groupIndex + 1, 0, firstPart, groups, (byte) (sumUntilNow - groups[groupIndex] - 1), cachedValues);
            }
            return 0;
        } else {
            return 0;
        }
    }

    record State(int index, int groupIndex, int actualLength) {
    }

}
