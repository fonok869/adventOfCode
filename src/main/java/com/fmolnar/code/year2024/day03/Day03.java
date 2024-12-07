package com.fmolnar.code.year2024.day03;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    public void calculateDay03() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day03/input.txt");

        String pattern = "(^mul\\((\\d{1,3}),(\\d{1,3})\\))";
        Pattern matcher = Pattern.compile(pattern);

        long sum1 = 0;
        long sum2 = 0;
        boolean doing = true;
        sum1 = getAllLines(lines, doing, sum1, matcher, true);
        sum2 = getAllLines(lines, doing, sum2, matcher, false);
        System.out.println("First : " + sum1);
        System.out.println("Second : " + sum2);
    }

    private static long getAllLines(List<String> lines, boolean doing, long sum, Pattern matcher, boolean firstExercice) {
        for (String line : lines) {
            for (int index = 0; index < line.length(); index++) {
                String newLine = line.substring(index);
                doing = shouldCheckDoing(newLine, doing, firstExercice);
                if (doing) {
                    sum = getSum(matcher, newLine, sum);
                }
            }
        }
        return sum;
    }

    private static boolean shouldCheckDoing(String newLine, boolean doing, boolean firstExercice) {
        if (firstExercice) {
            return true;
        }
        if (newLine.startsWith("don't()")) {
            doing = false;
        } else if (newLine.startsWith("do()")) {
            doing = true;
        }
        return doing;
    }

    private static long getSum(Pattern matcher, String newLine, long sum) {
        Matcher match = matcher.matcher(newLine);
        if (match.find()) {
            sum += Long.valueOf(match.group(2)) * Long.valueOf(match.group(3));
        }
        return sum;
    }
}
