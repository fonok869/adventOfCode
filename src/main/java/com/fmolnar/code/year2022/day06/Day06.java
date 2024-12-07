package com.fmolnar.code.year2022.day06;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Day06 {

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day06/input.txt");
        int a = 0;
        int b = 0;
        boolean sameA = true;
        boolean sameB = true;
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {

                if (sameA) {
                    sameA = isSame(line, i, 4);
                    if (!sameA) {
                        a = i + 4;
                    }
                }

                if (sameB) {
                    sameB = isSame(line, i, 14);
                    if (!sameA) {
                        b = i + 14;
                    }
                }

                if (!sameA && !sameB) {
                    break;
                }


            }
        }


        System.out.println("First: " + a);
        System.out.println("Second: " + b);
    }

    public boolean isSame(String line, int i, int maxDistinct) {

        for (int j = i; j < i + maxDistinct; j++) {
            for (int k = j + 1; k < i + maxDistinct; k++) {
                if (Objects.equals(line.charAt(j), line.charAt(k))) {
                    return true;
                }
            }
        }
        return false;
    }
}
