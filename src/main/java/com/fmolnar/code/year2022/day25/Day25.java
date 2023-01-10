package com.fmolnar.code.year2022.day25;

import com.fmolnar.code.FileReaderUtils;
import com.fmolnar.code.year2022.aoc.NumberUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day25 {

    Map<String, Long> dict = new HashMap<>();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day25/input.txt");

        dict.put("2", 2l);
        dict.put("1", 1l);
        dict.put("0", 0l);
        dict.put("-", -1l);
        dict.put("=", -2l);
        List<Long> sums = new ArrayList<>();

        for (String line : lines) {
            long szam = 0;
            long init = 1;
            for (int j = line.length() - 1; 0 <= j; j--) {
                String letter = "";
                if (j == line.length() - 1) {
                    letter = line.substring(j);
                } else {
                    letter = line.substring(j, j + 1);
                }
                szam += init * dict.get(letter);
                init *= 5;
            }
            sums.add(szam);
        }

        long sumTotal = sums.stream().mapToLong(s -> s).sum();
        System.out.println("Result (1): " + transformBase5(NumberUtils.printInBase(sumTotal, 5)));
    }


    public String transformBase5(String input) {
        Map<Long, String> unDict = new HashMap<>();
        unDict.put(2l, "2");
        unDict.put(1l, "1");
        unDict.put(0l, "0");
        unDict.put(-1l, "-");
        unDict.put(-2l, "=");

        String newNumber = "";
        int rest = 0;
        for (int i = input.length() - 1; 0 <= i; i--) {
            String value = input.substring(i, i + 1);
            int intValue = Integer.valueOf(value) + rest;
            String valueToPut = "";
            if (intValue < 3) {
                valueToPut = unDict.get(Long.valueOf(intValue));
                rest = 0;
            } else {
                rest = 1;
                intValue -= 5;
                valueToPut = unDict.get(Long.valueOf(intValue));
            }
            newNumber = valueToPut + newNumber;

            // Check last element
            if (rest != 0 && i == 0) {
                newNumber = rest + newNumber;
            }
        }

        return newNumber;
    }
}
