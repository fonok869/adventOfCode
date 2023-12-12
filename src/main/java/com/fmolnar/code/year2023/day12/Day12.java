package com.fmolnar.code.year2023.day12;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day12 {
    public static void main(String[] args) throws IOException {
        calculate();
    }


    public static void calculate() throws IOException {

        List<String> lines = FileReaderUtils.readFile("/2023/day12/input.txt");
        List<Integer> counterSzamok = new ArrayList<>();

        for (String line : lines) {
            String firstPart = line.substring(0, line.indexOf(' '));
            String secondPart = line.substring(line.indexOf(' ') + 1);
            List<Integer> kerdojelPosition = new ArrayList<>();
            int counterkerdojel = countKerdojel(firstPart, kerdojelPosition);
            String[] numbers = secondPart.split(",");
            List<String> strings = Arrays.asList(secondPart.split(","));


            List<String> allOptions = new ArrayList<>();
            allOptions.add(firstPart);
            List<String> newOptions = new ArrayList<>();
            for (Integer kerdojelIndex : kerdojelPosition) {
                for (String actualFirstPart : allOptions) {
                    // #
                    String actDiez = transformJel(kerdojelIndex, actualFirstPart, '#');
                    newOptions.add(actDiez);
                    // .
                    String actPoint = transformJel(kerdojelIndex, actualFirstPart, '.');
                    ;
                    newOptions.add(actPoint);
                }
                allOptions.clear();
                allOptions.addAll(newOptions);
                newOptions.clear();
            }

            allOptions.forEach(option -> {
                String solution = new Config(option).calculateDiez();
                if(solution.equals(secondPart)){
                    counterSzamok.add(1);
                }
            });

        }

        System.out.println("Sum: " + counterSzamok.size());
    }

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

            if(toReturn.equals("")){
                return toReturn;
            }
            return toReturn.substring(0, toReturn.length()-1);
        }
    }

    ;
}
