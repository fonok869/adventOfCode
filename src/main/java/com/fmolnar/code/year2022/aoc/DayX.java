package com.fmolnar.code.year2022.aoc;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.List;

public class DayX {

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day04/input.txt");

        for(String line : lines){
            System.out.println("Lines: " + line);
        }

        System.out.println("Totot");
    }
}
