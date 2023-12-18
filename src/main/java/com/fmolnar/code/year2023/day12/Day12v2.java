package com.fmolnar.code.year2023.day12;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12v2 {


    public List<Integer> calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day12/input.txt");
        List<Integer> eredmenyek = new ArrayList<>();
        long sum = calculateFor1(lines, eredmenyek);
        System.out.println("Sum: " + sum);
        return eredmenyek;
    }

    public long calculateFor1(List<String> lines, List<Integer> eredmenyek) {

        List<Short> counterSzamok = new ArrayList<>();
        int i = 1;
        for (String line : lines) {
            String firstPart = line.substring(0, line.indexOf(' ')) + ".";
            String secondPart = line.substring(line.indexOf(' ') + 1);
            List<Integer> kerdojelPosition = new ArrayList<>();
            int counterkerdojel = countKerdojel(firstPart, kerdojelPosition);
            List<Integer> numbers = Arrays.stream(secondPart.split(",")).mapToInt(s -> Integer.valueOf(s)).boxed().toList();
            List<GroupSearchFor> groups = IntStream.range(0, numbers.size()).mapToObj(index -> new GroupSearchFor(index, numbers.get(index))).toList();
            List<String> allOptions = new ArrayList<>();
            allOptions.add(firstPart);

            solutionFinder(0, groups.get(0), 0, firstPart, groups, counterSzamok);

            //System.out.println("i: " + i++ + " " + counterSzamok.size());
            eredmenyek.add(counterSzamok.size());
        }
        return counterSzamok.size();
    }

    private static void solutionFinder(int index, GroupSearchFor groupSearchFor, int actualLength, String firstPart, List<GroupSearchFor> groups, List<Short> counter) {

        if (firstPart.length() <= index) {
            // .  miatt ok
            return;
        }
        Character charActual = firstPart.charAt(index);
        if ('.' == charActual) {
            // TODO beginning
            pointTreat(index, groupSearchFor, actualLength, firstPart, groups, counter);
        } else if ('#' == charActual) {
            hashtagTreat(index, groupSearchFor, actualLength, firstPart, groups, counter);
        } else if ('?' == charActual) {
            // TODO beginnin
            pointTreat(index, groupSearchFor, actualLength, firstPart, groups, counter);
            hashtagTreat(index, groupSearchFor, actualLength, firstPart, groups, counter);
        }
    }

    private static void hashtagTreat(int index, GroupSearchFor groupSearchFor, int actualLength, String firstPart, List<GroupSearchFor> groups, List<Short> counter) {
        solutionFinder(index + 1, groupSearchFor, actualLength + 1, firstPart, groups, counter);
    }

    private static void pointTreat(int index, GroupSearchFor groupSearchFor, int actualLength, String firstPart, List<GroupSearchFor> groups, List<Short> counter) {
        // Beginning
        if (actualLength == 0 && index < firstPart.indexOf('#')) {
            solutionFinder(index + 1, groupSearchFor, actualLength, firstPart, groups, counter);
        } else if (actualLength == groupSearchFor.value()) {
            if (firstPart.length() <= index + 1 && groupSearchFor.index() + 1 == groups.size()) {
                counter.add((short) 1);
                return;
            }
            if (Math.max(firstPart.lastIndexOf('#'), firstPart.lastIndexOf('?')) <= (index + 1)) {
                if (groupSearchFor.index() + 1 == groups.size()) {
                    counter.add((short) 1);
                    return;
                }
            }
            if (groupSearchFor.index() + 1 == groups.size()) {
                if (IntStream.range(index, firstPart.length()).allMatch(indexToCheck -> Arrays.asList('.', '?').contains(firstPart.charAt(indexToCheck)))) {
                    counter.add((short) 1);
                    return;
                }
                return;
            }
            GroupSearchFor nextGroup = groups.get(groupSearchFor.index() + 1);
            solutionFinder(index + 1, nextGroup, 0, firstPart, groups, counter);
        } else if (actualLength == 0) {
            solutionFinder(index + 1, groupSearchFor, actualLength, firstPart, groups, counter);
        }
    }

    record GroupSearchFor(int index, int value) {
    }

    ;

    private static String transformJel(Integer kerdojelIndex, String actualFirstPart, char c) {
        if (kerdojelIndex == 0) {
            return c + actualFirstPart.substring(1);
        } else if (kerdojelIndex == actualFirstPart.length() - 1) {
            return actualFirstPart.substring(0, kerdojelIndex) + c;
        } else {
            return actualFirstPart.substring(0, kerdojelIndex) + c + actualFirstPart.substring(kerdojelIndex + 1);
        }
    }

    private static int countKerdojel(String firstPart, List<Integer> kerdojelPoisition) {
        int counterkerdojel = 0;
        for (int i = 0; i < firstPart.length(); i++) {
            if (firstPart.charAt(i) == '?') {
                kerdojelPoisition.add(i);
                counterkerdojel++;
            }
        }
        return counterkerdojel;
    }

    record Config(String line) {
        String calculateDiez() {
            int counter = 0;
            List<Integer> islands = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '#') {
                    counter++;
                    if (i == line.length() - 1) {
                        islands.add(counter);
                    }
                } else {
                    if (counter != 0) {
                        islands.add(counter);
                    }
                    counter = 0;
                }
            }
            String toReturn = "";
            for (Integer island : islands) {
                toReturn = toReturn + island + ",";
            }

            if (toReturn.equals("")) {
                return toReturn;
            }
            return toReturn.substring(0, toReturn.length() - 1);
        }
    }

    ;
}
