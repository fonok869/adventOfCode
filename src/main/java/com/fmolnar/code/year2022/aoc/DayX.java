package com.fmolnar.code.year2022.aoc;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayX {

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day04/input.txt");

        for(String line : lines){
            System.out.println("Lines: " + line);
        }

        System.out.println("Totot");
    }
}
