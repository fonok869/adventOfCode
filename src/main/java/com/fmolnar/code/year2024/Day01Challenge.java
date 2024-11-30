package com.fmolnar.code.year2024;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.List;

public class Day01Challenge {


    public void calculate() throws IOException {

        List<String> lines = FileReaderUtils.readFile("/2024/day01/input.txt");

        for (String line : lines) {
            System.out.println(line);
        }

    }
}
