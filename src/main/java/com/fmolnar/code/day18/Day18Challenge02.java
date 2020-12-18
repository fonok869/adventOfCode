package com.fmolnar.code.day18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18Challenge02 {

    public Day18Challenge02() {
    }

    List<Long> sumByLine = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day18/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    sumByLine.add(countLine(removeParenthesis(line)));
                }
            }
        }
        System.out.println("Sum : " + sumByLine.stream().mapToLong(s -> s).sum());


    }

    private String removeParenthesis(String line) {
        List<Integer> parenthesisLeft = new ArrayList<>();
        List<Integer> parenthesisRiight = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                parenthesisLeft.add(i);
            } else if (line.charAt(i) == ')') {
                parenthesisRiight.add(i);
            }
        }
        return selectHubs(line, parenthesisLeft, parenthesisRiight);
    }

    private String selectHubs(String line, List<Integer> parenthesisLeft, List<Integer> parenthesisRight) {
        if (parenthesisLeft.size() == 0) {
            return line;
        }
        if (parenthesisLeft.size() == 1) {
            int left = parenthesisLeft.get(0);
            int right = parenthesisRight.get(0);
            long value = countLine(line.substring(left + 1, right));
            return line.substring(0, left) + value + line.substring(right + 1);
        }
        if (parenthesisLeft.size() > 1) {
            int right = parenthesisRight.get(0);
            int mostLeft = parenthesisLeft.stream().filter(s -> (s < right)).mapToInt(s -> s).max().getAsInt();
            List<Integer> mostLeftList = Arrays.asList(mostLeft);
            List<Integer> mostRightList = Arrays.asList(right);
            return removeParenthesis(selectHubs(line, mostLeftList, mostRightList));
        }
        return "";
    }

    String patternSeat = "(^(\\d+)\\s(\\+|\\*)\\s(\\d+)).*$";
    Pattern pattern = Pattern.compile(patternSeat);

    String osszeadas = "((\\d+)\\s(\\+)\\s(\\d+))";
    Pattern patternOsszeadas = Pattern.compile(osszeadas);

    String patternNumbze = "(^(\\d+)$)";
    Pattern number = Pattern.compile(patternNumbze);

    long countLine(String lineToCount) {
        while (true) {

            if (number.matcher(lineToCount).find()) {
                return Long.valueOf(lineToCount);
            }

            Matcher matchOsszeadas = patternOsszeadas.matcher(lineToCount);
            if (matchOsszeadas.find()) {
                long sumTemp = Long.valueOf(matchOsszeadas.group(2)) + Long.valueOf(matchOsszeadas.group(4));
                int firstIndex = lineToCount.indexOf(matchOsszeadas.group(1));
                if (firstIndex <= 1) {
                    lineToCount = sumTemp + lineToCount.substring(firstIndex + matchOsszeadas.group(1).length());
                } else {
                    lineToCount = lineToCount.substring(0, firstIndex) + sumTemp + lineToCount.substring(firstIndex + matchOsszeadas.group(1).length());
                }
                continue;
            } else {
                Matcher match = pattern.matcher(lineToCount);
                if (match.find()) {
                    long sumTemp = Long.valueOf(match.group(2)) * Long.valueOf(match.group(4));
                    int firstIndex = lineToCount.indexOf(match.group(1));
                    if (lineToCount.length() - 1 <= firstIndex + match.group(1).length()) {
                        return sumTemp;
                    }
                    lineToCount = sumTemp + lineToCount.substring(firstIndex + match.group(1).length());
                }
            }
        }
    }
}
